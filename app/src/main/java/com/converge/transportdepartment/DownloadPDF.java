package com.converge.transportdepartment;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.converge.transportdepartment.Utility.MarshMallowPermission;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DownloadPDF.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DownloadPDF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DownloadPDF extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;
    private long lastDownload = -1L;
    private DownloadManager mgr;
    private SharedPreferences sharedpreferences;
    private static final String mypreference= "mypref";

    public DownloadPDF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DownloadPDF.
     */
    // TODO: Rename and change types and number of parameters
    public static DownloadPDF newInstance(String param1, String param2) {
        DownloadPDF fragment = new DownloadPDF();
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
        View view = inflater.inflate(R.layout.fragment_download_pd, container, false);

        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        final Button buttonDownloadPdf = (Button) view.findViewById(R.id.buttonDownloadFormPdf);

        if(new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage()) {
            buttonDownloadPdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(), "Downloading Form", Toast.LENGTH_SHORT).show();
                    downloadPdf();
                }
            });
        }else
        new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();
        return view;
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

    @Override
    public void onResume() {
        super.onResume();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(CheckNuevosAvisosIntentService.ACTION_PROGRESO);
//        filter.addAction(CheckNuevosAvisosIntentService.ACTION_FIN);
//        getActivity().registerReceiver(rcv, filter);

        mgr=(DownloadManager)getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        getActivity().registerReceiver(onCompleteDownload,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        getActivity().registerReceiver(onNotificationClickDownload,
                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(onCompleteDownload);
        getActivity().unregisterReceiver(onNotificationClickDownload);
    }

    public void downloadPdf() {
        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan/LL_Application_Form.php?referenceId="+sharedpreferences.getString("receiptNum",""));
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(false);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Downloading");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading File");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Form" + System.currentTimeMillis() + ".pdf");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue a new download and same the referenceId
        lastDownload = mgr.enqueue(request);
    }

    @Override
    public void onStop()
    {

        super.onStop();
    }

    BroadcastReceiver onCompleteDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            getActivity().findViewById(R.id.start).setEnabled(true);
            Toast.makeText(getActivity(),"complete",Toast.LENGTH_LONG).show();
        }
    };

    BroadcastReceiver onNotificationClickDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Toast.makeText(ctxt, "Ummmm...hi!", Toast.LENGTH_LONG).show();
        }
    };

}
