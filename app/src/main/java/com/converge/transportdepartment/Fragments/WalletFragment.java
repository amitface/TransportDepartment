package com.converge.transportdepartment.Fragments;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.converge.transportdepartment.PaymentSuccessfull;
import com.converge.transportdepartment.R;
import com.converge.transportdepartment.Utility.ConValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1, msg;
    private String mParam2;

    private EditText editUserName;
    private EditText editPassword;
    private Button btnWallet;

    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private static final String PGInfo="PgInfo";

    private String jsonString;
    private Long applicantNum, aLong;
    private String transId, amt;
    Double tax;
    private long appNumber;
    private String receiptNumber, date, time, rtoCode, name;
    private ProgressDialog progress;
    private String userName, userPassword;

    public WalletFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WalletFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
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
        View view = inflater.inflate(R.layout.fragment_wallet, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        editUserName = (EditText) view.findViewById(R.id.editWalletUser);
        editPassword = (EditText) view.findViewById(R.id.editWalletPassword);
        btnWallet = (Button) view.findViewById(R.id.btnWallet);
        progress = new ProgressDialog(getActivity());
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

        btnWallet.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        userName = editUserName.getText().toString();
        userPassword = editPassword.getText().toString();
        if(validate())
        alertDialogNote();

    }

    private boolean validate()
    {
        if(editUserName.getText().length()<6){
            Toast.makeText(getActivity(),"User name should length be greater then six characters ",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(editPassword.getText().length()==0)
        {
            Toast.makeText(getActivity(),"Password cannot be empty",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    public void PayWalletRequest() {
        new sendWalletRequest(getActivity()).execute();
    }

    private class sendWalletRequest extends AsyncTask<Void, Integer, Integer> {

        private final Context context;

        private static final int  MEGABYTE = 1024 * 1024;
        String s;

        public sendWalletRequest(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

            progress.setMessage("Please wait...");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int l=0;
            try
            {
//                URL url = new URL("http://27.251.76.25:9012/DemoWebServices/resources/data/info");
                URL url = new URL("http://27.251.76.25:9012/DemoWebServices/resources/wallet/transaction");

                JSONObject jsonObject = new JSONObject();

                JSONObject jsonData = new JSONObject();

                jsonData.put("ref",Long.toString(appNumber));
                jsonData.put("usr",userName);
                jsonData.put("pass",userPassword);
                jsonData.put("tamt",Long.toString(calulateTax(1L)));
//                jsonData.put("data",jsonlist);

                String json =jsonData.toString();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(25000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(json);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print("ResponseCode ====  "+responseCode+"\nRespone === " +connection.getResponseMessage()+"\n");

//                Toast.makeText(getActivity(),connection.getResponseMessage().toString(),Toast.LENGTH_SHORT).show();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Response Message " +connection.getResponseMessage());
//                output.append(System.getProperty("line.separator") + "Type " + "POST" + " " + connection.getRequestProperty("success"));
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
                System.out.print("Resposne out put ====  "+responseOutput.toString()+"\n");

                JSONObject  jsonObject1= new JSONObject(responseOutput.toString());
                msg = jsonObject1.getString("message");
                transId =jsonObject1.getString("transaction_id");
                amt="1";
                if(jsonObject1.get("status").equals("Success"))
                    return 1;

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return l;
        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progress.setProgress(percent[0]);
        }
        @Override
        protected void onPostExecute(Integer result) {

            progress.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(new Intent(getActivity(),TestDebitPaymentActivity.class));
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, NetbankingFragment.newInstance("1","1")).commit();
                        new PaymentReport(transId,"Paid",amt,date,time,receiptNumber,Long.toString(appNumber),rtoCode).savePayment();

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PaymentSuccessfull.newInstance("1","1")).commit();
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, DebitCardFragment.newInstance("1","1")).commit();
                    }
                });

            }
            else
            {
                Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
            }
        }

    }

    private void alertDialogNote()
    {
        final String[] items = {"Your amount will be Rs. "+calulateTax(1L)};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("M-Parivahan ");
        builder.setItems(items, null);
        builder.setCancelable(false);
        builder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                PayWalletRequest();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private Long calulateTax(Long amt)
    {
        return amt;
    }
}
