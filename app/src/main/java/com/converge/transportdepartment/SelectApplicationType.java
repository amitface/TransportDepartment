package com.converge.transportdepartment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
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

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static Map<Integer,Boolean> mCheckBox = new HashMap<>(7);


    //Check Box Buttons
    private CheckBox checkBox1;
    private CheckBox checkBox2;
    private CheckBox checkBox3;
    private CheckBox checkBox4;
    private CheckBox checkBox5;
    private CheckBox checkBox6;
    private CheckBox checkBox7;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_select_application_type, container, false);
        Button buttonSelectionApplicationNext = (Button) fragmentView.findViewById(R.id.buttonSelectApplication);

        intializeHashMap();
        checkBox1 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_1);
        checkBox2 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_2);
        checkBox3 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_3);
        checkBox4 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_4);
        checkBox5 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_5);
        checkBox6 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_6);
        checkBox7 =(CheckBox) fragmentView.findViewById(R.id.checkbox_application_type_7);



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

            /*onClickListener */
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

        Button buttonSave =(Button) fragmentView.findViewById(R.id.buttonClearSelectApplication);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCheckBox(view);
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
//
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


//    public void onClickApplicationType(View view) {
//       switch (view.getId())
//       {
//           case R.id.buttonSelectApplication:
//
//               break;
//           default:break;
//       }
//    }

    public void onClickCheckBox(View view)
    {
        // Is the view now checked?
        boolean checked= false;
        if(view.getId()!= R.id.buttonClearSelectApplication){
            checked = ((CheckBox) view).isChecked();
        }


        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_application_type_1:
                if (checked)
                    // Put some meat on the sandwich
                    mCheckBox.put(0,checked);
                else
                    // Remove the meat
                    mCheckBox.put(0,checked); Save(view);
                break;
            case R.id.checkbox_application_type_2:
                if (checked)
                    // Cheese me
                    mCheckBox.put(1,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(1,checked); Save(view);
                break;
            case R.id.checkbox_application_type_3:
                if (checked)
                    // Cheese me
                    mCheckBox.put(2,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(2,checked); Save(view);
                break;
            case R.id.checkbox_application_type_4:
                if (checked)
                    // Cheese me
                    mCheckBox.put(3,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(3,checked); Save(view);
                break;
            case R.id.checkbox_application_type_5:
                if (checked)
                    // Cheese me
                    mCheckBox.put(4,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(4,checked); Save(view);
                break;
            case R.id.checkbox_application_type_6:
                if (checked)
                    // Cheese me
                    mCheckBox.put(5,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(5,checked);
                Save(view);
                break;
            case R.id.checkbox_application_type_7:
                if (checked)
                    // Cheese me
                    mCheckBox.put(6,checked);
                else
                    // I'm lactose intolerant
                    mCheckBox.put(6,checked);
                Save(view);
                break;
            case R.id.buttonClearSelectApplication:
                clear(view);
                break;
            default:break;
            // TODO: Veggie sandwich
        }
    }


    public void Save(View view) {
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putBoolean(CheckBoxApplicationType1,mCheckBox.get(0));
        editor.putBoolean(CheckBoxApplicationType2,mCheckBox.get(1));
        editor.putBoolean(CheckBoxApplicationType3,mCheckBox.get(2));
        editor.putBoolean(CheckBoxApplicationType4,mCheckBox.get(3));
        editor.putBoolean(CheckBoxApplicationType5,mCheckBox.get(4));
        editor.putBoolean(CheckBoxApplicationType6,mCheckBox.get(5));
        editor.putBoolean(CheckBoxApplicationType7,mCheckBox.get(6));
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
        mCheckBox.put(0,false);
        mCheckBox.put(1,false);
        mCheckBox.put(2,false);
        mCheckBox.put(3,false);
        mCheckBox.put(4,false);
        mCheckBox.put(5,false);
        mCheckBox.put(6,false);
        Save(view);
        Toast.makeText(getActivity(),"clear",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {

    }


    private void intializeHashMap() {
        for(int i=0;i<7;i++)
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


}
