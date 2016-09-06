package com.converge.transportdepartment.Utility;

import android.util.Log;

/**
 * Created by cnvgpc on 2/9/16.
 */
public class Utilities {

    public static  Long[] getNext30DateArray(long startDateTime) {
        Long[] next30DayList = new Long[15];

//        ArrayList<Long> next30DayList = new ArrayList<>();

        long val = (startDateTime*1000)+19800000;

//        next30DayList.clear();
        for(int i=1;i<=15;i++) {
//            next30DayList.add(val+(86400000*i));
            next30DayList[i-1] = (val+(86400000*i));
            Log.e("NextDateTime", "day = "+(val+(86400000*i)));
        }
        return next30DayList;

    }



}
