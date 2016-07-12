package com.converge.transportdepartment.DatePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.converge.transportdepartment.IdProof;

import java.util.Calendar;

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

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user





            br = new StringBuilder();
            br.append(Integer.toString(day)).append("/").append(Integer.toString(month+1)).append("/").append(Integer.toString(year));

            IdProof.editTextDateofIssue2.setText(br.toString());

        }

        private void showToast(String s)
        {
            Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
        }
    }
