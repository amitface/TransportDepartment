package com.converge.transportdepartment;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;

public class SelectLangActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    Button enterButton;
    RadioButton oriyaButton, englishButton;

    String langSelected = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lang);
        initializeViews();
    }

    private void initializeViews() {
        enterButton = (Button) findViewById(R.id.button_enter);
        oriyaButton = (RadioButton) findViewById(R.id.radioButton_select_lang_oriya);
        englishButton = (RadioButton) findViewById(R.id.radioButton_select_lang_english);
        englishButton.setChecked(true);
        enterButton.setOnClickListener(this);
        oriyaButton.setOnCheckedChangeListener(this);
        englishButton.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.button_enter) {
            //Handle click event on Enter button
            setLocale(langSelected);
            startActivity(new Intent(SelectLangActivity.this,Home.class));
            finish();
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getApplication().getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.radioButton_select_lang_oriya) {
            //Handle click event on Enter button
            if(isChecked) {
                langSelected = "or";
                englishButton.setChecked(false);
            }

        } else {
            if(isChecked) {
                langSelected = "";
                oriyaButton.setChecked(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(SelectLangActivity.this,Home.class));
    }
}
