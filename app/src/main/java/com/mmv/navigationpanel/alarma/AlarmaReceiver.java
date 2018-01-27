package com.mmv.navigationpanel.alarma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.example.navigationpanel.notificacion.NotificationUtils;


public class AlarmaReceiver extends BroadcastReceiver {
    public static final int REQUEST_CODE_ALARMA = 12345;

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationUtils.sendNotification(context);
    }
}
