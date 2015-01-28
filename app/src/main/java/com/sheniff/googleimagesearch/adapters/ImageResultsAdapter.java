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
    public ImageResultsAdapter(Context context, List images) {
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
}
