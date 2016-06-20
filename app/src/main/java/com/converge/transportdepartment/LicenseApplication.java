package com.converge.transportdepartment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LicenseApplication.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LicenseApplication#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LicenseApplication extends Fragment implements TabHost.OnTabChangeListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FragmentTabHost mTabHost;
    private SelectApplicationType.OnFragmentInteractionListener mSelectApplication;


    public LicenseApplication() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LicenseApplication.
     */
    // TODO: Rename and change types and number of parameters
    public static LicenseApplication newInstance(String param1, String param2) {
        LicenseApplication fragment = new LicenseApplication();
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

        onAttachFragment(getParentFragment());

        mTabHost = new FragmentTabHost(getActivity());
        mTabHost.setup(getActivity(), getChildFragmentManager(), R.id.fragment1);

        mTabHost.addTab(mTabHost.newTabSpec("Type").setIndicator("Class of Vehicle"),
                SelectApplicationType.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("schedule").setIndicator("Appointment Schedule"),
               SelectSchedule.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("details").setIndicator("Personal Details"),
               PersonalDetails.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("other").setIndicator("Other Details"),
                IdProof.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("pay").setIndicator("Confirm and Pay"),
               ConfirmAndPay.class, null);
        mTabHost.getTabWidget().setEnabled(false);

        mTabHost.setCurrentTab(Integer.parseInt(mParam1));

        /* Increase tab height programatically
             * tabs.getTabWidget().getChildAt(1).getLayoutParams().height = 150;
             */

        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
            final TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);

            mTabHost.getTabWidget().getChildAt(i).setPadding(6,0,0,0);

            if (tv == null)
                continue;
            else
            {
                tv.setTextSize(8);
                tv.setPadding(6,0,0,0);
            }



        }

        return mTabHost;
//        return inflater.inflate(R.layout.fragment_license_application, container, false);
    }

    public FragmentTabHost getTabHost()
    {
        return mTabHost;
    }
    public void onAttachFragment(Fragment fragment)
    {
        try
        {
            mSelectApplication = (SelectApplicationType.OnFragmentInteractionListener)fragment;

        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(
                    fragment.toString() + " must implement OnPlayerSelectionSetListener");
        }
    }


    private View getTabIndicator(Context context,  int icon) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
        iv.setImageResource(icon);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(View view) {
        if (mListener != null) {
            mListener.onFragmentInteraction(view);
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
        mTabHost = null;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.button_confirm_and_pay:
                mTabHost.setCurrentTab(1);
                break;
            default:break;
        }
    }



    @Override
    public void onTabChanged(String s) {
        showToast("Fragment Personal Details on Tab Changed");
    }

    private void showToast(String s)
    {
        Toast.makeText(getActivity(), s,Toast.LENGTH_LONG).show();
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
        void onFragmentInteraction(View view);
    }
}
