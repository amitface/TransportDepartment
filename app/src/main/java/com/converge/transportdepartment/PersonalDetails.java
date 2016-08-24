package com.converge.transportdepartment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DataBaseHelper.DBAdapter;
import com.converge.transportdepartment.Utility.ConValidation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PersonalDetails.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PersonalDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PersonalDetails extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
    CheckBox  checkboxSameAddress;
    EditText setectRto_text;
    EditText spinnerRelationshipType_text;
    EditText spinnerCountry_text;
    EditText spinnerCitizenship_text;
    EditText sPerDistrict_text;
    EditText spinnerPermanentState_text;
    EditText spinnerPresentState_text;
    EditText spinnerQualification_text;
    EditText spinnerIdmark_text;
    EditText spinnerIdmark2_text;
    EditText spinnerBloodGroup_text;
    EditText spinnerRH_text;

    private String []arrCov;
    private final String mFinalStringCov="mFinalStringCov";

    private String mtextViewDateString = "mtextViewDate";
    private String mtextViewDateInt = "mtextViewDateInt";

    ScrollView scrollView;
    private TextView textView_personalDetail;
    private TextView textView13;

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

    int arrTaluka[]={R.array.theesil,R.array.theesil1,R.array.theesil2,R.array.theesil3,R.array.theesil4,R.array.theesil5,R.array.theesil6,
            R.array.theesil7,R.array.theesil8,R.array.theesil9,R.array.theesil10,
            R.array.theesil11,R.array.theesil12,R.array.theesil13,R.array.theesil14,R.array.theesil15,R.array.theesil16,R.array.theesil17,
            R.array.theesil18,R.array.theesil19,R.array.theesil20,R.array.theesil21,R.array.theesil22,R.array.theesil23,R.array.theesil24,
            R.array.theesil25,R.array.theesil26,R.array.theesil27,R.array.theesil28,R.array.theesil29,R.array.theesil30,R.array.theesil31,
            R.array.theesil32,R.array.theesil33,R.array.theesil34,R.array.theesil35,R.array.theesil36,R.array.theesil37,R.array.theesil38};
    String rtoC[], rtoRealCode[], qualificateCode[], stateCode[];

    private Spinner mspinnerRTO, mspinnerRelationshipType, mspinnerQualification, mspinnerGender;
    private Spinner mspinnerIdmark, mspinnerBloodGroup, mspinnerRH, mspinnerPermanentState,mspinnerPresentState;
    private Spinner mspinnerCitizenship, mspinnerCountry,mspinnerIdmark2, mSPerDistrict,meditTextPermanentTaluka;

    private ImageView mimageViewDatePicker;

    private ImageView buttonNextPersonalDetails;
    private ImageView buttonClearPersonalDetails;
    private ImageView buttonBackPersonalDetails;

    private OnFragmentInteractionListener mListener;
    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private static final String mFinalString1="mFinalString1";
    private static final String PGInfo="PgInfo";
    private final String NICjson= "NICjson";
    private int num;


    public PersonalDetails() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PersonalDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static PersonalDetails newInstance(String param1, String param2) {
        PersonalDetails fragment = new PersonalDetails();
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
        View rootView = inflater.inflate(R.layout.fragment_personal_details, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        rtoC=getResources().getStringArray(R.array.Rto_code);
        rtoRealCode=getResources().getStringArray(R.array.Rto_code_Real);
        qualificateCode=getResources().getStringArray(R.array.qualificationCode);
        stateCode=getResources().getStringArray(R.array.statesCode);
        Random rand = new Random();
        num = rand.nextInt(9000000) + 1000000;
        initailizeFelids(rootView);
//        sendPostRequest(rootView);
        hidePermanent();
        hidePresent();
        setListner();
        return rootView;
    }

    public void onClickPersonalDetails(View view) {
        switch (view.getId()) {
            case R.id.buttonNextPersonalDetail:
//                getFieldData();
                removeSpace();
                if(validate()) {
                    aBooleanstop=true;
                    saveSession();
                    String s = detailString();
                    saveSharedPreference();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("2", "1")).commit();
                }else
                {

                }
                break;
            case R.id.editTextYear:
                if(!meditViewPlaceOfBirth.getText().toString().equals("INDIA"))
                {
                    meditViewMonth.setEnabled(true);
                    meditViewYear.setEnabled(true);
                }
                break;
            case R.id.editTextMonth:
                if(!meditViewPlaceOfBirth.getText().toString().equals("INDIA"))
                {
                    meditViewMonth.setEnabled(true);
                    meditViewYear.setEnabled(true);
                }
                break;
            case R.id.buttonBackPersonalDetail:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("0", "1")).commit();
                break;
            case R.id.buttonClearPersonalDetail:
                clearFelids(view);
                break;
            case R.id.imageViewDatePicker:
                DialogFragment newFragment = new DatePickerFragment();



                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                break;
            case R.id.linearlayoutPermanant:
                if(permanent==true)
                    hidePermanent();
                else
                    showPremanent();

                break;
            case R.id.linearlayoutPresentAddress:
                if(present==true)
                    hidePresent();
                else
                    showPresent();
                break;
            case R.id.linearlayoutOtherInfo:
                if(otherInfo==true)
                    hideOtherInfo();
                else
                    showOtherInfo();
                break;
            case R.id.linearlayoutPersonalDetail:
                if(personalDetail==true)
                    hidePersonalDetail();
                else
                    showPersonalDetail();
                break;
            default:
                break;
        }
    }

    private void saveSharedPreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(mFinalString1, detailString());
        editor.putString(PGInfo,jsonString());
        editor.putString(NICjson,jsonNIC());
        editor.putString("EmailZ",meditViewEmail.getText().toString());
        editor.commit();
    }


    private void showToast(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    private void setListner() {
        mimageViewDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPersonalDetails(view);
            }
        });

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
        buttonNextPersonalDetails.setOnClickListener(new View.OnClickListener()
                                                     {
                                                         @Override
                                                         public void onClick(View view) {
                                                             onClickPersonalDetails(view);
                                                         }
                                                     }
        );
        buttonBackPersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPersonalDetails(view);
            }
        });
        buttonClearPersonalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPersonalDetails(view);

            }
        });

        mlinearlayoutPresentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPersonalDetails(v);
            }
        });
        mlinearlayoutPremanentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPersonalDetails(v);
            }
        });
        mlinearlayoutPersonalDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPersonalDetails(v);
            }
        });
        mlinearlayoutOtherInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickPersonalDetails(v);
            }
        });

    }



    private void initailizeFelids(View rootView) {

        imageViewPermanent=(ImageView) rootView.findViewById(R.id.imagePermanent);
        imageViewPresent=(ImageView) rootView.findViewById(R.id.imagePresent);
        imageViewPersonal=(ImageView) rootView.findViewById(R.id.imagePersonal);
        imageViewOther=(ImageView) rootView.findViewById(R.id.imageOther);


        meditViewApplicantFirstName = (EditText) rootView.findViewById(R.id.editTextViewApplicantFirstName);
        meditViewApplicantMiddleName = (EditText) rootView.findViewById(R.id.editTextViewApplicantMiddleName);
        meditViewApplicantLastName = (EditText) rootView.findViewById(R.id.editTextViewApplicantLastName);

        meditViewEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        mimageViewDatePicker = (ImageView) rootView.findViewById(R.id.imageViewDatePicker);
        mtextViewDate = (TextView) rootView.findViewById(R.id.textViewDate);
        meditViewPlaceOfBirth= (EditText) rootView.findViewById(R.id.editTextPlaceofBirth);
        meditViewYear = (EditText) rootView.findViewById(R.id.editTextYear);
        meditViewMonth = (EditText) rootView.findViewById(R.id.editTextMonth);

//        meditViewYear.setEnabled(false);
//        meditViewMonth.setEnabled(false);

        meditViewApplicantRelationsName = (EditText) rootView.findViewById(R.id.editTextRelationsName);
        meditViewApplicantRelationsMiddleName = (EditText) rootView.findViewById(R.id.editTextRelationsMiddleName);
        meditViewApplicantRelationsLastName = (EditText) rootView.findViewById(R.id.editTextRelationsLastName);

        meditTextPermanentFlatNum = (EditText) rootView.findViewById(R.id.editTextPermanentFlatNum);
        meditTextPermanentHouseName = (EditText) rootView.findViewById(R.id.editTextPermanentHouseName);
        meditTextPermanentHouseNum = (EditText) rootView.findViewById(R.id.editTextPermanentHouseNum);
        meditTextPermanentStreet = (EditText) rootView.findViewById(R.id.editTextPermanentStreet);
        meditTextPermanentLocality = (EditText) rootView.findViewById(R.id.editTextPermanentLocality);
        meditTextPermanentvillage = (EditText) rootView.findViewById(R.id.editTextPermanentvillage);
        meditTextPermanentTaluka = (Spinner) rootView.findViewById(R.id.editTextPermanentTaluka);
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

        mspinnerRTO = (Spinner) rootView.findViewById(R.id.spinnerRTO);
        mspinnerCountry = (Spinner) rootView.findViewById(R.id.spinnerCountry);
        mspinnerRelationshipType = (Spinner) rootView.findViewById(R.id.spinnerRelationshipType);
        mspinnerQualification = (Spinner) rootView.findViewById(R.id.spinnerQualification);
        mspinnerGender = (Spinner) rootView.findViewById(R.id.spinnerGender);
        mspinnerIdmark = (Spinner) rootView.findViewById(R.id.spinnerIdmark);
        mspinnerIdmark2 = (Spinner) rootView.findViewById(R.id.spinnerIdmark2);
        mspinnerBloodGroup = (Spinner) rootView.findViewById(R.id.spinnerBloodGroup);
        mspinnerRH = (Spinner) rootView.findViewById(R.id.spinnerRH);
        mSPerDistrict = (Spinner) rootView.findViewById(R.id.sPerDistrict);
        mspinnerPermanentState = (Spinner) rootView.findViewById(R.id.spinnerPermanentState);
        mspinnerPresentState = (Spinner) rootView.findViewById(R.id.spinnerPresentState);
        mspinnerCitizenship = (Spinner) rootView.findViewById(R.id.spinnerCitizenship);



        mlinearlayoutPersonalDetail =(RelativeLayout) rootView.findViewById(R.id.linearlayoutPersonalDetail);
        mlinearlayoutPremanentAddress=(RelativeLayout) rootView.findViewById(R.id.linearlayoutPermanant);
        mlinearlayoutPresentAddress =(RelativeLayout) rootView.findViewById(R.id.linearlayoutPresentAddress);
        mlinearlayoutOtherInfo =(RelativeLayout) rootView.findViewById(R.id.linearlayoutOtherInfo);


        mtablelayoutPersonalDetail = (TableLayout)rootView.findViewById(R.id.tablelayoutPersonalDetail);
        mtablelayoutPresentAddress = (TableLayout)rootView.findViewById(R.id.tablelayoutPresentAddress);
        mtablelayoutPremanentAddress = (TableLayout)rootView.findViewById(R.id.tablelayoutPermanenetAddress);
        mtablelayoutOtherInfo = (TableLayout)rootView.findViewById(R.id.tablelayoutOtherInfo);


        buttonNextPersonalDetails = (ImageView) rootView.findViewById(R.id.buttonNextPersonalDetail);
        buttonClearPersonalDetails = (ImageView) rootView.findViewById(R.id.buttonClearPersonalDetail);
        buttonBackPersonalDetails = (ImageView) rootView.findViewById(R.id.buttonBackPersonalDetail);


        scrollView=(ScrollView) rootView.findViewById(R.id.scrollView);
        textView_personalDetail = (TextView) rootView.findViewById(R.id.textView_personalDetail);
        textView13 = (TextView) rootView.findViewById(R.id.textView13);
        setectRto_text = (EditText) rootView.findViewById(R.id.setectRto_text);
        spinnerRelationshipType_text = (EditText) rootView.findViewById(R.id.spinnerRelationshipType_text);
        spinnerCountry_text = (EditText) rootView.findViewById(R.id.spinnerCountry_text);
        spinnerCitizenship_text = (EditText) rootView.findViewById(R.id.spinnerCitizenship_text);
        sPerDistrict_text = (EditText) rootView.findViewById(R.id.sPerDistrict_text);
        spinnerPermanentState_text = (EditText) rootView.findViewById(R.id.spinnerPermanentState_text);
        spinnerPresentState_text = (EditText) rootView.findViewById(R.id.spinnerPresentState_text);
        spinnerQualification_text = (EditText) rootView.findViewById(R.id.spinnerQualification_text);
        spinnerIdmark_text = (EditText) rootView.findViewById(R.id.spinnerIdmark_text);
        spinnerIdmark2_text = (EditText) rootView.findViewById(R.id.spinnerIdmark2_text);
//        spinnerBloodGroup_text = (EditText) rootView.findViewById(R.id.spinnerBloodGroup_text);
//        spinnerRH_text = (EditText) rootView.findViewById(R.id.spinnerRH_text);

        addListenerOnSpinnerItemSelection();
        addListenerOnSpinnerEducationQualification();
        retrivesession();

        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.clearFocus();
                return false;
            }
        });

    }

    private void retrivesession() {
        DBAdapter db = new DBAdapter(getActivity());

        //---get all contacts---
        db.open();
        Cursor c = db.getAllDetails();
        if(c.moveToFirst()) {
            if(c.getString(1).length()>0)
                mspinnerRTO.setSelection(Integer.parseInt(c.getString(1)));

            if(c.getString(23).length()>0)
                mSPerDistrict.setSelection(Integer.parseInt(c.getString(23)));

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
            else
                mspinnerCountry.setSelection(1);

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

            if(c.getString(22).length()>0)
            {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, getResources().getStringArray(arrTaluka[Integer.parseInt(c.getString(23))]));
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                meditTextPermanentTaluka.setAdapter(spinnerArrayAdapter);
                meditTextPermanentTaluka.setSelection(Integer.parseInt(c.getString(22)));
            }


            if(c.getString(24).length()>0)
                mspinnerPermanentState .setSelection(Integer.parseInt(c.getString(24)));
            else
                mspinnerPermanentState .setSelection(1);

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
                mspinnerPresentState.setSelection(Integer.parseInt(c.getString(37)));
            else
                mspinnerPresentState.setSelection(1);

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
                mspinnerIdmark2.setSelection(Integer.parseInt(c.getString(45)));

            if(c.getString(46).length()>0)
                mspinnerBloodGroup.setSelection(Integer.parseInt(c.getString(46)));

            if(c.getString(47).length()>0)
                mspinnerRH.setSelection(Integer.parseInt(c.getString(47)));
        }
        db.close();
        addListenerOnSpinnerRtoSelection();
        addListenerOnSpinnerDistrictSelection();
