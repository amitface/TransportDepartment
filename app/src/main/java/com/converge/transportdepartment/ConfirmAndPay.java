package com.converge.transportdepartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DataBaseHelper.DBAdapter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmAndPay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmAndPay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmAndPay extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;

    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private String mFinalString1="mFinalString1";
    private final String mFinalString2="mFinalString2";
    private final String mFinalStringCov="mFinalStringCov";

    private static boolean permanent = false;
    private static boolean present = false;
    private static boolean personalDetail = true;
    private static boolean otherInfo = true;

    //Temporary data
    public ProgressDialog progress;
    HashMap<String, String> hashMap = new HashMap<String, String>();
    Validation validation = new Validation();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Handler handler;
    private static boolean aBooleanstop=false;

    ImageView imageViewPermanent ;
    ImageView imageViewPresent ;
    ImageView imageViewPersonal ;
    ImageView imageViewOther;
    CheckBox checkboxSameAddress;


    private String mtextViewDateString = "mtextViewDate";
    private String mtextViewDateInt = "mtextViewDateInt";


    // TextView and Spinner
    private EditText meditViewApplicantFirstName;
    private EditText meditViewApplicantMiddleName;
    private EditText meditViewApplicantLastName;

    private EditText meditViewApplicantRelationsName;
    private EditText meditViewApplicantRelationsMiddleName;
    private EditText meditViewApplicantRelationsLastName;


    private EditText meditViewPlaceOfBirth;
    private EditText meditViewYear;
    private EditText meditViewMonth;
    private EditText meditViewEmail;

    private EditText meditTextPermanentFlatNum;
    private EditText meditTextPermanentHouseName;
    private EditText meditTextPermanentHouseNum;
    private EditText meditTextPermanentStreet;
    private EditText meditTextPermanentLocality;
    private EditText meditTextPermanentvillage;
    private EditText meditTextPermanentTaluka;
    private EditText meditTextPermanentDistrict;
    private EditText meditTextPermanentMonth;
    private EditText meditTextPermanentYear;
    private EditText meditTextPermanentPinCode;
    private EditText meditTextPermanentMoblieNo;

    private EditText meditTextPresentFlatNum;
    private EditText meditTextPresentHouseName;
    private EditText meditTextPresentHouseNum;
    private EditText meditTextPresentStreet;
    private EditText meditTextPresentLocality;
    private EditText meditTextPresentvillage;
    private EditText meditTextPresentTaluka;
    private EditText meditTextPresentDistrict;
    private EditText meditTextPresentMonth;
    private EditText meditTextPresentYear;
    private EditText meditTextPresentPinCode;
    private EditText meditTextPresentMoblieNo;


    private EditText meditViewFee;

    private RelativeLayout mlinearlayoutPersonalDetail;
    private RelativeLayout mlinearlayoutPresentAddress;
    private RelativeLayout mlinearlayoutPremanentAddress;
    private RelativeLayout mlinearlayoutOtherInfo;

    private TableLayout mtablelayoutPersonalDetail;
    private TableLayout mtablelayoutPresentAddress;
    private TableLayout mtablelayoutPremanentAddress;
    private TableLayout mtablelayoutOtherInfo;

    public static TextView mtextViewDate;
    Thread th;

    String statecode[]={"AN", "N", "AP ", "AR ", "AS ", "BR ", "CG", "CH ", "DL ", "DN ", "GA ", "GJ ", "HP ", "HR ", "JK ", "JH ", "KA ",
            "KL ", "LD ", "MH ", "ML ", "MN ", "MP ", "MZ ", "NL ", "PB ", "PY ", "RJ ", "SK ", "TN ",  "TR ", "UP ", "WB ",
            "XX ", "DD ", "UK ", "UA ", "OD "};

    String qualificatinCode[] = {"0 ", "1 ","2 ","3 ", "4 ","6 ", "7 ", "10","11", "12",
            "13","14","30","31","32","33","34","35","39","50","51",
            "52", "53", "54","55","56","57", "58","59", "70", "80", "81","82","90"};
    String rtoCode []=  { "OD01 ",  "OD02 ", " OD02K","OD03 ",    "OD04 ", "OD05 ",  "OD06 ", "OD07 ", "OD08 ", "OD09 ",  "OD09B", "OD10 ",  "OD11 ", "OD11R",
            "OD12 ",  "OD13 ","OD13 ",     " OD15 ","OD16 ",  "OD17 ","OD18 ",
            " OD19","OD20 ",     "OD21 ",  "OD22 ","OD23 ","OD24 ", "OD25 ", "OD12 ",
            "OD26 ", "OD27 ", "OD28 ", " OD29", "OD30 ","OD31 ",  "OD32 ", "OD33 ",
            "OD34 ", "OD35 "};

    private Spinner mspinnerSDate,mspinnerSTime;
    private Spinner mspinnerRTO, mspinnerRelationshipType, mspinnerQualification, mspinnerGender;
    private Spinner mspinnerIdmark, mspinnerBloodGroup, mspinnerRH, mspinnerPermanentState,mspinnerPresentState;
    private Spinner mspinnerCitizenship, mspinnerCountry,mspinnerIdmark2;

    private ImageView mimageViewDatePicker;

    private Button buttonNext, buttonClearPersonalDetails, buttonBackConfirmAndPay;


    private String usr_fname, usr_lname, usr_relation_type, usr_father_name, usr_address, usr_city, usr_district, usr_pincode, usr_apply_class, usr_mobile;
    private String usr_email, usr_qualification, usr_dob, usr_gender;
    private String usr_blood_gr, usr_blood_rh, usr_idmark, usr_feeamnt, usr_status;

    private int int_relation_type,int_qualification, int_gender, int_idmark, int_blood_gr, int_blood_rh;
    private int int_apply_class, int_dob ;

    private String vehicleClass[] = {"Motor cycle without gear for less then 50cc",
            "Motor cycle without grear",
            "Motor cycle withgear",
            "Light Motor Vehicle Non Transport",
            "Light Motor Vehicle-Auto Riksha Non Transport",
            "LMC Trackor Trailer",
            "IVC"};


    private Spinner mspinner;
    public ConfirmAndPay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmAndPay.
     */
    // TODO: Rename and change types and number of parameters
    public static ConfirmAndPay newInstance(String param1, String param2) {
        ConfirmAndPay fragment = new ConfirmAndPay();
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
        View view = inflater.inflate(R.layout.fragment_confirm_and_pay, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        initailizeFelids(view);
//        sendPostRequest(rootView);
        hidePermanent();
        hidePresent();
        setListner();
        disableField(view);
        return view;
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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_confirm_and_pay:
//                getFieldData();
                if(validate()) {
                    sendPostRequest();
                    saveSharedPreference();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PayablePayment.newInstance("1","1")).commit();
                }
                break;

            case R.id.imageViewDatePickerC:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.linearlayoutPermanantC:
                if(permanent==true)
                    hidePermanent();
                else
                    showPremanent();
                break;
            case R.id.linearlayoutPresentAddressC:
                if(present==true)
                    hidePresent();
                else
                    showPresent();
                break;
            case R.id.linearlayoutOtherInfoC:
                if(otherInfo==true)
                    hideOtherInfo();
                else
                    showOtherInfo();
                break;
            case R.id.linearlayoutPersonalDetailC:
                if(personalDetail==true)
                    hidePersonalDetail();
                else
                    showPersonalDetail();
                break;
            default:
                break;
        }
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

    private void saveSharedPreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(mFinalString1, detailString());
        editor.commit();
    }


    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    private void setListner() {
        mimageViewDatePicker.setOnClickListener(this);

        checkboxSameAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkboxSameAddress.isChecked())
                {
                    checkPresentAddress();
                }
                else
                {
                    uncheckPresentAddress();
                }
            }
        });
        buttonNext.setOnClickListener(this);
        buttonBackConfirmAndPay.setOnClickListener(this);
