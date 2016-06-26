package com.converge.transportdepartment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DatePicker.DatePickerFragment1;
import com.converge.transportdepartment.DatePicker.DatePickerFragment2;
import com.converge.transportdepartment.DatePicker.DatePickerFragment3;
import com.converge.transportdepartment.DatePicker.DatePickerFragment4;


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

    private Spinner spinnerIdcard1, spinnerIdcard2,spinnerIdcard3,spinnerIdcard4;
    private EditText editTextDocumentNum1,editTextIssuingAuthority1;
    private EditText editTextDocumentNum2,editTextIssuingAuthority2;
    private EditText editTextDocumentNum3,editTextIssuingAuthority3;
    private EditText editTextDocumentNum4,editTextIssuingAuthority4;
    public static TextView editTextDateofIssue1,editTextDateofIssue2,editTextDateofIssue3,editTextDateofIssue4;

    TableLayout tableLayout2;
    TableLayout tableLayout3;
    TableLayout tableLayout4;



    private String mFinalString1="mFinalString1";

    String s1,s2;
    String idCode[]={"","B", "C", "D ", "F ","H "," I ","L ","T ","V ","Z ","E ", "A ", "1 ",
            "2 ", "3 ", "P ", "4 ", "5 ", "6 ", "7 "};

    ImageView img2,img1,imageViewDatePicker1,imageViewDatePicker2,imageViewDatePicker3,imageViewDatePicker4;

    public static final String mypreference = "mypref";
    private static int count=0;
    private SharedPreferences sharedpreferences;
    private final String mFinalString2="mFinalString2";
    private final String mFinalStringCov="mFinalStringCov";
    private String s3;

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
        Button button = (Button)view.findViewById(R.id.buttonNextIdProof);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });

        initalize(view);
        setListner(view);

            return view;
    }

    private void setListner(View view) {
        imageViewDatePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });
        imageViewDatePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });
        imageViewDatePicker3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });
        imageViewDatePicker4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });


        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickIdProof(v);
            }
        });


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
    }

    public void onClickIdProof(View view) {
        DialogFragment newFragment;
        switch (view.getId()) {
            case R.id.buttonNextIdProof:
                if(validate())
                {
                    saveSharedPreference();
                    if(sharedpreferences.contains(mFinalString1)) {
                        s1=sharedpreferences.getString(mFinalString1,"");
                        if(sharedpreferences.contains(mFinalStringCov))
                        {
                            s3="&covs="+sharedpreferences.getString(mFinalStringCov,"");
                        }else
                        s3= "&covs=3,4";

                        s2=detailString();
                    }
//                    sendPostRequest();

                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, LicenseApplication.newInstance("3", "1")).commit();
                }
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
                if(count==0)
                {
                    count++;
                    tableLayout2.setVisibility(View.VISIBLE);
                }
                else if(count==1)
                {
                    count++;
                    tableLayout3.setVisibility(View.VISIBLE);
                }
                else if(count==2)
                {
                    count++;
                    tableLayout4.setVisibility(View.VISIBLE);
                }
                else if(count==3)
                {
                    showToast("Cannot Add More");
                }
                break;
            case R.id.buttonRemove:
                if(count==0)
                {
                    showToast("Cannot Remove More");
                }
                else if(count==1)
                {
                    count--;
                    tableLayout2.setVisibility(View.INVISIBLE);
                }
                else if(count==2)
                {
                    count--;
                    tableLayout3.setVisibility(View.INVISIBLE);
                }
                else if(count==3)
                {
                    count--;
                    tableLayout4.setVisibility(View.GONE);
                }

                break;
        }
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
    public void onClick(View v) {

    }

    private void saveSharedPreference() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(mFinalString2, detailString());
        editor.commit();
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

    private void showToast(String s)
    {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

}
