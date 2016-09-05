package com.converge.transportdepartment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Cnvg on 03-09-2016.
 */
public class FeeReceiptActivity extends AppCompatActivity
{
    public Toolbar mToolbar;
    public ActionBar actionBar;

    TextView fee, totalFee;
    Double feeD = 30.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fee_reciept_layout);
        setuptoolbar();
        feeD = getIntent().getDoubleExtra("fee",30.0);
        fee = (TextView) findViewById(R.id.covFee);
        totalFee = (TextView) findViewById(R.id.totalFee);
        fee.setText("30 * "+(int)((feeD-28.76)/30.0)+" = "+Math.round((feeD-28.76)*100D)/100D);
        totalFee.setText("Rs. "+Double.toString(+Math.round(feeD*100D)/100D));
    }
    public void setuptoolbar() {
        mToolbar = (Toolbar) findViewById(R.id.tools);
        setSupportActionBar(mToolbar);
        ((ImageView) findViewById(R.id.imgBack)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
