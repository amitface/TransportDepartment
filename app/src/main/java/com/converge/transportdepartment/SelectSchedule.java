package com.converge.transportdepartment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectSchedule extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    public static final String PREFS_NAME = "MyTransportFile";
    SharedPreferences sharedpreferences;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;
    private CheckBox checkBox8;
    private CheckBox checkBox9;
    private CheckBox checkBox10;
    private CheckBox checkBox11;
    private CheckBox checkBox12;
    private CheckBox checkBox13;
    private CheckBox checkBox14;
    private CheckBox checkBox15;
    private CheckBox checkBox16;
    private CheckBox checkBox17;
    private CheckBox checkBox18;
    private CheckBox checkBox19;
    private CheckBox checkBox20;
    private CheckBox checkBox21;
    private CheckBox checkBox22;
    private CheckBox checkBox23;
    private CheckBox checkBox24;
    private CheckBox checkBoxLastClicked;
    private int lastCheckBoxId = 0;
    private int currentChecked = 0;


    private int [] arrayCheckBox ={ R.id.checkbox_schedule1,R.id.checkbox_schedule2,R.id.checkbox_schedule3,R.id.checkbox_schedule4,R.id.checkbox_schedule5,R.id.checkbox_schedule6,R.id.checkbox_schedule7,
            R.id.checkbox_schedule8,R.id.checkbox_schedule9,R.id.checkbox_schedule10,R.id.checkbox_schedule11,R.id.checkbox_schedule12,R.id.checkbox_schedule13,R.id.checkbox_schedule14,R.id.checkbox_schedule15,
            R.id.checkbox_schedule16,R.id.checkbox_schedule17,R.id.checkbox_schedule18,R.id.checkbox_schedule19,R.id.checkbox_schedule20,R.id.checkbox_schedule21,R.id.checkbox_schedule22,R.id.checkbox_schedule23,
            R.id.checkbox_schedule24};
    private OnFragmentInteractionListener mListener;
    private static final String CheckBoxSchedule = "currentCheckBox";
    private static final String mypreference="mypref";

    public SelectSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectSchedule newInstance(String param1, String param2) {
        SelectSchedule fragment = new SelectSchedule();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentSchedule =  inflater.inflate(R.layout.fragment_select_schedule, container, false);
        hideKeyboard(getContext());
        if(sharedpreferences.contains(CheckBoxSchedule) && sharedpreferences.getInt(CheckBoxSchedule,0)!=0) {
          CheckBox checkboxLocal =  (CheckBox) fragmentSchedule.findViewById(arrayCheckBox[sharedpreferences.getInt(CheckBoxSchedule, 0)-1]);
            lastCheckBoxId = arrayCheckBox[sharedpreferences.getInt(CheckBoxSchedule, 0)-1];
            currentChecked = sharedpreferences.getInt(CheckBoxSchedule,0);
            checkBoxLastClicked =checkboxLocal;
            checkboxLocal.setChecked(true);
        }
        initalize(fragmentSchedule);




        return fragmentSchedule;
    }

    private void initalize(View fragmentSchedule) {
        checkBox1 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule1);
        checkBox2 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule2);
        checkBox3 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule3);
        checkBox4 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule4);
        checkBox5 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule5);
        checkBox6 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule6);
        checkBox7 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule7);
        checkBox8 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule8);
        checkBox9 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule9);
        checkBox10 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule10);
        checkBox11 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule11);
        checkBox12 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule12);
        checkBox13 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule13);
        checkBox14 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule14);
        checkBox15 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule15);
        checkBox16 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule16);
        checkBox17 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule17);
        checkBox18 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule18);
        checkBox19 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule19);
        checkBox20 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule20);
        checkBox21 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule21);
        checkBox22 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule22);
        checkBox23 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule23);
        checkBox24 = (CheckBox) fragmentSchedule.findViewById(R.id.checkbox_schedule24);

        checkBox1.setOnClickListener(this);
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
        checkBox20.setOnClickListener(this);
        checkBox21.setOnClickListener(this);
        checkBox22.setOnClickListener(this);
        checkBox23.setOnClickListener(this);
        checkBox24.setOnClickListener(this);


        ImageView buttonBack = (ImageView) fragmentSchedule.findViewById(R.id.buttonBackSelectSchedule);
        ImageView buttonNext = (ImageView) fragmentSchedule.findViewById(R.id.buttonNextSelectSchedule);
        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
     }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void onClickCheckboxSchedule(View view)
    {
        Boolean checked = ((CheckBox) view).isChecked();

        if(lastCheckBoxId != 0 && lastCheckBoxId != view.getId())
        {
            checkBoxLastClicked.setChecked(false);
        }
        switch(view.getId())
        {
            case R.id.checkbox_schedule1:
                if(checked)
                    currentChecked = 1 ;
                else
                    currentChecked = 0 ;
                Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule2:
                if(checked)
                    currentChecked = 2 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule3:
                if(checked)
                    currentChecked = 3 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule4:
                if(checked)
                    currentChecked = 4 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule5:
                if(checked)
                    currentChecked = 5 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule6:
                if(checked)
                    currentChecked = 6 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule7:
                if(checked)
                    currentChecked = 7 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule8:
                if(checked)
                    currentChecked = 8 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule9:
                if(checked)
                    currentChecked = 9 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule10:
                if(checked)
                    currentChecked = 10 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule11:
                if(checked)
                    currentChecked = 11 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule12:
                if(checked)
                    currentChecked = 12 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule13:
                if(checked)
                    currentChecked = 13 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule14:
                if(checked)
                    currentChecked = 14 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule15:
                if(checked)
                    currentChecked = 15 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule16:
                if(checked)
                    currentChecked = 16 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule17:
                if(checked)
                    currentChecked = 17 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule18:
                if(checked)
                    currentChecked = 18 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule19:
                if(checked)
                    currentChecked = 19 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule20:
                if(checked)
                    currentChecked = 20 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule21:
                if(checked)
                    currentChecked = 21 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule22:
                if(checked)
                    currentChecked = 22 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule23:
                if(checked)
                    currentChecked = 23 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule24:
                if(checked)
                    currentChecked = 24 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;


            default:break;
        }
    }

    public void Save() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(CheckBoxSchedule,currentChecked);
        editor.commit();

    }
    @Override
    public void onClick(View view) {
        Boolean checked=false;
            try{
                checked= ((CheckBox) view).isChecked();
                if(lastCheckBoxId != 0 && lastCheckBoxId != view.getId())
                {
                    checkBoxLastClicked.setChecked(false);
                }
            }catch (Exception e)
            {
                e.printStackTrace();
            }


        switch(view.getId())
        {
            case R.id.buttonNextSelectSchedule:
                if(currentChecked!=0)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("4", "1")).commit();
                else
                    Toast.makeText(getActivity(),"Select atleast one time slot",Toast.LENGTH_SHORT).show();
                break;
            case R.id.buttonBackSelectSchedule:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("2", "1")).commit();
                break;
            case R.id.checkbox_schedule1:
                if(checked)
                    currentChecked = 1 ;
                else
                    currentChecked = 0 ;
                Save();
                lastCheckBoxId = R.id.checkbox_schedule1;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule2:
                if(checked)
                    currentChecked = 2 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule2;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule3:
                if(checked)
                    currentChecked = 3 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule3;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule4:
                if(checked)
                    currentChecked = 4 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule4;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule5:
                if(checked)
                    currentChecked = 5 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule5;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule6:
                if(checked)
                    currentChecked = 6 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule6;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule7:
                if(checked)
                    currentChecked = 7 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule7;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule8:
                if(checked)
                    currentChecked = 8 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule8;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule9:
                if(checked)
                    currentChecked = 9 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule9;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule10:
                if(checked)
                    currentChecked = 10 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule10;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule11:
                if(checked)
                    currentChecked = 11 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule11;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule12:
                if(checked)
                    currentChecked = 12 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule12;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule13:
                if(checked)
                    currentChecked = 13 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule13;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule14:
                if(checked)
                    currentChecked = 14 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule14;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule15:
                if(checked)
                    currentChecked = 15 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule15;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule16:
                if(checked)
                    currentChecked = 16 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule16;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule17:
                if(checked)
                    currentChecked = 17 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule17;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule18:
                if(checked)
                    currentChecked = 18 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule18;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule19:
                if(checked)
                    currentChecked = 19 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule19;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule20:
                if(checked)
                    currentChecked = 20 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule20;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule21:
                if(checked)
                    currentChecked = 21 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule21;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule22:
                if(checked)
                    currentChecked = 22 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule22;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule23:
                if(checked)
                    currentChecked = 23 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule23;
                checkBoxLastClicked =(CheckBox)view;
                break;
            case R.id.checkbox_schedule24:
                if(checked)
                    currentChecked = 24 ;
                else
                    currentChecked = 0 ;Save();
                lastCheckBoxId = R.id.checkbox_schedule24;
                checkBoxLastClicked =(CheckBox)view;
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
}
