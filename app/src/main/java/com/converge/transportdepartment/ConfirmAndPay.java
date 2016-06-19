package com.converge.transportdepartment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConfirmAndPay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConfirmAndPay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmAndPay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String PREFS_NAME = "MyTransportFile";
    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;


    // TextView and Spinner
    private TextView textViewCPReferenceId,textViewCPAppointmentPlace,textViewCPDate,textViewCPTime,
            textViewCPApplicationType,textViewCPTransactionType,textViewCPApplicantName,textViewCPEmail,
            textViewCPRelation,textViewCPQualification,textViewCPRelationName,textViewCPDOB,textViewCPAddress,
            textViewCPBloodGroup,textViewCPGender,textViewCPIdMark,textViewCPCity,textViewCPIdMark2,textViewCPPincode,
            textViewCPMobileNo;


    private static final String   meditViewApplicantFirstNameString = "meditViewApplicantFirstName";
    private static final String   meditViewApplicantMiddleNameString = "meditViewApplicantMiddleName";
    private static final String   meditViewApplicantLastNameString = "meditViewApplicantLastName";
    private static final String   meditViewApplicantRelationsNameString = "meditViewApplicantRelationsName";
    private static final String   meditViewEmailString = "meditViewEmail";
    private static final String   meditViewMobileNoString = "meditViewMobileNo";
    private static final String   meditViewPincodeString = "meditViewPincode";
    private static final String   meditViewAddressString = "meditViewAddress";
    private static final String   meditViewFeeString = "meditViewFee";


    private static final String   mspinnerRTOString = "mspinnerRTO";
    private static final String   mspinnerRTOInt = "mspinnerRTOInt";

    private static final String   mspinnerRelationshipTypeString = "mspinnerRelationshipType";
    private static final String   mspinnerRelationshipTypeInt = "mspinnerRelationshipTypeInt";

    private static final String   mspinnerQualificationString = "mspinnerQualification";
    private static final String   mspinnerQualificationInt = "mspinnerQualificationInt";

    private static final String   mspinnerGenderString = "mspinnerGender";
    private static final String   mspinnerGenderInt = "mspinnerGenderInt";

    private static final String   mspinnerIdmarkString = "mspinnerIdmark";
    private static final String   mspinnerIdmarkInt = "mspinnerIdmarkInt";

    private static final String   mspinnerBloodGroupString = "mspinnerBloodGroup";
    private static final String  mspinnerBloodGroupInt = "mspinnerBloodGroupInt";

    private static final String   mspinnerRHString = "mspinnerRH";
    private static final String   mspinnerRHInt = "mspinnerRHInt";

    private String mtextViewDateString = "mtextViewDate";
    private String mtextViewDateInt = "mtextViewDateInt";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private StringBuffer brName = new StringBuffer() ;
    private StringBuffer brRelation = new StringBuffer();
    private StringBuffer brRelationName = new StringBuffer();
    private StringBuffer brEmail = new StringBuffer();
    private StringBuffer brMobileNo = new StringBuffer();
    private StringBuffer brQualification = new StringBuffer();
    private StringBuffer brPincode = new StringBuffer();
    private StringBuffer brAddress = new StringBuffer();
    private StringBuffer brFee = new StringBuffer();
    private StringBuffer brIdMark = new StringBuffer();
    private StringBuffer brGender = new StringBuffer();
    private StringBuffer brDOB = new StringBuffer();
    private StringBuffer brBloodGroup = new StringBuffer();
    private StringBuffer brCity = new StringBuffer();

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
        deleteBufferedData();
        setText();
        getPreferenceData();
        return view;
    }

    private void initailizeFelids(View rootView) {
        textViewCPReferenceId = (TextView)rootView.findViewById(R.id.textViewCPReferenceId);
        textViewCPAppointmentPlace = (TextView)rootView.findViewById(R.id.textViewCPAppointmentPlace);
        textViewCPDate = (TextView)rootView.findViewById(R.id.textViewCPDate);

        textViewCPTime = (TextView)rootView.findViewById(R.id.textViewCPTime);
        textViewCPApplicationType = (TextView)rootView.findViewById(R.id.textViewCPApplicationType);
        textViewCPTransactionType = (TextView)rootView.findViewById(R.id.textViewCPTransactionType);
        textViewCPApplicantName = (TextView)rootView.findViewById(R.id.textViewCPApplicantName);
        textViewCPEmail = (TextView)rootView.findViewById(R.id.textViewCPEmail);
        textViewCPRelation = (TextView)rootView.findViewById(R.id.textViewCPRelation);
        textViewCPQualification = (TextView)rootView.findViewById(R.id.textViewCPQualification);
        textViewCPRelationName = (TextView)rootView.findViewById(R.id.textViewCPRelationName);
        textViewCPDOB = (TextView)rootView.findViewById(R.id.textViewCPDOB);
        textViewCPAddress = (TextView)rootView.findViewById(R.id.textViewCPAddress);
        textViewCPBloodGroup = (TextView)rootView.findViewById(R.id.textViewCPBloodGroup);
        textViewCPGender = (TextView)rootView.findViewById(R.id.textViewCPGender);
        textViewCPIdMark = (TextView)rootView.findViewById(R.id.textViewCPIdMark);
        textViewCPCity = (TextView)rootView.findViewById(R.id.textViewCPCity);
        textViewCPIdMark2 = (TextView)rootView.findViewById(R.id.textViewCPIdMark2);
        textViewCPPincode = (TextView)rootView.findViewById(R.id.textViewCPPincode);
        textViewCPMobileNo = (TextView)rootView.findViewById(R.id.textViewCPMobileNo);
    }
    private void deleteBufferedData() {
        brName.delete(0,brName.length());
        brRelation.delete(0,brRelation.length());
        brRelationName.delete(0,brRelationName.length());
        brEmail.delete(0,brEmail.length());
        brMobileNo.delete(0,brMobileNo.length());
        brQualification.delete(0,brQualification.length());
        brPincode.delete(0,brPincode.length());
        brAddress.delete(0,brAddress.length());
        brFee.delete(0,brFee.length());
        brIdMark.delete(0,brIdMark.length());
        brBloodGroup.delete(0,brBloodGroup.length());
        brGender.delete(0,brGender.length());

    }
    private void setText()
    {

//        brName.append("Applicant Name : ").append(sharedpreferences.getString(meditViewApplicantFirstNameString, "")).append(" ").
//                append(sharedpreferences.getString(meditViewApplicantMiddleNameString, "")).append(" ").append(sharedpreferences.getString(meditViewApplicantLastNameString, ""));
//        brRelation.append("RelationType : ").append(sharedpreferences.getString(mspinnerRelationshipTypeString,""));
//        brRelationName.append("Relation's Name : ").append(sharedpreferences.getString(meditViewApplicantRelationsNameString, ""));
//        brEmail.append("Email : ").append(sharedpreferences.getString(meditViewEmailString, ""));
//        brMobileNo.append("Moblie No. : ").append(sharedpreferences.getString(meditViewMobileNoString, ""));
//        brQualification.append("Qualification : ").append(sharedpreferences.getString(mspinnerQualificationString,""));
//        brPincode.append("Pincode : ").append(sharedpreferences.getString(meditViewPincodeString,""));
//        brAddress.append("Address : ").append(sharedpreferences.getString(meditViewAddressString,""));
//        brFee.append("Fee : Rs 400");
//        brGender.append("Gender : ").append(sharedpreferences.getString(mspinnerGenderString,""));
//        brIdMark.append("Id Mark : ").append(sharedpreferences.getString(mspinnerIdmarkString,""));
////        brDOB.append("DOB : ").append(sharedpreferences.getString(mtextViewDOB))
//        brBloodGroup.append("Blood Group : ").append(sharedpreferences.getString(mspinnerBloodGroupString,"")).append(sharedpreferences.getString(mspinnerRHString,""));
////        brCity.append("City : ").append(sharedpreferences.getString(mt))




    }



    private void getPreferenceData()
    {
//        textViewCPApplicantName.setText(brName.toString());
//        textViewCPRelation.setText(brRelation.toString());
//        textViewCPRelationName.setText(brRelationName.toString());
//        textViewCPEmail.setText(brEmail.toString());
//
//        textViewCPMobileNo.setText(brMobileNo.toString());
//        textViewCPQualification.setText(brQualification.toString());
//        textViewCPPincode.setText(brPincode.toString());
//        textViewCPAddress.setText(brAddress.toString());
//        textViewCPIdMark.setText(brIdMark.toString());
//        textViewCPBloodGroup.setText(brBloodGroup.toString());
//        textViewCPGender.setText(brGender.toString());



//
//        if(sharedpreferences.contains(mspinnerRTOInt)){
//            mspinnerRTO.setSelection(sharedpreferences.getInt(mspinnerRTOInt,1));
//        }

    }
    private void setTextFields()
    {

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
