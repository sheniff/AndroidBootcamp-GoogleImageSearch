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
 * Image Result Adapter
 */
public class ImageResultsAdapter extends ArrayAdapter {

    // region Constants
    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;
    // endregion

    // region Variables
    protected List images;
    protected int listMaxSize = -1;
    // endregion

    public ImageResultsAdapter(Context context, List images) {
        super(context, R.layout.item_image_result, images);
        this.images = images;
    }

    public void setListMaxSize (int listMaxSize) {
        this.listMaxSize = listMaxSize;
    }

    @Override
    public boolean isEnabled(int position) {
        return getItemViewType(position) == VIEW_TYPE_ACTIVITY;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= this.images.size()) ? VIEW_TYPE_LOADING : VIEW_TYPE_ACTIVITY;
    }

    @Override
    public Object getItem(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? images.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return (getItemViewType(position) == VIEW_TYPE_ACTIVITY) ? position : -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return this.images.size() > 0 ? this.images.size() + 1 : this.images.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position) == VIEW_TYPE_LOADING) {
            return getFooterView(position, convertView, parent);
        }

        ImageResult imageInfo = (ImageResult) getItem(position);
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        ImageView ivImage = (ImageView) convertView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvResultUrl = (TextView) convertView.findViewById(R.id.tvResultUrl);
        TextView tvResultDimension = (TextView) convertView.findViewById(R.id.tvResultDimension);

        if(imageInfo.getTitle() != null) {
            tvTitle.setText(Html.fromHtml(imageInfo.getTitle()));
        }

        if(imageInfo.getVisibleUrl() != null) {
            tvResultUrl.setText(imageInfo.getVisibleUrl());
        }

        tvResultDimension.setText(imageInfo.getWidth() + "x" + imageInfo.getHeight());

        ivImage.setImageResource(0);
        Picasso.with(getContext()).load(imageInfo.getThumbUrl()).placeholder(R.drawable.ic_launcher).into(ivImage);

        return convertView;
    }

    private View getFooterView(int position, View convertView, ViewGroup parent) {
        if(position >= listMaxSize && listMaxSize > 0) {
            // do not show anything
            return new TextView(getContext());
        }

        View row = convertView;
        if(row == null && this.images.size() > 0) {
            row = LayoutInflater.from(getContext()).inflate(R.layout.progress, parent, false);
        }

        return row;
    }
}
