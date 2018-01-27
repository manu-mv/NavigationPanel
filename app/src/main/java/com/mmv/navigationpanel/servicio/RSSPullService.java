package com.mmv.navigationpanel.servicio;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.mmv.navigationpanel.notificacion.NotificationUtils;


public class RSSPullService extends IntentService {
    public static final String ACTION_INICIADA_NOTIFICACION = "notif-service";

    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
            "com.mmv.navigationpanel.BROADCAST";
    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS =
            "com.mmv.navigationpanel.STATUS";

    public RSSPullService() {
        super("RSSPullService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Normally we would do some work here, like download a file.
        // For our sample, we just sleep for 5 seconds.
        try {
            /*verificar si el servicio lo ha iniciado la notificacion
             */
            String action = intent.getAction();
            if(action != null && action.equals(ACTION_INICIADA_NOTIFICACION)){
                Log.i("SERVICIO","NOTIFICACION");
                NotificationUtils.clearAllNotifications(this);
            }

            Thread.sleep(5000);


            /*
             * Creates a new Intent containing a Uri object
             * BROADCAST_ACTION is a custom Intent action
             */
            Intent localIntent = new Intent(BROADCAST_ACTION)
                    // Puts the status into the Intent
                    .putExtra(EXTENDED_DATA_STATUS, true);
            // Broadcasts the Intent to receivers in this app.
            LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);

        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }
    }

}