package com.converge.transportdepartment.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pankaj on 20/8/16.
 */
public class ConValidation {

    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private final String mFinalStringCov="mFinalStringCov";
    private String []arrCov;
    private String covTCode[]={"8", "9","7","10","53","54","16","15","17","58","59"};
    String qualificatinCode[] = {"0", "1","2 ","3", "4","6", "7", "10","11", "12",
            "13","14","30","31","32","33","34","35","39","50","51",
            "52", "53", "54","55","56","57", "58","59", "70", "80", "81","82","90"};

    private Context c;

    public ConValidation(Context c) {
        sharedpreferences = c.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        this.c = c;
        String temp = sharedpreferences.getString(mFinalStringCov,"");
        arrCov =temp.split(",");
    }


    public  boolean QualificationCheck()
    {

        for(int i = 0; i<covTCode.length;i++)
        {
            for(int j =0;j<arrCov.length;j++)
                if(covTCode[i].equals(arrCov[j]))
                {
                    return true;
                }
        }
        return false  ;
    }

    public static long currentDateInMilliSec(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return Long.parseLong(formatter.format(new Date(milliSeconds)));
    }

    public static boolean isNetworkAvailable(Context con) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String getDateString(Long aLong)
    {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("E,dd/MM/yy");
        calendar.setTimeInMillis(aLong);
     return   dateFormat.format(calendar.getTime());
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