//        buttonClearPersonalDetails.setOnClickListener(this);

        mlinearlayoutPresentAddress.setOnClickListener(this);
        mlinearlayoutPremanentAddress.setOnClickListener(this);
        mlinearlayoutPersonalDetail.setOnClickListener(this);
        mlinearlayoutOtherInfo.setOnClickListener(this);
    }



    private void initailizeFelids(View rootView) {

        mspinnerSDate=(Spinner) rootView.findViewById(R.id.spinnerSDate);
        mspinnerSTime=(Spinner) rootView.findViewById(R.id.spinnerSTime);

        imageViewPermanent=(ImageView) rootView.findViewById(R.id.imagePermanentC);
        imageViewPresent=(ImageView) rootView.findViewById(R.id.imagePresentC);
        imageViewPersonal=(ImageView) rootView.findViewById(R.id.imagePersonalC);
        imageViewOther=(ImageView) rootView.findViewById(R.id.imageOtherC);

        meditViewApplicantFirstName = (EditText) rootView.findViewById(R.id.editTextViewApplicantFirstNameC);
        meditViewApplicantMiddleName = (EditText) rootView.findViewById(R.id.editTextViewApplicantMiddleNameC);
        meditViewApplicantLastName = (EditText) rootView.findViewById(R.id.editTextViewApplicantLastNameC);

        meditViewEmail = (EditText) rootView.findViewById(R.id.editTextEmailC);
        mimageViewDatePicker = (ImageView) rootView.findViewById(R.id.imageViewDatePickerC);
        mtextViewDate = (TextView) rootView.findViewById(R.id.textViewDateC);
        meditViewPlaceOfBirth= (EditText) rootView.findViewById(R.id.editTextPlaceofBirthC);
        meditViewYear = (EditText) rootView.findViewById(R.id.editTextYearC);
        meditViewMonth = (EditText) rootView.findViewById(R.id.editTextMonthC);

        meditViewApplicantRelationsName = (EditText) rootView.findViewById(R.id.editTextRelationsNameC);
        meditViewApplicantRelationsMiddleName = (EditText) rootView.findViewById(R.id.editTextRelationsMiddleNameC);
        meditViewApplicantRelationsLastName = (EditText) rootView.findViewById(R.id.editTextRelationsLastNameC);

        meditTextPermanentFlatNum = (EditText) rootView.findViewById(R.id.editTextPermanentFlatNum);
        meditTextPermanentHouseName = (EditText) rootView.findViewById(R.id.editTextPermanentHouseName);
        meditTextPermanentHouseNum = (EditText) rootView.findViewById(R.id.editTextPermanentHouseNum);
        meditTextPermanentStreet = (EditText) rootView.findViewById(R.id.editTextPermanentStreet);
        meditTextPermanentLocality = (EditText) rootView.findViewById(R.id.editTextPermanentLocality);
        meditTextPermanentvillage = (EditText) rootView.findViewById(R.id.editTextPermanentvillage);
        meditTextPermanentTaluka = (EditText) rootView.findViewById(R.id.editTextPermanentTaluka);
        meditTextPermanentDistrict = (EditText) rootView.findViewById(R.id.editTextPermanentDistrict);
        meditTextPermanentMonth = (EditText) rootView.findViewById(R.id.editTextPermanentMonth);
        meditTextPermanentYear = (EditText) rootView.findViewById(R.id.editTextPermanentYear);
        meditTextPermanentPinCode = (EditText) rootView.findViewById(R.id.editTextPermanentPinCode);
        meditTextPermanentMoblieNo = (EditText) rootView.findViewById(R.id.editTextPermanentMoblieNo);

        checkboxSameAddress= (CheckBox) rootView.findViewById(R.id.checkboxSameAddress);

        meditTextPresentFlatNum = (EditText) rootView.findViewById(R.id.editTextPresentFlatNum);
        meditTextPresentHouseName = (EditText) rootView.findViewById(R.id.editTextPresentHouseName);
        meditTextPresentHouseNum = (EditText) rootView.findViewById(R.id.editTextPresentHouseNum);
        meditTextPresentStreet = (EditText) rootView.findViewById(R.id.editTextPresentStreet);
        meditTextPresentLocality = (EditText) rootView.findViewById(R.id.editTextPresentLocality);
        meditTextPresentvillage = (EditText) rootView.findViewById(R.id.editTextPresentvillage);
        meditTextPresentTaluka = (EditText) rootView.findViewById(R.id.editTextPresentTaluka);
        meditTextPresentDistrict = (EditText) rootView.findViewById(R.id.editTextPresentDistrict);
        meditTextPresentMonth = (EditText) rootView.findViewById(R.id.editTextPresentMonth);
        meditTextPresentYear = (EditText) rootView.findViewById(R.id.editTextPresentYear);
        meditTextPresentPinCode = (EditText) rootView.findViewById(R.id.editTextPresentPinCode);
        meditTextPresentMoblieNo = (EditText) rootView.findViewById(R.id.editTextPresentMoblieNo);

//        meditViewFee = (EditText) rootView.findViewById(R.id.editTextFee);
//        meditViewFee.setText("250");

        mspinnerRTO = (Spinner) rootView.findViewById(R.id.spinnerRTOC);
        mspinnerCountry = (Spinner) rootView.findViewById(R.id.spinnerCountryC);
        mspinnerRelationshipType = (Spinner) rootView.findViewById(R.id.spinnerRelationshipTypeC);
        mspinnerQualification = (Spinner) rootView.findViewById(R.id.spinnerQualificationC);
        mspinnerGender = (Spinner) rootView.findViewById(R.id.spinnerGenderC);
        mspinnerIdmark = (Spinner) rootView.findViewById(R.id.spinnerIdmarkC);
        mspinnerIdmark2 = (Spinner) rootView.findViewById(R.id.spinnerIdmark2C);
        mspinnerBloodGroup = (Spinner) rootView.findViewById(R.id.spinnerBloodGroupC);
        mspinnerRH = (Spinner) rootView.findViewById(R.id.spinnerRH);
        mspinnerPermanentState = (Spinner) rootView.findViewById(R.id.spinnerPermanentState);
        mspinnerPresentState = (Spinner) rootView.findViewById(R.id.spinnerPresentState);
        mspinnerCitizenship = (Spinner) rootView.findViewById(R.id.spinnerCitizenshipC);



        mlinearlayoutPersonalDetail =(RelativeLayout) rootView.findViewById(R.id.linearlayoutPersonalDetailC);
        mlinearlayoutPremanentAddress=(RelativeLayout) rootView.findViewById(R.id.linearlayoutPermanantC);
        mlinearlayoutPresentAddress =(RelativeLayout) rootView.findViewById(R.id.linearlayoutPresentAddressC);
        mlinearlayoutOtherInfo =(RelativeLayout) rootView.findViewById(R.id.linearlayoutOtherInfoC);


        mtablelayoutPersonalDetail = (TableLayout)rootView.findViewById(R.id.tablelayoutPersonalDetailC);
        mtablelayoutPresentAddress = (TableLayout)rootView.findViewById(R.id.tablelayoutPresentAddressC);
        mtablelayoutPremanentAddress = (TableLayout)rootView.findViewById(R.id.tablelayoutPermanenetAddressC);
        mtablelayoutOtherInfo = (TableLayout)rootView.findViewById(R.id.tablelayoutOtherInfoC);


        buttonNext = (Button) rootView.findViewById(R.id.button_confirm_and_pay);
//        buttonClearPersonalDetails = (Button) rootView.findViewById(R.id.buttonClearPersonalDetail);
        buttonBackConfirmAndPay = (Button) rootView.findViewById(R.id.buttonBackConfirmAndPay);

        retrivesession();

    }

    private void disableField(View rootView) {


        meditViewApplicantFirstName.setEnabled(false);
        meditViewApplicantMiddleName.setEnabled(false);
        meditViewApplicantLastName.setEnabled(false);

        meditViewEmail.setEnabled(false);
        mimageViewDatePicker.setEnabled(false);
        mtextViewDate.setEnabled(false);
        meditViewPlaceOfBirth.setEnabled(false);
        meditViewYear.setEnabled(false);
        meditViewMonth.setEnabled(false);

        meditViewApplicantRelationsName.setEnabled(false);
        meditViewApplicantRelationsMiddleName.setEnabled(false);
        meditViewApplicantRelationsLastName.setEnabled(false);

        meditTextPermanentFlatNum.setEnabled(false);
        meditTextPermanentHouseName.setEnabled(false);
        meditTextPermanentHouseNum.setEnabled(false);
        meditTextPermanentStreet.setEnabled(false);
        meditTextPermanentLocality.setEnabled(false);
        meditTextPermanentvillage.setEnabled(false);
        meditTextPermanentTaluka.setEnabled(false);
        meditTextPermanentDistrict.setEnabled(false);
        meditTextPermanentMonth.setEnabled(false);
        meditTextPermanentYear.setEnabled(false);
        meditTextPermanentPinCode.setEnabled(false);
        meditTextPermanentMoblieNo.setEnabled(false);

        checkboxSameAddress.setEnabled(false);

        meditTextPresentFlatNum.setEnabled(false);
        meditTextPresentHouseName.setEnabled(false);
        meditTextPresentHouseNum.setEnabled(false);
        meditTextPresentStreet.setEnabled(false);
        meditTextPresentLocality.setEnabled(false);
        meditTextPresentvillage.setEnabled(false);
        meditTextPresentTaluka.setEnabled(false);
        meditTextPresentDistrict.setEnabled(false);
        meditTextPresentMonth.setEnabled(false);
        meditTextPresentYear.setEnabled(false);
        meditTextPresentPinCode.setEnabled(false);
        meditTextPresentMoblieNo.setEnabled(false);

        mspinnerRTO.setEnabled(false);
        mspinnerCountry.setEnabled(false);
        mspinnerRelationshipType.setEnabled(false);
        mspinnerQualification.setEnabled(false);
        mspinnerGender.setEnabled(false);
        mspinnerIdmark.setEnabled(false);
        mspinnerIdmark2.setEnabled(false);
        mspinnerBloodGroup.setEnabled(false);
        mspinnerRH.setEnabled(false);
        mspinnerPermanentState.setEnabled(false);
        mspinnerPresentState.setEnabled(false);
        mspinnerCitizenship.setEnabled(false);

    }

    private void retrivesession() {
        DBAdapter db = new DBAdapter(getActivity());

        //---get all contacts---
        db.open();
        Cursor c = db.getAllDetails();
        if(c.moveToFirst()) {
            if(c.getString(1).length()>0)
                mspinnerRTO.setSelection(Integer.parseInt(c.getString(1)));

            meditViewApplicantFirstName.setText(c.getString(2));
            meditViewApplicantMiddleName.setText(c.getString(3));
            meditViewApplicantLastName.setText(c.getString(4));

            mtextViewDate.setText(c.getString(5));
            if(c.getString(6).length()>0)
                mspinnerGender.setSelection(Integer.parseInt(c.getString(6)));

            meditViewPlaceOfBirth.setText(c.getString(7));
            meditViewYear.setText(c.getString(8));
            meditViewMonth.setText(c.getString(9));

            if(c.getString(10).length()>0)
                mspinnerCountry.setSelection(Integer.parseInt(c.getString(10)));

            meditViewEmail.setText(c.getString(11));

            if(c.getString(12).length()>0)
                mspinnerRelationshipType.setSelection(Integer.parseInt(c.getString(12)));

            meditViewApplicantRelationsName.setText(c.getString(13));
            meditViewApplicantRelationsMiddleName.setText(c.getString(14));
            meditViewApplicantRelationsLastName.setText(c.getString(15));

            meditTextPermanentFlatNum.setText(c.getString(16));
            meditTextPermanentHouseName.setText(c.getString(17));
            meditTextPermanentHouseNum.setText(c.getString(18));
            meditTextPermanentStreet.setText(c.getString(19));
            meditTextPermanentLocality.setText(c.getString(20));
            meditTextPermanentvillage.setText(c.getString(21));
            meditTextPermanentTaluka.setText(c.getString(22));
            meditTextPermanentDistrict.setText(c.getString(23));
            if(c.getString(24).length()>0)
                mspinnerPermanentState .setSelection(Integer.parseInt(c.getString(24)));

            meditTextPermanentMonth.setText(c.getString(25));
            meditTextPermanentYear.setText(c.getString(26));
            meditTextPermanentPinCode.setText(c.getString(27));
            meditTextPermanentMoblieNo.setText(c.getString(28));

            meditTextPresentFlatNum.setText(c.getString(29));
            meditTextPresentHouseName.setText(c.getString(30));
            meditTextPresentHouseNum.setText(c.getString(31));
            meditTextPresentStreet.setText(c.getString(32));
            meditTextPresentLocality.setText(c.getString(33));
            meditTextPresentvillage.setText(c.getString(34));
            meditTextPresentTaluka.setText(c.getString(35));
            meditTextPresentDistrict.setText(c.getString(36));
            if(c.getString(37).length()>0)
                mspinnerPresentState .setSelection(Integer.parseInt(c.getString(37)));
            meditTextPresentMonth.setText(c.getString(38));
            meditTextPresentYear.setText(c.getString(39));
            meditTextPresentPinCode.setText(c.getString(40));
            meditTextPresentMoblieNo.setText(c.getString(41));
//
//
            if(c.getString(42).length()>0)
                mspinnerCitizenship.setSelection(Integer.parseInt(c.getString(42)));


            if(c.getString(43).length()>0)
                mspinnerQualification.setSelection(Integer.parseInt(c.getString(43)));

            if(c.getString(44).length()>0)
                mspinnerIdmark.setSelection(Integer.parseInt(c.getString(44)));
            if(c.getString(45).length()>0)
                mspinnerBloodGroup.setSelection(Integer.parseInt(c.getString(45)));


            if(c.getString(46).length()>0)
                mspinnerRH.setSelection(Integer.parseInt(c.getString(46)));
        }
        db.close();

    }





    private boolean validate() {
        if(mspinnerSDate.getSelectedItemPosition()==0)
        {
            showToast("Enter Date");
            return false;
        }
        else if(mspinnerSTime.getSelectedItemPosition()==0)
        {
            showToast("Enter Time");
            return false;
        }
        return true;
    }

    private void checkPresentAddress() {

        meditTextPresentFlatNum.setText(meditTextPermanentFlatNum.getText().toString());
        meditTextPresentHouseName.setText(meditTextPermanentHouseName.getText().toString());
        meditTextPresentHouseNum.setText(meditTextPermanentHouseNum.getText().toString());
        meditTextPresentStreet.setText(meditTextPermanentStreet.getText().toString());
        meditTextPresentLocality.setText(meditTextPermanentLocality.getText().toString());
        meditTextPresentvillage.setText(meditTextPermanentvillage.getText().toString());
        meditTextPresentTaluka.setText(meditTextPermanentTaluka.getText().toString());
        meditTextPresentDistrict.setText(meditTextPermanentDistrict.getText().toString());
        meditTextPresentMonth.setText(meditTextPermanentMonth.getText().toString());
        meditTextPresentYear.setText(meditTextPermanentYear.getText().toString());
        meditTextPresentPinCode.setText(meditTextPermanentPinCode.getText().toString());
        meditTextPresentMoblieNo.setText(meditTextPermanentMoblieNo.getText().toString());

        mspinnerPresentState.setSelection(mspinnerPermanentState.getSelectedItemPosition());

        meditTextPresentFlatNum.setEnabled(false);
        meditTextPresentHouseName.setEnabled(false);
        meditTextPresentHouseNum.setEnabled(false);
        meditTextPresentStreet.setEnabled(false);
        meditTextPresentLocality.setEnabled(false);
        meditTextPresentvillage.setEnabled(false);
        meditTextPresentTaluka.setEnabled(false);
        meditTextPresentDistrict.setEnabled(false);
        meditTextPresentMonth.setEnabled(false);
        meditTextPresentYear.setEnabled(false);
        meditTextPresentPinCode.setEnabled(false);
        meditTextPresentMoblieNo.setEnabled(false);

        mspinnerPresentState.setEnabled(false);
    }

    private void uncheckPresentAddress() {
        meditTextPresentFlatNum.setEnabled(true);
        meditTextPresentHouseName.setEnabled(true);
        meditTextPresentHouseNum.setEnabled(true);
        meditTextPresentStreet.setEnabled(true);
        meditTextPresentLocality.setEnabled(true);
        meditTextPresentvillage.setEnabled(true);
        meditTextPresentTaluka.setEnabled(true);
        meditTextPresentDistrict.setEnabled(true);
        meditTextPresentMonth.setEnabled(true);
        meditTextPresentYear.setEnabled(true);
        meditTextPresentPinCode.setEnabled(true);
        meditTextPresentMoblieNo.setEnabled(true);

        mspinnerPresentState.setEnabled(true);
    }



    private void hidePersonalDetail()
    {
        mtablelayoutPersonalDetail.setVisibility(View.GONE);
        personalDetail = false;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }

    private void showPersonalDetail()
    {
        mtablelayoutPersonalDetail.setVisibility(View.VISIBLE);
        personalDetail = true;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));
    }
    private void hideOtherInfo() {
        mtablelayoutOtherInfo.setVisibility(View.GONE);
        otherInfo = false;
        imageViewOther.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }
    private void showOtherInfo() {
        mtablelayoutOtherInfo.setVisibility(View.VISIBLE);
        otherInfo = true;
        imageViewOther.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));
    }

    private void hidePermanent()
    {
        mtablelayoutPremanentAddress.setVisibility(View.GONE);
        permanent=false;
        imageViewPermanent.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }

    private void showPremanent()
    {
        mtablelayoutPremanentAddress.setVisibility(View.VISIBLE);
        permanent=true;
        imageViewPermanent.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));

    }
    private void hidePresent()
    {
        mtablelayoutPresentAddress.setVisibility(View.GONE);
        present=false;
        imageViewPresent.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }
    private void showPresent()
    {
        mtablelayoutPresentAddress.setVisibility(View.VISIBLE);
        present=true;
        imageViewPresent.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));
    }

    @Override
    public void onPause ()
    {
        super.onPause();
        Log.d("onPause :"," saving session");
//        saveSession();
        Log.d("Session :"," saved session");
    }
    @Override
    public void onResume ()
    {
        super.onResume();
        Log.d("onResume :"," retrieving  session");
        retrivesession();
        Log.d("Session :"," session retrieved");
    }

    public void sendPostRequest() {
        new PostClass(getActivity()).execute();
    }

    private class PostClass extends AsyncTask<String, Void, Void> {

        private final Context context;
        private ProgressDialog progressDialog;

        public PostClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(this.context);
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
//            progressDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {


                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/user_registration.php");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlString =sharedpreferences.getString(mFinalString1,"")+sharedpreferences.getString(mFinalString2,"")+(sharedpreferences.contains(mFinalStringCov));

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setConnectTimeout(20000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlString);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlString);
                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                output.append(System.getProperty("line.separator") + "Type " + "POST" + " " + connection.getRequestProperty("success"));
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
                String responseDetail= responseOutput.toString();
                System.out.println(responseDetail+"\n");
                String detail[] =responseDetail.split(":");