//        addListenerOnSpinnerQuaSelection();
    }

    private void saveSession()
    {

//        hashMap.put("statecode","");
        hashMap.put("rtocode",Integer.toString(mspinnerRTO.getSelectedItemPosition()));
//        hashMap.put("licence_type","L");
        hashMap.put("first_name",meditViewApplicantFirstName.getText().toString());
        hashMap.put("middle_name",meditViewApplicantMiddleName.getText().toString());
        hashMap.put("last_name",meditViewApplicantLastName.getText().toString());

        hashMap.put("dob",mtextViewDate.getText().toString());
        hashMap.put("gender",Integer.toString(mspinnerGender.getSelectedItemPosition()));

        hashMap.put("birth_place",meditViewPlaceOfBirth.getText().toString());
        hashMap.put("year",meditViewYear.getText().toString());
        hashMap.put("month",meditViewMonth.getText().toString());
        hashMap.put("birth_country",Integer.toString(mspinnerCountry.getSelectedItemPosition()));
        hashMap.put("email_id",meditViewEmail.getText().toString());

        hashMap.put("relation",Integer.toString(mspinnerRelationshipType.getSelectedItemPosition()));
        hashMap.put("p_first_name",meditViewApplicantRelationsName.getText().toString());
        hashMap.put("p_middle_name",meditViewApplicantMiddleName.getText().toString());
        hashMap.put("p_last_name",meditViewApplicantRelationsLastName.getText().toString());

//        hashMap.put("permanent_address","");
        hashMap.put("p_flat_no",meditTextPermanentFlatNum.getText().toString());
        hashMap.put("p_flat_house_name",meditTextPermanentHouseName.getText().toString());
        hashMap.put("p_house_no",meditTextPermanentHouseNum.getText().toString());
        hashMap.put("p_street",meditTextPermanentStreet.getText().toString());
        hashMap.put("p_locality",meditTextPermanentLocality.getText().toString());
        hashMap.put("p_village_city",meditTextPermanentvillage.getText().toString());
        hashMap.put("p_taluka",Integer.toString(meditTextPermanentTaluka.getSelectedItemPosition()));
        hashMap.put("p_district",Integer.toString(mSPerDistrict.getSelectedItemPosition()));
        hashMap.put("p_state",Integer.toString(mspinnerPermanentState.getSelectedItemPosition()));

        hashMap.put("p_years",meditTextPermanentYear.getText().toString());
        hashMap.put("p_months",meditTextPermanentMonth.getText().toString());
        hashMap.put("p_pin",meditTextPermanentPinCode.getText().toString());
        hashMap.put("p_mobile_no",meditTextPermanentMoblieNo.getText().toString());
//        hashMap.put("p_phone_no","");


        hashMap.put("t_flat_no",meditTextPresentFlatNum.getText().toString());
        hashMap.put("t_flat_house_name",meditTextPresentHouseName.getText().toString());
        hashMap.put("t_house_no",meditTextPresentHouseNum.getText().toString());
        hashMap.put("t_street",meditTextPermanentStreet.getText().toString());
        hashMap.put("t_locality",meditTextPermanentLocality.getText().toString());
        hashMap.put("t_village_city",meditTextPresentvillage.getText().toString());
        hashMap.put("t_taluka",meditTextPresentTaluka.getText().toString());
        hashMap.put("t_district",meditTextPresentDistrict.getText().toString());

        hashMap.put("t_state",Integer.toString(mspinnerPresentState.getSelectedItemPosition()));

        hashMap.put("t_years",meditTextPresentYear.getText().toString());
        hashMap.put("t_months",meditTextPresentMonth.getText().toString());
        hashMap.put("t_pin",meditTextPresentPinCode.getText().toString());
        hashMap.put("t_moblie_no",meditTextPresentMoblieNo.getText().toString());


        hashMap.put("citizenship_status",Integer.toString(mspinnerCitizenship.getSelectedItemPosition()));
        hashMap.put("edu_qualification",Integer.toString(mspinnerQualification.getSelectedItemPosition()));
        hashMap.put("identification_marks",Integer.toString(mspinnerIdmark.getSelectedItemPosition()));
        hashMap.put("identification_marks2",Integer.toString(mspinnerIdmark2.getSelectedItemPosition()));
        hashMap.put("blood_group",Integer.toString(mspinnerBloodGroup.getSelectedItemPosition()));
        hashMap.put("blood_group_rh",Integer.toString(mspinnerRH.getSelectedItemPosition()));
        try{


            DBAdapter db = new DBAdapter(getActivity());

            //---get all contacts---
            db.open();
            if(db.updateData(hashMap))
            {
                System.out.println("date Saved----------");
            }
            else {
                System.out.println("date cannot be Saved----------");
            }
        }catch (Exception e)
        {
            System.out.println("ErrorSaving");
        }
    }

    private void clearFelids(View rootView) {
        meditViewApplicantFirstName.setText("");
        meditViewApplicantMiddleName.setText("");
        meditViewApplicantLastName.setText("");

        meditViewApplicantRelationsName.setText("");
        meditViewApplicantRelationsMiddleName.setText("");
        meditViewApplicantRelationsLastName.setText("");

        meditViewPlaceOfBirth.setText("");
        meditViewEmail.setText("");

        meditTextPermanentFlatNum.setText("");
        meditTextPermanentHouseName.setText("");
        meditTextPermanentHouseNum.setText("");
        meditTextPermanentStreet.setText("");
        meditTextPermanentLocality.setText("");
        meditTextPermanentvillage.setText("");
        meditTextPermanentTaluka.setSelection(0);
        mSPerDistrict.setSelection(0);
        meditTextPermanentMonth.setText("");
        meditTextPermanentYear.setText("");
        meditTextPermanentPinCode.setText("");
        meditTextPermanentMoblieNo.setText("");

        meditTextPresentFlatNum.setText("");
        meditTextPresentHouseName.setText("");
        meditTextPresentHouseNum.setText("");
        meditTextPresentStreet.setText("");
        meditTextPresentLocality.setText("");
        meditTextPresentvillage.setText("");
        meditTextPresentTaluka.setText("");
        meditTextPresentDistrict.setText("");
        meditTextPresentMonth.setText("");
        meditTextPresentYear.setText("");
        meditTextPresentPinCode.setText("");
        meditTextPresentMoblieNo.setText("");

        mspinnerRTO.setSelection(0);
        mspinnerRelationshipType.setSelection(0);
        mspinnerQualification.setSelection(0);
        mspinnerGender.setSelection(0);
        mspinnerIdmark.setSelection(0);
        mspinnerIdmark2.setSelection(0);
        mspinnerBloodGroup.setSelection(0);
        mspinnerRH.setSelection(0);
        mspinnerPermanentState.setSelection(1);
        mspinnerPresentState.setSelection(1);
        mspinnerCountry.setSelection(1);

        mtextViewDate.setText("");
    }

    private void removeSpace() {
        meditViewApplicantFirstName.setText(meditViewApplicantFirstName.getText().toString().replaceAll("\\s",""));
        meditViewApplicantMiddleName.setText(meditViewApplicantMiddleName.getText().toString().replaceAll("\\s",""));
        meditViewApplicantLastName.setText(meditViewApplicantLastName.getText().toString().replaceAll("\\s",""));

        meditViewApplicantRelationsName.setText(meditViewApplicantRelationsName.getText().toString().replaceAll("\\s",""));
        meditViewApplicantRelationsMiddleName.setText(meditViewApplicantRelationsMiddleName.getText().toString().replaceAll("\\s",""));
        meditViewApplicantRelationsLastName.setText(meditViewApplicantRelationsLastName.getText().toString().replaceAll("\\s",""));

        meditViewPlaceOfBirth.setText(meditViewPlaceOfBirth.getText().toString().replaceAll("\\s",""));
        meditViewEmail.setText(meditViewEmail.getText().toString().replaceAll("\\s",""));

        meditTextPermanentFlatNum.setText(meditTextPermanentFlatNum.getText().toString().replaceAll("\\s",""));
        meditTextPermanentHouseName.setText(meditTextPermanentHouseName.getText().toString().replaceAll("\\s",""));
        meditTextPermanentHouseNum.setText(meditTextPermanentHouseNum.getText().toString().replaceAll("\\s",""));
        meditTextPermanentStreet.setText(meditTextPermanentStreet.getText().toString().replaceAll("\\s",""));
        meditTextPermanentLocality.setText(meditTextPermanentLocality.getText().toString().replaceAll("\\s",""));
        meditTextPermanentvillage.setText(meditTextPermanentvillage.getText().toString().replaceAll("\\s",""));
        meditTextPermanentMonth.setText(meditTextPermanentMonth.getText().toString().replaceAll("\\s",""));
        meditTextPermanentYear.setText(meditTextPermanentYear.getText().toString().replaceAll("\\s",""));
        meditTextPermanentPinCode.setText(meditTextPermanentPinCode.getText().toString().replaceAll("\\s",""));
        meditTextPermanentMoblieNo.setText(meditTextPermanentMoblieNo.getText().toString().replaceAll("\\s",""));

        meditTextPresentFlatNum.setText(meditTextPresentFlatNum.getText().toString().replaceAll("\\s",""));
        meditTextPresentHouseName.setText(meditTextPresentHouseName.getText().toString().replaceAll("\\s",""));
        meditTextPresentHouseNum.setText(meditTextPresentHouseNum.getText().toString().replaceAll("\\s",""));
        meditTextPresentStreet.setText(meditTextPresentStreet.getText().toString().replaceAll("\\s",""));
        meditTextPresentLocality.setText(meditTextPresentLocality.getText().toString().replaceAll("\\s",""));
        meditTextPresentvillage.setText(meditTextPresentvillage.getText().toString().replaceAll("\\s",""));
        meditTextPresentTaluka.setText(meditTextPresentTaluka.getText().toString().replaceAll("\\s",""));
        meditTextPresentDistrict.setText(meditTextPresentDistrict.getText().toString().replaceAll("\\s",""));
        meditTextPresentMonth.setText(meditTextPresentMonth.getText().toString().replaceAll("\\s",""));
        meditTextPresentYear.setText(meditTextPresentYear.getText().toString().replaceAll("\\s",""));
        meditTextPresentPinCode.setText(meditTextPresentPinCode.getText().toString().replaceAll("\\s",""));
        meditTextPresentMoblieNo.setText(meditTextPresentMoblieNo.getText().toString().replaceAll("\\s",""));
    }

    private boolean validate() {
        String s="000";
        if(meditTextPermanentPinCode.getText().length()==6)
        {
            s = meditTextPermanentPinCode.getText().toString().substring(0,2);
        }

        if( mspinnerRTO.getSelectedItemPosition()==0)
        {
            showToast("Select RTO");

            setectRto_text.setError("Select RTO");
            setectRto_text.requestFocus();

            return false;
        }
        else if(meditViewApplicantFirstName.getText().length()==0)
        {
            showToast("Enter applicant first name");

            showPersonalDetail();

            meditViewApplicantFirstName.setError("Enter applicant first name");
            meditViewApplicantFirstName.requestFocus();

            return false;
        }
        else if(meditViewApplicantLastName.getText().length()==0)
        {
            showToast("Enter applicant last name");

            showPersonalDetail();

            meditViewApplicantLastName.setError("Enter applicant last name");
            meditViewApplicantLastName.requestFocus();
            return false;
        }
        else if(!validation.isAlpha(meditViewApplicantFirstName.getText().toString()) || !validation.isAlpha(meditViewApplicantLastName.getText().toString()))
        {
            showToast("Only aplhabets  allowed in the Name ");

            showPersonalDetail();

            meditViewApplicantFirstName.setError("Only aplhabets allowed in the Name ");
            meditViewApplicantFirstName.requestFocus();

            return false;
        }
        else if(mtextViewDate.getText().length()==0)
        {
            showToast("Enter Date of Birth");

            showPersonalDetail();

            mtextViewDate.setError("Enter Date of Birth");
            mtextViewDate.requestFocus();

            return false;
        }
        else if(mspinnerGender.getSelectedItemPosition()==0)
        {
            showToast("Select Gender");
            showPersonalDetail();
            mspinnerGender.requestFocus();

            return false;
        }

        else if(mspinnerCountry.getSelectedItemPosition()==0)
        {
            showToast("Select Country of Birth");

            showPersonalDetail();

            spinnerCountry_text.setError("Select Country of Birth");
            spinnerCountry_text.requestFocus();

            return false;
        }
        else if(mspinnerCitizenship.getSelectedItemPosition()==0)
        {
            showToast("Select Citizenship");

            showPersonalDetail();

            spinnerCitizenship_text.setError("Select Citizenship");
            spinnerCitizenship_text.requestFocus();

            return false;
        }
        else if(meditViewPlaceOfBirth.getText().length()==0)
        {
            showToast("Enter place of birth");

            showPersonalDetail();

            meditViewPlaceOfBirth.setError("Enter place of birth");
            meditViewPlaceOfBirth.requestFocus();

            return false;
        }
        else if(meditViewEmail.getText().length()==0)
        {
            showToast("Enter valid Email Id");

            showPersonalDetail();

            meditViewEmail.setError("Enter Email");
            meditViewEmail.requestFocus();

            return false;
        }
        else if(!validation.isEmailAddress(meditViewEmail,true))
        {
            showToast("Enter valid Email Id");

            showPersonalDetail();

            meditViewEmail.setError("Enter valid Email Id");
            meditViewEmail.requestFocus();

            return false;

        }
        else if(mspinnerRelationshipType.getSelectedItemPosition()==0)
        {
            showToast("Select RelationType");

            showPersonalDetail();

            spinnerRelationshipType_text.setError("Select RelationType");
            spinnerRelationshipType_text.requestFocus();

            return false;
        }
        else if(mspinnerGender.getSelectedItemPosition()==1 && mspinnerRelationshipType.getSelectedItemPosition()==2)
        {
            showToast("RelationType and Gender Does not match");
            showPersonalDetail();
            mspinnerGender.requestFocus();
            return false;
        }

        else if(meditViewApplicantRelationsName.getText().length()==0)
        {
            showToast("Enter applicant relative first name");

            showPersonalDetail();

            meditViewApplicantRelationsName.setError("Enter applicant relative first name");
            meditViewApplicantRelationsName.requestFocus();

            return false;
        }
        else if(meditViewApplicantRelationsLastName.getText().length()==0)
        {
            showToast("Enter applicant relative Last name");

            showPersonalDetail();

            meditViewApplicantRelationsLastName.setError("Enter applicant relative Last name");
            meditViewApplicantRelationsLastName.requestFocus();

            return false;
        }
        else  if(!validation.isAlpha(meditViewApplicantFirstName.getText().toString()) )
        {
            showToast("Only aplhabets allowed in the Name");

            showPersonalDetail();

            meditViewApplicantFirstName.setError("Only aplhabets allowed in the Name");
            meditViewApplicantFirstName.requestFocus();

            return false;
        }
        else if(!validation.isAlpha(meditViewApplicantRelationsLastName.getText().toString()))
        {
            showToast("Only aplhabets allowed in the Name");

            showPersonalDetail();

            meditViewApplicantRelationsName.setError("Only aplhabets allowed in the Name");
            meditViewApplicantRelationsName.requestFocus();
        }
        else if(!validation.isAlpha(meditViewApplicantRelationsMiddleName.getText().toString()))
        {
            showToast("Only aplhabets allowed in the Name");

            showPersonalDetail();

            meditViewApplicantRelationsLastName.setError("Only aplhabets allowed in the Name");
            meditViewApplicantRelationsLastName.requestFocus();
        }
        else if(meditTextPermanentFlatNum.getText().length()==0 && meditTextPermanentHouseName.getText().length()==0 && meditTextPermanentHouseNum.getText().length()==0 && meditTextPermanentStreet.getText().length()==0 && meditTextPermanentLocality.getText().length()==0 && meditTextPermanentvillage.getText().length()==0 )
        {
            showToast("Atleast one field required in Present Address");

            showPremanent();

            meditTextPermanentFlatNum.setError("Atleast one field required in Present Address");
            meditTextPermanentFlatNum.requestFocus();


            return false;
        }

        else if( meditTextPermanentTaluka.getSelectedItemPosition()==0)
        {
            showToast("Present Taluka cannot be empty");

            showPremanent();

            meditTextPermanentTaluka.requestFocus();

            return false;
        }
        else if(mSPerDistrict.getSelectedItemPosition()==0)
        {
            showToast("Present District cannot be empty");

            showPremanent();

            sPerDistrict_text.setError("Present District cannot be empty");
            sPerDistrict_text.requestFocus();

            return false;
        }
        else if(mspinnerPermanentState.getSelectedItemPosition()==0)
        {
            showToast("Select Present State");

            showPremanent();

            spinnerPermanentState_text.setError("Select Present State");
            spinnerPermanentState_text.requestFocus();

            return false;
        }

        else if(meditTextPermanentMonth.getText().length()==0 && meditTextPermanentYear.getText().length()==0)
        {
            showToast("Present Years cannot be empty");

            showPremanent();

            meditTextPermanentMonth.setError("Present month cannot be empty");
            meditTextPermanentMonth.requestFocus();

            return false;
        }
        else if(validation.hasText(meditTextPermanentYear) && !validation.isPhoneNumber(meditTextPermanentYear,true))
        {
            showToast("only digits allowed in Present Years");

            showPremanent();

            meditTextPermanentYear.setError("only digits allowed in Present Years");
            meditTextPermanentYear.requestFocus();

            return false;
        }
        else if(validation.hasText(meditTextPermanentMonth) && !validation.isPhoneNumber(meditTextPermanentMonth,true))
        {
            showToast("only digits allowed in Present Months");

            showPremanent();

            meditTextPermanentMonth.setError("only digits allowed in Months");
            meditTextPermanentMonth.requestFocus();

            return false;
        }
        else if(meditTextPermanentPinCode.getText().length()<6)
        {
            showToast("Invalid Present Pincode format");

            showPremanent();

            meditTextPermanentPinCode.setError("Invalid Pincode format");
            meditTextPermanentPinCode.requestFocus();

            return false;
        }
        else if(!(s.equals("77") || s.equals("76") || s.equals("75")))
        {
            showToast("Present Pincode not vaild");

            showPremanent();
            meditTextPermanentPinCode.setError("Invalid Pincode");
            meditTextPermanentPinCode.requestFocus();
            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPermanentMonth,true))
        {
            showToast("only digits allowed in Present Month");

            showPremanent();

            meditTextPermanentMonth.setError("only digits allowed in Month");
            meditTextPermanentMonth.requestFocus();

            return false;
        }
        else if(meditTextPermanentMoblieNo.getText().length()!=10)
        {
            showToast("Mobile Number should have 10 digits");

            showPremanent();

            meditTextPermanentMoblieNo.setError("Mobile Number should have 10 digits");
            meditTextPermanentMoblieNo.requestFocus();

            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPermanentMoblieNo,true))
        {
            showToast("only digits allowed in Mobile Number");
            showPremanent();
            meditTextPermanentMoblieNo.setError("only digits allowed in Mobile Number");
            meditTextPermanentMoblieNo.requestFocus();
            return false;
        }

        else if(meditTextPresentFlatNum.getText().length()==0 && meditTextPresentHouseName.getText().length()==0 && meditTextPresentHouseNum.getText().length()==0 && meditTextPresentStreet.getText().length()==0 && meditTextPresentLocality.getText().length()==0 && meditTextPresentvillage.getText().length()==0 &&  meditTextPresentTaluka.getText().length()==0)
        {
            showToast("At least one field required in Permanent Address");
            showPresent();
            meditTextPresentFlatNum.setError("At least one field required in Permanent Address");
            meditTextPresentFlatNum.requestFocus();
            return false;
        }

        else if(meditTextPresentDistrict.getText().length()==0)
        {
            showToast("Permanent District cannot be empty");

            showPresent();

            meditTextPresentDistrict.setError("Permanent District cannot be empty");
            meditTextPresentDistrict.requestFocus();

            return false;
        }
        else if(mspinnerPresentState.getSelectedItemPosition()==0)
        {
            showToast("Select Permanent State ");

            showPresent();

            spinnerPresentState_text.setError("Select Permanent State");
            spinnerPresentState_text.requestFocus();

            return false;
        }

        else if(meditTextPresentYear.getText().length()==0)
        {
            showToast("Years cannot be empty");

            showPresent();

            meditTextPresentYear.setError("Years cannot be empty");
            meditTextPresentYear.requestFocus();

            return false;
        }
        else if(validation.hasText(meditTextPresentYear) && !validation.isPhoneNumber(meditTextPresentYear,true))
        {
            showToast("only digit in Years");

            showPresent();

            meditTextPresentYear.setError("only digit in Years");
            meditTextPresentYear.requestFocus();

            return false;
        }
        else if(validation.hasText(meditTextPresentMonth) && !validation.isPhoneNumber(meditTextPresentMonth,true))
        {
            showToast("only digits allowed in Months");

            showPresent();

            meditTextPresentMonth.setError("only digits allowed in Months");
            meditTextPresentMonth.requestFocus();

            return false;
        }
        else if(meditTextPresentPinCode.getText().length()<6)
        {
            showToast("Invalid Permanent Pincode ");

            showPresent();

            meditTextPresentPinCode.setError("Invalid Permanent Pincode ");
            meditTextPresentPinCode.requestFocus();

            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPresentPinCode,true))
        {
            showToast("Digits only in Pincode ");

            showPresent();

            meditTextPresentPinCode.setError("Digits only in Pincode");
            meditTextPresentPinCode.requestFocus();

            return false;
        }
        else if(meditTextPresentMoblieNo.getText().length()!=10)
        {
            showToast("Mobile number should have 10 digits");

            showPresent();

            meditTextPresentMoblieNo.setError("Mobile number should have 10 digits");
            meditTextPresentMoblieNo.requestFocus();

            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPresentMoblieNo,true))
        {
            showToast("Only digits allowed in mobile number ");

            showPresent();

            meditTextPresentMoblieNo.setError("Only digits allowed in mobile number");
            meditTextPresentMoblieNo.requestFocus();

            return false;
        }
        else if(mspinnerQualification.getSelectedItemPosition()==0)
        {
            showToast("Select Qualification");

            showOtherInfo();

            spinnerQualification_text.setError("Select Qualification");
            spinnerQualification_text.requestFocus();

            return false;
        }
        else if(mspinnerIdmark.getSelectedItemPosition()==0)
        {
            showToast("Select Id Mark");

            showOtherInfo();

            spinnerIdmark_text.setError("Select Id Mark");
            spinnerIdmark_text.requestFocus();

            return false;
        }

        else if(mspinnerIdmark2.getSelectedItemPosition()==0)
        {
            showToast("Select Id Mark2");

            showOtherInfo();

            spinnerIdmark2_text.setError("Select Id Mark2");
            spinnerIdmark2_text.requestFocus();

            return false;
        }

        else if(mspinnerBloodGroup.getSelectedItemPosition()==0)
        {
            showToast("Select BloodGroup");

            showOtherInfo();
//            mspinnerBloodGroup.setError("Select BloodGroup");
            mspinnerBloodGroup.requestFocus();

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
        meditTextPresentTaluka.setText(meditTextPermanentTaluka.getSelectedItem().toString());
        meditTextPresentDistrict.setText(mSPerDistrict.getSelectedItem().toString());
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

    private String detailString()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("receiptNum",Integer.toString(num));
        editor.commit();
        String s=
//                "ref_num="+num+
//                "&first_name="+meditViewApplicantFirstName.getText()+
                "&statecode=ODISHA"+
//                "&rtocode="+(rtoCode[mspinnerRTO.getSelectedItemPosition()+1])+
//                "&rtocode="+mspinnerRTO.getSelectedItem().toString().toUpperCase()+
                        "&rtocode="+Integer.parseInt(rtoC[mspinnerRTO.getSelectedItemPosition()-1])+
                        "&first_name="+meditViewApplicantFirstName.getText().toString().toUpperCase()+
                        "&middle_name="+meditViewApplicantMiddleName.getText().toString().toUpperCase()+
                        "&last_name="+meditViewApplicantLastName.getText().toString().toUpperCase()+

                        "&dob="+mtextViewDate.getText().toString().toUpperCase()+
                        "&gender="+mspinnerGender.getSelectedItem().toString().toUpperCase()+
                        "&relation="+mspinnerRelationshipType.getSelectedItem().toString().toUpperCase()+
                        "&p_first_name="+meditViewApplicantRelationsName.getText().toString().toUpperCase()+
                        "&p_middle_name="+meditViewApplicantRelationsMiddleName.getText().toString().toUpperCase()+
                        "&p_last_name="+meditViewApplicantRelationsLastName.getText().toString().toUpperCase()+
//                "&edu_qualification="+qualificatinCode[mspinnerQualification.getSelectedItemPosition()+1]+
                        "&edu_qualification="+mspinnerQualification.getSelectedItem().toString().toUpperCase()+
                        "&identification_mark="+mspinnerIdmark.getSelectedItem().toString().toUpperCase()+
                        "&blood_group="+mspinnerBloodGroup.getSelectedItemPosition()+

                        "&p_flat_house_no="+meditTextPresentFlatNum.getText().toString().toUpperCase()+
                        "&p_house_name="+meditTextPermanentHouseName.getText().toString().toUpperCase()+
                        "&p_house_no="+meditTextPermanentHouseNum.getText().toString().toUpperCase()+


                        "&p_street_locality="+meditTextPermanentStreet.getText().toString().toUpperCase()+
                        "&p_locality="+meditTextPermanentLocality.getText().toString().toUpperCase()+
                        "&p_village_city="+meditTextPermanentvillage.getText().toString().toUpperCase()+
                        "&p_taluka="+meditTextPermanentTaluka.getSelectedItem().toString().toUpperCase()+
                        "&p_district="+mSPerDistrict.getSelectedItem().toString().toUpperCase()+
//                "&p_state="+statecode[mspinnerPermanentState.getSelectedItemPosition()+1]+
                        "&p_state="+mspinnerPermanentState.getSelectedItem().toString().toUpperCase()+
                        "&p_pin="+meditTextPermanentPinCode.getText().toString().toUpperCase()+
                        "&p_phone_no="+meditTextPermanentMoblieNo.getText().toString().toUpperCase()+
                        "&p_mobile_no="+meditTextPermanentMoblieNo.getText().toString().toUpperCase()+
                        "&p_years="+meditTextPermanentYear.getText().toString().toUpperCase()+
                        "&p_months="+meditTextPermanentMonth.getText().toString().toUpperCase()+

                        "&t_flat_house_no="+meditTextPresentFlatNum.getText().toString().toUpperCase()+
                        "&t_house_name="+meditTextPresentHouseName.getText().toString().toUpperCase()+
                        "&t_house_no="+meditTextPresentHouseNum.getText().toString().toUpperCase()+

                        "&t_street_locality="+meditTextPresentStreet.getText().toString().toUpperCase()+
                        "&t_locality="+meditTextPresentLocality.getText().toString().toUpperCase()+
                        "&t_village_city="+meditTextPresentvillage.getText().toString().toUpperCase()+
                        "&t_taluka="+meditTextPresentTaluka.getText().toString().toUpperCase()+
                        "&t_district="+meditTextPresentDistrict.getText().toString().toUpperCase()+
//                "&t_state="+statecode[mspinnerPresentState.getSelectedItemPosition()+1]+
                        "&t_state="+mspinnerPresentState.getSelectedItem().toString().toUpperCase()+
                        "&t_pin="+meditTextPresentPinCode.getText().toString().toUpperCase()+
                        "&t_phone_no="+meditTextPresentMoblieNo.getText().toString().toUpperCase()+
                        "&t_mobile_no="+meditTextPresentMoblieNo.getText().toString().toUpperCase()+
                        "&t_years="+meditTextPresentYear.getText().toString().toUpperCase()+
                        "&t_months="+meditTextPresentMonth.getText().toString().toUpperCase()+

                        "&citizenship_status="+mspinnerCitizenship.getSelectedItem().toString().toUpperCase()+

                        "& birth_place="+meditViewPlaceOfBirth.getText().toString().toUpperCase()+
                        "&year="+meditViewYear.getText().toString().toUpperCase()+
                        "&birth_country="+mspinnerCountry.getSelectedItem().toString().toUpperCase()+
                        "&email_id="+meditViewEmail.getText().toString().toUpperCase()+
                        "&identification_marks="+mspinnerIdmark.getSelectedItem().toString().toUpperCase()+
                        "&identification_marks2="+mspinnerIdmark2.getSelectedItem().toString().toUpperCase()+
                        "&rcnumber="+
                        "&parentleterforbelow18age=n"+
                        "&allnecessarycertificates=y"+
                        "&exemptedmedicaltest=n"+
                        "&exemptedpreliminarytest=n"+
                        "&convicted=n"+
                        "&attdlnumber="+
                        "&attdtofconviction="+
                        "&attreason=";
        return s;
    }

    private String jsonNIC() {
        JSONObject jsNic = new JSONObject();
        try {

            jsNic.put("refno",Integer.toString(num));
            jsNic.put("statecode","OD");
            jsNic.put("rtocode",rtoC[mspinnerRTO.getSelectedItemPosition()-1]);
            jsNic.put("rtocodeReal",rtoRealCode[mspinnerRTO.getSelectedItemPosition()-1]);

            jsNic.put("licenceType","l");
            jsNic.put("aFName",meditViewApplicantFirstName.getText().toString());
            jsNic.put("aFMiddle",meditViewApplicantMiddleName.getText().toString());
            jsNic.put("aFLast",meditViewApplicantLastName.getText().toString());
            jsNic.put("dob",mtextViewDate.getText().toString().replace("/","-"));
            jsNic.put("gender",mspinnerGender.getSelectedItem().toString());
            jsNic.put("relation-type",mspinnerRelationshipType.getSelectedItem().toString());
            jsNic.put("pFName",meditViewApplicantRelationsName.getText().toString());
            jsNic.put("pFMiddle",meditViewApplicantRelationsMiddleName.getText().toString());
            jsNic.put("pFLast",meditViewApplicantRelationsLastName.getText().toString());

            jsNic.put("qualification",qualificateCode[mspinnerQualification.getSelectedItemPosition()-1]);


            jsNic.put("identification-marks",mspinnerIdmark.getSelectedItem().toString());
            jsNic.put("identification-marks2",mspinnerIdmark2.getSelectedItem().toString());

            jsNic.put("blood-group",mspinnerBloodGroup.getSelectedItem().toString());


            jsNic.put("tFlatHouseNo",meditTextPermanentFlatNum.getText().toString()+" "+meditTextPermanentHouseName.getText().toString()+" "+meditTextPresentHouseNum.getText().toString());
            jsNic.put("tStreetLocality",meditTextPermanentStreet.getText().toString()+" "+meditTextPermanentLocality.getText().toString());
            jsNic.put("tVillageCity",meditTextPermanentvillage.getText().toString()+" "+meditTextPresentTaluka.getText().toString());

            jsNic.put("tDistrict",mSPerDistrict.getSelectedItem().toString());
            jsNic.put("tState",stateCode[mspinnerPermanentState.getSelectedItemPosition()-1]);
            jsNic.put("tPin",meditTextPermanentPinCode.getText().toString());
            jsNic.put("tPhoneNo",meditTextPermanentMoblieNo.getText().toString());
            jsNic.put("tMobileNo",meditTextPermanentMoblieNo.getText().toString());
            jsNic.put("tYears",meditTextPermanentYear.getText().toString());
            jsNic.put("tMonths",meditTextPermanentMonth.getText().toString());


            jsNic.put("pFlatHouseNo",meditTextPresentFlatNum.getText().toString()+" "+meditTextPresentHouseName.getText().toString()+" "+meditTextPresentHouseNum.getText().toString());
            jsNic.put("pStreetLocality",meditTextPresentStreet.getText().toString()+" "+meditTextPresentLocality.getText().toString());
            jsNic.put("pVillageCity",meditTextPresentvillage.getText().toString()+" "+meditTextPresentTaluka.getText().toString());

            jsNic.put("pDistrict",meditTextPresentDistrict.getText().toString());
            jsNic.put("pState",stateCode[mspinnerPresentState.getSelectedItemPosition()-1]);
            jsNic.put("pPin",meditTextPresentPinCode.getText().toString());
            jsNic.put("pPhoneNo",meditTextPresentMoblieNo.getText().toString());
            jsNic.put("pMobileNo",meditTextPresentMoblieNo.getText().toString());
            jsNic.put("pYears",meditTextPresentYear.getText().toString());
            jsNic.put("pMonths",meditTextPresentMonth.getText().toString());

            jsNic.put("citizenship-status",mspinnerCitizenship.getSelectedItem().toString());
            jsNic.put("birth-place",meditViewPlaceOfBirth.getText().toString());
            jsNic.put("year",meditViewYear.getText().toString());
            jsNic.put("month",meditViewMonth.getText().toString());
            jsNic.put("birth-country","IND");
            jsNic.put("email-id",meditViewEmail.getText().toString());
//            jsNic.put("pMonths",meditTextPresentMonth.getText().toString().toUpperCase());

            return jsNic.toString();

        }catch (JSONException e)
        {
            e.printStackTrace();

        }
        return jsNic.toString();
    }

    private String jsonString()
    {
        JSONObject js = new JSONObject();

        try {
            js.put("moblie",meditTextPermanentMoblieNo.getText().toString().toUpperCase() );
            js.put("name", meditViewApplicantFirstName.getText().toString().toUpperCase());
            js.put("rtocode",rtoC[mspinnerRTO.getSelectedItemPosition()-1]);


            js.put("rtocodeReal",rtoRealCode[mspinnerRTO.getSelectedItemPosition()-1]);
            js.put("email",meditViewEmail.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.editTextYear:
                if(!meditViewPlaceOfBirth.getText().toString().equals("INDIA"))
                {
                    meditViewMonth.setEnabled(true);
                    meditViewYear.setEnabled(true);
                }
                break;
            case R.id.editTextMonth:
                if(!meditViewPlaceOfBirth.getText().toString().equals("INDIA"))
                {
                    meditViewMonth.setEnabled(true);
                    meditViewYear.setEnabled(true);
                }
                break;
        }
    }

    private void hidePersonalDetail()
    {
        mtablelayoutPersonalDetail.setVisibility(View.GONE);
        personalDetail = false;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }

    private void showPersonalDetail()
    {
        scrollView.scrollTo(0, mlinearlayoutPersonalDetail.getTop());
        mtablelayoutPersonalDetail.setVisibility(View.VISIBLE);
        personalDetail = true;
        imageViewPersonal.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));
    }

    private void hideOtherInfo() {
        mtablelayoutOtherInfo.setVisibility(View.GONE);
        otherInfo = false;
        imageViewOther.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_p,null));
    }

    private void showOtherInfo()
    {
        scrollView.scrollTo(0, mlinearlayoutOtherInfo.getTop());

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
        scrollView.scrollTo(0, mlinearlayoutPresentAddress.getTop());

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
        scrollView.scrollTo(0, mlinearlayoutPresentAddress.getTop());
        mtablelayoutPresentAddress.setVisibility(View.VISIBLE);
        present=true;
        imageViewPresent.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.iocn_m,null));
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
        Log.d("onResume :"," retrieving  session");
        retrivesession();
        Log.d("Session :"," session retrieved");
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

    public void addListenerOnSpinnerItemSelection() {
        mspinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().toUpperCase().equals("INDIA"))
                {
                    meditViewMonth.setText("");
                    meditViewYear.setText("");
                    meditViewYear.setEnabled(false);
                    meditViewMonth.setEnabled(false);
                }
                else
                {
                    meditViewYear.setEnabled(true);
                    meditViewMonth.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addListenerOnSpinnerRtoSelection() {
        mspinnerRTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mSPerDistrict.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addListenerOnSpinnerDistrictSelection() {
        mSPerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                mSPerDistrict.setSelection(i);
//                mSPerDistrict.setEnabled(false);
                // Application of the Array to the Spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),  android.R.layout.simple_spinner_item, getResources().getStringArray(arrTaluka[i]));
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                meditTextPermanentTaluka.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addListenerOnSpinnerEducationQualification() {
        mspinnerQualification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyboard(getActivity());

                if((i == 1 || i == 2 || i == 3) && new ConValidation(getActivity()).QualificationCheck())
                {
                    mspinnerQualification.setSelection(0);
                    showToast("For Transport Vehicle Applicant should be atleast 8th passed.");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                hideKeyboard(getActivity());
            }
        });
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

}


