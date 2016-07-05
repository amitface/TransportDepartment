package com.converge.transportdepartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.Environment;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.payment.NetbankingOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.converge.transportdepartment.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NetbankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetbankingFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView testClick;
    private CitrusClient citrusClient;


    public NetbankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetbankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetbankingFragment newInstance(String param1, String param2) {
        NetbankingFragment fragment = new NetbankingFragment();
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
        View view = inflater.inflate(R.layout.fragment_netbanking, container, false);
        testClick = (TextView) view.findViewById(R.id.testClick);
        testClick.setOnClickListener(this);
        citrusClient = CitrusClient.getInstance(getActivity()); // Activity Context

//        citrusClient.init("e9d6i0fazk-signup","9903a947ac90c8ae5406dbbd60febe53","e9d6i0fazk-signin", "15502bc50389b1e7b18809abe6e586e8", "e9d6i0fazk", Environment.SANDBOX);
        citrusClient.init("test-signup", "c78ec84e389814a05d3ae46546d16d2e", "test-signin", "52f7e15efd4208cf5345dd554443fd99", "prepaid", Environment.SANDBOX);
        return view;
    }

    private  void makePayment()
    {

//        citrusClient.enableAutoOtpReading(true);
        // No need to call init on CitrusClient if already done.

        NetbankingOption netbankingOption = new NetbankingOption("ICICI Bank"

                ,"CID001");

                // Init Net Banking PaymentType

        Amount amount = new Amount("5");
        PaymentType.PGPayment pgPayment = null;
        try {
            pgPayment = new PaymentType.PGPayment(amount, "https://27.251.76.25:9012/BillUrl.jsp", netbankingOption, null);
            citrusClient.pgPayment(pgPayment, new Callback<TransactionResponse>() {

                @Override

                public void success(TransactionResponse transactionResponse) { }

                @Override

                public void error(CitrusError error) { }

            });
        } catch (CitrusException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onClick(View v) {
        makePayment();
    }
}
