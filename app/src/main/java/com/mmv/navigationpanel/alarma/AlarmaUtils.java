package com.mmv.navigationpanel.alarma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;


public class AlarmaUtils {

    public static void pararAlarma(Context ct){
        AlarmManager alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, AlarmaReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(ct, AlarmaReceiver.REQUEST_CODE_ALARMA, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmMgr!=null) {
            alarmMgr.cancel(alarmIntent);
        }
    }

    public static void iniciarAlarma(Context ct){
        AlarmManager alarmMgr = (AlarmManager)ct.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ct, AlarmaReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(ct, AlarmaReceiver.REQUEST_CODE_ALARMA, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if(alarmMgr!=null) {
            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime()+1000,//AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    alarmIntent);
        }
    }
}
