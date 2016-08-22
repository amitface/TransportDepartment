package com.converge.transportdepartment;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.converge.transportdepartment.DataBaseHelper.DBAdapter;
import com.converge.transportdepartment.Utility.MarshMallowPermission;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PaymentSuccessfull.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PaymentSuccessfull#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentSuccessfull extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DownloadManager mgr=null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ProgressDialog progressDialog;

    private String jsonString;
    private static final String PGInfo="PgInfo";

    private String mFinalString1="mFinalString1";
    private final String mFinalString2="mFinalString2";
    private final String mFinalStringCov="mFinalStringCov";

    private static final String CheckBoxSchedule = "currentCheckBox";

    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;



    private OnFragmentInteractionListener mListener;
    private long lastDownload = -1L;
    private static String emailToSend;
    private HashMap<String,String> hashMap=new HashMap<>();
    private long appNumber;
    private String receiptNumber;
    private String rtoCode, msg;

    public PaymentSuccessfull() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentSuccessfull.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentSuccessfull newInstance(String param1, String param2) {
        PaymentSuccessfull fragment = new PaymentSuccessfull();
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
    public void onResume() {
        super.onResume();

        mgr=(DownloadManager)getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(onComplete);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_payment_successfull, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        jsonString=sharedpreferences.getString(PGInfo,"");
        try {
            JSONObject jsonObjectData= new JSONObject(jsonString);
            appNumber= jsonObjectData.getLong("applicantNum");
            receiptNumber = jsonObjectData.getString("receiptNum");
            rtoCode = jsonObjectData.getString("rtocodeReal");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        new getAddress(getActivity()).execute();
        TextView textView2 = (TextView) view.findViewById(R.id.textViewForm);
        TextView textFee = (TextView) view.findViewById(R.id.textViewFee);
        TextView textReceipt  = (TextView) view.findViewById(R.id.textViewReceipt);

        final EditText editText = (EditText) view.findViewById(R.id.emailToSend);
        editText.setText(sharedpreferences.getString("EmailZ",""));
        textView2.setText("Application Form : "+appNumber);
        textReceipt.setText("Receipt No.      : R"+receiptNumber);
        int fee= totalFee();
        textFee.setText("Payment Successful for amount Rs. "+fee);

        if(!new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage() )
        {
            new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();
        }

        ImageView buttonEmail = (ImageView) view.findViewById(R.id.buttonEmail);


        ImageView buttonDownload = (ImageView) view.findViewById(R.id.buttonDownload);
        ImageView saveReceipt = (ImageView) view.findViewById(R.id.saveReceipt);


                buttonDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(!new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage() )
                        {

                            new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();
                        }

                        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(extStorageDirectory, "M-Parivahan");
                        folder.mkdir();
                        downloadPdfForm();
                    }
                });
                buttonEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Validation.isEmailAddress(editText,true)) {
                            emailToSend = editText.getText().toString();
                            new SendMail(getActivity()).execute();
                            alertDialogPostReport("1. Documents (PDF) for Application Form and Fee Receipt have been sent to your Email ID.");
                        }
                    }
                });

                saveReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(!new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage() )
                        {
                            new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();
                        }
                        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(extStorageDirectory, "M-Parivahan");
                        folder.mkdir();
                        downloadPdf();
                    }
                });
        deleteSession();

        return view;
    }



    private int totalFee() {
        String s= sharedpreferences.getString("mFinalStringCov","");
        String arr[]=s.split(",");
        int len =arr.length;
        if(arr[0].length()>0)
            len = len*30+20;
        else
            len=0;
        return len;
    }

    private void deleteSession()
    {
        saveSessionPersonalDetails();
        saveSessionIdProof();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(mFinalString1,"");
        editor.putString(mFinalString2,"");
        editor.putString(mFinalStringCov,"");
        editor.putInt(CheckBoxSchedule,0);
        editor.commit();
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

    private void saveSessionPersonalDetails()
    {
        hashMap.put("rtocode","");
        hashMap.put("first_name","");
        hashMap.put("middle_name","");
        hashMap.put("last_name","");

        hashMap.put("dob","");
        hashMap.put("gender","");

        hashMap.put("birth_place","");
        hashMap.put("year","");
        hashMap.put("month","");
        hashMap.put("birth_country","");
        hashMap.put("email_id","");

        hashMap.put("relation","");
        hashMap.put("p_first_name","");
        hashMap.put("p_middle_name","");
        hashMap.put("p_last_name","");
//        hashMap.put("permanent_address","");
        hashMap.put("p_flat_no","");
        hashMap.put("p_flat_house_name","");
        hashMap.put("p_house_no","");
        hashMap.put("p_street","");
        hashMap.put("p_locality","");
        hashMap.put("p_village_city","");
        hashMap.put("p_taluka","");
        hashMap.put("p_district","");
        hashMap.put("p_state","");

        hashMap.put("p_years","");
        hashMap.put("p_months","");
        hashMap.put("p_pin","");
        hashMap.put("p_mobile_no","");

        hashMap.put("t_flat_no","");
        hashMap.put("t_flat_house_name","");
        hashMap.put("t_house_no","");
        hashMap.put("t_street","");
        hashMap.put("t_locality","");
        hashMap.put("t_village_city","");
        hashMap.put("t_taluka","");
        hashMap.put("t_district","");

        hashMap.put("t_state","");

        hashMap.put("t_years","");
        hashMap.put("t_months","");
        hashMap.put("t_pin","");
        hashMap.put("t_moblie_no","");


        hashMap.put("citizenship_status","");
        hashMap.put("edu_qualification","");
        hashMap.put("identification_marks","");
        hashMap.put("identification_marks2","");
        hashMap.put("blood_group","");
        hashMap.put("blood_group_rh","");

        try{
            DBAdapter db = new DBAdapter(getActivity());

            //---get all contacts---
            db.open();
            if(db.updateData(hashMap))
            {
                System.out.println("date Saved----------");
            }
            else {
                System.out.println("date cannot be Saved----------");
            }
        }catch (Exception e)
        {
            System.out.println("ErrorSaving");
        }
    }

    private void saveSessionIdProof() {
        hashMap.put("name1","0");
        hashMap.put("doc_num1","");
        hashMap.put("authority1","");
        hashMap.put("do_issue1","");

        hashMap.put("name2","0");
        hashMap.put("doc_num2","");
        hashMap.put("authority2","");
        hashMap.put("do_issue2","");

        hashMap.put("name3","0");
        hashMap.put("doc_num3","");
        hashMap.put("authority3","");
        hashMap.put("do_issue3","");

        hashMap.put("name4","0");
        hashMap.put("doc_num4","");
        hashMap.put("authority4","");
        hashMap.put("do_issue4","");
        try{
            DBAdapter db = new DBAdapter(getActivity());

            //---get all contacts---
            db.open();
            if(db.updateDataIdProof(hashMap))
            {
                System.out.println("date Saved----------");
            }
            else {
                System.out.println("date cannot be Saved----------");
            }
        }catch (Exception e)
        {
            System.out.println("ErrorSaving Id proof");
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

    public void downloadPdf() {
//        showProgress();
//        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan/LL_Cash_Receipt.php?referenceId="+sharedpreferences.getString("receiptNum",""));
        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan-Odisha/allpdf/"+appNumber+"LL_Cash_Receipt.pdf");
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(true);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Receipt");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir("M-Parivahan", "Receipt" + System.currentTimeMillis() + ".pdf");


        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue a new download and same the referenceId
        lastDownload = mgr.enqueue(request);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialogPostReport("Documents (PDF) for  Fee Receipt   can be accessed from your Mobile Device from this location – M-Parivahan");
            }
        });
    }

    public void downloadPdfForm() {
//        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan-Odisha/LL_Application.php?referenceId="+sharedpreferences.getString("receiptNum",""));
        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan-Odisha/allpdf/"+appNumber+".pdf");
        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);

        //Restrict the types of networks over which this download may proceed.
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        //Set whether this download may proceed over a roaming connection.
        request.setAllowedOverRoaming(true);
        //Set the title of this download, to be displayed in notifications (if enabled).
        request.setTitle("Form");
        //Set a description of this download, to be displayed in notifications (if enabled)
        request.setDescription("Downloading");
        //Set the local destination for the downloaded file to a path within the application's external files directory
        request.setDestinationInExternalPublicDir("M-Parivahan", "Form_" + System.currentTimeMillis() + ".pdf");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue a new download and same the referenceId

        lastDownload = mgr.enqueue(request);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                alertDialogPostReport("Documents (PDF) for Application Form  can be accessed from your Mobile Device from this location – M-Parivahan");
            }
        });
    }

    public void startDownload(View v) {
        Uri uri=Uri.parse("http://103.27.233.206/learningLicense/LL_Cash_Receipt.php?referenceId="+sharedpreferences.getString("ref_num",""));

        Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                .mkdirs();

        lastDownload=
                mgr.enqueue(new DownloadManager.Request(uri)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                                DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("Demo")
                        .setDescription("Something useful. No, really.")
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                                "Receipt.pdf"));

