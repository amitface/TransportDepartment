package com.converge.transportdepartment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.converge.transportdepartment.task.GetRefIdAndTime;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectApplicationType.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectApplicationType#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectApplicationType extends Fragment implements View.OnClickListener, GetRefIdAndTime.GetRefIdAndTimeResultListener {
    // TODO: Rename parameter arguments, choose names that match

    public static final String PREFS_NAME = "MyTransportFile";
    SharedPreferences sharedpreferences;
    private final String mFinalStringCov="mFinalStringCov";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Map<Integer,Boolean> mCheckBox = new HashMap<>(19);

    private String covCode[]={"3","5" ,"8", "9","4","7","6","10","12","13","2","53","54","16","15","17","58","59","65"};

    //Check Box Buttons
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    private CheckBox checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14;
    private CheckBox checkBox15, checkBox16, checkBox17, checkBox18, checkBox19;

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String []arrCov;

    public static long generatedRefId = 0,generatedDate = 0;

    private OnFragmentInteractionListener mListener;

    private static final String CheckBoxApplicationType1 = "CheckBoxApplicationType1";
    private static final String CheckBoxApplicationType2 = "CheckBoxApplicationType2";
    private static final String CheckBoxApplicationType3 = "CheckBoxApplicationType3";
    private static final String CheckBoxApplicationType4 = "CheckBoxApplicationType4";
    private static final String CheckBoxApplicationType5 = "CheckBoxApplicationType5";
    private static final String CheckBoxApplicationType6 = "CheckBoxApplicationType6";
    private static final String CheckBoxApplicationType7 = "CheckBoxApplicationType7";
    private static final String CheckBoxApplicationType8 = "CheckBoxApplicationType8";
    private static final String CheckBoxApplicationType9 = "CheckBoxApplicationType9";
    private static final String CheckBoxApplicationType10 = "CheckBoxApplicationType10";
    private static final String CheckBoxApplicationType11 = "CheckBoxApplicationType11";
    private static final String CheckBoxApplicationType12 = "CheckBoxApplicationType12";
    private static final String CheckBoxApplicationType13 = "CheckBoxApplicationType13";
    private static final String CheckBoxApplicationType14 = "CheckBoxApplicationType14";
    private static final String CheckBoxApplicationType15 = "CheckBoxApplicationType15";
    private static final String CheckBoxApplicationType16 = "CheckBoxApplicationType16";
    private static final String CheckBoxApplicationType17 = "CheckBoxApplicationType17";
    private static final String CheckBoxApplicationType18 = "CheckBoxApplicationType18";
    private static final String CheckBoxApplicationType19 = "CheckBoxApplicationType19";
    private static final String CheckBoxApplicationType20 = "CheckBoxApplicationType20";
    private static final String CheckBoxApplicationType21 = "CheckBoxApplicationType21";
    private static final String CheckBoxApplicationType22 = "CheckBoxApplicationType22";
    private static final String CheckBoxApplicationType23 = "CheckBoxApplicationType23";

    public SelectApplicationType() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectApplicationType.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectApplicationType newInstance(String param1, String param2) {
        SelectApplicationType fragment = new SelectApplicationType();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }
    @Override
    public void onPause()
    {
        super.onPause();
//        Save();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_select_application_type, container, false);

        ImageView buttonSelectionApplicationNext = (ImageView) fragmentView.findViewById(R.id.buttonNextSelectApplication);

        hideKeyboard(getContext());
        //Initialize Hash Map
        intializeHashMap();

        //Initialize all Check box.
        intializeCheckBox(fragmentView);

        //Get all Shared preference data.
         retriveCov();

        /*onClickListener */
        checkboxSetListener();

        ImageView buttonClear =(ImageView) fragmentView.findViewById(R.id.buttonClearSelectApplication);
        buttonClear.setOnClickListener(this);

        ImageView buttonNext = (ImageView) fragmentView.findViewById(R.id.buttonNextSelectApplication);
        buttonNext.setOnClickListener(this);

        Log.e("Log","Starting Web Call");
        //Task for getting Ref Id and Current time from server
//        GetRefIdAndTime getRefIdTask = new GetRefIdAndTime(getActivity());
//        getRefIdTask.setGetRefIdAndTimeResultListener(this);
//        getRefIdTask.initializeGetRefIdAndTime();
        return fragmentView;
    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(int id) {
        if (mListener != null) {
            mListener.onFragmentInteraction(id);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public void onClickCheckBox(View view)
    {
        // Is the view now checked?
        boolean checked= false;

        if(view.getId()!= R.id.buttonClearSelectApplication && view.getId()!=R.id.buttonNextSelectApplication){
            checked = ((CheckBox) view).isChecked();
        }

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.buttonNextSelectApplication:
                if(valdate())
                {
                    vibrate();
                    Save();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("1", "1")).commit();
                }
                else
                Toast.makeText(getActivity(),"Select atleast one class of vehicle",Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonClearSelectApplication:
                clear(view);
                break;
            case R.id.checkbox_application_type_1:
                if (checked)
                    // Put some meat on the sandwich
                    mCheckBox.put(0,checked);
                else
                    // Remove the meat
                    mCheckBox.put(0,checked);
                break;
            case R.id.checkbox_application_type_2:
                if (checked)
                    // Cheese me
                    mCheckBox.put(1,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(1,checked);
                break;
            case R.id.checkbox_application_type_3:
                if (checked)
                    // Cheese me
                    mCheckBox.put(2,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(2,checked);
                break;
            case R.id.checkbox_application_type_4:
                if (checked)
                    // Cheese me
                    mCheckBox.put(3,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(3,checked);
                break;
            case R.id.checkbox_application_type_5:
                if (checked)
                    // Cheese me
                    mCheckBox.put(4,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(4,checked);
                break;
            case R.id.checkbox_application_type_6:
                if (checked)
                    // Cheese me
                    mCheckBox.put(5,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(5,checked);
                break;
            case R.id.checkbox_application_type_7:
                if (checked)
                    // Cheese me
                    mCheckBox.put(6,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(6,checked);

                break;
            case R.id.checkbox_application_type_8:
                if (checked)
                    // Cheese me
                    mCheckBox.put(7,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(7,checked);

                break;
            case R.id.checkbox_application_type_9:
                if (checked)
                    // Cheese me
                    mCheckBox.put(8,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(8,checked);

                break;
            case R.id.checkbox_application_type_10:
                if (checked)
                    // Cheese me
                    mCheckBox.put(9,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(9,checked);

                break;
            case R.id.checkbox_application_type_11:
                if (checked)
                    // Cheese me
                    mCheckBox.put(10,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(10,checked);

                break;
            case R.id.checkbox_application_type_12:
                if (checked)
                    // Cheese me
                    mCheckBox.put(11,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(11,checked);

                break;
            case R.id.checkbox_application_type_13:
                if (checked)
                    // Cheese me
                    mCheckBox.put(12,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(12,checked);

                break;
            case R.id.checkbox_application_type_14:
                if (checked)
                    // Cheese me
                    mCheckBox.put(13,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(13,checked);

                break;
            case R.id.checkbox_application_type_15:
                if (checked)
                    // Cheese me
                    mCheckBox.put(14,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(14,checked);

                break;
            case R.id.checkbox_application_type_16:
                if (checked)
                    // Cheese me
                    mCheckBox.put(15,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(15,checked);

                break;
            case R.id.checkbox_application_type_17:
                if (checked)
                    // Cheese me
                    mCheckBox.put(16,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(16,checked);

                break;
            case R.id.checkbox_application_type_18:
                if (checked)
                    // Cheese me
                    mCheckBox.put(17,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(17,checked);

                break;
            case R.id.checkbox_application_type_19:
                if (checked)
                    // Cheese me
                    mCheckBox.put(18,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(18,checked);

                break;

            default:break;
            // TODO: Veggie sandwich
        }

    }

    private boolean valdate() {

            if(checkBox1.isChecked())
            return  true;
            else if(checkBox2.isChecked())
            return  true;
            else if(checkBox3.isChecked())
            return  true;
            else if(checkBox4.isChecked())
            return  true;
            else if(checkBox5.isChecked())
            return  true;
            else if( checkBox6.isChecked())
            return  true;
            else if(checkBox7.isChecked())
            return  true;
            else if(checkBox8.isChecked())
            return  true;
            else if(checkBox9.isChecked())
            return  true;
            else if(checkBox10.isChecked())
            return  true;
            else if(checkBox11.isChecked())
            return  true;
            else if(checkBox12.isChecked())
            return  true;
            else if(checkBox13.isChecked())
            return  true;
            else if(checkBox14.isChecked())
            return  true;
            else if(checkBox15.isChecked())
            return  true;
            else if(checkBox16.isChecked())
            return  true;
            else if(checkBox17.isChecked())
            return  true;
            else if(checkBox18.isChecked())
            return  true;
            else if(checkBox19.isChecked())
            return  true;
//            else if(checkBox20.isChecked())
//            return  true;
        return false;
    }


    public void Save() {

        SharedPreferences.Editor editor = sharedpreferences.edit();
        StringBuffer br= new StringBuffer();

        for(int i=0;i<19;i++)
        {
            if(mCheckBox.get(i))
            {
                br.append(covCode[i]).append(",");
            }
        }

        if(br.length()!=0)
        editor.putString(mFinalStringCov,br.substring(0,br.length()-1).toString());

        editor.commit();
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

    public void clear(View view) {

        checkBox1.setChecked(false);
        checkBox2.setChecked(false);
        checkBox3.setChecked(false);
        checkBox4.setChecked(false);
        checkBox5.setChecked(false);
        checkBox6.setChecked(false);
        checkBox7.setChecked(false);
        checkBox8.setChecked(false);
        checkBox9.setChecked(false);
        checkBox10.setChecked(false);
        checkBox11.setChecked(false);
        checkBox12.setChecked(false);
        checkBox13.setChecked(false);
        checkBox14.setChecked(false);
        checkBox15.setChecked(false);
        checkBox16.setChecked(false);
        checkBox17.setChecked(false);
        checkBox18.setChecked(false);
        checkBox19.setChecked(false);
//        checkBox20.setChecked(false);

        mCheckBox.put(0,false);
        mCheckBox.put(1,false);
        mCheckBox.put(2,false);
        mCheckBox.put(3,false);
        mCheckBox.put(4,false);
        mCheckBox.put(5,false);
        mCheckBox.put(6,false);
        mCheckBox.put(7,false);
        mCheckBox.put(8,false);
        mCheckBox.put(9,false);
        mCheckBox.put(10,false);
        mCheckBox.put(11,false);
        mCheckBox.put(12,false);
        mCheckBox.put(13,false);
        mCheckBox.put(14,false);
        mCheckBox.put(15,false);
        mCheckBox.put(16,false);
        mCheckBox.put(17,false);
        mCheckBox.put(18,false);
        Save();
    }

    @Override
    public void onClick(View view) {
        // Is the view now checked?
        boolean checked= false;

        if(view.getId()!= R.id.buttonClearSelectApplication && view.getId()!=R.id.buttonNextSelectApplication){
            checked = ((CheckBox) view).isChecked();
        }

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.buttonNextSelectApplication:
                if(valdate())
                {
                    vibrate();
                    Save();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("1", "1")).commit();
                }
                else
                    Toast.makeText(getActivity(),"Select at least one class",Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonClearSelectApplication:
                clear(view);
                break;
            case R.id.checkbox_application_type_1:
                if (checked)
                    // Put some meat on the sandwich
                    mCheckBox.put(0,checked);
                else
                    // Remove the meat
                    mCheckBox.put(0,checked);
                break;
            case R.id.checkbox_application_type_2:
                if (checked)
                    // Cheese me
                    mCheckBox.put(1,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(1,checked);
                break;
            case R.id.checkbox_application_type_3:
                if (checked)
                    // Cheese me
                    mCheckBox.put(2,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(2,checked);
                break;
            case R.id.checkbox_application_type_4:
                if (checked)
                    // Cheese me
                    mCheckBox.put(3,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(3,checked);
                break;
            case R.id.checkbox_application_type_5:
                if (checked)
                    // Cheese me
                    mCheckBox.put(4,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(4,checked);
                break;
            case R.id.checkbox_application_type_6:
                if (checked)
                    // Cheese me
                    mCheckBox.put(5,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(5,checked);
                break;
            case R.id.checkbox_application_type_7:
                if (checked)
                    // Cheese me
                    mCheckBox.put(6,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(6,checked);

                break;
            case R.id.checkbox_application_type_8:
                if (checked)
                    // Cheese me
                    mCheckBox.put(7,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(7,checked);

                break;
            case R.id.checkbox_application_type_9:
                if (checked)
                    // Cheese me
                    mCheckBox.put(8,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(8,checked);

                break;
            case R.id.checkbox_application_type_10:
                if (checked)
                    // Cheese me
                    mCheckBox.put(9,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(9,checked);

                break;
            case R.id.checkbox_application_type_11:
                if (checked)
                    // Cheese me
                    mCheckBox.put(10,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(10,checked);

                break;
            case R.id.checkbox_application_type_12:
                if (checked)
                    // Cheese me
                    mCheckBox.put(11,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(11,checked);

                break;
            case R.id.checkbox_application_type_13:
                if (checked)
                    // Cheese me
                    mCheckBox.put(12,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(12,checked);

                break;
            case R.id.checkbox_application_type_14:
                if (checked)
                    // Cheese me
                    mCheckBox.put(13,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(13,checked);

                break;
            case R.id.checkbox_application_type_15:
                if (checked)
                    // Cheese me
                    mCheckBox.put(14,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(14,checked);

                break;
            case R.id.checkbox_application_type_16:
                if (checked)
                    // Cheese me
                    mCheckBox.put(15,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(15,checked);

                break;
            case R.id.checkbox_application_type_17:
                if (checked)
                    // Cheese me
                    mCheckBox.put(16,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(16,checked);

                break;
            case R.id.checkbox_application_type_18:
                if (checked)
                    // Cheese me
                    mCheckBox.put(17,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(17,checked);

                break;
            case R.id.checkbox_application_type_19:
                if (checked)
                    // Cheese me
                    mCheckBox.put(18,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(18,checked);

                break;

            default:break;
            // TODO: Veggie sandwich
        }
    }


    private void intializeHashMap() {
        for(int i=0;i<19;i++)
        {
            mCheckBox.put(i,false);
        }
    }

    @Override
    public void onGetRefIdAndTimeCompleteResult(String result) {
        try {

            //{"TRANS_ID":1472729515,"CURR_DATE":1472668200}

            JSONObject obj = new JSONObject(result);
            generatedDate = obj.getLong("CURR_DATE");
            generatedRefId = obj.getLong("TRANS_ID");

            Log.e("Result From Server", "RESULT : " + result);


        } catch(Exception ex) {

            Calendar calendar = Calendar.getInstance();
            Log.d("**slotDate**",""+calendar.getTimeInMillis());
            generatedDate = calendar.getTimeInMillis();
            Random rand = new Random();
            generatedRefId = rand.nextInt(9000000) + 1000000;;
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
        void onFragmentInteraction(int id);
    }

    private void intializeCheckBox(View fragmentView) {
        checkBox1 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_1);
        checkBox2 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_2);
        checkBox3 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_3);
        checkBox4 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_4);
        checkBox5 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_5);
        checkBox6 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_6);
        checkBox7 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_7);
        checkBox8 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_8);
        checkBox9 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_9);
        checkBox10 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_10);
        checkBox11 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_11);
        checkBox12 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_12);
        checkBox13 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_13);
        checkBox14 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_14);
        checkBox15 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_15);
        checkBox16 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_16);
        checkBox17 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_17);
        checkBox18 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_18);
        checkBox19 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_19);
//        checkBox20 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_20);

        retriveCov();
    }

    private void retriveCov() {
        if(sharedpreferences.contains(mFinalStringCov))
        {
            String temp = sharedpreferences.getString(mFinalStringCov,"");
            arrCov =temp.split(",");
            if(arrcontains(covCode[0])) {
               checkBox1.setChecked(true);
            }
            if(arrcontains(covCode[1])) {
                checkBox2.setChecked(true);
            }
            if(arrcontains(covCode[2])) {
                checkBox3.setChecked(true);
            }
            if(arrcontains(covCode[3])) {
                checkBox4.setChecked(true);
            }
            if(arrcontains(covCode[4])) {
                checkBox5.setChecked(true);
            }
            if(arrcontains(covCode[5])) {
                checkBox6.setChecked(true);
            }
            if(arrcontains(covCode[6])) {
                checkBox7.setChecked(true);
            }
            if(arrcontains(covCode[7])) {
                checkBox8.setChecked(true);
            }
            if(arrcontains(covCode[8])) {
                checkBox9.setChecked(true);
            }
            if(arrcontains(covCode[9])) {
                checkBox10.setChecked(true);
            }
            if(arrcontains(covCode[10])) {
                checkBox11.setChecked(true);
            }
            if(arrcontains(covCode[11])) {
                checkBox12.setChecked(true);
            }
            if(arrcontains(covCode[12])) {
                checkBox13.setChecked(true);
            }
            if(arrcontains(covCode[13])) {
                checkBox14.setChecked(true);
            }
            if(arrcontains(covCode[14])) {
                checkBox15.setChecked(true);
            }
            if(arrcontains(covCode[15])) {
                checkBox16.setChecked(true);
            }
            if(arrcontains(covCode[16])) {
                checkBox17.setChecked(true);
            }
            if(arrcontains(covCode[17])) {
                checkBox18.setChecked(true);
            }
            if(arrcontains(covCode[18])) {
                checkBox19.setChecked(true);
            }
//            if(arrcontains(covCode[19])) {
//                checkBox20.setChecked(true);
//            }
        }
    }

    private boolean arrcontains(String s) {
//        int i=Integer.parseInt(s);
        for(int a=0;a<arrCov.length;a++)
        {
            if(arrCov[a].equals(s))
                return true;
        }
        return false;
    }


    private void checkboxSetListener() {
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);
        checkBox9.setOnClickListener(this);
        checkBox10.setOnClickListener(this);
        checkBox11.setOnClickListener(this);
        checkBox12.setOnClickListener(this);

        checkBox13.setOnClickListener(this);
        checkBox14.setOnClickListener(this);
        checkBox15.setOnClickListener(this);
        checkBox16.setOnClickListener(this);
        checkBox17.setOnClickListener(this);
        checkBox18.setOnClickListener(this);
        checkBox19.setOnClickListener(this);
//        checkBox20.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onClickCheckBox(view);
//            }
//        });

    }

    private void vibrate()
    {
        // Get instance of Vibrator from current Context
        Vibrator mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        mVibrator.vibrate(20);
    }
}


