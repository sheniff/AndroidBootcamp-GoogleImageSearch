package com.sheniff.googleimagesearch.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sheniff.googleimagesearch.R;
import com.sheniff.googleimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sheniff on 1/27/15.
 */
public class ImageResultsAdapter extends ArrayAdapter {
//    public static final int VIEW_TYPE_LOADING = 0;
//    public static final int VIEW_TYPE_ACTIVITY = 1;
    protected List images;
//    protected int listMaxSize = -1;


    public ImageResultsAdapter(Context context, List images) {
        super(context, R.layout.item_image_result, images);
        this.images = images;
    }

//    public void setListMaxSize (int listMaxSize) {
//        this.listMaxSize = listMaxSize;
//    }
//
//    @Override
//    public boolean isEnabled(int position) {
//        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return (position >= this.images.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? images.get(position) : null;
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position : -1;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getCount() {
//        return images.size() + 1;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        if(getItemViewType(position) == VIEW_TYPE_LOADING) {
//            return getFooterView(position, convertView, parent);
//        }

        ImageResult imageInfo = (ImageResult) getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).placeholder(R.drawable.ic_launcher).into(ivImage);

        return convertView;
    }

//    private View getFooterView(int position, View convertView, ViewGroup parent) {
//        Log.d("FOOTER VIEW", "End of list?" + Integer.toString(position) + " " + Integer.toString(listMaxSize));
//        if(position >= listMaxSize && listMaxSize > 0) {
//            TextView tvLastRow = new TextView(getContext());
//            tvLastRow.setHint("Reached the last row.");
//            tvLastRow.setGravity(Gravity.CENTER);
//            return tvLastRow;
//        }
//
//        View row = convertView;
//        if(row == null) {
//            row = LayoutInflater.from(getContext()).inflate(R.layout.progress, parent, false);
//        }
//
//        return row;
//    }
}