//        v.setEnabled(false);
//        findViewById(R.id.query).setEnabled(true);
    }

    public void queryStatus(View v) {
        Cursor c=mgr.query(new DownloadManager.Query().setFilterById(lastDownload));

        if (c==null) {
           showToast("Download not found!");
        }
        else {
            c.moveToFirst();

            Log.d(getClass().getName(), "COLUMN_ID: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_ID)));
            Log.d(getClass().getName(), "COLUMN_BYTES_DOWNLOADED_SO_FAR: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)));
            Log.d(getClass().getName(), "COLUMN_LAST_MODIFIED_TIMESTAMP: "+
                    c.getLong(c.getColumnIndex(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP)));
            Log.d(getClass().getName(), "COLUMN_LOCAL_URI: "+
                    c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI)));
            Log.d(getClass().getName(), "COLUMN_STATUS: "+
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS)));
            Log.d(getClass().getName(), "COLUMN_REASON: "+
                    c.getInt(c.getColumnIndex(DownloadManager.COLUMN_REASON)));

            showToast( statusMessage(c));
        }
    }

    private void showToast(String s) {
        Toast.makeText(getActivity(),s,Toast.LENGTH_SHORT).show();
    }

    public void viewLog(View v) {
        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
    }

    private String statusMessage(Cursor c) {
        String msg="???";

        switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
            case DownloadManager.STATUS_FAILED:
                msg="Download failed!";
                break;

            case DownloadManager.STATUS_PAUSED:
                msg="Download paused!";
                break;

            case DownloadManager.STATUS_PENDING:
                msg="Download pending!";
                break;

            case DownloadManager.STATUS_RUNNING:
                msg="Download in progress!";
                break;

            case DownloadManager.STATUS_SUCCESSFUL:
                msg="Download complete!";
                break;

            default:
                msg="Download is nowhere in sight";
                break;
        }

        return(msg);
    }

    private class SendMail extends AsyncTask<Void, Integer, Long>
    {
        private ProgressDialog progress;
        private final Context context;
        private ProgressDialog progressSendMail;

        public SendMail(Context c) {
            this.context = c;
        }
        protected void onPreExecute() {
            progressSendMail = new ProgressDialog(this.context);
            progressSendMail.setMessage("Sending mail");
            progressSendMail.setCancelable(true);
            progressSendMail.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressSendMail.setProgress(0);

        }

        @Override
        protected Long doInBackground(Void... params) {
            try{

                int  MEGABYTE = 1024 * 1024;
//                URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/send_mail.php");
                URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/ll_app.php?");
                String s="referenceId=" +appNumber+
                        "&email="+emailToSend;

                HttpURLConnection connection = (HttpURLConnection) email.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setConnectTimeout(25000);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());

                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                String response = connection.getResponseMessage();

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("output===============" + br);
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();

                return 1L;
            }catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return 0L;
        }
        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progressSendMail.setProgress(percent[0]);
        }
        protected void onPostExecute(Long result) {


            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        showToast("failure");
                    }//public void run() {
                });

            }
        }
    }

    private class DownloadFile extends AsyncTask<Void, Integer, Long> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public DownloadFile(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Downloading");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setProgress(0);
//            progress.show();

        }

        @Override
        protected Long doInBackground(Void... params)
        {
            Long l=0L;
            try
                {
                        URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/LL_Application.php?referenceId=" + sharedpreferences.getString("receiptNum", ""));

                        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                        File folder = new File(extStorageDirectory, "M-Parivahan");
                        folder.mkdir();

                        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                        String hjkl = currentDateTimeString.replaceAll(" ", "_");
                        String hiop = hjkl.replaceAll(":", "-");

                        File pdfFile = new File(folder, "Form_" + hiop+".html" +
                                "");

                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        //urlConnection.setRequestMethod("GET");
                        //urlConnection.setDoOutput(true);
                        urlConnection.connect();

                        InputStream inputStream = urlConnection.getInputStream();
                        FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                        int totalSize = urlConnection.getContentLength();
                        publishProgress(70);
                        byte[] buffer = new byte[MEGABYTE];
                        int bufferLength = 0;
                        while ((bufferLength = inputStream.read(buffer)) > 0) {
                            fileOutputStream.write(buffer, 0, bufferLength);
                        }

                        for (int i = 1; i < 101; i++) {
                            publishProgress(i);
                        }

                        fileOutputStream.close();
                        l = 1L;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
            return l;
        }

    protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
        progress.setProgress(percent[0]);
    }
        protected void onPostExecute(Long result) {
           
            progress.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
//                        showToast("success");
//                        showToast("Check M-Parivahan folder in directory for HTML");
//                        showToast("Back press to go home");
                        alertDialogPostReport("1. PDF Successfully downloaded check M-Parivahan folder in Directory or Notification");
                    }//public void run() {
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
                        showToast("failure");
                    }//public void run() {
                });

            }
        }

    }

    private class DownloadReceipt extends AsyncTask<Void, Integer, Long> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public DownloadReceipt(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Downloading");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected Long doInBackground(Void... params)
        {
            Long l=0L;
            try
            {
                URL url = new URL("http://103.27.233.206/M-Parivahan/LL_Cash_Receipt.php?referenceId=" + sharedpreferences.getString("receiptNum", ""));

                String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "M-Parivahan");
                folder.mkdir();

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                String hjkl = currentDateTimeString.replaceAll(" ", "_");
                String hiop = hjkl.replaceAll(":", "-");

                File pdfFile = new File(folder, "Receipt_" + hiop+".pdf");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(pdfFile);
                int totalSize = urlConnection.getContentLength();
                publishProgress(70);
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while ((bufferLength = inputStream.read(buffer)) > 0) {
                    fileOutputStream.write(buffer, 0, bufferLength);
                }

                for (int i = 1; i < 101; i++) {
                    publishProgress(i);
                }

                fileOutputStream.close();
                l = 1L;

            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return l;
        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progress.setProgress(percent[0]);
        }
        protected void onPostExecute(Long result) {

            progress.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
//                        showToast("success");
//                        showToast("Check M-Parivahan folder in directory for HTML");
//                        showToast("Back press to go home");
                        alertDialogPostReport("1. PDF Successfully downloaded check M-Parivahan folder in Directory or Notification");
                    }//public void run() {
                });
            }
            else
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
                        showToast("failure");
                    }//public void run() {
                });

            }
        }

    }

    public static class FileDownloader {

        private static final int  MEGABYTE = 1024 * 1024;
        public  static void downloadFile(String fileUrl, File directory){
            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(directory);
                int totalSize = urlConnection.getContentLength();

                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onStop()
    {
        super.onStop();
        progressDialog.dismiss();
    }

    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            getActivity().findViewById(R.id.start).setEnabled(true);
//            hideProgress();

//            alertDialogPostReport();
//            showToast("press back button to go home");


        }
    };

    BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

        }
    };

    BroadcastReceiver onCompleteDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {

        }
    };

    BroadcastReceiver onNotificationClickDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {


        }
    };

    public void alertDialogPostReport(String s)
    {
            final String[] items = {s,"2. Press ok to go Home.","3. Press stay for further activity."
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
             builder.setTitle("M-Parivahan ");
            builder.setItems(items, null);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment")==null)
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, HomeFragment.newInstance("1", "1"), "HomeFragment").commit();
                }
            });
            builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
