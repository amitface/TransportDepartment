package com.converge.transportdepartment.task;

import android.content.Context;

import com.converge.transportdepartment.network.MakeNetworkRequest;
import com.converge.transportdepartment.network.NetworkChecker;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arpit Sinha on 2/1/2016.
 */
public class GetRefIdAndTime implements NetworkChecker.NetworkListener, MakeNetworkRequest.ProcessCompletionListener {

    Context mContext;
    NetworkChecker networkChecker;

    String mTrendId, mThought, mUserId, mUniqueKey;

    public GetRefIdAndTime(Context context) {
        mContext = context;
        networkChecker = new NetworkChecker(mContext);
        networkChecker.setNetworkListener(this);
    }

    public void initializeGetRefIdAndTime() {
        networkChecker.CheckInternetAvailabilityTask(mContext);
    }

    @Override
    public void onNetworkResult(boolean result) {
        if(result) {
            List<NameValuePair> params = new ArrayList<>();
//            params.add(new BasicNameValuePair("unique_key", Keys.unique_key));
//            params.add(new BasicNameValuePair("trend_Id", mTrendId));
//            params.add(new BasicNameValuePair("user_Id", mUserId));
//            params.add(new BasicNameValuePair("thought_discription", mThought));

            MakeNetworkRequest getRefIdAndTime = new MakeNetworkRequest(
                    mContext,
                    false,
                    "Please wait....",
                    "http://103.27.233.206/M-Parivahan-Odisha/unique_random.php",
                    params
            );
            getRefIdAndTime.setProcessCompletionListener(this);
            getRefIdAndTime.execute();

        } else {
            networkChecker.showDialogBoxForUnavailableInternetService(mContext,"Internet connection not available. Please try again later.",false);
        }
    }

    @Override
    public void resultOnProcessCompletion(String result) {
        if(result.equals("")) {
            networkChecker.showDialogBoxForUnavailableInternetService(
                    mContext,
                    "Internet connection not available. Please try again later.",
                    false
            );
        } else {
            mTaskCompleteListener.onGetRefIdAndTimeCompleteResult(result);
        }
    }

    GetRefIdAndTimeResultListener mTaskCompleteListener;

    public void setGetRefIdAndTimeResultListener(GetRefIdAndTimeResultListener taskCompleteListener){
        this.mTaskCompleteListener = taskCompleteListener;
    }

    public interface GetRefIdAndTimeResultListener {
        void onGetRefIdAndTimeCompleteResult(String result);
    }

}
