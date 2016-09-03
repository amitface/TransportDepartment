package com.converge.transportdepartment;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DatePicker.DatePickerFragmentDownload;
import com.converge.transportdepartment.Utility.ConValidation;
import com.converge.transportdepartment.Utility.Links;
import com.converge.transportdepartment.Utility.MarshMallowPermission;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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
    private String mParam1, mParam2;
    private String refNum,fname, lname,dob;
//   privategggg

    private OnFragmentInteractionListener mListener;
    private long lastDownload = -1L;
    private DownloadManager mgr;
    private SharedPreferences sharedpreferences;
    private static final String mypreference= "mypref";
    private int ref;
    private String s;
    private TextView textDownload;
    public static TextView editDownloadPdfDate;
    private ProgressDialog progress;
    private EditText fN,lN;
    private ImageView im;
    private Button buttonDownloadPdf;

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
        buttonDownloadPdf = (Button) view.findViewById(R.id.buttonDownloadFormPdf);
        fN = (EditText) view.findViewById(R.id.etFirstName);
        lN = (EditText) view.findViewById(R.id.etLastName);


        im= (ImageView) view.findViewById(R.id.imageViewDatePickerDownloadPdf);
        im.setOnClickListener(this);

        editDownloadPdfDate = (TextView) view.findViewById(R.id.tvDate);
        textDownload = (TextView) view.findViewById(R.id.editDownloadPdf);



        if(new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage()) {

        }else
        new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();

        buttonDownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s = getLength();
                if (ConValidation.isNetworkAvailable(getActivity())) {
                    if (s.length() == 7) {
                        ref = Integer.parseInt(s);
                        Toast.makeText(getActivity(), "Downloading Form", Toast.LENGTH_SHORT).show();
                        downloadPdfForm(ref);
                    } else if (fN.getText().length() >= 3 && lN.getText().length() >= 3 && editDownloadPdfDate.getText().length() != 0) {
                        fname = fN.getText().toString();
                        lname = lN.getText().toString();
                        dob = editDownloadPdfDate.getText().toString();
                        download(view);
                    } else {
                        Toast.makeText(getActivity(), "Reference number wrong", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"No internet connection...",Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private String getLength() {
       return   textDownload.getText().toString();
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
        switch (view.getId())
        {
            case R.id.imageViewDatePickerDownloadPdf:
                DialogFragment newFragment = new DatePickerFragmentDownload();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
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
//        getActivity().registerReceiver(onNotificationClickDownload,
//                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(onCompleteDownload);
//        getActivity().unregisterReceiver(onNotificationClickDownload);
    }

    public void downloadPdfForm(int n) {
//        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan/LL_Application_Form.php?referenceId="+sharedpreferences.getString("receiptNum",""));
        Uri Download_Uri = Uri.parse(Links.downloadFormsPdf+n+".pdf");
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
        request.setDestinationInExternalPublicDir("M-Parivahan", "Form_" + System.currentTimeMillis() + ".pdf");

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
            Toast.makeText(ctxt, "Form!", Toast.LENGTH_LONG).show();
        }
    };

    public void download(View view){

       new  findDetail(getActivity()).execute();
    }

    private class findDetail extends AsyncTask<String, Void, Integer> {

        private final Context context;
        private ProgressDialog progressDialog;

        public findDetail(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(this.context);
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            try {
                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/search_by_name.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                String urlString ="fname="+fname+"&lname="+lname+"&dob="+dob;

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(urlString);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                output.append(System.getProperty("line.separator") + "Request Parameters " + urlString);
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
                String responseDetail= responseOutput.toString();
                JSONArray jsonObject =new JSONArray(responseDetail);
                JSONObject jsonObject1=jsonObject.getJSONObject(0);
                refNum =jsonObject1.get("ref_num").toString();
                return 1;
            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0;
        }
        protected void onPostExecute(Integer result) {

            progressDialog.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downloadPdfForm(Integer.parseInt(refNum));
                    }//public void run() {
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here

                    }//public void run() {
                });
            }
        }

    }
}
//        progress=new ProgressDialog(getActivity());
//        progress.setMessage("Downloading Form");
//        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//        progress.setIndeterminate(true);
//        progress.setProgress(0);
//        progress.show();
//
//        final int totalProgressTime = 100;
//        final Thread t = new Thread() {
//            @Override
//            public void run() {
//                int jumpTime = 0;
//
//                while(jumpTime < totalProgressTime) {
//                    try {
//                        sleep(200);
//                        jumpTime += 5;
//                        progress.setProgress(jumpTime);
//                    }
//                    catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        progress.hide();
//                        Toast.makeText(getActivity(),"No Data Found",Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        };
//        t.start();