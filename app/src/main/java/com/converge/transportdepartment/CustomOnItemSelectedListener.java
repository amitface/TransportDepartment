package com.converge.transportdepartment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by root on 30/5/16.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
        if(parent.getItemAtPosition(pos).toString().toUpperCase().equals("INDIA"))
        {

        }
        else
        {

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
