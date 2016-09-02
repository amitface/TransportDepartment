package com.converge.transportdepartment.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Arpit Sinha on 30-Oct-15.
 */

/**
 * This class is used for checking Active Internet Connection in android devices.
 * User need to make a object of this class which will require Context and He/She will
 * be required to Implement the NetworkListener interface present in the class.
 * A small snippet of the usage will be like :
 *
 *  NetworkChecker checkConn = new NetworkChecker(mContext);
 *  checkConn.setNetworkListener(this);
 *  checkConn.CheckInternetAvailabilityTask();
 *
 * result will be provided in the method which is written in the class for Implementation.
 */
public class NetworkChecker {

    private static String LOG_TAG =  NetworkChecker.class.getSimpleName();

    private Context mContext;

    private NetworkListener mNetworkListener;

    private String url = "http://www.google.com";

    public NetworkChecker(Context context) {
        mContext = context;
    }

    public void setNetworkListener(NetworkListener networkListener){
        this.mNetworkListener = networkListener;
    }

    public void CheckInternetAvailabilityTaskWithNoDialog() {

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {

                return isNetworkAvailable();
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                mNetworkListener.onNetworkResult(result);
            }
        }.execute();
    }

    public void CheckInternetAvailabilityTask(final Context context) {



        new AsyncTask<Void, Void, Boolean>() {

            @Override
            protected Boolean doInBackground(Void... params) {

                return isNetworkAvailable();

                /**
                 * This part has been commented because it was told to do so for this particular project.
                 * Not necessary for every project.
                 * Above appInstance.isInternetConnectionActive can return true even if the network is not available.
                 */
//                boolean response = hasActiveInternetConnection();
//                Log.e(LOG_TAG, "Response from hasActiveInternetConnection() :"  +response);
//                return response;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                mNetworkListener.onNetworkResult(result);
            }
        }.execute();
    }

    private boolean hasActiveInternetConnection() {

        if (isNetworkAvailable()) {
            try {
                Log.e(LOG_TAG, "Inside hasActiveInternetConnection()");
                HttpURLConnection urlc = (HttpURLConnection) (new URL(url).openConnection());
                urlc.setRequestProperty("User-Agent", "Test");
                urlc.setRequestProperty("Connection", "close");
                urlc.setConnectTimeout(3500);
                urlc.connect();
                Log.e(LOG_TAG, "ResponseCode = "+urlc.getResponseCode());
                return (urlc.getResponseCode() == 200);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error checking internet connection", e);
            }
        } else {
            Log.d(LOG_TAG, "No network available!");
        }
        return false;
    }

    public boolean isNetworkAvailable() {
        Log.e(LOG_TAG, "Inside isNetworkAvailable()");
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private static boolean dialogActive = false;

    public void showDialogBoxForUnavailableInternetService(final Context context, String message, final boolean finishActivity) {
        if(!dialogActive) {
            if (context != null) {
                dialogActive = true;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                builder.setTitle(message);
                builder.setInverseBackgroundForced(true);
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialogActive = false;
                                dialog.dismiss();
                                if (finishActivity)
                                    ((Activity) context).finish();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public interface NetworkListener {
        void onNetworkResult(boolean result);
    }

}
