package com.converge.transportdepartment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.MerchantPaymentOption;
import com.citrus.sdk.payment.NetbankingOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;

import java.util.ArrayList;

public class TestDebitPaymentActivity extends AppCompatActivity implements OnClickListener{

    private CitrusClient citrusClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_debit_payment);

        citrusClient = CitrusClient.getInstance(this); //pass Activity Context
        citrusClient.init("e9d6i0fazk-signup","9903a947ac90c8ae5406dbbd60febe53", "e9d6i0fazk-signin", "15502bc50389b1e7b18809abe6e586e8", "e9d6i0fazk", Environment.SANDBOX);
//        citrusClient.enableAutoOtpReading(true);
        CitrusClient.getInstance(this).getMerchantPaymentOptions(new Callback<MerchantPaymentOption>() {
            @Override
            public void success(MerchantPaymentOption mMerchantPaymentOption) {
                ArrayList<NetbankingOption> mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list
            }
            @Override
            public void error(CitrusError error) {
            }
        });
        TextView pay = (TextView) findViewById(R.id.textDebitloadACT);
        pay.setOnClickListener(this);
    }

    private  void makePayment()
    {
        DebitCardOption debitCardOption = new DebitCardOption("RAMKRISHNA RANDWA", "5596010020362354", "664", Month.getMonth("11"), Year.getYear("22"));
        Amount amount = new Amount("5");

        // Init PaymentType
        PaymentType.PGPayment pgPayment = null;
        try {
//            pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp?ref=12345678", debitCardOption, new CitrusUser("amit.choudhary@cnvg.com","9981950533"));
            pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp", debitCardOption, null);
        } catch (CitrusException e) {
            e.printStackTrace();
        }

        CitrusClient.getInstance(this).simpliPay(pgPayment, new Callback<TransactionResponse>()
        {
            @Override
            public void success(TransactionResponse transactionResponse) {
                Toast.makeText(getApplicationContext(),"Payment Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(CitrusError error) {

                Toast.makeText(getApplicationContext(),"Payment Failed"+error.getTransactionResponse().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        makePayment();
    }
}
