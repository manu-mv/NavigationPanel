package com.mmv.navigationpanel.jobscheduler;


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.mmv.navigationpanel.notificacion.NotificationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyJobService extends JobService {
    private static final String LOG_TAG = "MyJobService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "MyJobService created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(LOG_TAG, "MyJobService destroyed");
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        // This is where you would implement all of the logic for your job. Note that this runs
        // on the main thread, so you will want to use a separate thread for asynchronous work
        // If you use a separate thread, return true to indicate that you need a "reschedule" to
        // return to the job at some point in the future to finish processing the work. Otherwise,
        // return false when finished.
        Log.i(LOG_TAG, "Totally and completely working on job " + params.getJobId());
        // First, check the network, and then attempt to connect.
        if (isNetworkConnected()) {
            new SimpleDownloadTask() .execute(params);
            return true;
        } else {
            Log.i(LOG_TAG, "No connection on job " + params.getJobId() + "; sad face");
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        // Called if the job must be stopped before jobFinished() has been called. This may
        // happen if the requirements are no longer being met, such as the user no longer
        // connecting to WiFi, or the device no longer being idle. Use this callback to resolve
        // anything that may cause your application to misbehave from the job being halted.
        // Return true if the job should be rescheduled based on the retry criteria specified
        // when the job was created or return false to drop the job. Regardless of the value
        // returned, your job must stop executing.
        Log.i(LOG_TAG, "Whelp, something changed, so I'm calling it on job " + params.getJobId());
        return false;
    }

    /**
     * Determines if the device is currently online.
     */
    private boolean isNetworkConnected() {
        /*ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
        */
        return true;
    }

    /**
     *  Uses AsyncTask to create a task away from the main UI thread. This task creates a
     *  HTTPUrlConnection, and then downloads the contents of the webpage as an InputStream.
     *  The InputStream is then converted to a String, which is logged by the
     *  onPostExecute() method.
     */
    private class SimpleDownloadTask extends AsyncTask<JobParameters, Void, String> {

        protected JobParameters mJobParam;

        @Override
        protected String doInBackground(JobParameters... params) {
            // cache system provided job requirements
            mJobParam = params[0];
            try {
                Thread.sleep(500);
                NotificationUtils.sendNotification(getApplicationContext());
                return "OK";
            } catch (InterruptedException e) {
                return "FALLO";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            jobFinished(mJobParam, false);
            Log.i(LOG_TAG, result);
        }
    }
}