//                SharedPreferences.Editor editor = sharedpreferences.edit();
//                editor.putString("receiptNum",detail[1].substring(1, 8));
//                editor.commit();

                if(Integer.parseInt(detail[1].substring(1, 8))>0)
                {

                    int  MEGABYTE = 1024 * 1024;
//                URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/send_mail.php");
                    URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/ll_app.php?");
                    String s="referenceId=" +sharedpreferences.getString("receiptNum","")+
                            "&email=amit.choudhary@cnvg.in";

                    HttpURLConnection connection1 = (HttpURLConnection) email.openConnection();

                    connection1.setRequestMethod("POST");
                    connection1.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                    connection1.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                    connection1.setConnectTimeout(25000);
                    connection1.setDoInput(true);
                    connection1.setDoOutput(true);
                    DataOutputStream dStream1 = new DataOutputStream(connection1.getOutputStream());

                    dStream1.writeBytes(s);
                    dStream1.flush();
                    dStream1.close();
                    responseCode = connection1.getResponseCode();
//                    BufferedReader br1 = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
//                    StringBuilder responseOutput2 = new StringBuilder();
//                    System.out.println("output===============" + br1);
//                    while ((line = br1.readLine()) != null) {
//                        responseOutput2.append(line);
//                    }
//                    br.close();
//                    System.out.println("email Response "+responseOutput2.toString());
                }
                else
                {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Your code to run in GUI thread here

//                        showToast("Email sent");

                        }//public void run() {
                    });
                }
//                return Integer.parseInt(detail[1].substring(1, 8));

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            finally {
                progressDialog.hide();
            }

            return null;
        }


        protected void onPostExecute(Long result) {

            progressDialog.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here

//                        showToast("Email sent");

                    }//public void run() {
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
                        showToast("failure");
                    }//public void run() {
                });

            }
        }

    }
}
