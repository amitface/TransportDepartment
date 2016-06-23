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

import com.converge.transportdepartment.Utility.MarshMallowPermission;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.Date;


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

    ProgressDialog progressDialog;

    public static final String mypreference = "mypref";
    private SharedPreferences sharedpreferences;

    private OnFragmentInteractionListener mListener;
    private long lastDownload = -1L;
    private static String emailToSend;

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
//        IntentFilter filter = new IntentFilter();
//        filter.addAction(CheckNuevosAvisosIntentService.ACTION_PROGRESO);
//        filter.addAction(CheckNuevosAvisosIntentService.ACTION_FIN);
//        getActivity().registerReceiver(rcv, filter);

        mgr=(DownloadManager)getActivity().getSystemService(getActivity().DOWNLOAD_SERVICE);
        getActivity().registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
////        getActivity().registerReceiver(onNotificationClick,
////                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
//        getActivity().registerReceiver(onCompleteDownload,
//                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
////        getActivity().registerReceiver(onNotificationClickDownload,
////                new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(onComplete);
//        getActivity().unregisterReceiver(onNotificationClick);
//        getActivity().unregisterReceiver(onCompleteDownload);
//        getActivity().unregisterReceiver(onNotificationClickDownload);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_payment_successfull, container, false);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);

        TextView textView1 = (TextView) view.findViewById(R.id.textView10);
        TextView textView2 = (TextView) view.findViewById(R.id.textView11);

        final EditText editText = (EditText) view.findViewById(R.id.emailToSend);
        textView1.setText("Please note Reference Number");
        textView2.setText(sharedpreferences.getString("receiptNum",""));


        ImageView buttonEmail = (ImageView) view.findViewById(R.id.buttonEmail);
//        ImageView openForm =  (ImageView) view.findViewById(R.id.openForm);
//        ImageView openReceipt =  (ImageView) view.findViewById(R.id.openReceipt);

        ImageView buttonDownload = (ImageView) view.findViewById(R.id.buttonDownload);
        ImageView saveReceipt = (ImageView) view.findViewById(R.id.saveReceipt);
        try {

            if(new MarshMallowPermission(getActivity()).checkPermissionForExternalStorage()) {
//                openForm.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DownloadFile(getActivity()).execute();
//                    }
//                });
                buttonDownload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       new DownloadFile(getActivity()).execute();
//                        downloadPdf();
//                        downloadPdfForm();
                    }
                });
                buttonEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emailToSend=editText.getText().toString();
                        new SendMail(getActivity()).execute();
                    }
                });
//                openReceipt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        new DownloadReceipt(getActivity()).execute();
//                    }
//                });
                saveReceipt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        new DownloadReceipt(getActivity()).execute();
                        downloadPdf();
                    }
                });
            }
            else
            {
                new MarshMallowPermission(getActivity()).requestPermissionForExternalStorage();
            }
        }catch (Exception e)
        {
            showToast("error");
        }



        return view;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

        private void showProgress()
        {
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setTitle("Downloading");
            progressDialog.setMessage("please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
        }
    private void hideProgress()
    {
        progressDialog.hide();
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

    public void downloadPdf() {
//        showProgress();
        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan/LL_Cash_Receipt.php?referenceId="+sharedpreferences.getString("receiptNum",""));
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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Receipt" + System.currentTimeMillis() + ".pdf");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue a new download and same the referenceId
        lastDownload = mgr.enqueue(request);
    }

    public void downloadPdfForm() {
        Uri Download_Uri = Uri.parse("http://103.27.233.206/M-Parivahan-Odisha/LL_Application.php?referenceId="+sharedpreferences.getString("receiptNum",""));
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
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "Form" + System.currentTimeMillis() + ".html");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //Enqueue a new download and same the referenceId
        lastDownload = mgr.enqueue(request);
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
            progressSendMail.setCancelable(false);
            progressSendMail.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressSendMail.setProgress(0);
            progressSendMail.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            try{
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
                        showToast("Dowloading");
                    }//public void run() {
                });

                int  MEGABYTE = 1024 * 1024;
                URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/send_mail.php");
                String s="referenceId=" +sharedpreferences.getString("receiptNum","")+
                        "&email="+emailToSend;

                HttpURLConnection connection = (HttpURLConnection) email.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());

                dStream.writeBytes(s);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();

                if(responseCode==200 || responseCode==201) {
                    for (int i = 1; i < 101; i++) {
                        publishProgress(i);
                    }
                }
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

            progressSendMail.dismiss();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Your code to run in GUI thread here
                        showToast("Email sent");
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
                        alertDialogPostReport();
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
                        alertDialogPostReport();
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
//        getActivity().unregisterReceiver(onComplete);
//        getActivity().unregisterReceiver(onNotificationClick);
        super.onStop();
    }

    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            getActivity().findViewById(R.id.start).setEnabled(true);
//            hideProgress();

//            alertDialogPostReport();
//            showToast("press back button to go home");
            alertDialogPostReport();
        }
    };

    BroadcastReceiver onNotificationClick=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            Toast.makeText(ctxt, "Check Notification!", Toast.LENGTH_SHORT).show();
        }
    };

    BroadcastReceiver onCompleteDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            getActivity().findViewById(R.id.start).setEnabled(true);
//            Toast.makeText(getActivity(),"complete",Toast.LENGTH_SHORT).show();
        }
    };

    BroadcastReceiver onNotificationClickDownload=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
//            Toast.makeText(ctxt, "Check Notification!", Toast.LENGTH_SHORT).show();

        }
    };

    public void alertDialogPostReport()
    {
            final CharSequence[] items = {" PDF Successfully downloaded check M-Parivahan folder in Directory. Press ok to go Home.  Press stay for further activity."
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("Steps to follow ");
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
}

