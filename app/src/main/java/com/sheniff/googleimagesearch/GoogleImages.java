package com.sheniff.googleimagesearch;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sheniff.googleimagesearch.models.ImageSearchSettings;

/**
 * Created by sheniff on 1/27/15.
 */
public class GoogleImages {
    public static int PER_PAGE = 8;

    public static void query(Context context, String q, ImageSearchSettings searchSettings, int page, JsonHttpResponseHandler callback) {
        if (q.length() == 0) return;

        AsyncHttpClient client = new AsyncHttpClient();
        String API = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=" + PER_PAGE;
        String searchUrl = API + "&q=" + q;
        String setting;

        // apply settings
        if(searchSettings.getColor() > 0) {
            setting = context.getResources().getStringArray(R.array.image_colors)[searchSettings.getColor()].toLowerCase();
            searchUrl = searchUrl + "&imgcolor=" + setting;
        }
        if(searchSettings.getSize() > 0) {
            setting = context.getResources().getStringArray(R.array.image_sizes)[searchSettings.getSize()].toLowerCase();
            searchUrl = searchUrl + "&imgsz=" + setting;
        }
        if(searchSettings.getType() > 0) {
            setting = context.getResources().getStringArray(R.array.image_types)[searchSettings.getType()].toLowerCase();
            searchUrl = searchUrl + "&imgtype=" + setting;
        }
        if(searchSettings.getSite().length() > 0) {
            searchUrl = searchUrl + "&as_sitesearch=" + searchSettings.getSite();
        }

        // apply pagination
        if(page > 0) {
            int start = page * PER_PAGE;
            searchUrl = searchUrl + "&start=" + start;
        }

        client.get(searchUrl, callback);
    }
}
