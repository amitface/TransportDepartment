package com.converge.transportdepartment.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.apache.http.NameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Arpit Sinha on 07-Jan-16.
 */
public class MakeNetworkRequest extends AsyncTask<Void, Void, String> {

    private String TAG = MakeNetworkRequest.class.getSimpleName();

    ProcessCompletionListener mProcessCompletionListener;

    Context mContext;
    Boolean mShowDialog;
    String mUrl;
    String mMsgForLoadingDialog;
    private JSONParser jsonParser = null;
    private ProgressDialog pDialog;


    List<NameValuePair> mParams;

    HashMap<String, String> hashMapParams;
    HashMap<String, File> hashMapFileParams;

    int PROCESS_REQUEST_WITH_OLD_TYPE_METHODS = 1;
    int PROCESS_REQUEST_WITH_NEW_TYPE_METHODS = 2;

    int requestStyle;

    public MakeNetworkRequest(Context context, String url, List<NameValuePair> params) {
        mContext = context;
        mShowDialog = true;
        mMsgForLoadingDialog = "Please wait, Loading....";
//        mCustomDialog = new CustomDialog(context);
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(mMsgForLoadingDialog);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
//        pDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//        pDialog.setMessage(mMsgForLoadingDialog);
        jsonParser = new JSONParser();
        mUrl = url;
        mParams = params;
    }

    public MakeNetworkRequest(Context context,
                              Boolean showDialog,
                              String msgForLoader,
                              String url,
                              List<NameValuePair> params) {
        mContext = context;
        mShowDialog = showDialog;
        mMsgForLoadingDialog = msgForLoader;
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.setMessage(mMsgForLoadingDialog);

//        mCustomDialog = new CustomDialog(context);
        //dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        //dialog.setMessage(msg);
//        mCustomDialog.setCancelable(false);
//        mCustomDialog.setCanceledOnTouchOutside(false);
//        pDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//        pDialog.setMessage(mMsgForLoadingDialog);
        jsonParser = new JSONParser();
        mUrl = url;
        mParams = params;
        requestStyle = PROCESS_REQUEST_WITH_OLD_TYPE_METHODS;
    }

    public MakeNetworkRequest(Context context,
                              Boolean showDialog,
                              String msgForLoader,
                              String url,
                              HashMap<String, String> params) {
        mContext = context;
        mShowDialog = showDialog;
        mMsgForLoadingDialog = msgForLoader;
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
//        mCustomDialog = new CustomDialog(context);
        //dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        //dialog.setMessage(msg);
//        mCustomDialog.setCancelable(false);
//        mCustomDialog.setCanceledOnTouchOutside(false);
//        pDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//        pDialog.setMessage(mMsgForLoadingDialog);
        jsonParser = new JSONParser();
        mUrl = url;
        hashMapParams = params;
        hashMapFileParams = null;
        requestStyle = PROCESS_REQUEST_WITH_NEW_TYPE_METHODS;
    }

    public MakeNetworkRequest(Context context,
                              Boolean showDialog,
                              String msgForLoader,
                              String url,
                              HashMap<String, String> params,
                              HashMap<String, File> fileParams) {

        mContext = context;
        mShowDialog = showDialog;
        mMsgForLoadingDialog = msgForLoader;
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
//        mCustomDialog = new CustomDialog(context);
        //dialog = new ProgressDialog(context);
        //dialog.setTitle(title);
        //dialog.setMessage(msg);
//        mCustomDialog.setCancelable(false);
//        mCustomDialog.setCanceledOnTouchOutside(false);
//        pDialog = new ProgressDialog(mContext, R.style.AppTheme_Dark_Dialog);
//        pDialog.setMessage(mMsgForLoadingDialog);
        jsonParser = new JSONParser();
        mUrl = url;
        hashMapParams = params;
        hashMapFileParams = fileParams;
        requestStyle = PROCESS_REQUEST_WITH_NEW_TYPE_METHODS;
    }

    public void setProcessCompletionListener(ProcessCompletionListener processCompletionListener){
        this.mProcessCompletionListener = processCompletionListener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if(jsonParser == null)
            jsonParser = new JSONParser();
        if(mShowDialog)
            pDialog.show();
    }

    @Override
    protected String doInBackground(Void... param) {
        String result = null;
//        Miscellaneous.showLog(TAG, mUrl);
        try{
            if(requestStyle == PROCESS_REQUEST_WITH_OLD_TYPE_METHODS) {
//                Miscellaneous.showLog(TAG, mParams.toString());
                JSONObject jsonObject = jsonParser.makeHttpRequest(mUrl, "POST", mParams);
                result = jsonObject.toString();
            } else if(requestStyle == PROCESS_REQUEST_WITH_NEW_TYPE_METHODS) {
//                Miscellaneous.showLog(TAG, "Attempting to send user Data with hash map and files may be included.");
                JSONObject jsonObject = jsonParser.makeHttpRequest(mUrl, "POST", hashMapParams, hashMapFileParams);
                result = jsonObject.toString();
            }
        } catch (Exception e) {

            if(pDialog.isShowing())
                pDialog.dismiss();
//            Log.e(TAG,e.getMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        if(result==null)
            result = "";
//        Miscellaneous.showLog(TAG, result);
        if(pDialog.isShowing())
            pDialog.dismiss();
        mProcessCompletionListener.resultOnProcessCompletion(result);
    }

    public interface ProcessCompletionListener {
        void resultOnProcessCompletion(String result);
    }
}
