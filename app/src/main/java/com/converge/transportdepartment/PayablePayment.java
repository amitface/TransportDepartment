package com.converge.transportdepartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.ActivityFragments.NoPaymentFragment;
import com.converge.transportdepartment.Fragments.CreditCardFragment;
import com.converge.transportdepartment.Fragments.DebitCardFragment;
import com.converge.transportdepartment.Fragments.NetbankingFragment;
import com.converge.transportdepartment.Fragments.WalletFragment;
import com.converge.transportdepartment.Utility.ConValidation;

import org.json.JSONArray;
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
 * Activities that contain this fragment must implement the
 * {@link PayablePayment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayablePayment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PayablePayment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private String mFinalString1="mFinalString1";
    private final String mFinalString2="mFinalString2";
    private final String mFinalStringCov="mFinalStringCov";

    private static final String PGInfo="PgInfo";

    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4;
    private RelativeLayout R1,R2;
    private ImageView imagePersonal,imageAtRto;
    private int marker =1;
    public static int status=0;

    TextView payableBifurfication;
    private String jsonString;

    private int pgoption ;

    public PayablePayment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PayablePayment.
     */
    // TODO: Rename and change types and number of parameters
    public static PayablePayment newInstance(String param1, String param2) {
        PayablePayment fragment = new PayablePayment();
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

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        ConValidation.hideKeyboard(getContext());

        View view = inflater.inflate(R.layout.fragment_payable_payment, container, false);
        pgoption=0;

        double fee =Math.round(totalFee()*100D)/100D;
        TextView textView = (TextView) view.findViewById(R.id.textPayment);
        textView.setText(getString(R.string.payable_amount)+" Rs. "+fee);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioPaymentOption);

        radioButton1 = (RadioButton) view.findViewById(R.id.radioCreditCard);
        radioButton2 = (RadioButton) view.findViewById(R.id.radioDebitCard);
        radioButton3 = (RadioButton) view.findViewById(R.id.radioNetBank);
        radioButton4 = (RadioButton) view.findViewById(R.id.radioWalletAccounts);
        payableBifurfication=(TextView)view.findViewById(R.id.payableBifurfication);

        R1 = (RelativeLayout)view.findViewById(R.id.paymentOptionsTextId);
        R2 = (RelativeLayout)view.findViewById(R.id.payAtRto);

        R1.setOnClickListener(this);
        R2.setOnClickListener(this);
        payableBifurfication.setOnClickListener(this);

        imagePersonal = (ImageView) view.findViewById(R.id.imagePersonal);
        imageAtRto = (ImageView)view.findViewById(R.id.imageAtRto);

        TextView textPayNow = (TextView) view.findViewById(R.id.buttonPayNow);
        textPayNow.setText("Next");
        textPayNow.setOnClickListener(this);

        jsonString=sharedpreferences.getString(PGInfo,"");
//        PayRequest(view);

        return  view;
    }

    @Override
    public void onClick(View v) {
        if(!validate())
            return ;
        switch (v.getId())
        {
            case R.id.paymentOptionsTextId:
                showBox();
                break;
            case R.id.payAtRto:
                showBox();
                break;

            case R.id.buttonPayNow:
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if(marker==1)
                {
                    if(selectedId==R.id.radioWalletAccounts)
                    {
                        pgoption=4;
                        if(status==0)
                            PayRequest(v);
                        else
                            callFragment();
                    }
                    else{
                        if(selectedId==R.id.radioCreditCard)
                        {
                            pgoption=1;
                        }else if(selectedId==R.id.radioDebitCard)
                        {
                            pgoption=2;
                        }else if(selectedId==R.id.radioNetBank)
                        {
                            pgoption=3;
                        }else
                        {
                            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.selectOne),Toast.LENGTH_LONG).show();
                            break;
                        }

                        if(status==0)
                            PayRequest(v);
                        else
                            callFragment();
                    }
                }
                else
                {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, NoPaymentFragment.newInstance("1","1"),"NoPaymentFragment").commit();
                }
                break;

            case R.id.payableBifurfication:
                Intent intent = new Intent(getContext(),FeeReceiptActivity.class);
                intent.putExtra("fee",totalFee());
                startActivity(intent);
                break;
        }
    }

    private  boolean validate()
    {
        if(!ConValidation.isNetworkAvailable(getActivity()))
        {
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.noInternet),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void showBox()
    {
        if(marker==1)
        {
            imagePersonal.setImageDrawable(getResources().getDrawable(R.drawable.check_wrong));
            imageAtRto.setImageDrawable(getResources().getDrawable(R.drawable.check_right));
            radioGroup.setVisibility(View.GONE);
            marker=2;
        }
        else
        {
            imagePersonal.setImageDrawable(getResources().getDrawable(R.drawable.check_right));
            imageAtRto.setImageDrawable(getResources().getDrawable(R.drawable.check_wrong));
            radioGroup.setVisibility(View.VISIBLE);
            marker=1;
        }
    }

    private void callFragment() {

        status=1;
        if(pgoption==1)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, CreditCardFragment.newInstance("1","1"),"CreditCard").commit();
        }else if(pgoption ==2)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, DebitCardFragment.newInstance("1","1"),"DebitCard").commit();
        }else if(pgoption==3)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, NetbankingFragment.newInstance("1","1"),"NetBanking").commit();
        }else if(pgoption==4)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, WalletFragment.newInstance("1","1"),"Wallet").commit();
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, WalletWebViewFragment.newInstance(sharedpreferences.getString("receiptNum",""),"1"),"Wallet").commit();
        }
    }

    private double totalFee() {
        String s= sharedpreferences.getString("mFinalStringCov","");
        String arr[]=s.split(",");
        double len =arr.length;
        if(arr[0].length()>0)
            len = len*30+28.76;
        else
            len=0;
        return len;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public void PayRequest(View View) {
        new sendPayRequest(getActivity()).execute();
    }

    private class sendPayRequest extends AsyncTask<Void, Integer, Integer> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public sendPayRequest(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
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
                URL url = new URL("http://27.251.76.25:9012/DemoWebServices/resources/data/info");
                JSONObject jsonObjectData= new JSONObject(jsonString);
                JSONObject jsonObject = new JSONObject();

                JSONObject jsonData = new JSONObject();
                JSONObject jsonlist = new JSONObject();
                JSONObject jsonlist1 = new JSONObject();
                JSONObject jsonlist2 = new JSONObject();
                JSONObject jsonlist3 = new JSONObject();

//                jsonlist1.put("amount","0.7");
//                jsonlist1.put("code","11");
//                jsonlist1.put("hoa","1234456778898");
//
//                jsonlist2.put("amount","1");
//                jsonlist2.put("code","11");
//                jsonlist2.put("hoa","11455656");
//
//                jsonlist3.put("amount","1");
//                jsonlist3.put("code","11");
//                jsonlist3.put("hoa","1998878776676");


                jsonlist1.put("amount","0.7");
                jsonlist1.put("code","11");
                jsonlist1.put("hoa","41001020098");

                jsonlist2.put("amount","0.4");
                jsonlist2.put("code","11");
                jsonlist2.put("hoa","30434594815");

                JSONArray arraylist = new JSONArray();
                arraylist.put(jsonlist1);
                arraylist.put(jsonlist2);
//                arraylist.put(jsonlist3);

                jsonlist.put("list",arraylist);

                jsonData.put("ref",jsonObjectData.get("applicantNum").toString());
                jsonData.put("rto_code",jsonObjectData.get("rtocodeReal").toString());
                jsonData.put("vehicle_number",jsonObjectData.get("name").toString());

                jsonData.put("applicant_name",jsonObjectData.get("name").toString());
                jsonData.put("tax_type","6");
                jsonData.put("rto_acc_no","0");
                jsonData.put("rfu1","");
                jsonData.put("rfu2","");
                jsonData.put("rfu3","");


                jsonData.put("data",jsonlist);

                String json =jsonData.toString();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(60000);
                connection.setReadTimeout(25000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(json);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print("ResponseCode ====  "+responseCode+"\nRespone === " +connection.getResponseMessage()+"\n");

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
                        callFragment();
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.PayFail),Toast.LENGTH_LONG).show();
            }
        }
    }


    public void sendMessage()
    {
//        new sendMsg(getActivity()).execute();
    }

    private class sendMsg extends AsyncTask<Void, Integer, Integer> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public sendMsg(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int l=0;
            try
            {
                JSONObject jsonObjectData= new JSONObject(jsonString);
                URL url = new URL(" http://103.27.233.206/sendsms/");
                String s ="msg=Thanks for using M-Parivahan, your application no "+sharedpreferences.getString("receiptNum","")+" and Receipt No "+sharedpreferences.getString("receiptNum","")+". Date of appointment 11/08/2016 and Time 11:15 AM &mobile="+jsonObjectData.get("moblie");
                System.out.println(s);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print(responseCode);

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


        }
    }
}