//                    if(getActivity().getSupportFragmentManager().findFragmentByTag("HomeFragment")==null)
//                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home, HomeFragment.newInstance("1", "1"), "HomeFragment").commit();
                }
            });
            builder.show();

    }

    public void sendMessage(String s)
    {
        new sendFormMsg(getActivity()).execute();
        new sendRtoMsg(getActivity(),s).execute();
    }

    private class sendRtoMsg extends AsyncTask<Void, Integer, Integer> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;
        private String message;

        public sendRtoMsg(Context c,String message) {
            this.context = c;
            this.message = message;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int l=0;
            try
            {
                JSONObject jsonObjectData= new JSONObject(jsonString);
                URL url = new URL("http://210.210.26.40/newsendsms/push_sms_new.php");


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("E,dd/MM/yy");
                Long aLong = jsonObjectData.getLong("slotDate");
                calendar.setTimeInMillis(aLong);
                dateFormat.format(calendar.getTime());
                System.out.println(dateFormat.format(calendar.getTime()));
//                String s ="rtocode="+jsonObjectData.get("rtocodeReal")+"&mobile="+jsonObjectData.get("moblie")+"&msg=Thanks for using M-Parivahan, your application no "+jsonObjectData.getLong("applicantNum")+" and Receipt No N/A. Date of appointment "+dateFormat.format(calendar.getTime())+" and Time "+jsonObjectData.get("slotTime");
                String s ="user=pmtkc&pwd=pmtkc&from=TPTDEP&to="+jsonObjectData.get("moblie")+"&msg="+message;
                System.out.println(s);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();


                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print(responseCode);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return l;
        }

        protected void onProgressUpdate(Integer... percent) {
            progress.setProgress(percent[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {


        }
    }

    private class sendFormMsg extends AsyncTask<Void, Integer, Integer> {

        private final Context context;
        private ProgressDialog progress;
        private static final int  MEGABYTE = 1024 * 1024;

        public sendFormMsg(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int l=0;
            try
            {
                JSONObject jsonObjectData= new JSONObject(jsonString);
//                URL url = new URL(" http://103.27.233.206/sendsms/");
                URL url = new URL("http://210.210.26.40/newsendsms/push_sms_new.php");


                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat dateFormat = new SimpleDateFormat("E,dd/MM/yy");
                Long aLong = jsonObjectData.getLong("slotDate");
                calendar.setTimeInMillis(aLong);
                dateFormat.format(calendar.getTime());
                System.out.println(dateFormat.format(calendar.getTime()));
//                String s ="rtocode="+jsonObjectData.get("rtocode")+"&msg=Thanks for using M-Parivahan, your application no "+sharedpreferences.getString("receiptNum","")+". Date of appointment "+dateFormat.format(calendar.getTime())+" and Time "+jsonObjectData.get("slotTime")+" &mobile="+jsonObjectData.get("moblie");
                String s ="user=pmtkc&pwd=pmtkc&from=TPTDEP&to="+jsonObjectData.get("moblie")+"&msg=Thanks for using M-Parivahan, your application no "+appNumber+" and Receipt No N/A. Date of appointment "+dateFormat.format(calendar.getTime())+" and Time "+jsonObjectData.get("slotTime");
                System.out.println(s);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("Content-Type", "application/json");
//                connection.setRequestProperty("Accept", "application/json");
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print(responseCode);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return l;
        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progress.setProgress(percent[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {


        }
    }

    private class getAddress extends AsyncTask<Void, Integer, Integer> {

        private final Context context;

        private static final int  MEGABYTE = 1024 * 1024;

        public getAddress(Context c) {
            this.context = c;
        }

        protected void onPreExecute() {
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... params)
        {
            int l=0;
            try
            {

                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/sendsms/index.php");

                String s="rtocode="+rtoCode;

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print(responseCode);
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();
                String reponseMsg=responseOutput.toString();
                JSONObject jsonObject = new JSONObject(reponseMsg);
                msg=jsonObject.getString("Message");

                return 1;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 0;
            } catch (IOException e) {
                e.printStackTrace();
                return 0;
            } catch (JSONException e) {
                e.printStackTrace();
                return 0;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return 0;
            }


        }

        protected void onProgressUpdate(Integer... percent) {

        }

        @Override
        protected void onPostExecute(Integer result) {
            if(result==1)
            {
                progressDialog.hide();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        sendMessage(msg);
                    }
                });
            }
        }
    }
}

