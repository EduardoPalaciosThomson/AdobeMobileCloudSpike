package com.thomson.mythomson.dev;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.adobe.mobile.Analytics;


public class GPBroadcastReceiver extends BroadcastReceiver {
    @Override public void onReceive(Context context, Intent intent) {
        Analytics.processReferrer(context, intent);
        // DEBUG - show the referrer
        String referrer = intent.getStringExtra("referrer");
        Log.d("ABDMobile", "Referrer is: " + referrer);
    }
}
