package com.converge.transportdepartment.DatePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.converge.transportdepartment.IdProof;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by root on 1/6/16.
 */
public   class DatePickerFragment2 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        StringBuilder br;
       Calendar c;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
                c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            Calendar calendar = new GregorianCalendar(year,month,day);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);

//        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

            return datePickerDialog;
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user


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

            IdProof.editTextDateofIssue2.setText(br.toString());

        }

        private void showToast(String s)
        {
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }
