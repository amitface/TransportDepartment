package com.converge.transportdepartment.Utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by pankaj on 20/8/16.
 */
public class ConValidation {

    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private final String mFinalStringCov="mFinalStringCov";
    private String []arrCov;
    private String covTCode[]={"8", "9","7","10","53","54","16","15","17","58","59"};
    String qualificatinCode[] = {"0 ", "1 ","2 ","3 ", "4 ","6 ", "7 ", "10","11", "12",
            "13","14","30","31","32","33","34","35","39","50","51",
            "52", "53", "54","55","56","57", "58","59", "70", "80", "81","82","90"};

    public ConValidation(Context c) {
        sharedpreferences = c.getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String temp = sharedpreferences.getString(mFinalStringCov,"");
        arrCov =temp.split(",");
    }


    public  boolean isTransportVihcle()
    {

        for(int i = 0; i<qualificatinCode.length;i++)
        {
            for(int j =0;j<arrCov.length;j++)
                if(covTCode[i].equals(arrCov[j]))
                {
                    return true;
                }
        }

        return false  ;
    }
}
