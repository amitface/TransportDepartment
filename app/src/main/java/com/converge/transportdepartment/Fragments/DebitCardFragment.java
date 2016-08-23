package com.converge.transportdepartment.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.citrus.widgets.CardNumberEditText;
import com.citrus.widgets.ExpiryDate;
import com.converge.transportdepartment.PaymentSuccessfull;
import com.converge.transportdepartment.R;
import com.converge.transportdepartment.Utility.ConValidation;
import com.converge.transportdepartment.Utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DebitCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DebitCardFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1, mParam2;
    private String transId, amt;

    private CitrusClient citrusClient;
    private Constants constants;

    private EditText editCardHolderName;
    private EditText editCardNumber;
    private EditText editCVV;
    private EditText editExpiryDate;


    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private static final String PGInfo="PgInfo";
    private String jsonString;
    private Long applicantNum;
    private String cardHolderName, cardNumber, cardCVV, md[];
    private long appNumber;
    private String receiptNumber, date, time, rtoCode;
    private long aLong;

    public DebitCardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DebitCardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DebitCardFragment newInstance(String param1, String param2) {
        DebitCardFragment fragment = new DebitCardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_debit_card, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        jsonString=sharedpreferences.getString(PGInfo,"");
        try {
            JSONObject jsonObjectData= new JSONObject(jsonString);
            appNumber= jsonObjectData.getLong("applicantNum");
            receiptNumber = jsonObjectData.getString("receiptNum");
            aLong = jsonObjectData.getLong("slotDate");
            date = ConValidation.getDateString(aLong);
            time = jsonObjectData.getString("slotTime");
            rtoCode = jsonObjectData.getString("rtocodeReal");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        TextView pay = (TextView) view.findViewById(R.id.textDebitload);

        editCardNumber = (CardNumberEditText) view
                .findViewById(R.id.cardHolderNumber);

        editExpiryDate = (ExpiryDate) view.findViewById(R.id.cardExpiry);
        editCardHolderName = (EditText) view.findViewById(R.id.cardHolderName);
//        cardHolderNickName = (EditText) view.findViewById(R.id.cardHolderNickName);
        editCVV = (EditText) view.findViewById(R.id.cardCvv);


        pay.setOnClickListener(this);
        return  view;
    }

    private void goPaymentSuccessful()
    {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PaymentSuccessfull.newInstance("1","1")).commit();
    }

    private boolean validate()
    {
        try {
            JSONObject jsonObjectData= new JSONObject(jsonString);
            applicantNum=jsonObjectData.getLong("applicantNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

         cardHolderName = editCardHolderName.getText().toString();
         cardNumber = editCardNumber.getText().toString();
         cardCVV = editCVV.getText().toString();

         md = editExpiryDate.getText().toString().split("/");
        if(cardHolderName.length()<5 || cardCVV.length()!=3 || cardNumber.length()<10 || editExpiryDate.length()==0)
        {
            Toast.makeText(getActivity(),"Enter all fields correctly",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private  void makePayment()
    {

        citrusClient = CitrusClient.getInstance(getActivity());
        DebitCardOption debitCardOption = new DebitCardOption(cardHolderName,cardNumber, cardCVV, Month.getMonth(md[0]), Year.getYear(md[1]));
        final Amount amount = new Amount(Double.toString(calulateTax(1.0)));
        PaymentType paymentType;

        Callback<TransactionResponse> callback = new Callback<TransactionResponse>() {
            @Override
            public void success(TransactionResponse transactionResponse) {

                try {
                    JSONObject jsonObject = new JSONObject(transactionResponse.getJsonResponse().toString());
                    transId= jsonObject.getString("transactionId");
                    amt= jsonObject.getString("amount");

                    //Log of payment and receipt generation.
                     new PaymentReport(transId,"Paid",amt,date,time,receiptNumber,Long.toString(appNumber), rtoCode).savePayment();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

                alertDialogPostReport();
            }

            @Override
            public void error(CitrusError error) {
                Toast.makeText(getActivity(),"Payment Failed"+error.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        };
        // Init PaymentType
                PaymentType pgPayment;
                try {
//                 pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp?ref=12345678", debitCardOption, new CitrusUser("amit.choudhary@cnvg.com","9981950533"));
//                 pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp?ref=12345678", debitCardOption, null);
                 pgPayment = new PaymentType.PGPayment(amount, "http://27.251.76.25:9012/DemoWebServices/BillUrl.jsp?ref="+applicantNum, debitCardOption, null);

//                paymentType = new PaymentType.PGPayment(amount, Constants.BILL_URL, debitCardOption, null);
                citrusClient.simpliPay(pgPayment, callback);

        } catch (CitrusException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(validate())
        alertDialogNote();

    }

    public void alertDialogPostReport()
    {
        final String[] items = {"Payment Successful."
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("M-Parivahan ");
        builder.setItems(items, null);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment")==null)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PaymentSuccessfull.newInstance("1", "1"), "PaymentSuccessfull").commit();
            }
        });

        builder.show();
    }

    private void alertDialogNote()
    {
        final String[] items = {" 0.75% (Banking charges) + 15.0% Service Tax will be added for all debit cards","Your amount will be Rs. "+calulateTax((double) 1)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("M-Parivahan");
        builder.setItems(items, null);
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                makePayment();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    private Double calulateTax(Double amt)
    {
        amt = amt+(amt/100)*0.75+(amt/100)*15;

        return (Math.round(amt * 100D)) / 100D;
    }


}

