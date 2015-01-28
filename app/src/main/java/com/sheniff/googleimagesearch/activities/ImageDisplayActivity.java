package com.sheniff.googleimagesearch.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;

import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplayActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        getSupportActionBar().hide();
        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        Picasso.with(this).load(result.getImageUrl()).placeholder(R.drawable.ic_launcher).into(ivImageResult);
    }

}
