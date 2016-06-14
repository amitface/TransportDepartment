package com.converge.transportdepartment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
public class CheckStatus extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String s = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+"<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
            "<applicants>"+
                "<applicant refno=\"1\">"+
                    "<statecode>AP</statecode>"+
                            "<rtocode>AP028</rtocode>"+
                            "<licence-type>L</licence-type>"+
                            "<applicant-name>" +
                                "<first-name>Ramana</first-name>" +
                                "<middle-name>Rao</middle-name>" +
                                "<last-name>Yogi</last-name>" +
                                "</applicant-name>"+
                            "</applicant-name>"+
                            "<dob>05-08-1989</dob>" +
                            "<gender type=\"male\"/>" +
                            "<relation type=\"father\"/>"+
                            "<parent-name>" +
                                "<first-name>Shankar</first-name>" +
                                "<middle-name>rao</middle-name>" +
                                "<last-name>Yogi</last-name>" +
                            "</parent-name>"+
                            "<edu-qualification>50</edu-qualification>" +
                            "<identification-marks>A mole on the right side of the neck</identification-marks>" +
                            "<identification-marks></identification-marks>" +
                            "<blood-group>O+</blood-group>"+
                            "<permanent-address>" +
                                "<p-flat-house-no>21</p-flat-house-no>" +
                                "<p-street-locality>Ramarajyanagar</p-street-locality>" +
                                "<p-village-city>Hyderabad</p-village-city>" +
                                "<p-district> Ranga Reddy </p-district>" +
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
                                "<t-street-locality>Ramarajyanagar</t-street-locality>" +                                "\t\t\t<t-village-city>Hyderabad</t-village-city>\n" +
                                "<t-district> Ranga Reddy </t-district>" +
                                "<t-state>AP</t-state>" +
                                "<t-pin>500012</t-pin>" +
                                "<t-phone-no></t-phone-no>" +
                                "<t-mobile-no>9533241448</t-mobile-no>" +
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
                         "<covs>58</covs>" +
                "</applicant>" +
            "</applicants>";
    String s1 = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4NCjwhRE9DVFlQRSBhcHBsaWNhbnRzIFBVQkxJQyAiLy9OYXRpb25hbCBJbmZvcm1hdGljcyBDZW50ZXIvIiAiLi4vLi4vZmlsZXNfdWMwOS9sbGZvcm0uZHRkIj4NCjxhcHBsaWNhbnRzPg0KCTxhcHBsaWNhbnQgcmVmbm89IjIiPg0KCQk8c3RhdGVjb2RlPkFQPC9zdGF0ZWNvZGU+DQoJCTxydG9jb2RlPkFQMDI4PC9ydG9jb2RlPg0KCQk8bGljZW5jZS10eXBlPkw8L2xpY2VuY2UtdHlwZT4NCgkJPGFwcGxpY2FudC1uYW1lPg0KCQkJPGZpcnN0LW5hbWU+UmFtYW5hPC9maXJzdC1uYW1lPg0KCQkJPG1pZGRsZS1uYW1lPlJhbzwvbWlkZGxlLW5hbWU+DQoJCQk8bGFzdC1uYW1lPllvZ2k8L2xhc3QtbmFtZT4NCgkJPC9hcHBsaWNhbnQtbmFtZT4NCgkJPGRvYj4wNS0wOC0xOTg5PC9kb2I+DQoJCTxnZW5kZXIgdHlwZT0ibWFsZSIvPg0KCQk8cmVsYXRpb24gdHlwZT0iZmF0aGVyIi8+DQoJCTxwYXJlbnQtbmFtZT4NCgkJCTxmaXJzdC1uYW1lPlNoYW5rYXI8L2ZpcnN0LW5hbWU+DQoJCQk8bWlkZGxlLW5hbWU+cmFvPC9taWRkbGUtbmFtZT4NCgkJCTxsYXN0LW5hbWU+WW9naTwvbGFzdC1uYW1lPg0KCQk8L3BhcmVudC1uYW1lPg0KCQk8ZWR1LXF1YWxpZmljYXRpb24+NTA8L2VkdS1xdWFsaWZpY2F0aW9uPg0KCQk8aWRlbnRpZmljYXRpb24tbWFya3M+QSBtb2xlIG9uIHRoZSByaWdodCBzaWRlIG9mIHRoZSBuZWNrPC9pZGVudGlmaWNhdGlvbi1tYXJrcz4NCgkJPGlkZW50aWZpY2F0aW9uLW1hcmtzPjwvaWRlbnRpZmljYXRpb24tbWFya3M+DQoJCTxibG9vZC1ncm91cD5PKzwvYmxvb2QtZ3JvdXA+DQoJCTxwZXJtYW5lbnQtYWRkcmVzcz4NCgkJCTxwLWZsYXQtaG91c2Utbm8+MjE8L3AtZmxhdC1ob3VzZS1ubz4NCgkJCTxwLXN0cmVldC1sb2NhbGl0eT5SYW1hcmFqeWFuYWdhcjwvcC1zdHJlZXQtbG9jYWxpdHk+DQoJCQk8cC12aWxsYWdlLWNpdHk+SHlkZXJhYmFkPC9wLXZpbGxhZ2UtY2l0eT4NCgkJCTxwLWRpc3RyaWN0PiBSYW5nYSBSZWRkeSA8L3AtZGlzdHJpY3Q+DQoJCQk8cC1zdGF0ZT5BUDwvcC1zdGF0ZT4NCgkJCTxwLXBpbj41MDAwMTI8L3AtcGluPg0KCQkJPHAtcGhvbmUtbm8+PC9wLXBob25lLW5vPg0KCQkJPHAtbW9iaWxlLW5vPjk1MzMyNDE0NDg8L3AtbW9iaWxlLW5vPg0KCQkJPHAtZHVyYXRpb25vZnN0YXk+DQoJCQkJPHAteWVhcnM+NTwvcC15ZWFycz4NCgkJCQk8cC1tb250aHM+NTwvcC1tb250aHM+DQoJCQk8L3AtZHVyYXRpb25vZnN0YXk+DQoJCTwvcGVybWFuZW50LWFkZHJlc3M+DQoJCTxwcmVzZW50LWFkZHJlc3M+DQoJCQk8dC1mbGF0LWhvdXNlLW5vPjIxPC90LWZsYXQtaG91c2Utbm8+DQoJCQk8dC1zdHJlZXQtbG9jYWxpdHk+UmFtYXJhanlhbmFnYXI8L3Qtc3RyZWV0LWxvY2FsaXR5Pg0KCQkJPHQtdmlsbGFnZS1jaXR5Pkh5ZGVyYWJhZDwvdC12aWxsYWdlLWNpdHk+DQoJCQk8dC1kaXN0cmljdD4gUmFuZ2EgUmVkZHkgPC90LWRpc3RyaWN0Pg0KCQkJPHQtc3RhdGU+QVA8L3Qtc3RhdGU+DQoJCQk8dC1waW4+NTAwMDEyPC90LXBpbj4NCgkJCTx0LXBob25lLW5vPjwvdC1waG9uZS1ubz4NCgkJCTx0LW1vYmlsZS1ubz45NTMzMjQxNDQ4PC90LW1vYmlsZS1ubz4NCgkJCTx0LWR1cmF0aW9ub2ZzdGF5Pg0KCQkJCTx0LXllYXJzPjU8L3QteWVhcnM+DQoJCQkJPHQtbW9udGhzPjU8L3QtbW9udGhzPg0KCQkJPC90LWR1cmF0aW9ub2ZzdGF5Pg0KCQk8L3ByZXNlbnQtYWRkcmVzcz4NCgkJPGNpdGl6ZW5zaGlwLXN0YXR1cyB0eXBlPSJiaXJ0aCIvPg0KCQk8YmlydGgtcGxhY2U+V2FyYW5nYWw8L2JpcnRoLXBsYWNlPg0KCQk8bWlncmF0aW9uPg0KCQkJPHllYXI+MjAxMjwveWVhcj4NCgkJCTxtb250aD4wNTwvbW9udGg+DQoJCTwvbWlncmF0aW9uPg0KCQk8YmlydGgtY291bnRyeT5JTkQ8L2JpcnRoLWNvdW50cnk+DQoJCTxlbWFpbC1pZD5yYW1hbmFAZ21haWwuY29tPC9lbWFpbC1pZD4NCgkJPGxpc3Qtb2YtcHJvb2ZzPg0KCQk8ZG9jPg0KCQk8cHJvb2Zjb2RlPkQ8L3Byb29mY29kZT4NCgkJPGxpY2VuY2UtY2VydGlmaWNhdGUtYmFkZ2Utbm8+TUgwMTE5NzYwMTI5MTQ0PC9saWNlbmNlLWNlcnRpZmljYXRlLWJhZGdlLW5vPg0KCQk8aXNzdWluZy1hdXRob3JpdHk+TUgwMTwvaXNzdWluZy1hdXRob3JpdHk+DQoJCTxkYXRlLW9mLWlzc3VlPjE4LTA3LTIwMDc8L2RhdGUtb2YtaXNzdWU+DQoJCTwvZG9jPg0KCQk8L2xpc3Qtb2YtcHJvb2ZzPg0KCQk8Y292cz41ODwvY292cz4NCgk8L2FwcGxpY2FudD4NCjwvYXBwbGljYW50cz4=";
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
        sendPostRequest(view);
        return view;
    }


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

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public PostClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... params) {
            try {

//                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://164.100.148.109:8080/SOW3LLDLWS_MH/rsServices/AgentChoiceBusiness/readXMLFile");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters ="base64file="+s1+"&agentID=smartchip" +

                        "&password=3998151263B55EB10F7AE1A974FD036E";


//                "fName=" + URLEncoder.encode("Amit", "UTF-8") +
//                                "&lName=" + URLEncoder.encode("???", "UTF-8")
                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/TEXT_PLAIN");
                connection.setRequestProperty("Accept", "application/TEXT_PLAIN");

                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
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
//                {"success":1,"referenceId":"MPO91000000001"referenceId" -> "1"","receiptNum":1000000001}



            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }
    }
}
