package com.converge.transportdepartment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by root on 1/6/16.
 */
public   class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    StringBuilder br;
    Calendar c;

    private String []arrCov;
    private String covNTCode[]={"3","5" ,"4","6","12","13","2","65"};
    private String covTCode[]={"8", "9","7","10","53","54","16","15","17","58","59"};
    private SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    private final String mFinalStringCov="mFinalStringCov";
    private int age;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String temp = sharedpreferences.getString(mFinalStringCov,"");
        arrCov =temp.split(",");

        System.out.println("arrCov ---=== "+ arrCov[0]);

        if(isTransportVihcle())
        {
            year=year-20;
        }
        else if(isNonTransportVihcle())
        {
            year=year-18;
        }
        else
        {
            year=year-16;
        }

        Calendar calendar = new GregorianCalendar(year,month,day);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        // Do something with the date chosen by the user

        if(c.get(Calendar.YEAR)-year<0 || c.get(Calendar.YEAR)-year<16 )
        {
            showToast("age should be greater than 16");
            return;
        }

      /*  if(isNonTransportVihcle())
        {
            if(age < 18)
            {
                showToast("age should be greater than 18");
                return;
            }
        }

        if(isTransportVihcle())
        {
            if(age < 20)
            {
                showToast("age should be greater than 20");
                return;
            }
        }*/

        String monthFormat,dayFormat;

        if(Integer.toString(month+1).length()<2)
        {
            monthFormat="0"+Integer.toString(month+1);
        }
        else
            monthFormat = Integer.toString(month+1);

        if(Integer.toString(day).length()<2)
        {
            dayFormat="0"+Integer.toString(day);
        }
        else
            dayFormat = Integer.toString(day);



        br = new StringBuilder();
        br.append(dayFormat).append("/").append(monthFormat).append("/").append(Integer.toString(year));

        PersonalDetails.mtextViewDate.setText(br.toString());
    }

    public  boolean isNonTransportVihcle()
    {
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String temp = sharedpreferences.getString(mFinalStringCov,"");
        arrCov =temp.split(",");

        System.out.println("temp   === "+ temp);
        System.out.println("arrCov   === "+ arrCov);

        for(int i = 0; i<covNTCode.length;i++)
           {
               for(int j =0;j<arrCov.length;j++)
               if(covNTCode[i].equals(arrCov[j]))
               {
                   return true;
               }
           }
        return false;
    }

    public  boolean isTransportVihcle()
    {
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        String temp = sharedpreferences.getString(mFinalStringCov,"");
        arrCov =temp.split(",");

        System.out.println("temp   === "+ temp);
        System.out.println("arrCov   === "+ arrCov);

        for(int i = 0; i<covTCode.length;i++)
        {
            for(int j =0;j<arrCov.length;j++)
                if(covTCode[i].equals(arrCov[j]))
                {
                    return true;
                }
        }

        return false;
    }

    private void showToast(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
}
