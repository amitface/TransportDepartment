package com.converge.transportdepartment.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.citrus.sdk.Callback;
import com.citrus.sdk.CitrusClient;
import com.citrus.sdk.TransactionResponse;
import com.citrus.sdk.classes.Amount;
import com.citrus.sdk.classes.CitrusException;
import com.citrus.sdk.payment.MerchantPaymentOption;
import com.citrus.sdk.payment.NetbankingOption;
import com.citrus.sdk.payment.PaymentType;
import com.citrus.sdk.response.CitrusError;
import com.converge.transportdepartment.PaymentSuccessfull;
import com.converge.transportdepartment.R;
import com.converge.transportdepartment.Utility.Constants;

import java.util.ArrayList;

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

    private Constants constants;

    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;


    private ArrayList<NetbankingOption> mNetbankingOptionsList;

    private Amount amount = null;
    private String couponCode = null;
    private Amount alteredAmount = null;
    private MerchantPaymentOption mMerchantPaymentOption = null;
    private MerchantPaymentOption mLoadMoneyPaymentOptions = null;
    private CitrusClient citrusClient;
    private PaymentType paymentType;
    private PaymentType pgPayment;


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
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
//        testClick = (TextView) view.findViewById(R.id.testClick);
//        testClick.setOnClickListener(this);

        final NetbankingAdapter netbankingAdapter = new NetbankingAdapter(getActivity(), mNetbankingOptionsList);

        RecyclerView recylerViewNetbanking = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recylerViewNetbanking.setAdapter(netbankingAdapter);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recylerViewNetbanking.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recylerViewNetbanking.setLayoutManager(mLayoutManager);

        recylerViewNetbanking.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new OnItemClickListener()));

        citrusClient = CitrusClient.getInstance(getActivity()); // Activity Context
        // Get the load money payment options.
        CitrusClient.getInstance(getActivity()).getMerchantPaymentOptions(new Callback<MerchantPaymentOption>() {
            @Override
            public void success(MerchantPaymentOption merchantPaymentOption) {
                mMerchantPaymentOption = merchantPaymentOption;
                netbankingAdapter.setNetbankingOptionList(mMerchantPaymentOption.getNetbankingOptionList());
                netbankingAdapter.notifyDataSetChanged();

                mNetbankingOptionsList = mMerchantPaymentOption.getNetbankingOptionList();
            }

            @Override
            public void error(CitrusError error) {

            }
        });
        return view;
    }

    public  void makePayment()
    {

//        citrusClient.enableAutoOtpReading(true);
        // No need to call init on CitrusClient if already done.

        NetbankingOption netbankingOption = new NetbankingOption("ICICI Bank","CID001");

        // Init Net Banking PaymentType
        Callback<TransactionResponse> callback = new Callback<TransactionResponse>() {
            @Override
            public void success(TransactionResponse transactionResponse) {
                Toast.makeText(getActivity(),"Payment Success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void error(CitrusError error) {
                Toast.makeText(getActivity(),"Payment Success",Toast.LENGTH_LONG).show();
            }
        };

        Amount amount = new Amount("5");
        PaymentType.PGPayment pgPayment = null;
        try {
            pgPayment = new PaymentType.PGPayment(amount, "http://27.251.76.25:9012/DemoWebServices/BillUrl.jsp?ref=23456701", netbankingOption, null);
            citrusClient.simpliPay(pgPayment, callback);
        } catch (CitrusException e) {
            e.printStackTrace();
        }
    }

    private class OnItemClickListener extends RecyclerItemClickListener.SimpleOnItemClickListener {

        @Override
        public void onItemClick(View childView, int position) {
            NetbankingOption netbankingOption = getItem(position);
            CitrusClient client = CitrusClient.getInstance(getActivity());

            amount=new Amount("1");

                PaymentType paymentType1;
                Callback<TransactionResponse> callback = new Callback<TransactionResponse>() {
                    @Override
                    public void success(TransactionResponse transactionResponse) {
//                        ((UIActivity) getActivity()).onPaymentComplete(paymentType, transactionResponse);
//                        Toast.makeText(getActivity(),"success",Toast.LENGTH_LONG).show();
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PaymentSuccessfull.newInstance("1","1")).commit();
//                            }
//                        });
                        alertDialogPostReport();
                    }

                    @Override
                    public void error(CitrusError error) {
//                        ((UIActivity) getActivity()).showSnackBar(error.getMessage());
                        Toast.makeText(getActivity(),"Failure"+error.toString(),Toast.LENGTH_LONG).show();
                    }
                };

                try {

//                        paymentType1 = new PaymentType.PGPayment(amount, Constants.BILL_URL, netbankingOption, null);
                        pgPayment = new PaymentType.PGPayment(amount, "http://27.251.76.25:9012/DemoWebServices/BillUrl.jsp?ref="+sharedpreferences.getString("receiptNum",""), netbankingOption, null);
                        client.simpliPay(pgPayment, callback);

                } catch (CitrusException e) {
                    e.printStackTrace();


                }

        }
    }

    private NetbankingOption getItem(int position) {
        NetbankingOption netbankingOption = null;

        if (mNetbankingOptionsList != null && mNetbankingOptionsList.size() > position && position >= -1) {
            netbankingOption = mNetbankingOptionsList.get(position);
        }

        return netbankingOption;
    }


    @Override
    public void onClick(View v) {
        makePayment();
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
        final String[] items = {"HDFC & ICICI-1.70% +ST, REST 1.55% + ST ","Your final amit"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("M-Parivahan ");
        builder.setItems(items, null);
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        builder.show();
    }


    private Double calulateTax(Double amt)
    {
        amt = amt+(amt/100)*0.2;
        return amt;
    }
}
