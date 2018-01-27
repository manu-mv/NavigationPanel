package com.mmv.navigationpanel.servicio;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mmv.navigationpanel.notificacion.NotificationUtils;

public class RSSPullStateReceiver extends BroadcastReceiver {
    // Prevents instantiation
    public RSSPullStateReceiver() {
    }

    // Called when the BroadcastReceiver gets an Intent it's registered to receive
    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Handle Intents here.
         */
        NotificationUtils.sendNotification(context);
    }
}