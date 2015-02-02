package com.sheniff.googleimagesearch.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sheniff on 1/27/15.
 */
public class ImageResult implements Serializable {
    private static final long serialVersionUID = -2893089570992474768L;
    private String imageUrl;
    private String thumbUrl;
    private String title;
    private String titleNoFormat;
    private String visibleUrl;
    private int width;
    private int height;

    public ImageResult(JSONObject json) {
        try {
            this.imageUrl = json.getString("unescapedUrl");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
            this.titleNoFormat = json.getString("titleNoFormatting");
            this.visibleUrl = json.getString("visibleUrl");
            this.width = json.getInt("width");
            this.height = json.getInt("height");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleNoFormat() {
        return titleNoFormat;
    }

    public String getVisibleUrl() {
        return visibleUrl;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
