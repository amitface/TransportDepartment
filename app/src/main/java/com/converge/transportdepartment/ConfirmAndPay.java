package com.converge.transportdepartment;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DataBaseHelper.DBAdapter;
import com.converge.transportdepartment.Utility.BackGroundTasks;
import com.converge.transportdepartment.Utility.ConValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmAndPay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmAndPay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmAndPay extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private OnFragmentInteractionListener mListener;

    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private String mFinalString1 = "mFinalString1";
    private final String mFinalString2 = "mFinalString2";
    private final String mFinalStringCov = "mFinalStringCov";
    private final String NICjson = "NICjson";
    private final String NICDetail = "NICDetail";

    private static boolean permanent = false;
    private static boolean present = false;
    private static boolean personalDetail = false;
    private static boolean otherInfo = true;

    //Temporary data
    public ProgressDialog progress;
    HashMap<String, String> hashMap = new HashMap<String, String>();
    Validation validation = new Validation();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static Handler handler;
    private static boolean aBooleanstop = false;

    private ProgressDialog progressDialog;

    ImageView imageViewPermanent;
    ImageView imageViewPresent;
    ImageView imageViewPersonal;
    ImageView imageViewOther;
    CheckBox checkboxSameAddress;

    private int arrTaluka[] = {R.array.theesil, R.array.theesil1, R.array.theesil2, R.array.theesil3, R.array.theesil4, R.array.theesil5, R.array.theesil6,
            R.array.theesil7, R.array.theesil8, R.array.theesil9, R.array.theesil10,
            R.array.theesil11, R.array.theesil12, R.array.theesil13, R.array.theesil14, R.array.theesil15, R.array.theesil16, R.array.theesil17,
            R.array.theesil18, R.array.theesil19, R.array.theesil20, R.array.theesil21, R.array.theesil22, R.array.theesil23, R.array.theesil24,
            R.array.theesil25, R.array.theesil26, R.array.theesil27, R.array.theesil28, R.array.theesil29, R.array.theesil30, R.array.theesil31,
            R.array.theesil32, R.array.theesil33, R.array.theesil34, R.array.theesil35, R.array.theesil36, R.array.theesil37, R.array.theesil38};


    private String mtextViewDateString = "mtextViewDate";
    private String mtextViewDateInt = "mtextViewDateInt";


    // TextView and Spinner
    private TextView meditViewApplicantFirstName;
    private TextView meditViewApplicantMiddleName;
    private TextView meditViewApplicantLastName;

    private TextView meditViewApplicantRelationsName;
    private TextView meditViewApplicantRelationsMiddleName;
    private TextView meditViewApplicantRelationsLastName;


    private TextView meditViewPlaceOfBirth;
    private TextView meditViewYear;
    private TextView meditViewMonth;
    private TextView meditViewEmail;

    private TextView meditTextPermanentFlatNum;
    private TextView meditTextPermanentHouseName;
    private TextView meditTextPermanentHouseNum;
    private TextView meditTextPermanentStreet;
    private TextView meditTextPermanentLocality;
    private TextView meditTextPermanentvillage;
    private TextView meditTextPermanentTaluka;
    private TextView meditTextPermanentDistrict;
    private TextView meditTextPermanentMonth;
    private TextView meditTextPermanentYear;
    private TextView meditTextPermanentPinCode;
    private TextView meditTextPermanentMoblieNo;

    private TextView meditTextPresentFlatNum;
    private TextView meditTextPresentHouseName;
    private TextView meditTextPresentHouseNum;
    private TextView meditTextPresentStreet;
    private TextView meditTextPresentLocality;
    private TextView meditTextPresentvillage;
    private TextView meditTextPresentTaluka;
    private TextView meditTextPresentDistrict;
    private TextView meditTextPresentMonth;
    private TextView meditTextPresentYear;
    private TextView meditTextPresentPinCode;
    private TextView meditTextPresentMoblieNo;
    private TextView mTxtAppointmentDate;
    private TextView mTxtAppointmentTime;


    private TextView meditViewFee;

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

    String statecode[] = {"AN", "N", "AP ", "AR ", "AS ", "BR ", "CG", "CH ", "DL ", "DN ", "GA ", "GJ ", "HP ", "HR ", "JK ", "JH ", "KA ",
            "KL ", "LD ", "MH ", "ML ", "MN ", "MP ", "MZ ", "NL ", "PB ", "PY ", "RJ ", "SK ", "TN ", "TR ", "UP ", "WB ",
            "XX ", "DD ", "UK ", "UA ", "OD "};

    String qualificatinCode[] = {"0 ", "1 ", "2 ", "3 ", "4 ", "6 ", "7 ", "10", "11", "12",
            "13", "14", "30", "31", "32", "33", "34", "35", "39", "50", "51",
            "52", "53", "54", "55", "56", "57", "58", "59", "70", "80", "81", "82", "90"};
    String rtoCode[] = {"OD01 ", "OD02 ", " OD02K", "OD03 ", "OD04 ", "OD05 ", "OD06 ", "OD07 ", "OD08 ", "OD09 ", "OD09B", "OD10 ", "OD11 ", "OD11R",
            "OD12 ", "OD13 ", "OD13 ", " OD15 ", "OD16 ", "OD17 ", "OD18 ",
            " OD19", "OD20 ", "OD21 ", "OD22 ", "OD23 ", "OD24 ", "OD25 ", "OD12 ",
            "OD26 ", "OD27 ", "OD28 ", " OD29", "OD30 ", "OD31 ", "OD32 ", "OD33 ",
            "OD34 ", "OD35 "};

    private TextView mspinnerSDate, mspinnerSTime;
    private TextView mspinnerRTO, mspinnerRelationshipType, mspinnerQualification, mspinnerGender;
    private TextView mspinnerIdmark, mspinnerBloodGroup, mspinnerRH, mspinnerPermanentState, mspinnerPresentState;
    private TextView mspinnerCitizenship, mspinnerCountry, mspinnerIdmark2;

    private ImageView mimageViewDatePicker;

    private ImageView buttonNext;
    private Button buttonClearPersonalDetails;
    private ImageView buttonBackConfirmAndPay;


    private String usr_fname, usr_lname, usr_relation_type, usr_father_name, usr_address, usr_city, usr_district, usr_pincode, usr_apply_class, usr_mobile;
    private String usr_email, usr_qualification, usr_dob, usr_gender;
    private String usr_blood_gr, usr_blood_rh, usr_idmark, usr_feeamnt, usr_status;

    private int int_relation_type, int_qualification, int_gender, int_idmark, int_blood_gr, int_blood_rh;
    private int int_apply_class, int_dob;

    private String vehicleClass[] = {"Motor cycle without gear for less then 50cc",
            "Motor cycle without grear",
            "Motor cycle withgear",
            "Light Motor Vehicle Non Transport",
            "Light Motor Vehicle-Auto Riksha Non Transport",
            "LMC Trackor Trailer",
            "IVC"};


    private TextView mspinner;
    private String jsonString;
    private static final String PGInfo = "PgInfo";
    private long appNumber;

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
        hideKeyboard(getContext());

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());

        jsonString = sharedpreferences.getString(PGInfo, "");
        TextView textfee = (TextView) view.findViewById(R.id.textfee);
        TextView textTotal = (TextView) view.findViewById(R.id.textTotal);
        textfee.setText("Application Fee : Rs. " + (totalFee() - 20));
        textTotal.setText("Total Fee : Rs. " + totalFee());

        initailizeFelids(view);
