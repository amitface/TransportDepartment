package com.converge.transportdepartment.Fragments;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by converge on 21/8/16.
 */
public class PaymentReport {

    String transId, status, amount;

    public PaymentReport(String transId, String status, String amount)
    {
        this.transId = transId;
        this.status = status;
        this.amount = amount;
    }

    public void savePayment()
    {
        new savePaymentToServer().execute();
    }

    //For Converge server
    private class savePaymentToServer extends AsyncTask<Void, Integer, Long>
    {



        public savePaymentToServer() {

        }

        protected void onPreExecute() {

        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{
                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/payment_log.php");
                connection = (HttpURLConnection) url.openConnection();

                String json = "trans_id="+transId+"&status="+status+"&payment="+amount;

                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(20000);
                connection.setReadTimeout(20000);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                DataOutputStream dStream = new DataOutputStream(connection.getOutputStream());
                dStream.writeBytes(json);
                dStream.flush();
                dStream.close();
                int responseCode = connection.getResponseCode();
                System.out.print("ResponseCode ====  "+responseCode+"\nRespone === " +connection.getResponseMessage()+"\n");

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                br.close();
//                responseOutput.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());
                System.out.println("Converge Server "+responseOutput.toString());
                return 1L;
            }catch (FileNotFoundException e) {
                e.printStackTrace();
                return 0L;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return 0L;
            } catch (IOException e) {
                e.printStackTrace();
                return 0L;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return 0L;
            }

        }

        protected void onProgressUpdate(Integer... percent) {

        }

        protected void onPostExecute(Long result) {


        }
    }
}
