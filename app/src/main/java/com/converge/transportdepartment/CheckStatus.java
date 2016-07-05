package com.converge.transportdepartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CheckStatus.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CheckStatus#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CheckStatus extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String s =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
                "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
                "<applicants>"+
                "<applicant refno=\"6\">"+
                            "<statecode>AP</statecode>"+
                            "<rtocode>OD01</rtocode>"+
                            "<licence-type>L</licence-type>"+
                            "<applicant-name>" +
                                "<first-name>Ramana</first-name>" +
                                "<middle-name>Rao</middle-name>" +
                                "<last-name>Yogi</last-name>" +
                            "</applicant-name>"+
                            "<dob>05-08-1989</dob>" +
                            "<gender type=\"male\"/>" +
                            "<relation type=\"father\"/>"+
                            "<parent-name>" +
                                "<first-name>Shankar</first-name>" +
                                "<middle-name>rao</middle-name>" +
                                "<last-name>Yogi</last-name>" +
                            "</parent-name>"+
                            "<edu-qualification>4</edu-qualification>" +
                            "<identification-marks>A mole on the right side of the neck</identification-marks>" +
                            "<identification-marks/>"+
                            "<blood-group>O+</blood-group>"+
                            "<permanent-address>" +
                                "<p-flat-house-no>21</p-flat-house-no>" +
                                "<p-street-locality>Ramarajyanagar</p-street-locality>" +
                                "<p-village-city>Hyderabad</p-village-city>" +
                                "<p-district>Ranga Reddy</p-district>" +
                                "<p-state>AP</p-state>" +
                                "<p-pin>500012</p-pin>" +
                                "<p-phone-no></p-phone-no>" +
                                "<p-mobile-no>9533241448</p-mobile-no>" +
                                "<p-durationofstay>" +
                                    "<p-years>5</p-years>" +
                                    "<p-months>5</p-months>" +
                                "</p-durationofstay>" +
                             "</permanent-address>" +
                             "<present-address>" +
                                "<t-flat-house-no>21</t-flat-house-no>" +
                                "<t-street-locality>Ramarajyanagar</t-street-locality>" +
                                "<t-village-city>Hyderabad</t-village-city>" +
                                "<t-district> Ranga Reddy </t-district>" +
                                "<t-state>AP</t-state>" +
                                "<t-pin>500012</t-pin>" +
                                "<t-phone-no></t-phone-no>" +
                                "<t-durationofstay>" +
                                  "<t-years>5</t-years>" +
                                  "<t-months>5</t-months>" +
                                "</t-durationofstay>" +
                             "</present-address>"+
                        "<citizenship-status type=\"birth\"/>" +
                        "<birth-place>Warangal</birth-place>" +
                        "<migration>" +
                            "<year>2012</year>" +
                            "<month>05</month>" +
                        "</migration>" +
                        "<birth-country>IND</birth-country>" +
                        "<email-id>ramana@gmail.com</email-id>" +
                        "<list-of-proofs>" +
                        "<doc>" +
                        "<proofcode>D</proofcode>" +
                        "<licence-certificate-badge-no>MH0119760129144</licence-certificate-badge-no>" +
                        "<issuing-authority>MH01</issuing-authority>" +
                        "<date-of-issue>18-07-2007</date-of-issue>" +
                        "</doc>" +
                        "</list-of-proofs>"+
                        "<covs>3</covs>" +
                        "<rcnumber/>"+
                        "<parentleterforbelow18age/>"+
                        "<allnecessarycertificates type=\"y\"/>"+
                        "<exemptedmedicaltest type=\"n\"/>"+
                        "<exemptedpreliminarytest type=\"n\"/>"+
                        "<convicted type=\"n\"/>"+
                            "<attachdoc>" +
                                "<attdlnumber/>" +
                                "<attdtofconviction/>" +
                                "<attreason/>" +
                            "</attachdoc>"+
                "</applicant>" +
            "</applicants>";
    String s2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
            "<applicants>"
             +
            "<applicant refno=\"2\">" +

            "<statecode>KA</statecode>" +

            "<rtocode>KA53</rtocode>" +

            "<licence-type>l</licence-type>" +

            "<applicant-name>" +

            "<first-name>VENKATESWARA</first-name>" +

            "<middle-name>RAO</middle-name>" +

            "<last-name>S S</last-name>" +

            "</applicant-name>" +

            "<dob>14-06-1989</dob>" +

            "<gender type=\"male\"/>" +

            "<relation type=\"father\" />" +

            "<parent-name>" +

            "<first-name>NARAYANA</first-name>" +

            "<middle-name>RAO</middle-name>" +

            "<last-name />" +

            "</parent-name>" +

            "<edu-qualification>31</edu-qualification>" +

            "<identification-marks />" +

            "<identification-marks />" +

            "<blood-group>O-</blood-group>" +

            "<permanent-address>" +

            "<p-flat-house-no>Flat-102, SAI APTS</p-flat-house-no>" +

            "<p-street-locality>39, 19th crs, muneeswar nagar</p-street-locality>" +

            "<p-village-city>tc palya main road</p-village-city>" +

            "<p-district>bangalore</p-district>" +

            "<p-state>KA</p-state>" +

            "<p-pin>560016</p-pin>" +

            "<p-phone-no />" +

            "<p-mobile-no>9573443091</p-mobile-no>" +

            "<p-durationofstay>" +

            "<p-years />" +

            "<p-months />" +

            "</p-durationofstay>" +

            "</permanent-address>" +

            "<temporary-address>" +

            "<t-flat-house-no>Flat-102, SAI APTS</t-flat-house-no>" +

            "<t-street-locality>39, 19th crs, muneeswar nagar</t-street-locality>" +

            "<t-village-city>tc palya main road</t-village-city>" +

            "<t-district>bangalore</t-district>" +

            "<t-state>KA</t-state>" +

            "<t-pin>560016</t-pin>" +

            "<t-phone-no />" +

            "<t-durationofstay>" +

            "<t-years />" +

            "<t-months />" +

            "</t-durationofstay>" +

            "</temporary-address>" +

            "<citizenship-status type=\"birth\" />" +

            "<birth-place>Warangal</birth-place>" +

            "<migration>" +

            "<year />" +

            "<month />" +

            "</migration>" +

            "<birth-country>IND</birth-country>" +

            "<email-id>sasdfs23-sd@abc.com</email-id>" +

            "<list-of-proofs>" +

            "<doc>" +

            "<proofcode>1</proofcode>" +

            "<licence-certificate-badge-no>c1</licence-certificate-badge-no>" +

            "<issuing-authority>i1</issuing-authority>" +

            "<date-of-issue>02-12-1992</date-of-issue>" +

            "</doc>" +

            "<doc>" +

            "<proofcode>3</proofcode>" +

            "<licence-certificate-badge-no>c2</licence-certificate-badge-no>" +

            "<issuing-authority>i2</issuing-authority>" +

            "<date-of-issue>02-12-2011</date-of-issue>" +

            "</doc>" +

            "<doc>" +

            "<proofcode>O</proofcode>" +

            "<licence-certificate-badge-no />" +

            "<issuing-authority />" +

            "<date-of-issue>02-12-2014</date-of-issue>" +

            "</doc>" +

            "</list-of-proofs>" +

            "<covs>3,4</covs>" +

            "<rcnumber />" +

            "<parentleterforbelow18age type=\"n\" />" +

            "<allnecessarycertificates type=\"y\" />" +

            "<exemptedmedicaltest type=\"n\" />" +

            "<exemptedpreliminarytest type=\"n\" />" +

            "<convicted type=\"n\" />" +

            "<attachdoc>" +

            "<attdlnumber />" +

            "<attdtofconviction />" +

            "<attreason />" +

            "</attachdoc>" +

            "</applicant>" +

            "</applicants>";

    private OnFragmentInteractionListener mListener;

    public CheckStatus() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CheckStatus.
     */
    // TODO: Rename and change types and number of parameters
    public static CheckStatus newInstance(String param1, String param2) {
        CheckStatus fragment = new CheckStatus();
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
        View view =  inflater.inflate(R.layout.fragment_check_status, container, false);
//        {"access_key":"L8YKJ41HP65M1DIOBVLY","signup-id":"e9d6i0fazk-signup","signup-secret":"9903a947ac90c8ae5406dbbd60febe53","signin-id":"e9d6i0fazk-signin","signin-secret":"15502bc50389b1e7b18809abe6e586e8","vanity":"e9d6i0fazk"}


//        CitrusClient citrusClient = CitrusClient.getInstance(getActivity()); //pass Activity Context
//        citrusClient.init("signup-id", "e9d6i0fazk-signup", "test-signin",
//                "52f7e15efd4208cf5345dd554443fd99", "prepaid", Environment.SANDBOX);
//
//        CitrusClient.getInstance(getActivity()).getMerchantPaymentOptions(new
//
//                                                                                  Callback<MerchantPaymentOption>() {
//
//                                                                                      @Override
//
//                                                                                      public void success(MerchantPaymentOption mMerchantPaymentOption)
//
//                                                                                      {
//
//                                                                                          ArrayList<NetbankingOption> mNetbankingOptionsList =
//
//                                                                                                  mMerchantPaymentOption.getNetbankingOptionList();//this will give you only bank list that you can show.
//
//
//                                                                                      }
//
//                                                                                      @Override
//
//                                                                                      public void error(CitrusError error) {
//
//                                                                                      }
//
//                                                                                  });
//        //Method to Send data to api.
////        sendPostRequest(view);
//        Button buttonCheckStatus = (Button) view.findViewById(R.id.buttonCheckStatus);
//        buttonCheckStatus.setOnClickListener(this);
        return view;
    }

    //Method to Encode to Base64
    private String endcodetoBase64(String s) throws UnsupportedEncodingException {
        byte[] byteArray = s.getBytes("UTF-8");
       return Base64.encodeToString(byteArray,0);
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
    public void onClick(View v) {
        payment(v);
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


    public void sendPostRequest(View View) {
        new PostClass(getActivity()).execute();
    }

    public void PayRequest(View View) {
        new sendPayRequest(getActivity()).execute();
    }

    public void payment(View View) {
        new sendPayRequest(getActivity()).execute();
    }

    //Class to Post Data in Background
    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public PostClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            HttpURLConnection connection=null;
            try {

//                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://164.100.148.109:8080/SOW3LLDLWS_MH/rsServices/AgentChoiceBusiness/readXMLFile");

                 connection = (HttpURLConnection) url.openConnection();
//                String urlParameters ="base64file="+endcodetoBase64(s)+"&agentID=smartchip"+"&password=3998151263B55EB10F7AE1A974FD036E";

                //Creating json object.
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("base64file", endcodetoBase64(s));
                jsonObject.accumulate("agentID", "smartchip");
                jsonObject.accumulate("password", "3998151263B55EB10F7AE1A974FD036E");
                jsonObject.accumulate("seckey","");

                String json = jsonObject.toString();


                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
//                connection.setDoInput(true);
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



            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }

            return null;
        }
    }


    private class sendPayRequest extends AsyncTask<Void, Integer, Long> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public sendPayRequest(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Downloading");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected Long doInBackground(Void... params)
        {
            Long l=0L;
            try
            {
                URL url = new URL("http://27.251.76.25:9012/DemoWebServices/resources/data/info");

                JSONObject jsonObject = new JSONObject();

                JSONObject jsonData = new JSONObject();
                JSONObject jsonlist = new JSONObject();
                JSONObject jsonlist1 = new JSONObject();
                JSONObject jsonlist2 = new JSONObject();
                JSONObject jsonlist3 = new JSONObject();

                jsonlist1.put("amount","1");
                jsonlist1.put("code","11");
                jsonlist1.put("hoa","1234456778898");


                jsonlist2.put("amount","1");
                jsonlist2.put("code","11");
                jsonlist2.put("hoa","11455656");


                jsonlist3.put("amount","1");
                jsonlist3.put("code","11");
                jsonlist3.put("hoa","1998878776676");

                JSONArray arraylist = new JSONArray();
                arraylist.put(jsonlist1);
                arraylist.put(jsonlist2);
                arraylist.put(jsonlist3);

                jsonlist.put("list",arraylist);

                jsonData.put("ref","123456789");
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
                connection.setConnectTimeout(15000);
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
        protected void onPostExecute(Long result) {

            progress.dismiss();
            if(result==1)
            {

            }
            else
            {

            }
        }

    }
}