//        sendPostRequest(rootView);
        hidePersonalDetail();
        hidePermanent();
        hidePresent();
        setListner();
        disableField(view);
        return view;
    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    private int totalFee() {
        String s = sharedpreferences.getString("mFinalStringCov", "");
        String arr[] = s.split(",");
        int len = arr.length;
        if (arr[0].length() > 0)
            len = len * 30 + 20;
        else
            len = 0;
        return len;
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
                if (validate()) {
                    sendPostRequest();
//                    saveSharedPreference();


                }
                break;
            case R.id.buttonBackConfirmAndPay:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("3", "1")).commit();
                break;
            case R.id.imageViewDatePickerC:
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.linearlayoutPermanantC:
                if (permanent == true)
                    hidePermanent();
                else
                    showPremanent();
                break;
            case R.id.linearlayoutPresentAddressC:
                if (present == true)
                    hidePresent();
                else
                    showPresent();
                break;
            case R.id.linearlayoutOtherInfoC:
                if (otherInfo == true)
                    hideOtherInfo();
                else
                    showOtherInfo();
                break;
            case R.id.linearlayoutPersonalDetailC:
                if (personalDetail == true)
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
     * <p>
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


        buttonNext.setOnClickListener(this);
        buttonBackConfirmAndPay.setOnClickListener(this);
