package com.sheniff.googleimagesearch;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Created by sheniff on 1/27/15.
 */
public class GoogleImages {
    private static String API = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";

    public static void query(String q, JsonHttpResponseHandler callback) {
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = API + "&q=" + q;
        client.get(searchUrl, callback);
    }
}
