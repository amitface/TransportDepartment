package com.converge.transportdepartment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    ImageView imageViewPermanent ;
    ImageView imageViewPresent ;
    ImageView imageViewPersonal ;
    ImageView imageViewOther;
    CheckBox  checkboxSameAddress;


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


    private Spinner mspinnerRTO, mspinnerRelationshipType, mspinnerQualification, mspinnerGender;
    private Spinner mspinnerIdmark, mspinnerBloodGroup, mspinnerRH, mspinnerPermanentState,mspinnerPresentState;
    private Spinner mspinnerCitizenship, mspinnerCountry;

    private ImageView mimageViewDatePicker;

    private Button buttonNextPersonalDetails, buttonClearPersonalDetails, buttonBackPersonalDetails;


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

    private OnFragmentInteractionListener mListener;
    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;
    private String mFinalString1="mFinalString1";


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
        initailizeFelids(rootView);
//        sendPostRequest(rootView);
        hidePermanent();
        hidePresent();

        setListner();
//        getPreferenceData();
        final Handler handlerTemp = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
//                saveSharedPreference();
//                showToast("saved preference");
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try
                    {
                        for(int i=0;i<2;)
                        {
                            Thread.sleep(10000);
                            Message msgObj = handlerTemp.obtainMessage();
                            Bundle b = new Bundle();
                            b.putInt("num", 1);
                            msgObj.setData(b);
                            handlerTemp.sendMessage(msgObj);
                        }
                    }
                    catch (Exception e)
                    {
                        showToast(e.toString());
                    }
            }
        }).start();
        return rootView;
    }

    public void onClickPersonalDetails(View view) {
        switch (view.getId()) {
            case R.id.buttonNextPersonalDetail:

//                getFieldData();
                if(validate()) {
                    handler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("4", "1")).commit();
                        }
                    };
                    String s = detailString();
                    saveSharedPreference();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("3", "1")).commit();
                }
                break;
            case R.id.buttonBackPersonalDetail:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("1", "1")).commit();
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
        buttonNextPersonalDetails.setOnClickListener(new View.OnClickListener() {
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
//                onClickPersonalDetails(view);
                saveSession();
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

        meditViewApplicantRelationsName = (EditText) rootView.findViewById(R.id.editTextRelationsName);
        meditViewApplicantRelationsMiddleName = (EditText) rootView.findViewById(R.id.editTextRelationsMiddleName);
        meditViewApplicantRelationsLastName = (EditText) rootView.findViewById(R.id.editTextRelationsLastName);

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

        mspinnerRTO = (Spinner) rootView.findViewById(R.id.spinnerRTO);
        mspinnerCountry = (Spinner) rootView.findViewById(R.id.spinnerCountry);
        mspinnerRelationshipType = (Spinner) rootView.findViewById(R.id.spinnerRelationshipType);
        mspinnerQualification = (Spinner) rootView.findViewById(R.id.spinnerQualification);
        mspinnerGender = (Spinner) rootView.findViewById(R.id.spinnerGender);
        mspinnerIdmark = (Spinner) rootView.findViewById(R.id.spinnerIdmark);
        mspinnerBloodGroup = (Spinner) rootView.findViewById(R.id.spinnerBloodGroup);
        mspinnerRH = (Spinner) rootView.findViewById(R.id.spinnerRH);
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


        buttonNextPersonalDetails = (Button) rootView.findViewById(R.id.buttonNextPersonalDetail);
        buttonClearPersonalDetails = (Button) rootView.findViewById(R.id.buttonClearPersonalDetail);
        buttonBackPersonalDetails = (Button) rootView.findViewById(R.id.buttonBackPersonalDetail);
        retrivesession();
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

    private void saveSession()
    {

        DBAdapter db = new DBAdapter(getActivity());

        //---get all contacts---
        db.open();
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
        hashMap.put("p_taluka",meditTextPermanentTaluka.getText().toString());
        hashMap.put("p_district",meditTextPermanentDistrict.getText().toString());
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
        hashMap.put("blood_group",Integer.toString(mspinnerBloodGroup.getSelectedItemPosition()));
        hashMap.put("blood_group_rh",Integer.toString(mspinnerRH.getSelectedItemPosition()));

//        hashMap.put("migration","");

//        hashMap.put("covs","3,4");
//        hashMap.put("rcnumber","");
//        hashMap.put("parentletterforbelow18age","");
//        hashMap.put("allnecessarycertificates","y");
//        hashMap.put("exemptedmedicaltest","");
//        hashMap.put("exemptedpreliminarytest","");
//        hashMap.put("convicted","n");
//        hashMap.put("attdlnumber","");
//        hashMap.put("attdtofconviction","");
//        hashMap.put("attreason","");
       db.updateData(hashMap);
    }

    private void clearFelids(View rootView) {
        meditViewApplicantFirstName.setText("");
        meditViewApplicantMiddleName.setText("");
        meditViewApplicantLastName.setText("");

        meditViewApplicantRelationsName.setText("");
        meditViewApplicantRelationsMiddleName.setText("");
        meditViewApplicantRelationsLastName.setText("");

        meditTextPermanentFlatNum.setText("");
        meditTextPermanentHouseName.setText("");
        meditTextPermanentHouseNum.setText("");
        meditTextPermanentStreet.setText("");
        meditTextPermanentLocality.setText("");
        meditTextPermanentvillage.setText("");
        meditTextPermanentTaluka.setText("");
        meditTextPermanentDistrict.setText("");
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
        mspinnerBloodGroup.setSelection(0);
        mspinnerRH.setSelection(0);
        mspinnerPermanentState.setSelection(0);

        mtextViewDate.setText("");
    }


    private boolean validate() {
        if( mspinnerRTO.getSelectedItemPosition()==0)
        {
            showToast("Select RTO");
            return false;
        }

        else if(meditViewApplicantFirstName.getText().length()==0)
        {
            showToast("Enter applicant first name");
            return false;
        }
        else if(meditViewApplicantLastName.getText().length()==0)
        {
            showToast("Enter applicant first name");
            return false;
        }
        else if(!validation.isAlpha(meditViewApplicantFirstName.getText().toString()) || !validation.isAlpha(meditViewApplicantLastName.getText().toString()))
        {
            showToast("Only aplhabets  allowed in Name ");
            return false;
        }
        else if(mtextViewDate.getText().length()==0)
        {
            showToast("Select DOB");
            return false;
        }
        else if(mspinnerGender.getSelectedItemPosition()==0)
        {
            showToast("Select Gender");
            return false;
        }

//        else if(validation.hasText(meditViewPlaceOfBirth) && !validation.isPhoneNumber(meditViewPlaceOfBirth,true))
//        {
//                showToast("only digit in phone number");
//                return false;
//        }
        else if(validation.hasText(meditViewYear) && !validation.isPhoneNumber(meditViewYear,true))
        {
            showToast("year & month only digit ");
            return false;
        }
        else if(validation.hasText(meditViewMonth) && !validation.isPhoneNumber(meditViewMonth,true))
        {
            showToast("year & month only digit ");
            return false;
        }
        else if(mspinnerCountry.getSelectedItemPosition()==0)
        {
            showToast("Select Country of Birth");
            return false;
        }
        else if(meditViewEmail.getText().length()==0)
        {
            showToast("Enter email");
            return false;
        }
        else if(!validation.isEmailAddress(meditViewEmail,true))
        {
                showToast("Email not correct");
                return false;

        }
        else if(mspinnerRelationshipType.getSelectedItemPosition()==0)
        {
            showToast("Select RelationType");
            return false;
        }
        else if(mspinnerGender.getSelectedItemPosition()<=1 && mspinnerRelationshipType.getSelectedItemPosition()>1)
        {
            showToast("RelationType and Gender Does not match");
            return false;
        }
        else if(mspinnerGender.getSelectedItemPosition()>1 && mspinnerRelationshipType.getSelectedItemPosition()<=1)
        {
            showToast("RelationType and Gender Does not match");
            return false;
        }
        else if(meditViewApplicantRelationsName.getText().length()==0)
        {
            showToast("Enter applicant first name");
            return false;
        }
        else if(meditViewApplicantRelationsLastName.getText().length()==0)
        {
            showToast("Enter applicant first name");
            return false;
        }
        else  if(!validation.isAlpha(meditViewApplicantFirstName.getText().toString()) || !validation.isAlpha(meditViewApplicantRelationsLastName.getText().toString()) || !validation.isAlpha(meditViewApplicantRelationsMiddleName.getText().toString()))
        {
                showToast("Only aplhabets  allowed in Relations Name ");
                return false;
        }
        else if(meditTextPermanentFlatNum.getText().length()==0)
        {
            showToast("Flat number cannot be empty");
            return false;
        }
        else if(meditTextPermanentHouseName.getText().length()==0)
        {
            showToast("House name cannot be empty");
            return false;
        }
        else if(meditTextPermanentHouseNum.getText().length()==0)
        {
            showToast("House number cannot be empty");
            return false;
        }
        else if(meditTextPermanentStreet.getText().length()==0)
        {
            showToast("Street cannot be empty");
            return false;
        }
        else if(meditTextPermanentLocality.getText().length()==0)
        {
            showToast("Locality cannot be empty");
            return false;
        }
        else if(meditTextPermanentvillage.getText().length()==0)
        {
            showToast("Permanent village cannot be empty");
            return false;
        }
        else if( meditTextPermanentTaluka.getText().length()==0)
        {
            showToast("Permanent Taluka cannot be empty");
            return false;
        }
        else if(meditTextPermanentDistrict.getText().length()==0)
        {
            showToast("Permanent District cannot be empty");
            return false;
        }
        else if(mspinnerPermanentState.getSelectedItemPosition()==0)
        {
            showToast("Select Permanent State");
            return false;
        }

        else if(meditTextPermanentMonth.getText().length()==0 && meditTextPermanentYear.getText().length()==0)
        {
            showToast("Years cannot be empty");
            return false;
        }
        else if(validation.hasText(meditTextPermanentYear) && !validation.isPhoneNumber(meditTextPermanentYear,true))
        {
                showToast("only digit in Years");
                return false;
        }
        else if(validation.hasText(meditTextPermanentMonth) && !validation.isPhoneNumber(meditTextPermanentMonth,true))
        {
            showToast("only digit in Months");
            return false;
        }
        else if(meditTextPermanentPinCode.getText().length()<6)
        {
            showToast("Invalid Pincode ");
            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPermanentMonth,true))
        {
            showToast("only digit in Pincode");
            return false;
        }
        else if(meditTextPermanentMoblieNo.getText().length()!=10)
        {
            showToast("Invalid Moblie Number ");
            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPermanentMoblieNo,true))
        {
            showToast("only digit in Mobile number");
            return false;
        }
        else if(meditTextPresentFlatNum.getText().length()==0)
        {
            showToast("Flat number cannot be empty");
            return false;
        }
        else if(meditTextPresentHouseName.getText().length()==0)
        {
            showToast("House name cannot be empty");
            return false;
        }
        else if(meditTextPresentHouseNum.getText().length()==0)
        {
            showToast("House number cannot be empty");
            return false;
        }
        else if(meditTextPresentStreet.getText().length()==0)
        {
            showToast("Street cannot be empty");
            return false;
        }
        else if(meditTextPresentLocality.getText().length()==0)
        {
            showToast("Locality cannot be empty");
            return false;
        }
        else if(meditTextPresentvillage.getText().length()==0)
        {
            showToast("Present village cannot be empty");
            return false;
        }
        else if( meditTextPresentTaluka.getText().length()==0)
        {
            showToast("Present Taluka cannot be empty");
            return false;
        }
        else if(meditTextPresentDistrict.getText().length()==0)
        {
            showToast("Present District cannot be empty");
            return false;
        }
        else if(mspinnerPresentState.getSelectedItemPosition()==0)
        {
            showToast("Select  Present village ");
            return false;
        }
        else if(meditTextPresentvillage.getText().length()==0)
        {
            showToast("Present village cannot be empty");
            return false;
        }
        else if(meditTextPresentYear.getText().length()==0)
        {
            showToast("Years cannot be empty");
            return false;
        }
        else if(validation.hasText(meditTextPresentYear) && !validation.isPhoneNumber(meditTextPresentYear,true))
        {
            showToast("only digit in Years");
            return false;
        }
        else if(validation.hasText(meditTextPresentMonth) && !validation.isPhoneNumber(meditTextPresentMonth,true))
        {
            showToast("only digit in Months");
            return false;
        }
        else if(meditTextPresentPinCode.getText().length()<6)
        {
            showToast("Invalid Present Pincode ");
            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPresentPinCode,true))
        {
            showToast("Digits only in Pincode ");
            return false;
        }
        else if(meditTextPresentMoblieNo.getText().length()!=10)
        {
            showToast("Invalid  Present Moblie Number ");
            return false;
        }
        else if(!validation.isPhoneNumber(meditTextPresentMoblieNo,true))
        {
            showToast("Digits only in mobile number ");
            return false;
        }
        else if(mspinnerCitizenship.getSelectedItemPosition()==0)
        {
            showToast("Select Citizenship");
        }
        else if(mspinnerQualification.getSelectedItemPosition()==0)
        {
            showToast("Select Qualification");
            return false;
        }
        else if(mspinnerIdmark.getSelectedItemPosition()==0)
        {
            showToast("Select Id Mark");
            return false;
        }
        else if(mspinnerBloodGroup.getSelectedItemPosition()==0)
        {
            showToast("Select BloodGroup");
            return false;
        }
        else if(mspinnerRH.getSelectedItemPosition()==0)
        {
            showToast("Select Rh of Blood Group");
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

    private String detailString()
    {
        Random r = new Random();
        int i1 = r.nextInt(100000000 - 90000000) + 9000;
        String s= "ref_num="+i1+
                "&first_name="+meditViewApplicantFirstName.getText()+
                "&statecode="+
                "&tocode="+(rtoCode[mspinnerRTO.getSelectedItemPosition()+1])+
                "&applicant_first_name="+meditViewApplicantFirstName.getText().toString()+
                "&applicant_middle_name="+meditViewApplicantMiddleName.getText().toString()+
                "&applicant_last_name="+meditViewApplicantLastName.getText().toString()+

                "&dob="+mtextViewDate.getText().toString()+
                "&gender="+mspinnerGender.getSelectedItem().toString()+
                "&relation="+mspinnerRelationshipType.getSelectedItem().toString()+
                "&p_first_name="+meditViewApplicantRelationsName.getText().toString()+
                "&p_middle_name="+meditViewApplicantRelationsMiddleName.getText().toString()+
                "&p_last_name="+meditViewApplicantRelationsLastName.getText().toString()+
                "&edu_qualification="+qualificatinCode[mspinnerQualification.getSelectedItemPosition()+1]+
                "&identification_mark="+mspinnerIdmark.getSelectedItem().toString()+
                "&blood_group="+mspinnerBloodGroup.getSelectedItem().toString()+mspinnerRH.getSelectedItem().toString()+

                "&p_flat_house_no="+meditTextPresentFlatNum.getText().toString()+
                "&p_street_locality="+meditTextPermanentLocality.getText().toString()+
                "&p_village_city="+meditTextPermanentvillage.getText().toString()+
                "&p_district="+meditTextPermanentDistrict.getText().toString()+
                "&p_state="+statecode[mspinnerPermanentState.getSelectedItemPosition()+1]+
                "&p_pin="+meditTextPermanentPinCode.getText().toString()+
                "&p_phone_no="+meditTextPermanentMoblieNo.getText().toString()+
                "&p_mobile_no="+meditTextPermanentMoblieNo.getText().toString()+
                "&p_years"+meditTextPermanentYear.getText().toString()+
                "&p_months="+meditTextPermanentMonth.getText().toString()+

                "&t_flat_house_no="+meditTextPresentFlatNum.getText().toString()+
                "&t_street_locality="+meditTextPresentStreet.getText().toString()+
                "&t_village_city="+meditTextPresentvillage.getText().toString()+
                "&t_district="+meditTextPresentDistrict.getText().toString()+
                "&t_state="+statecode[mspinnerPresentState.getSelectedItemPosition()+1]+
                "&t_pin="+meditTextPresentPinCode+
                "&t_phone_no="+meditTextPresentMoblieNo.getText().toString()+
                "&t_mobile_no="+meditTextPresentMoblieNo.getText().toString()+
                "&t_years="+meditTextPresentYear.getText().toString()+
                "&t_months="+meditTextPresentMonth.getText().toString()+

                "& birth_place="+meditViewPlaceOfBirth.getText().toString()+
                "&year="+meditViewYear.getText().toString()+
                "&birth_country="+mspinnerCountry.getSelectedItem().toString()+
                "&email_id="+meditViewEmail.getText().toString()+
                "&identification_marks="+mspinnerIdmark.getSelectedItem().toString()+

                "&covs=3,4"+
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

    private String createJsonString()
    {
        JSONArray ja = new JSONArray();
        JSONObject js = new JSONObject();
        JSONObject applicant = new JSONObject();
        try
        {
        applicant.put("refno","");
        applicant.put("statecode","");
        applicant.put("rtocode","");

        js.put("pplicant_first_name","");
        js.put("applicant_middle_name","");
        js.put("applicant_last_name","");
        applicant.put("applicant",js);
        applicant.put("dob","");
        applicant.put("gender_type","");
        applicant.put("relation_type","");
        applicant.put("relative_first_name","");
        applicant.put("relative_middle_name","");
        applicant.put("relative_last_name","");
        applicant.put("edu_qualification","");
        applicant.put("identification_mark","");
        applicant.put("blood_group","");

        JSONObject permanentAddress = new JSONObject();
        permanentAddress.put("p_flat_house_no","");
        permanentAddress.put("p_street_locality","");
        permanentAddress.put("p_village_city","");
        permanentAddress.put("p_district","");
        permanentAddress.put("p_state","");
        permanentAddress.put("p_pin","");
        permanentAddress.put("p_phone_no","");
        permanentAddress.put("p_mobile_no","");
        permanentAddress.put("p_years","");
        permanentAddress.put("p_months","");

        applicant.accumulate("permanent",permanentAddress);

        JSONObject presentAddress = new JSONObject();
        presentAddress.put("t_flat_house_no","");
        presentAddress.put("t_street_locality","");
        presentAddress.put("t_village_city","");
        presentAddress.put("t_district","");
        presentAddress.put("t_state","");
        presentAddress.put("t_pin","");
        presentAddress.put("t_phone_no","");
        presentAddress.put("t_mobile_no","");
        presentAddress.put("t_years","");
        presentAddress.put("t_months","");


        applicant.accumulate("present",presentAddress);
        applicant.put("citizenship_status_type","");
        applicant.put(" birth_place","");
        applicant.put("year","");
        applicant.put("birth_country","");
        applicant.put("email_id","");
        applicant.put("proofcode","");
        applicant.put("licence_certificate_badge_no","");
        applicant.put("issuing_authority","");
        applicant.put("date_of_issue","");
        applicant.put("covs","");
        applicant.put("rcnumber","");
        applicant.put("parentleterforbelow18age","");
        applicant.put("allnecessarycertificates","");
        applicant.put("exemptedmedicaltest","");
        applicant.put("exemptedpreliminarytest","");
        applicant.put("convicted","");
        applicant.put("attdlnumber","");
        applicant.put("attdtofconviction","");
        applicant.put("attreason","");

            return applicant.toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }


    private void getFieldData() {
        usr_fname = meditViewApplicantFirstName.getText().toString() + " " + meditViewApplicantMiddleName.getText().toString();
        usr_lname = meditViewApplicantLastName.getText().toString();
        usr_father_name = meditViewApplicantRelationsName.getText().toString();
       /* usr_email = meditViewEmail.getText().toString();
        usr_mobile = meditViewMobileNo.getText().toString();
        usr_pincode = meditViewPincode.getText().toString();
        usr_address = meditViewAddress.getText().toString();
        usr_feeamnt = meditViewFee.getText().toString();*/

        usr_city = "indore";
        usr_district = usr_city;

//        usr_rtmspinnerRTO.getSelectedItem().toString();
        usr_relation_type = mspinnerRelationshipType.getSelectedItem().toString();
        usr_qualification = mspinnerQualification.getSelectedItem().toString();
        usr_gender = mspinnerGender.getSelectedItem().toString();
        usr_idmark = mspinnerIdmark.getSelectedItem().toString();
        usr_blood_gr = mspinnerBloodGroup.getSelectedItem().toString();
        usr_blood_rh = mspinnerRH.getSelectedItem().toString();
//        usr_apply_class = getVehicleClass();
        usr_dob = mtextViewDate.getText().toString();

        usr_status = "1";

        int_relation_type = mspinnerRelationshipType.getSelectedItemPosition();
        int_qualification = mspinnerQualification.getSelectedItemPosition();
        int_gender = mspinnerGender.getSelectedItemPosition();
        int_idmark = mspinnerIdmark.getSelectedItemPosition();
        int_blood_gr = mspinnerBloodGroup.getSelectedItemPosition();
        int_blood_rh = mspinnerRH.getSelectedItemPosition();

    }





    private boolean textFieldValidation()
    {
        if(usr_fname.length()<1 ||  usr_lname.length() <1 ||  usr_father_name.length() <1 || usr_email.length()<1 || usr_mobile.length()<1 ||
             usr_pincode.length()<1 || usr_address.length()<1 || usr_feeamnt.length()<1 || usr_city.length()<1 || usr_district.length()<1){
            showToast("Enter All fields");
            return false;
        }
        else if( int_relation_type == 0 || int_qualification== 0 || int_gender== 0 ||
            int_idmark== 0 || int_blood_gr== 0 || int_blood_rh== 0
           ){
            showToast("Enter All Drop DownItem");
            return false;
        }
        else if( usr_dob.length()<1)
        {
            showToast("Enter Date");
            return false;
        }


        return true;

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
    public void onClick(View view) {

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
        mspinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
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
            progress = new ProgressDialog(this.context);
            progress.setMessage("Loading");
            progress.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            try {


                URL url = new URL("http://103.27.233.206/learningLicense/user_registration.php/");

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlParameters =
                        "usr_fname=" + usr_fname + "&" + "usr_lname=" + usr_lname + "& " + "usr_relation_type=" + usr_relation_type + "&" + "usr_father_name=" + usr_father_name + "&" +
                                "usr_address=" + usr_address + "&" + "usr_city= " + usr_city + "&" +
                                "usr_district=" + usr_district + "&" + "usr_pincode=" + usr_pincode + "&" + "usr_apply_class=" + usr_apply_class + "&" + "usr_mobile=" + usr_mobile + "&" +
                                "usr_email=" + usr_email + "&" + "usr_qualification=" + usr_qualification + "&" + "usr_dob=" + usr_dob + "&" + "usr_gender=" + usr_gender + "&"
                                + "usr_blood_gr=" + usr_blood_gr + "&" + "usr_blood_rh=" + usr_blood_rh + "&" + "usr_idmark=" + usr_idmark + "& " + "usr_feeamnt=" + usr_feeamnt + "&" +
                                "usr_status=" + usr_status;

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlParameters);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();


                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);
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
                String detail[] =responseDetail.split(",");
                SharedPreferences.Editor editor = sharedpreferences.edit();
                String ref[] = detail[2].split(":");
                editor.putString("receiptNum",ref[1].substring(0, 10));
                String ref2[]=detail[1].split(":");
                editor.putString("referenceId",ref2[1].substring(1,15));
                editor.commit();
                progress.dismiss();
                Message msgObj = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putInt("num", 1);
                msgObj.setData(b);
                handler.sendMessage(msgObj);
//                getActivity().runOnUiThread(new Runnable() {
//
//                    @Override
//                    public void run() {
////                        outputView.setText(output);
////                        Toast.makeText(getActivity(),output.toString(),Toast.LENGTH_LONG).show();
//                        Log.d("Rsponse   ", output.toString());
//                        progress.dismiss();
//                    }
//                });

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
/*
Check Box.
    private static final String CheckBoxApplicationType1 = "CheckBoxApplicationType1";
    private static final String CheckBoxApplicationType2 = "CheckBoxApplicationType2";
    private static final String CheckBoxApplicationType3 = "CheckBoxApplicationType3";
    private static final String CheckBoxApplicationType4 = "CheckBoxApplicationType4";
    private static final String CheckBoxApplicationType5 = "CheckBoxApplicationType5";
    private static final String CheckBoxApplicationType6 = "CheckBoxApplicationType6";
    private static final String CheckBoxApplicationType7 = "CheckBoxApplicationType7";

    private static final String meditViewApplicantFirstNameString = "meditViewApplicantFirstName";
    private static final String meditViewApplicantMiddleNameString = "meditViewApplicantMiddleName";
    private static final String meditViewApplicantLastNameString = "meditViewApplicantLastName";
    private static final String meditViewApplicantRelationsNameString = "meditViewApplicantRelationsName";
    private static final String meditViewEmailString = "meditViewEmail";
    private static final String meditViewMobileNoString = "meditViewMobileNo";
    private static final String meditViewPincodeString = "meditViewPincode";
    private static final String meditViewAddressString = "meditViewAddress";
    private static final String meditViewFeeString = "meditViewFee";


    private static final String mspinnerRTOString = "mspinnerRTO";
    private static final String mspinnerRTOInt = "mspinnerRTOInt";

    private static final String mspinnerRelationshipTypeString = "mspinnerRelationshipType";
    private static final String mspinnerRelationshipTypeInt = "mspinnerRelationshipTypeInt";

    private static final String mspinnerQualificationString = "mspinnerQualification";
    private static final String mspinnerQualificationInt = "mspinnerQualificationInt";

    private static final String mspinnerGenderString = "mspinnerGender";
    private static final String mspinnerGenderInt = "mspinnerGenderInt";

    private static final String mspinnerIdmarkString = "mspinnerIdmark";
    private static final String mspinnerIdmarkInt = "mspinnerIdmarkInt";

    private static final String mspinnerBloodGroupString = "mspinnerBloodGroup";
    private static final String mspinnerBloodGroupInt = "mspinnerBloodGroupInt";

    private static final String mspinnerRHString = "mspinnerRH";
    private static final String mspinnerRHInt = "mspinnerRHInt";
*/
//    private void saveSharedPreference() {
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putString(meditViewApplicantFirstNameString, meditViewApplicantFirstName.getText().toString());
//        editor.putString(meditViewApplicantMiddleNameString, meditViewApplicantMiddleName.getText().toString());
//        editor.putString(meditViewApplicantLastNameString, meditViewApplicantLastName.getText().toString());
//        editor.putString(meditViewApplicantRelationsNameString, meditViewApplicantRelationsName.getText().toString());
//        editor.putString(meditViewEmailString, meditViewEmail.getText().toString());
//        editor.putString(meditViewMobileNoString, meditViewMobileNo.getText().toString());
//        editor.putString(meditViewPincodeString, meditViewPincode.getText().toString());
//        editor.putString(meditViewAddressString, meditViewAddress.getText().toString());
//        editor.putString(meditViewFeeString, meditViewFee.getText().toString());
//
//        editor.putString(mspinnerRelationshipTypeString, mspinnerRelationshipType.getSelectedItem().toString());
//        editor.putString(mspinnerQualificationString, mspinnerQualification.getSelectedItem().toString());
//        editor.putString(mspinnerGenderString, mspinnerGender.getSelectedItem().toString());
//        editor.putString(mspinnerIdmarkString, mspinnerIdmark.getSelectedItem().toString());
//        editor.putString(mspinnerBloodGroupString, mspinnerBloodGroup.getSelectedItem().toString());
//        editor.putString(mspinnerRHString, mspinnerRH.getSelectedItem().toString());
//        editor.putString(mtextViewDateString, mtextViewDate.getText().toString());
//
//        editor.putInt(mspinnerRTOInt,mspinnerRTO.getSelectedItemPosition());
//        editor.putInt(mspinnerRelationshipTypeInt, mspinnerRelationshipType.getSelectedItemPosition());
//        editor.putInt(mspinnerQualificationInt, mspinnerQualification.getSelectedItemPosition());
//        editor.putInt(mspinnerGenderInt, mspinnerGender.getSelectedItemPosition());
//        editor.putInt(mspinnerIdmarkInt, mspinnerIdmark.getSelectedItemPosition());
//        editor.putInt(mspinnerBloodGroupInt, mspinnerBloodGroup.getSelectedItemPosition());
//        editor.putInt(mspinnerRHInt, mspinnerRH.getSelectedItemPosition());
//        editor.putString(mtextViewDateInt,mtextViewDate.getSelectedItemPosition());
//
//
//
//        editor.commit();
//    }

//    private void getPreferenceData()
//    {
//        if(sharedpreferences.contains(meditViewApplicantFirstNameString)) {
//            meditViewApplicantFirstName.setText(sharedpreferences.getString(meditViewApplicantFirstNameString, ""));
//        }
//        if(sharedpreferences.contains(meditViewApplicantMiddleNameString)) {
//            meditViewApplicantMiddleName.setText(sharedpreferences.getString(meditViewApplicantMiddleNameString, ""));
//        }
//        if(sharedpreferences.contains(meditViewApplicantLastNameString)) {
//            meditViewApplicantLastName.setText(sharedpreferences.getString(meditViewApplicantLastNameString, ""));
//        }
//        if(sharedpreferences.contains(meditViewApplicantRelationsNameString)) {
//            meditViewApplicantRelationsName.setText(sharedpreferences.getString(meditViewApplicantRelationsNameString, ""));
//        }
//        /*if(sharedpreferences.contains(meditViewEmailString)) {
//            meditViewEmail.setText(sharedpreferences.getString(meditViewEmailString, ""));
//        }
//        if(sharedpreferences.contains(meditViewMobileNoString)) {
//            meditViewMobileNo.setText(sharedpreferences.getString(meditViewMobileNoString, ""));
//        }
//        if(sharedpreferences.contains(meditViewPincodeString)) {
//            meditViewPincode.setText(sharedpreferences.getString(meditViewPincodeString, ""));
//        }
//        if(sharedpreferences.contains(meditViewAddressString)) {
//            meditViewAddress.setText(sharedpreferences.getString(meditViewAddressString, ""));
//        }
//        if(sharedpreferences.contains(meditViewFeeString)) {
//            meditViewFee.setText(sharedpreferences.getString(meditViewFeeString, ""));
//        }*/
//
//        if(sharedpreferences.contains(mspinnerRTOInt)){
//            mspinnerRTO.setSelection(sharedpreferences.getInt(mspinnerRTOInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerRelationshipTypeInt)){
//            mspinnerRelationshipType.setSelection(sharedpreferences.getInt(mspinnerRelationshipTypeInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerQualificationInt)){
//            mspinnerQualification.setSelection(sharedpreferences.getInt(mspinnerQualificationInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerGenderInt)){
//            mspinnerGender.setSelection(sharedpreferences.getInt(mspinnerGenderInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerBloodGroupInt)){
//            mspinnerBloodGroup.setSelection(sharedpreferences.getInt(mspinnerBloodGroupInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerRHInt)){
//            mspinnerRH.setSelection(sharedpreferences.getInt(mspinnerRHInt,1));
//        }
//        if(sharedpreferences.contains(mspinnerIdmarkInt)){
//            mspinnerIdmark.setSelection(sharedpreferences.getInt(mspinnerIdmarkInt,1));
//        }
//
//    }

//    private String getVehicleClass() {
//        StringBuilder brVehicleClass = new StringBuilder();
//        if (sharedpreferences.contains(CheckBoxApplicationType1) && sharedpreferences.getBoolean(CheckBoxApplicationType1, true)) {
//            brVehicleClass.append(vehicleClass[0]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType2) && sharedpreferences.getBoolean(CheckBoxApplicationType2, true)) {
//            brVehicleClass.append(vehicleClass[1]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType3) && sharedpreferences.getBoolean(CheckBoxApplicationType3, true)) {
//            brVehicleClass.append(vehicleClass[2]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType4) && sharedpreferences.getBoolean(CheckBoxApplicationType4, true)) {
//            brVehicleClass.append(vehicleClass[3]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType5) && sharedpreferences.getBoolean(CheckBoxApplicationType5, true)) {
//            brVehicleClass.append(vehicleClass[4]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType6) && sharedpreferences.getBoolean(CheckBoxApplicationType6, true)) {
//            brVehicleClass.append(vehicleClass[5]).append(" ");
//        }
//        if (sharedpreferences.contains(CheckBoxApplicationType7) && sharedpreferences.getBoolean(CheckBoxApplicationType7, true)) {
//            brVehicleClass.append(vehicleClass[6]).append(" ");
//        }
//        return brVehicleClass.toString();
//    }