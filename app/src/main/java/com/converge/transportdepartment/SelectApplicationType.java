package com.converge.transportdepartment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectApplicationType.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectApplicationType#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectApplicationType extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    public static final String PREFS_NAME = "MyTransportFile";
    SharedPreferences sharedpreferences;
    private final String mFinalStringCov="mFinalStringCov";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Map<Integer,Boolean> mCheckBox = new HashMap<>(21);
    private String covCode[]={"3",   "5" ,"8", "9",   "4","7",     "6", "10",   "12","13", "2","53",  "53",  "54", "16", "15", "17","58","59", "59",  "65"};


    //Check Box Buttons
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    private CheckBox checkBox8, checkBox9, checkBox10, checkBox11, checkBox12, checkBox13, checkBox14;
    private CheckBox checkBox15, checkBox16, checkBox17, checkBox18, checkBox19, checkBox20, checkBox21,checkBox22;

    public static final String mypreference = "mypref";
    public static final String Name = "nameKey";
    public static final String Email = "emailKey";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

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
        Save();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_select_application_type, container, false);
        Button buttonSelectionApplicationNext = (Button) fragmentView.findViewById(R.id.buttonNextSelectApplication);

        //Initialize Hash Map
        intializeHashMap();

        //Initialize all Check box.
        intializeCheckBox(fragmentView);

        //Get all Shared preference data.
         getSharedPreferenceData();

        /*onClickListener */
        checkboxSetListener();

        Button buttonClear =(Button) fragmentView.findViewById(R.id.buttonClearSelectApplication);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });

        Button buttonNext = (Button) fragmentView.findViewById(R.id.buttonNextSelectApplication);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCheckBox(v);
            }
        });
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
            case R.id.checkbox_application_type_20:
                if (checked)
                    // Cheese me
                    mCheckBox.put(19,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(19,checked);

                break;
            case R.id.checkbox_application_type_21:
                if (checked)
                    // Cheese me
                    mCheckBox.put(20,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(20,checked);

                break;
            case R.id.checkbox_application_type_22:
                if (checked)
                    // Cheese me
                    mCheckBox.put(21,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(21,checked);

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
            else if(checkBox20.isChecked())
            return  true;
            else if(checkBox21.isChecked())
            return  true;
            else if(checkBox22.isChecked())
            return  true;

        return false;
    }


    public void Save() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        StringBuffer br= new StringBuffer();

        for(int i=0;i<21;i++)
        {
            if(mCheckBox.get(i))
            {
                br.append(covCode[i]).append(",");
            }
        }


        editor.putString(mFinalStringCov,br.substring(0,br.length()-1).toString());


        editor.commit();
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
        checkBox20.setChecked(false);
        checkBox21.setChecked(false);
        checkBox22.setChecked(false);
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
        mCheckBox.put(19,false);
        mCheckBox.put(20,false);
        mCheckBox.put(21,false);
        mCheckBox.put(22,false);
        Save();
        Toast.makeText(getActivity(),"clear",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {

    }


    private void intializeHashMap() {
        for(int i=0;i<22;i++)
        {
            mCheckBox.put(i,false);
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
        checkBox20 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_20);
        checkBox21 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_21);
        checkBox22 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_22);
    }

    private void getSharedPreferenceData() {
        if(sharedpreferences.contains(CheckBoxApplicationType1)) {
            checkBox1.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType1, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType2)) {
            checkBox2.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType2, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType3)) {
            checkBox3.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType3, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType4)) {
            checkBox4.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType4, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType5)) {
            checkBox5.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType5, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType6)) {
            checkBox6.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType6, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType7)) {
            checkBox7.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType7, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType8)) {
            checkBox8.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType8, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType9)) {
            checkBox9.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType9, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType10)) {
            checkBox10.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType10, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType11)) {
            checkBox11.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType11, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType12)) {
            checkBox12.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType12, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType13)) {
            checkBox13.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType13, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType14)) {
            checkBox14.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType14, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType15)) {
            checkBox15.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType15, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType16)) {
            checkBox16.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType16, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType17)) {
            checkBox17.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType17, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType18)) {
            checkBox18.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType18, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType19)) {
            checkBox19.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType19, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType20)) {
            checkBox20.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType20, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType21)) {
            checkBox21.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType21, true));
        }
        if(sharedpreferences.contains(CheckBoxApplicationType21)) {
            checkBox22.setChecked(sharedpreferences.getBoolean(CheckBoxApplicationType22, true));
        }

    }

    private void checkboxSetListener() {
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });

        checkBox13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
        checkBox22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
            }
        });
    }

    private void vibrate()
    {
        // Get instance of Vibrator from current Context
        Vibrator mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 300 milliseconds
        mVibrator.vibrate(20);
    }
}


