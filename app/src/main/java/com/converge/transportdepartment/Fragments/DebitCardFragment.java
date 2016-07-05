package com.converge.transportdepartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.CitrusUser;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.classes.Month;
import com.citrus.sdk.classes.Year;
import com.citrus.sdk.payment.DebitCardOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.converge.transportdepartment.R;

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
    private String mParam1;
    private String mParam2;
    private CitrusClient citrusClient;

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

        TextView pay = (TextView) view.findViewById(R.id.textDebitload);
        pay.setOnClickListener(this);
        return  view;
    }


    private  void makePayment()
    {
        citrusClient = CitrusClient.getInstance(getActivity()); //pass Activity Context
        citrusClient.init("e9d6i0fazk-signup", "9903a947ac90c8ae5406dbbd60febe53", "e9d6i0fazk-signin", "15502bc50389b1e7b18809abe6e586e8", "e9d6i0fazk", Environment.SANDBOX);
        citrusClient.enableAutoOtpReading(true);
        DebitCardOption debitCardOption = new DebitCardOption("RAMKRISHNA RANDWA", "5596010020362354", "664", Month.getMonth("11"), Year.getYear("22"));
        Amount amount = new Amount("5");

        // Init PaymentType
        PaymentType.PGPayment pgPayment = null;
        try {
            pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp?ref=12345678", debitCardOption, new CitrusUser("amit.choudhary@cnvg.com","9981950533"));
        } catch (CitrusException e) {
            e.printStackTrace();
        }

        citrusClient.simpliPay(pgPayment, new Callback<TransactionResponse>()
        {
            @Override
            public void success(TransactionResponse transactionResponse) {
                Toast.makeText(getActivity(),"Payment Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(CitrusError error) {

                Toast.makeText(getActivity(),"Payment Failed"+error.getTransactionResponse().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        makePayment();
    }
}
