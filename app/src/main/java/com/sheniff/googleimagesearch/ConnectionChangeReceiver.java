package com.sheniff.googleimagesearch;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by sheniff on 2/1/15.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    public static boolean iAmOnline = true;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo == null || !activeNetInfo.isConnectedOrConnecting()) {
            iAmOnline = false;
            // Toast.makeText(context, "Connection Lost. Please wait...", Toast.LENGTH_LONG).show();
        } else {
            iAmOnline = true;
            // Toast.makeText(context, "Connection is back!", Toast.LENGTH_LONG).show();
        }
    }
}
