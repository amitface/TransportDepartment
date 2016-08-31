package com.converge.transportdepartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DatePicker.DatePickerFragmentStatus;
import com.converge.transportdepartment.Utility.ConValidation;

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

//    String s =  "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
//                "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
//                "<applicants>"+
//                "<applicant refno=\"15\">"+
//                            "<statecode>OD</statecode>"+
//                            "<rtocode>OD01</rtocode>"+
//                            "<licence-type>L</licence-type>"+
//                            "<applicant-name>" +
//                                "<first-name>Ramana</first-name>" +
//                                "<middle-name>Rao</middle-name>" +
//                                "<last-name>Yogi</last-name>" +
//                            "</applicant-name>"+
//                            "<dob>05-08-1989</dob>" +
//                            "<gender type=\"male\"/>" +
//                            "<relation type=\"father\"/>"+
//                            "<parent-name>" +
//                                "<first-name>Shankar</first-name>" +
//                                "<middle-name>rao</middle-name>" +
//                                "<last-name>Yogi</last-name>" +
//                            "</parent-name>"+
//                            "<edu-qualification>4</edu-qualification>" +
//                            "<identification-marks>A mole on the right side of the neck</identification-marks>" +
//                            "<identification-marks/>"+
//                            "<blood-group>O+</blood-group>"+
//                            "<permanent-address>" +
//                                "<p-flat-house-no>21</p-flat-house-no>" +
//                                "<p-street-locality>Ramarajyanagar</p-street-locality>" +
//                                "<p-village-city>Hyderabad</p-village-city>" +
//                                "<p-district>Ranga Reddy</p-district>" +
//                                "<p-state>OD</p-state>" +
//                                "<p-pin>750012</p-pin>" +
//                                "<p-phone-no></p-phone-no>" +
//                                "<p-mobile-no>9533241448</p-mobile-no>" +
//                                "<p-durationofstay>" +
//                                    "<p-years>5</p-years>" +
//                                    "<p-months>5</p-months>" +
//                                "</p-durationofstay>" +
//                             "</permanent-address>" +
//                             "<present-address>" +
//                                "<t-flat-house-no>21</t-flat-house-no>" +
//                                "<t-street-locality>Ramarajyanagar</t-street-locality>" +
//                                "<t-village-city>Hyderabad</t-village-city>" +
//                                "<t-district> Ranga Reddy </t-district>" +
//                                "<t-state>OD</t-state>" +
//                                "<t-pin>750012</t-pin>" +
//                                "<t-phone-no></t-phone-no>" +
//                                "<t-durationofstay>" +
//                                  "<t-years>5</t-years>" +
//                                  "<t-months>5</t-months>" +
//                                "</t-durationofstay>" +
//                             "</present-address>"+
//                        "<citizenship-status type=\"birth\"/>" +
//                        "<birth-place>Warangal</birth-place>" +
//                        "<migration>" +
//                            "<year>2012</year>" +
//                            "<month>05</month>" +
//                        "</migration>" +
//                        "<birth-country>IND</birth-country>" +
//                        "<email-id>ramana@gmail.com</email-id>" +
//                        "<list-of-proofs>" +
//                        "<doc>" +
//                        "<proofcode>D</proofcode>" +
//                        "<licence-certificate-badge-no>MH0119760129144</licence-certificate-badge-no>" +
//                        "<issuing-authority>MH01</issuing-authority>" +
//                        "<date-of-issue>18-07-2007</date-of-issue>" +
//                        "</doc>" +
//                        "</list-of-proofs>"+
//                        "<covs>3</covs>" +
//                        "<rcnumber/>"+
//                        "<parentleterforbelow18age/>"+
//                        "<allnecessarycertificates type=\"y\"/>"+
//                        "<exemptedmedicaltest type=\"n\"/>"+
//                        "<exemptedpreliminarytest type=\"n\"/>"+
//                        "<convicted type=\"n\"/>"+
//                            "<attachdoc>" +
//                                "<attdlnumber/>" +
//                                "<attdtofconviction/>" +
//                                "<attreason/>" +
//                            "</attachdoc>"+
//                "</applicant>" +
//            "</applicants>";

    //s2 is working
    String s2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
            "<applicants>"
             +
            "<applicant refno=\"15\">" +

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

    //s2 is working
    String s3="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
            "<applicants>"
            +
            "<applicant refno=\"19\">" +

            "<statecode>OD</statecode>" +

            "<rtocode>OD22</rtocode>" +

            "<licence-type>l</licence-type>" +

            "<applicant-name>" +

            "<first-name>JIGNESH</first-name>" +

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

            "<p-district>Bhadrak</p-district>" +

            "<p-state>OD</p-state>" +

            "<p-pin>760016</p-pin>" +

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

            "<t-district>Bhadrak</t-district>" +

            "<t-state>OD</t-state>" +

            "<t-pin>760016</t-pin>" +

            "<t-phone-no />" +

            "<t-durationofstay>" +

            "<t-years />" +

            "<t-months />" +

            "</t-durationofstay>" +

            "</temporary-address>" +

            "<citizenship-status type=\"birth\" />" +

            "<birth-place>Bhadrak</birth-place>" +

            "<migration>" +

            "<year />" +

            "<month />" +

            "</migration>" +

            "<birth-country>IND</birth-country>" +

            "<email-id>amit.choudhary@cnvg.in</email-id>" +

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

            "<covs>3,4,2,10,53</covs>" +

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

    private Button button, buttonSave, buttonSaveSlot;
    private EditText eTRefNum;
    public static TextView textDateofIssueStatus;

    private ImageView datePickerStatus;

    private OnFragmentInteractionListener mListener;
    private String jsonData;

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
        button =(Button) view.findViewById(R.id.buttonCheckStatus);
        buttonSave =(Button) view.findViewById(R.id.buttonSave);
        buttonSaveSlot =(Button) view.findViewById(R.id.buttonSaveSlot);
        datePickerStatus = (ImageView) view.findViewById(R.id.datePickerStatus);


        datePickerStatus.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        button.setOnClickListener(this);
        buttonSaveSlot.setOnClickListener(this);
        eTRefNum = (EditText) view.findViewById(R.id.eTRefNum);
        textDateofIssueStatus = (TextView) view.findViewById(R.id.eTDob);
        String db=s2;
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.eTRefNum:
                break;
            case R.id.buttonCheckStatus:
                if(validate())
                getApplicant(Integer.parseInt(eTRefNum.getText().toString()),textDateofIssueStatus.getText().toString());
                break;
            case R.id.buttonSave:
                sendPostRequest(v);
                break;
            case R.id.buttonSaveSlot:
                saveSlot(5,"14-06-1989",3064487L);
                break;
            case R.id.datePickerStatus:
                DialogFragment newFragment = new DatePickerFragmentStatus();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
        }
    }

    private boolean validate()
    {
        if(!ConValidation.isNetworkAvailable(getActivity()))
        {
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.noInternet),Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(eTRefNum.getText().length()<=6)
        {
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.refLength),Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(textDateofIssueStatus.getText().length()==0)
        {
            Toast.makeText(getActivity(),getActivity().getResources().getString(R.string.dateCEmpty),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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



    //Class to Post Data in Background
    public class PostClass extends AsyncTask<String, Void, Integer> {

        private final Context context;

        public PostClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection connection=null;
            try {

//                final TextView outputView = (TextView) findViewById(R.id.showOutput);
                URL url = new URL("http://164.100.148.109:8080/SOW3LLDLWS_MH/rsServices/AgentChoiceBusiness/readXMLFile");

                connection = (HttpURLConnection) url.openConnection();

                //Creating json object.
                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("base64file", endcodetoBase64(s3));
                jsonObject.accumulate("agentID", "smartchip");
                jsonObject.accumulate("password", "3998151263B55EB10F7AE1A974FD036E");
                jsonObject.accumulate("seckey","");

                String json = jsonObject.toString();

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("POST");
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
                String str [] = responseOutput.toString().split("|");

                if(str[0].equals("Success"))
                {
                    return 1;
                }
                else
                    return 0;




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
        protected void onPostExecute(Integer result) {

            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(),"Success",Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else
            {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }
        }

    }



//    3064482
    private void getApplicant(int i, String dob)
    {
        if(ConValidation.isNetworkAvailable(getActivity()))
        {
            new getApplicantData(getActivity(), i, dob).execute();
        }
        else
        {
            Toast.makeText(getActivity(),"Please check network connection",Toast.LENGTH_SHORT).show();
        }
        }

    //working now 16/08/2016

    private class getApplicantData extends AsyncTask<Void, Integer, Long>
    {
        private ProgressDialog progress;
        private final Context context;
        private ProgressDialog progressSendMail;
        private int i;
        private String dob;

        public getApplicantData(Context c, int i, String dob) {
            this.context = c;
            this.i=i;
            this.dob=dob;
        }
        protected void onPreExecute() {
            progressSendMail = new ProgressDialog(this.context);
            progressSendMail.setMessage("Please Wait");
            progressSendMail.setCancelable(false);
            progressSendMail.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressSendMail.setProgress(0);
            progressSendMail.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{

                URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/ApplcntDetails/getApplDet");

                connection = (HttpURLConnection) url.openConnection();
                //Creating json object.

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("applNo",i);
                jsonObject.put("dob", dob);
                jsonObject.put("servType", "LL");
                jsonObject.put("usrName", "smartchip");
                jsonObject.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
                jsonObject.put("serviceName","LLSlotBook");

                String json = jsonObject.toString();
                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(200000);
                connection.setReadTimeout(200000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(json);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print("ResponseCode ====  "+responseCode+"\nRespone === " +connection.getResponseMessage()+"\n");

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();
//                responseOutput.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
                System.out.println(responseOutput.toString());
                JSONObject jsonObject1 = new JSONObject(responseOutput.toString());
                JSONObject jsonObject2 = jsonObject1.getJSONObject("retobj");
                jsonData=jsonObject2.getString("msg");
                return 1L;
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0L;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 0L;
            } catch (IOException e) {
                e.printStackTrace();
                return 0L;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return 0L;
            }

        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progressSendMail.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {
            progressSendMail.hide();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    Toast.makeText(getActivity(),jsonData,Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else
            {
                Toast.makeText(getActivity(),"Error "+jsonData,Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveSlot(int i, String dob, long l)
    {
        new saveApplicantSlot(getActivity(),i,dob,l).execute();
    }

    private class saveApplicantSlot extends AsyncTask<Void, Integer, Long>
    {
        private ProgressDialog progress;
        private final Context context;
        private ProgressDialog progressSendMail;
        private int i;
        private String dob;
        private Long refNum;

        public saveApplicantSlot(Context c, int i, String dob, Long refNum) {
            this.context = c;
            this.i=i;
            this.dob=dob;
            this.refNum=refNum;
        }
        protected void onPreExecute() {
            progressSendMail = new ProgressDialog(this.context);
            progressSendMail.setMessage("Please Wait");
            progressSendMail.setCancelable(false);
            progressSendMail.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressSendMail.setProgress(0);
            progressSendMail.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{
                URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/SaveSlotDetServ/insSltDet");
                connection = (HttpURLConnection) url.openConnection();
                //Creating json object.

                JSONObject jsonObject2 = new JSONObject();

                jsonObject2.put("applno", refNum);
                jsonObject2.put("serviceType", "LL");
                jsonObject2.put("agentId", "smartchip");
                jsonObject2.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
                jsonObject2.put("serviceName","LLSlotBook");
                jsonObject2.put("rtocode","OD02");
                jsonObject2.put("slotDate","1471503953386");
                jsonObject2.put("slotNo",i);

                String json = jsonObject2.toString();
                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(200000);
                connection.setReadTimeout(200000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(json);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print("ResponseCode ====  "+responseCode+"\nRespone === " +connection.getResponseMessage()+"\n");

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();
//                responseOutput.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
                System.out.println(responseOutput.toString());
                jsonData=responseOutput.toString();
                return 1L;
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0L;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 0L;
            } catch (IOException e) {
                e.printStackTrace();
                return 0L;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return 0L;
            }

        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progressSendMail.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {
            progressSendMail.hide();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
            else
            {
                Toast.makeText(getActivity(),"Error",Toast.LENGTH_SHORT).show();
            }
        }
    }


}

