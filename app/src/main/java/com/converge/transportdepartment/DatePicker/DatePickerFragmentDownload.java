package com.converge.transportdepartment.DatePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.Toast;

import com.converge.transportdepartment.DownloadPDF;

import java.util.Calendar;

/**
 * Created by converge on 1/7/16.
 */

public class DatePickerFragmentDownload extends DialogFragment
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
        br.append(Integer.toString(day)).append("/").append(Integer.toString(month)).append("/").append(Integer.toString(year));

        DownloadPDF.editDownloadPdfDate.setText(br.toString());

    }

    private void showToast(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }
}