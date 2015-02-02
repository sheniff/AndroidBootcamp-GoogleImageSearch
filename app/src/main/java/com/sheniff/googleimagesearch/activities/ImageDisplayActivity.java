package com.sheniff.googleimagesearch.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.TouchImageView;
import com.sheniff.googleimagesearch.models.ImageResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageDisplayActivity extends ActionBarActivity {

    // region Variables
    private android.support.v7.widget.ShareActionProvider miShareAction;
    private TouchImageView ivImageResult;
    private TextView tvZoomPerc;
    // endregion

    // region Listeners
    private TouchImageView.OnTouchImageViewListener touchImageViewListener = new TouchImageView.OnTouchImageViewListener() {
        @Override
        public void onMove() {
            updateZoom();
        }
    };
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        bindUIElements();
        setUpListeners();

        ivImageResult.setMinZoom(0.5f);
        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        getSupportActionBar().setTitle(result.getTitleNoFormat());

        Picasso.with(this).load(result.getImageUrl())
            //.fit() it distorts the image...
            .placeholder(R.drawable.ic_launcher)
            .error(R.drawable.crash)
            .into(ivImageResult, new Callback() {
                @Override
                public void onSuccess() {
                    setupShareIntent();
                    updateZoom();
                }

                @Override
                public void onError() {
                    Log.e("DETAIL ERROR", "Error fetching full image");
                }
            });
    }

    private void bindUIElements() {
        ivImageResult = (TouchImageView) findViewById(R.id.ivImageResult);
        tvZoomPerc = (TextView) findViewById(R.id.tvZoomPercentage);
    }

    private void setUpListeners() {
        ivImageResult.setOnTouchImageViewListener(touchImageViewListener);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        miShareAction = (android.support.v7.widget.ShareActionProvider) MenuItemCompat.getActionProvider(item);
        return true;
    }

    private void setupShareIntent() {
        ImageView ivImage = (ImageView) findViewById(R.id.ivImageResult);
        Uri bmpUri = getLocalBitmapUri(ivImage);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
        shareIntent.setType("image/*");
        // Attach share event to the menu item provider
        if(miShareAction != null) {
            miShareAction.setShareIntent(shareIntent);
        }
    }

    private void updateZoom() {
        tvZoomPerc.setText(Integer.toString((int) (ivImageResult.getCurrentZoom() * 100)) + "%");
    }

    private Uri getLocalBitmapUri(ImageView ivImage) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = ivImage.getDrawable();
        Bitmap bmp;

        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        } else {
            return null;
        }

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