//        buttonClearPersonalDetails.setOnClickListener(this);

        mlinearlayoutPresentAddress.setOnClickListener(this);
        mlinearlayoutPremanentAddress.setOnClickListener(this);
        mlinearlayoutPersonalDetail.setOnClickListener(this);
        mlinearlayoutOtherInfo.setOnClickListener(this);
    }


    private void initailizeFelids(View rootView) {

//        mspinnerSDate=(Spinner) rootView.findViewById(R.id.spinnerSDate);
//        mspinnerSTime=(Spinner) rootView.findViewById(R.id.spinnerSTime);

        imageViewPermanent = (ImageView) rootView.findViewById(R.id.imagePermanentC);
        imageViewPresent = (ImageView) rootView.findViewById(R.id.imagePresentC);
        imageViewPersonal = (ImageView) rootView.findViewById(R.id.imagePersonalC);
        imageViewOther = (ImageView) rootView.findViewById(R.id.imageOtherC);

        meditViewApplicantFirstName = (TextView) rootView.findViewById(R.id.editTextViewApplicantFirstNameC);
        meditViewApplicantMiddleName = (TextView) rootView.findViewById(R.id.editTextViewApplicantMiddleNameC);
        meditViewApplicantLastName = (TextView) rootView.findViewById(R.id.editTextViewApplicantLastNameC);

        meditViewEmail = (TextView) rootView.findViewById(R.id.editTextEmailC);
        mimageViewDatePicker = (ImageView) rootView.findViewById(R.id.imageViewDatePickerC);
        mtextViewDate = (TextView) rootView.findViewById(R.id.textViewDateC);
        meditViewPlaceOfBirth = (TextView) rootView.findViewById(R.id.editTextPlaceofBirthC);
        meditViewYear = (TextView) rootView.findViewById(R.id.editTextYearC);
        meditViewMonth = (TextView) rootView.findViewById(R.id.editTextMonthC);

        meditViewApplicantRelationsName = (TextView) rootView.findViewById(R.id.editTextRelationsNameC);
        meditViewApplicantRelationsMiddleName = (TextView) rootView.findViewById(R.id.editTextRelationsMiddleNameC);
        meditViewApplicantRelationsLastName = (TextView) rootView.findViewById(R.id.editTextRelationsLastNameC);

        meditTextPermanentFlatNum = (TextView) rootView.findViewById(R.id.editTextPermanentFlatNum);
        meditTextPermanentHouseName = (TextView) rootView.findViewById(R.id.editTextPermanentHouseName);
        meditTextPermanentHouseNum = (TextView) rootView.findViewById(R.id.editTextPermanentHouseNum);
        meditTextPermanentStreet = (TextView) rootView.findViewById(R.id.editTextPermanentStreet);
        meditTextPermanentLocality = (TextView) rootView.findViewById(R.id.editTextPermanentLocality);
        meditTextPermanentvillage = (TextView) rootView.findViewById(R.id.editTextPermanentvillage);
        meditTextPermanentTaluka = (TextView) rootView.findViewById(R.id.editTextPermanentTaluka);
        meditTextPermanentDistrict = (TextView) rootView.findViewById(R.id.editTextPermanentDistrict);
        meditTextPermanentMonth = (TextView) rootView.findViewById(R.id.editTextPermanentMonth);
        meditTextPermanentYear = (TextView) rootView.findViewById(R.id.editTextPermanentYear);
        meditTextPermanentPinCode = (TextView) rootView.findViewById(R.id.editTextPermanentPinCode);
        meditTextPermanentMoblieNo = (TextView) rootView.findViewById(R.id.editTextPermanentMoblieNo);


        meditTextPresentFlatNum = (TextView) rootView.findViewById(R.id.editTextPresentFlatNum);
        meditTextPresentHouseName = (TextView) rootView.findViewById(R.id.editTextPresentHouseName);
        meditTextPresentHouseNum = (TextView) rootView.findViewById(R.id.editTextPresentHouseNum);
        meditTextPresentStreet = (TextView) rootView.findViewById(R.id.editTextPresentStreet);
        meditTextPresentLocality = (TextView) rootView.findViewById(R.id.editTextPresentLocality);
        meditTextPresentvillage = (TextView) rootView.findViewById(R.id.editTextPresentvillage);
        meditTextPresentTaluka = (TextView) rootView.findViewById(R.id.editTextPresentTaluka);
        meditTextPresentDistrict = (TextView) rootView.findViewById(R.id.editTextPresentDistrict);
        meditTextPresentMonth = (TextView) rootView.findViewById(R.id.editTextPresentMonth);
        meditTextPresentYear = (TextView) rootView.findViewById(R.id.editTextPresentYear);
        meditTextPresentPinCode = (TextView) rootView.findViewById(R.id.editTextPresentPinCode);
        meditTextPresentMoblieNo = (TextView) rootView.findViewById(R.id.editTextPresentMoblieNo);

//        meditViewFee = (TextView) rootView.findViewById(R.id.editTextFee);
//        meditViewFee.setText("250");

        mspinnerRTO = (TextView) rootView.findViewById(R.id.spinnerRTOC);
        mspinnerCountry = (TextView) rootView.findViewById(R.id.spinnerCountryC);
        mspinnerRelationshipType = (TextView) rootView.findViewById(R.id.spinnerRelationshipTypeC);
        mspinnerQualification = (TextView) rootView.findViewById(R.id.spinnerQualificationC);
        mspinnerGender = (TextView) rootView.findViewById(R.id.spinnerGenderC);
        mspinnerIdmark = (TextView) rootView.findViewById(R.id.spinnerIdmarkC);
        mspinnerIdmark2 = (TextView) rootView.findViewById(R.id.spinnerIdmark2C);
        mspinnerBloodGroup = (TextView) rootView.findViewById(R.id.spinnerBloodGroupC);
        mspinnerRH = (TextView) rootView.findViewById(R.id.spinnerRH);
        mspinnerPermanentState = (TextView) rootView.findViewById(R.id.spinnerPermanentState);
        mspinnerPresentState = (TextView) rootView.findViewById(R.id.spinnerPresentState);
        mspinnerCitizenship = (TextView) rootView.findViewById(R.id.spinnerCitizenshipC);


        mlinearlayoutPersonalDetail = (RelativeLayout) rootView.findViewById(R.id.linearlayoutPersonalDetailC);
        mlinearlayoutPremanentAddress = (RelativeLayout) rootView.findViewById(R.id.linearlayoutPermanantC);
        mlinearlayoutPresentAddress = (RelativeLayout) rootView.findViewById(R.id.linearlayoutPresentAddressC);
        mlinearlayoutOtherInfo = (RelativeLayout) rootView.findViewById(R.id.linearlayoutOtherInfoC);


        mtablelayoutPersonalDetail = (TableLayout) rootView.findViewById(R.id.tablelayoutPersonalDetailC);
        mtablelayoutPresentAddress = (TableLayout) rootView.findViewById(R.id.tablelayoutPresentAddressC);
        mtablelayoutPremanentAddress = (TableLayout) rootView.findViewById(R.id.tablelayoutPermanenetAddressC);
        mtablelayoutOtherInfo = (TableLayout) rootView.findViewById(R.id.tablelayoutOtherInfoC);


        buttonNext = (ImageView) rootView.findViewById(R.id.button_confirm_and_pay);
//        buttonClearPersonalDetails = (Button) rootView.findViewById(R.id.buttonClearPersonalDetail);
        buttonBackConfirmAndPay = (ImageView) rootView.findViewById(R.id.buttonBackConfirmAndPay);

        mTxtAppointmentDate = (TextView) rootView.findViewById(R.id.txtAppiontmentDate);
        mTxtAppointmentTime = (TextView) rootView.findViewById(R.id.txtAppiontmentTime);
        try {
            JSONObject jsonObject = new JSONObject(sharedpreferences.getString(PGInfo, ""));
            mTxtAppointmentTime.setText("Appointment time : " + jsonObject.getString("slotTime"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MM/yy");
            Calendar calendar = Calendar.getInstance();
            Long aLong = jsonObject.getLong("slotDate");
            calendar.setTimeInMillis(aLong);
            dateFormat.format(calendar.getTime());
            System.out.println(dateFormat.format(calendar.getTime()));
            mTxtAppointmentDate.setText("Appointment time : " + dateFormat.format(calendar.getTime()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        retrivesession();
    }

    private void disableField(View rootView) {

        meditViewApplicantFirstName.setEnabled(false);
        meditViewApplicantMiddleName.setEnabled(false);
        meditViewApplicantLastName.setEnabled(false);

        meditViewEmail.setEnabled(false);
        mimageViewDatePicker.setEnabled(false);
        mimageViewDatePicker.setVisibility(View.GONE);
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
        if (c.moveToFirst()) {
            if (c.getString(1).length() > 0)
                mspinnerRTO.setText(getResources().getStringArray(R.array.RTO)[Integer.parseInt(c.getString(1))]);

            meditViewApplicantFirstName.setText(c.getString(2));
            meditViewApplicantMiddleName.setText(c.getString(3));
            meditViewApplicantLastName.setText(c.getString(4));

            mtextViewDate.setText(c.getString(5));
            if (c.getString(6).length() > 0)
                mspinnerGender.setText(getResources().getStringArray(R.array.Gender)[Integer.parseInt(c.getString(6))]);

            meditViewPlaceOfBirth.setText(c.getString(7));
            if (c.getString(8).equals(""))
                meditViewYear.setText("0");
            else
                meditViewYear.setText(c.getString(8));

            if (c.getString(9).equals(""))
                meditViewMonth.setText("0");
            else
                meditViewMonth.setText(c.getString(9));

            if (c.getString(10).length() > 0)
                mspinnerCountry.setText(getResources().getStringArray(R.array.Country)[Integer.parseInt(c.getString(10))]);

            meditViewEmail.setText(c.getString(11));

            if (c.getString(12).length() > 0)
                mspinnerRelationshipType.setText(getResources().getStringArray(R.array.RelationType)[Integer.parseInt(c.getString(12))]);

            meditViewApplicantRelationsName.setText(c.getString(13));
            meditViewApplicantRelationsMiddleName.setText(c.getString(14));
            meditViewApplicantRelationsLastName.setText(c.getString(15));

            meditTextPermanentFlatNum.setText(c.getString(16));
            meditTextPermanentHouseName.setText(c.getString(17));
            meditTextPermanentHouseNum.setText(c.getString(18));
            meditTextPermanentStreet.setText(c.getString(19));
            meditTextPermanentLocality.setText(c.getString(20));
            meditTextPermanentvillage.setText(c.getString(21));
            if (c.getString(22).length() > 0)
                meditTextPermanentTaluka.setText(getResources().getStringArray(arrTaluka[Integer.parseInt(c.getString(23))])[Integer.parseInt(c.getString(22))]);
//            meditTextPermanentTaluka.setText(c.getString(22));

            if (c.getString(23).length() > 0)
                meditTextPermanentDistrict.setText(getResources().getStringArray(R.array.district)[Integer.parseInt(c.getString(23))]);
//            meditTextPermanentDistrict.setText(c.getString(23));

            if (c.getString(24).length() > 0)
                mspinnerPermanentState.setText(getResources().getStringArray(R.array.states)[Integer.parseInt(c.getString(24))]);

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

            if (c.getString(37).length() > 0)
                mspinnerPresentState.setText(getResources().getStringArray(R.array.states)[Integer.parseInt(c.getString(37))]);

            meditTextPresentMonth.setText(c.getString(38));
            meditTextPresentYear.setText(c.getString(39));
            meditTextPresentPinCode.setText(c.getString(40));
            meditTextPresentMoblieNo.setText(c.getString(41));
//
//
            if (c.getString(42).length() > 0)
                mspinnerCitizenship.setText(getResources().getStringArray(R.array.Citizenship)[Integer.parseInt(c.getString(42))]);


            if (c.getString(43).length() > 0)
                mspinnerQualification.setText(getResources().getStringArray(R.array.Qualification)[Integer.parseInt(c.getString(43))]);

            if (c.getString(44).length() > 0)
                mspinnerIdmark.setText(getResources().getStringArray(R.array.idMark)[Integer.parseInt(c.getString(44))]);

            if (c.getString(45).length() > 0)
                mspinnerIdmark2.setText(getResources().getStringArray(R.array.idMark)[Integer.parseInt(c.getString(45))]);

            if (c.getString(46).length() > 0)
                mspinnerBloodGroup.setText(getResources().getStringArray(R.array.Blood)[Integer.parseInt(c.getString(46))]);


            if (c.getString(47).length() > 0)
                mspinnerRH.setText(getResources().getStringArray(R.array.Rh)[Integer.parseInt(c.getString(47))]);
        }
        db.close();

    }

    private boolean validate() {
        if(!ConValidation.isNetworkAvailable(getActivity()))
        {
            showToast("Please check internet connection ...");
            return false;
        }
        return true;
    }



    private void hidePersonalDetail() {
        mtablelayoutPersonalDetail.setVisibility(View.GONE);
        personalDetail = false;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_p, null));
    }

    private void showPersonalDetail() {
        mtablelayoutPersonalDetail.setVisibility(View.VISIBLE);
        personalDetail = true;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_m, null));
    }

    private void hideOtherInfo() {
        mtablelayoutOtherInfo.setVisibility(View.GONE);
        otherInfo = false;
        imageViewOther.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_p, null));
    }

    private void showOtherInfo() {
        mtablelayoutOtherInfo.setVisibility(View.VISIBLE);
        otherInfo = true;
        imageViewOther.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_m, null));
    }

    private void hidePermanent() {
        mtablelayoutPremanentAddress.setVisibility(View.GONE);
        permanent = false;
        imageViewPermanent.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_p, null));
    }

    private void showPremanent() {
        mtablelayoutPremanentAddress.setVisibility(View.VISIBLE);
        permanent = true;
        imageViewPermanent.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_m, null));

    }

    private void hidePresent() {
        mtablelayoutPresentAddress.setVisibility(View.GONE);
        present = false;
        imageViewPresent.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_p, null));
    }

    private void showPresent() {
        mtablelayoutPresentAddress.setVisibility(View.VISIBLE);
        present = true;
        imageViewPresent.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.iocn_m, null));
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("onPause :", " saving session");
//        saveSession();
        Log.d("Session :", " saved session");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("onResume :", " retrieving  session");
        retrivesession();
        Log.d("Session :", " session retrieved");
    }

    public void onDestroy()
    {
        super.onDestroy();
        progressDialog.dismiss();
    }

    public void sendPostRequest() {
        new PostClass(getActivity()).execute();
    }

    private class PostClass extends AsyncTask<String, Void, Integer> {

        private final Context context;


        public PostClass(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/user_registration.php");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    JSONObject jsonObject = new JSONObject(sharedpreferences.getString(PGInfo,""));

                    appNumber = jsonObject.getLong("applicantNum");

                String urlString ="ref_num="+appNumber+sharedpreferences.getString(mFinalString1, "") + sharedpreferences.getString(mFinalString2, "") + "&covs=" + (sharedpreferences.getString(mFinalStringCov, ""));

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
                String responseDetail = responseOutput.toString();
                System.out.println("Response output "+responseDetail);
                JSONObject jsonObject2 = new JSONObject(responseDetail);
                String s = jsonObject2.getString("receiptNum");
                System.out.println("Receipt No." + s);
                JSONObject jsonObject1 = new JSONObject(jsonString);
                jsonObject1.put("receiptNum", s);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(PGInfo, jsonObject1.toString());
                editor.apply();

                if (Integer.parseInt(s) > 0) {
                    BackGroundTasks.sendMail(appNumber);
                }

                return 1;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return 0;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return 0;
            } catch (Exception e) {
                e.printStackTrace();
                return 0;
            }

        }


        protected void onPostExecute(Integer result) {

            progressDialog.dismiss();
            if (result == 1) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, PayablePayment.newInstance("1", "1")).commit();
                    }
                });
            } else {
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


