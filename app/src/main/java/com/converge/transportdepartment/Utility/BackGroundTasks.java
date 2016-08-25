package com.converge.transportdepartment.Utility;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by converge on 17/6/16.
 */
public class BackGroundTasks {




    public static  void sendMail(Long appNumber)
    {
        new sendBackgroundMail(appNumber).execute();
    }
    private static class sendBackgroundMail extends AsyncTask<String, Void, Integer> {



        private  Long appNumber;

        public sendBackgroundMail(Long appNumber) {
            this.appNumber = appNumber;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Integer doInBackground(String... params) {
            try {

//                URL email = new URL("http://103.27.233.206/M-Parivahan-Odisha/LL_noreceipt.php");
                URL email = new URL(Links.noReceiptMail);
//                String s1 = "referenceId=" + appNumber +
//                        "&email=amit.choudhary@cnvg.in";

                String s1 = "referenceId=" + appNumber;

                HttpURLConnection connection1 = (HttpURLConnection) email.openConnection();

                connection1.setRequestMethod("POST");
                connection1.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection1.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection1.setConnectTimeout(25000);
                connection1.setDoInput(true);
                connection1.setDoOutput(true);
                DataOutputStream dStream1 = new DataOutputStream(connection1.getOutputStream());

                dStream1.writeBytes(s1);
                dStream1.flush();
                dStream1.close();
                int responseCode = connection1.getResponseCode();
                System.out.println("Response code for mail"+responseCode);
            } catch (ProtocolException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();

            }
            return null;
        }

    }

}
