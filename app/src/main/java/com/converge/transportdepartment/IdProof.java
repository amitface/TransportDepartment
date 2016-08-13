package com.converge.transportdepartment;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DataBaseHelper.DBAdapter;
import com.converge.transportdepartment.DatePicker.DatePickerFragment1;
import com.converge.transportdepartment.DatePicker.DatePickerFragment2;
import com.converge.transportdepartment.DatePicker.DatePickerFragment3;
import com.converge.transportdepartment.DatePicker.DatePickerFragment4;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdProof#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdProof extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner spinnerIdcard1, spinnerIdcard2, spinnerIdcard3, spinnerIdcard4;
    private EditText editTextDocumentNum1,editTextIssuingAuthority1;
    private EditText editTextDocumentNum2,editTextIssuingAuthority2;
    private EditText editTextDocumentNum3,editTextIssuingAuthority3;
    private EditText editTextDocumentNum4,editTextIssuingAuthority4;
    public static TextView editTextDateofIssue1,editTextDateofIssue2,editTextDateofIssue3,editTextDateofIssue4;

    TableLayout tableLayout2;
    TableLayout tableLayout3;
    TableLayout tableLayout4;



    private final String mFinalString1="mFinalString1";
    private final String NICjson= "NICjson";

    String s1,s2;
    String idCode[]={"","B", "C", "D", "F","H","I","L","T","V","Z","E", "A", "1",
            "2", "3", "P", "4", "5", "6", "7"};

    ImageView img2,img1,imageViewDatePicker1,imageViewDatePicker2,imageViewDatePicker3,imageViewDatePicker4;

    public static final String mypreference = "mypref";
    private static int count=0;
    private SharedPreferences sharedpreferences;
    private final String mFinalString2="mFinalString2";
    private final String mFinalStringCov="mFinalStringCov";

    private String s3;
    private HashMap<String,String> hashMap = new HashMap<String, String>();;

    public IdProof() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IdProof.
     */
    // TODO: Rename and change types and number of parameters
    public static IdProof newInstance(String param1, String param2) {
        IdProof fragment = new IdProof();
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
//        return
            count=0;
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        View view =  inflater.inflate(R.layout.fragment_id_proof, container, false);
        ImageView button = (ImageView)view.findViewById(R.id.buttonNextIdProof);
        ImageView buttonback = (ImageView)view.findViewById(R.id.buttonBackIdProof);
        ImageView buttonClear = (ImageView)view.findViewById(R.id.buttonClearIdProof);


        buttonback.setOnClickListener(this);
        button.setOnClickListener(this);
        buttonClear.setOnClickListener(this);

        initalize(view);
        setListner(view);

            return view;
    }

    @Override
    public void onPause ()
    {
        super.onPause();
        Log.d("onPause :"," saving session");
        saveSession();
        Log.d("Session :"," saved session");
    }


    @Override
    public void onResume ()
    {
        super.onResume();
        Log.d("onResume :"," retrieving  session Id proof");
        retrivesession();
        Log.d("Session :"," Id proof session retrieved");
    }
    private void setListner(View view) {
        imageViewDatePicker1.setOnClickListener(this);
        imageViewDatePicker2.setOnClickListener(this);
        imageViewDatePicker3.setOnClickListener(this);
        imageViewDatePicker4.setOnClickListener(this);

        img1.setOnClickListener(this);
        img2.setOnClickListener(this);


    }


    private void initalize(View view) {
        tableLayout2 = (TableLayout) view.findViewById(R.id.table2);
        tableLayout3 = (TableLayout) view.findViewById(R.id.table3);
        tableLayout4 = (TableLayout) view.findViewById(R.id.table4);

         img2 =(ImageView) view.findViewById(R.id.buttonRemove);
         img1 =(ImageView) view.findViewById(R.id.buttonAdd);

        spinnerIdcard1 = (Spinner)view.findViewById(R.id.spinnerIdcard1);
        spinnerIdcard2 = (Spinner)view.findViewById(R.id.spinnerIdcard2);
        spinnerIdcard3 = (Spinner)view.findViewById(R.id.spinnerIdcard3);
        spinnerIdcard4 = (Spinner)view.findViewById(R.id.spinnerIdcard4);

        editTextDocumentNum1 = (EditText)view.findViewById(R.id.editTextDocumentNum1);
        editTextIssuingAuthority1 =(EditText) view.findViewById(R.id.editTextIssuingAuthority1);
        editTextDateofIssue1 = (TextView) view.findViewById(R.id.editTextDateofIssue1);
        editTextDocumentNum2= (EditText)view.findViewById(R.id.editTextDocumentNum2);
        editTextIssuingAuthority2 =(EditText) view.findViewById(R.id.editTextIssuingAuthority2);
        editTextDateofIssue2 = (TextView) view.findViewById(R.id.editTextDateofIssue2);
        editTextDocumentNum3 = (EditText)view.findViewById(R.id.editTextDocumentNum3);
        editTextIssuingAuthority3 =(EditText) view.findViewById(R.id.editTextIssuingAuthority3);
        editTextDateofIssue3 = (TextView) view.findViewById(R.id.editTextDateofIssue3);
        editTextDocumentNum4 = (EditText)view.findViewById(R.id.editTextDocumentNum4);
        editTextIssuingAuthority4 =(EditText) view.findViewById(R.id.editTextIssuingAuthority4);
        editTextDateofIssue4 = (TextView) view.findViewById(R.id.editTextDateofIssue4);

        imageViewDatePicker1 = (ImageView) view.findViewById(R.id.imageViewDatePicker1);
        imageViewDatePicker2 = (ImageView) view.findViewById(R.id.imageViewDatePicker2);
        imageViewDatePicker3 = (ImageView) view.findViewById(R.id.imageViewDatePicker3);
        imageViewDatePicker4 = (ImageView) view.findViewById(R.id.imageViewDatePicker4);
        retrivesession();

    }



    public void onClickIdProof(View view) {

    }

    private boolean validate() {
        if(spinnerIdcard1.getSelectedItemPosition()==0)
        {
            showToast("Enter Id proof");
            return false;
        }else if(editTextDocumentNum1.getText().length()==0)
        {
            showToast("Enter Document Number");
            return false;
        }else if(editTextIssuingAuthority1.getText().length()==0)
        {
            showToast("Enter Issuing Authority");
            return false;
        }else if(editTextDateofIssue1.getText().length()==0)
        {
            showToast("Enter Date of Issue");
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        DialogFragment newFragment;
        switch (view.getId()) {
            case R.id.buttonNextIdProof:
                if (validate()) {
                    detailNIC();
                    saveSharedPreference();
                    if (sharedpreferences.contains(mFinalString1)) {
                        s1 = sharedpreferences.getString(mFinalString1, "");
                        s2 = detailString();
                    }
//                    sendPostRequest();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("3", "1")).commit();
                }
                break;
            case R.id.buttonBackIdProof:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("1", "1")).commit();
                break;
            case R.id.buttonClearIdProof:
                clearALL();
                break;
            case R.id.imageViewDatePicker1:
                newFragment = new DatePickerFragment1();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.imageViewDatePicker2:
                newFragment = new DatePickerFragment2();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.imageViewDatePicker3:
                newFragment = new DatePickerFragment3();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.imageViewDatePicker4:
                newFragment = new DatePickerFragment4();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;

            case R.id.buttonBackPersonalDetail:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("1", "1")).commit();
                break;
            case R.id.buttonClearPersonalDetail:

                break;
            case R.id.buttonAdd:
                if (count == 0) {
                    count++;
                    tableLayout2.setVisibility(View.VISIBLE);
                } else if (count == 1) {
                    count++;
                    tableLayout3.setVisibility(View.VISIBLE);
                } else if (count == 2) {
                    count++;
                    tableLayout4.setVisibility(View.VISIBLE);
                } else if (count == 3) {
                    showToast("Cannot Add More");
                }
                break;
            case R.id.buttonRemove:
                if (count == 0) {
                    showToast("Cannot Remove More");
                } else if (count == 1) {
                    count--;
                    tableLayout2.setVisibility(View.INVISIBLE);
                    clearIdproof(1);
                } else if (count == 2) {
                    count--;
                    tableLayout3.setVisibility(View.INVISIBLE);
                    clearIdproof(2);
                } else if (count == 3) {
                    count--;
                    tableLayout4.setVisibility(View.GONE);
                    clearIdproof(3);
                }
                break;
        }
    }

    private void clearIdproof(int i) {
        if(i==0)
        {
            spinnerIdcard1.setSelection(0);
            editTextDocumentNum1.setText("");
            editTextIssuingAuthority1.setText("");
            editTextDateofIssue1.setText("");
        }
        else if(i==1)
        {
            spinnerIdcard2.setSelection(0);
            editTextDocumentNum2.setText("");
            editTextIssuingAuthority2.setText("");
            editTextDateofIssue2.setText("");
        }
        else if(i==2)
        {
            spinnerIdcard3.setSelection(0);
            editTextDocumentNum3.setText("");
            editTextIssuingAuthority3.setText("");
            editTextDateofIssue3.setText("");
        }
        else if(i==3)
        {
            spinnerIdcard4.setSelection(0);
            editTextIssuingAuthority4.setText("");
            editTextDocumentNum4.setText("");
            editTextDateofIssue4.setText("");
        }
    }

    private void clearALL() {

            spinnerIdcard1.setSelection(0);
            editTextDocumentNum1.setText("");
            editTextIssuingAuthority1.setText("");
            editTextDateofIssue1.setText("");

            spinnerIdcard2.setSelection(0);
            editTextDocumentNum2.setText("");
            editTextIssuingAuthority2.setText("");
            editTextDateofIssue2.setText("");

            spinnerIdcard3.setSelection(0);
            editTextDocumentNum3.setText("");
            editTextIssuingAuthority3.setText("");
            editTextDateofIssue3.setText("");

            spinnerIdcard4.setSelection(0);
            editTextIssuingAuthority4.setText("");
            editTextDocumentNum4.setText("");
            editTextDateofIssue4.setText("");

    }

    private void saveSharedPreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(mFinalString2, detailString());
        editor.commit();
        editor.putString(NICjson,jsonNIC());
        editor.apply();
    }


    private String detailString()
    {
        String proof1=idCode[spinnerIdcard1.getSelectedItemPosition()];

        String proof2=new String();
        if(spinnerIdcard2.getSelectedItemPosition()!=0)
        proof2 =idCode[spinnerIdcard2.getSelectedItemPosition()];

        String proof3=new String();
        if(spinnerIdcard3.getSelectedItemPosition()!=0)
            proof3 =idCode[spinnerIdcard3.getSelectedItemPosition()];

        String proof4=new String();
        if(spinnerIdcard4.getSelectedItemPosition()!=0)
            proof4 =idCode[spinnerIdcard4.getSelectedItemPosition()];
        String idProof=
//                "&proofcode%5B%5D="+idCode[spinnerIdcard1.getSelectedItemPosition()]+
//                "&proofcode%5B%5D="+idCode[spinnerIdcard2.getSelectedItemPosition()]+
//                "&proofcode%5B%5D="+idCode[spinnerIdcard3.getSelectedItemPosition()]+
//                "&proofcode%5B%5D="+idCode[spinnerIdcard4.getSelectedItemPosition()]+
                "&proofcode%5B%5D="+proof1+
                "&proofcode%5B%5D="+proof2+
                "&proofcode%5B%5D="+proof3+
                "&proofcode%5B%5D="+proof4+
                "&licence_certificate_badge_no%5B%5D="+editTextDocumentNum1.getText().toString()+
                "&licence_certificate_badge_no%5B%5D="+editTextDocumentNum2.getText().toString()+
                "&licence_certificate_badge_no%5B%5D="+editTextDocumentNum3.getText().toString()+
                "&licence_certificate_badge_no%5B%5D="+editTextDocumentNum4.getText().toString()+
                "&issuing_authority%5B%5D="+editTextIssuingAuthority1.getText().toString()+
                "&issuing_authority%5B%5D="+editTextIssuingAuthority2.getText().toString()+
                "&issuing_authority%5B%5D="+editTextIssuingAuthority3.getText().toString()+
                "&issuing_authority%5B%5D="+editTextIssuingAuthority4.getText().toString()+
                "&date_of_issue%5B%5D="+editTextDateofIssue1.getText().toString()+
                "&date_of_issue%5B%5D="+editTextDateofIssue2.getText().toString()+
                "&date_of_issue%5B%5D="+editTextDateofIssue3.getText().toString()+
                "&date_of_issue%5B%5D="+editTextDateofIssue4.getText().toString();
        return idProof;
    }

    private String jsonNIC() {
        JSONObject jsNic = new JSONObject();
        try {

           jsNic.put("proofcode",3);
           jsNic.put("licence-certificate-badge-no",3);
           jsNic.put("issuing-authority",3);
           jsNic.put("date-of-issue",3);

            return jsNic.toString();

        }catch (JSONException e)
        {
            //s2 is working
            String s2="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                    "<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">" +
                    "<applicants>"+

                    "<dob>14-06-1989</dob>" +

                    "<edu-qualification>31</edu-qualification>" +

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
//only till here
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

        }
        return jsNic.toString();
    }

    private String detailNIC()
    {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(sharedpreferences.getString(NICjson,"").toString());

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            stringBuffer.append("<!DOCTYPE applicants PUBLIC \"//National Informatics Center/\" \"../../files_uc09/llform.dtd\">");
            stringBuffer.append("<applicants>");
            stringBuffer.append("<applicant refno=").append(jsonObject.getString("refno")).append(">");
            stringBuffer.append("<statecode>").append(jsonObject.getString("statecode")).append("</statecode>");
            stringBuffer.append("<rtocode>OD").append(jsonObject.getString("rtocode")).append("</rtocode>");
            stringBuffer.append("<licence-type>").append(jsonObject.getString("licenceType")).append("</licence-type>");
            stringBuffer.append("<applicant-name>");
            stringBuffer.append("<first-name>").append(jsonObject.getString("aFName")).append("</first-name>");
            stringBuffer.append("<middle-name>").append(jsonObject.getString("aFMiddle")).append("</middle-name>");
            stringBuffer.append("<last-name>").append(jsonObject.getString("aFLast")).append("</last-name>");
            stringBuffer.append("</applicant-name>");
            stringBuffer.append("<dob>").append(jsonObject.getString("dob")).append("</dob>");
            stringBuffer.append("<gender type=\"").append(jsonObject.getString("gender")).append("\"/>");
            stringBuffer.append("<relation type=\"").append(jsonObject.getString("relation-type")).append("\"/>");
            stringBuffer.append("<parent-name>");
            stringBuffer.append("<first-name>").append(jsonObject.getString("pFName")).append("</first-name>");
            stringBuffer.append("<middle-name>").append(jsonObject.getString("pFMiddle")).append("</middle-name>");
            stringBuffer.append("<last-name>").append(jsonObject.getString("pFLast")).append("</last-name>");
            stringBuffer.append("</parent-name>");
            stringBuffer.append("<edu-qualification>").append("31").append("</edu-qualification>");//pending
            stringBuffer.append("<identification-marks>").append(jsonObject.getString("identification-marks")).append("</identification-marks>");
            stringBuffer.append("<identification-marks>").append(jsonObject.getString("identification-marks2")).append("</identification-marks>");
            stringBuffer.append("<blood-group>").append(jsonObject.getString("blood-group")).append("</blood-group>");
            stringBuffer.append("<permanent-address>");
            stringBuffer.append("<p-flat-house-no>").append(jsonObject.getString("pFlatHouseNo")).append("</p-flat-house-no>");
            stringBuffer.append("<p-street-locality>").append(jsonObject.getString("pStreetLocality")).append("</p-street-locality>");
            stringBuffer.append("<p-village-city>").append(jsonObject.getString("pVillageCity")).append("</p-village-city>");
            stringBuffer.append("<p-district>").append(jsonObject.getString("pDistrict")).append("</p-district>");
            stringBuffer.append("<p-state>").append("OD").append("</p-state>");
            stringBuffer.append("<p-pin>").append(jsonObject.getString("pPin")).append("</p-pin>");
            stringBuffer.append("<p-phone-no>").append(jsonObject.getString("pPhoneNo")).append("</p-phone-no>");
            stringBuffer.append("<p-mobile-no>").append(jsonObject.getString("pMobileNo")).append("</p-mobile-no>");
            stringBuffer.append("<p-durationofstay>");
            stringBuffer.append("<p-years>").append(jsonObject.getString("pYears")).append("</p-years>");
            stringBuffer.append("<p-months>").append(jsonObject.getString("pMonths")).append("</p-months>");
            stringBuffer.append("</p-durationofstay>");
            stringBuffer.append("</permanent-address>");

            stringBuffer.append("<temporary-address>");
            stringBuffer.append("<t-flat-house-no>").append(jsonObject.getString("tFlatHouseNo")).append("</t-flat-house-no>");
            stringBuffer.append("<t-street-locality>").append(jsonObject.getString("tStreetLocality")).append("</t-street-locality>");
            stringBuffer.append("<t-village-city>").append(jsonObject.getString("tVillageCity")).append("</t-village-city>");
            stringBuffer.append("<t-district>").append(jsonObject.getString("tDistrict")).append("</t-district>");
            stringBuffer.append("<t-state>").append("OD").append("</t-state>");
            stringBuffer.append("<t-pin>").append(jsonObject.getString("tPin")).append("</t-pin>");
            stringBuffer.append("<t-phone-no>").append(jsonObject.getString("tPhoneNo")).append("</t-phone-no>");
            stringBuffer.append("<t-mobile-no>").append(jsonObject.getString("tMobileNo")).append("</t-mobile-no>");
            stringBuffer.append("<t-durationofstay>");
            stringBuffer.append("<t-years>").append(jsonObject.getString("tYears")).append("</t-years>");
            stringBuffer.append("<t-months>").append(jsonObject.getString("tMonths")).append("</t-months>");
            stringBuffer.append("</t-durationofstay>");
            stringBuffer.append("</temporary-address>");
            stringBuffer.append("<citizenship-status type=\"").append(jsonObject.getString("citizenship-status")).append("\"/>");
            stringBuffer.append("<birth-place>").append(jsonObject.getString("birth-place")).append("</birth-place>");
            stringBuffer.append("<migration>");
            stringBuffer.append("<year>").append(jsonObject.getString("year")).append("</year>");
            stringBuffer.append("<month>").append(jsonObject.getString("month")).append("</month>");
            stringBuffer.append("</migration>");

            stringBuffer.append("<birth-country>").append(jsonObject.getString("birth-country")).append("</birth-country>");
            stringBuffer.append("<email-id>").append(jsonObject.getString("email-id")).append("</email-id>");
            stringBuffer.append("<list-of-proofs>");
            stringBuffer.append("<doc>");

            String proof1=idCode[spinnerIdcard1.getSelectedItemPosition()];
            stringBuffer.append("<proofcode>").append(proof1).append("</proofcode>");
            stringBuffer.append("<licence-certificate-badge-no>").append(editTextDocumentNum1.getText().toString()).append("</licence-certificate-badge-no>");
            stringBuffer.append("<issuing-authority>").append(editTextIssuingAuthority1.getText().toString()).append("</issuing-authority>");
            stringBuffer.append("<date-of-issue>").append(editTextDateofIssue1.getText().toString().replace("/","-")).append("</date-of-issue>");
            stringBuffer.append("</doc>");


            String proof2=new String();
            if(spinnerIdcard2.getSelectedItemPosition()!=0)
            {
                proof2 =idCode[spinnerIdcard2.getSelectedItemPosition()];
                stringBuffer.append("<doc>");
                stringBuffer.append("<proofcode>").append(proof2).append("</proofcode>");
                stringBuffer.append("<licence-certificate-badge-no>").append(editTextDocumentNum2.getText().toString()).append("<licence-certificate-badge-no>");
                stringBuffer.append("<issuing-authority>").append(editTextIssuingAuthority2.getText().toString()).append("</issuing-authority>");
                stringBuffer.append("<date-of-issue>").append(editTextDateofIssue2.getText().toString().replace("/","-")).append("</date-of-issue>");
                stringBuffer.append("</doc>");

            }

            String proof3=new String();
            if(spinnerIdcard3.getSelectedItemPosition()!=0)
            {
                proof3 =idCode[spinnerIdcard3.getSelectedItemPosition()];
                stringBuffer.append("<doc>");
                stringBuffer.append("<proofcode>").append(proof3).append("</proofcode>");
                stringBuffer.append("<licence-certificate-badge-no>").append(editTextDocumentNum3.getText().toString()).append("<licence-certificate-badge-no>");
                stringBuffer.append("<issuing-authority>").append(editTextIssuingAuthority3.getText().toString()).append("</issuing-authority>");
                stringBuffer.append("<date-of-issue>").append(editTextDateofIssue3.getText().toString().replace("/","-")).append("</date-of-issue>");
                stringBuffer.append("</doc>");
            }


            String proof4=new String();
            if(spinnerIdcard4.getSelectedItemPosition()!=0)
            {
                proof4 =idCode[spinnerIdcard4.getSelectedItemPosition()];
                stringBuffer.append("<doc>");
                stringBuffer.append("<proofcode>").append(proof4).append("</proofcode>");
                stringBuffer.append("<licence-certificate-badge-no>").append(editTextDocumentNum4.getText().toString()).append("<licence-certificate-badge-no>");
                stringBuffer.append("<issuing-authority>").append(editTextIssuingAuthority4.getText().toString()).append("</issuing-authority>");
                stringBuffer.append("<date-of-issue>").append(editTextDateofIssue4.getText().toString().replace("/","-")).append("</date-of-issue>");
                stringBuffer.append("</doc>");
            }

            stringBuffer.append("</list-of-proofs>");
            stringBuffer.append("<covs>").append(sharedpreferences.getString("mFinalStringCov","")).append("</covs>");
            stringBuffer.append("<rcnumber />").append("<parentleterforbelow18age type=\"n\"/>").append("<allnecessarycertificates type=\"y\"/>");
            stringBuffer.append("<exemptedmedicaltest type=\"n\"/>").append("<exemptedpreliminarytest type=\"n\"/>");
            stringBuffer.append("<allnecessarycertificates type=\"y\"/>").append("<convicted type=\"n\" />");
            stringBuffer.append("<attachdoc>");
            stringBuffer.append("<attdlnumber />");
            stringBuffer.append("<attdtofconviction />");
            stringBuffer.append("<attreason/>");
            stringBuffer.append("</attachdoc>");
            stringBuffer.append("</applicant>");
            stringBuffer.append("</applicants>");

            return stringBuffer.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

       return null;
    }
    private void showToast(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    private void retrivesession() {
        DBAdapter db = new DBAdapter(getActivity());

        //---get all contacts---
        db.open();
        Cursor c = db.getAllDetailsIdProof();
        if(c.moveToFirst()) {
            if(Integer.parseInt(c.getString(1))!=0)
                spinnerIdcard1.setSelection(Integer.parseInt(c.getString(1)));

            editTextDocumentNum1.setText(c.getString(2));
            editTextIssuingAuthority1.setText(c.getString(3));
            editTextDateofIssue1.setText(c.getString(4));

            if(Integer.parseInt(c.getString(5))!=0) {
                count=1;
                tableLayout2.setVisibility(View.VISIBLE);
                spinnerIdcard2.setSelection(Integer.parseInt(c.getString(5)));
            }

            editTextDocumentNum2.setText(c.getString(6));
            editTextIssuingAuthority2.setText(c.getString(7));
            editTextDateofIssue2.setText(c.getString(8));

            if(Integer.parseInt(c.getString(9))!=0)
            {
                count=2;
                tableLayout3.setVisibility(View.VISIBLE);
                spinnerIdcard3.setSelection(Integer.parseInt(c.getString(9)));
            }


            editTextDocumentNum3.setText(c.getString(10));
            editTextIssuingAuthority3.setText(c.getString(11));
            editTextDateofIssue3.setText(c.getString(12));

            if(Integer.parseInt(c.getString(13))!=0)
            {
                count=3;
                spinnerIdcard4.setSelection(Integer.parseInt(c.getString(13)));
                tableLayout4.setVisibility(View.VISIBLE);
            }


            editTextDocumentNum4.setText(c.getString(14));
            editTextIssuingAuthority4.setText(c.getString(15));
            editTextDateofIssue4.setText(c.getString(16));

        }
        db.close();

    }

    private void saveSession() {
        hashMap.put("name1",Integer.toString(spinnerIdcard1.getSelectedItemPosition()));
        hashMap.put("doc_num1",editTextDocumentNum1.getText().toString());
        hashMap.put("authority1",editTextIssuingAuthority1.getText().toString());
        hashMap.put("do_issue1",editTextDateofIssue1.getText().toString());

        hashMap.put("name2",Integer.toString(spinnerIdcard2.getSelectedItemPosition()));
        hashMap.put("doc_num2",editTextDocumentNum2.getText().toString());
        hashMap.put("authority2",editTextIssuingAuthority2.getText().toString());
        hashMap.put("do_issue2",editTextDateofIssue2.getText().toString());

        hashMap.put("name3",Integer.toString(spinnerIdcard3.getSelectedItemPosition()));
        hashMap.put("doc_num3",editTextDocumentNum3.getText().toString());
        hashMap.put("authority3",editTextIssuingAuthority3.getText().toString());
        hashMap.put("do_issue3",editTextDateofIssue3.getText().toString());

        hashMap.put("name4",Integer.toString(spinnerIdcard4.getSelectedItemPosition()));
        hashMap.put("doc_num4",editTextDocumentNum4.getText().toString());
        hashMap.put("authority4",editTextIssuingAuthority4.getText().toString());
        hashMap.put("do_issue4",editTextDateofIssue4.getText().toString());
        try{
            DBAdapter db = new DBAdapter(getActivity());

            //---get all contacts---
            db.open();
            if(db.updateDataIdProof(hashMap))
            {
                System.out.println("date Saved----------");
            }
            else {
                System.out.println("date cannot be Saved----------");
            }
        }catch (Exception e)
        {
            System.out.println("ErrorSaving Id proof");
        }

    }

}
