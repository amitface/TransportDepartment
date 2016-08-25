package com.converge.transportdepartment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.converge.transportdepartment.ActivityFragments.SuperAwesomeCardFragment;
import com.converge.transportdepartment.Utility.ConValidation;
import com.converge.transportdepartment.Utility.SlotData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectSchedule extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match

    public static final String PREFS_NAME = "MyTransportFile";
    SharedPreferences sharedpreferences;


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String PGInfo = "PgInfo";
    private static final String SLOTNUMBER = "SLOTNUMBER";
    private static final String SLOTDATE = "SLOTDATE";
    private static  Integer statusServer =0;

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private MyPagerAdapter adapter;
    private ProgressDialog progress;
    private String rtoCode=null;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9, checkBox10;
    private CheckBox checkBox11, checkBox12, checkBox13, checkBox14, checkBox15, checkBox16, checkBox17, checkBox18, checkBox19, checkBox20;
    private CheckBox checkBox21, checkBox22, checkBox23, checkBox24;
    private CheckBox checkBoxLastClicked;
    private int lastCheckBoxId = 0;
    private int currentChecked = 0;
    private String jsonData, jsonDataSaveSlot;
    //Start is 12/08/2016 & format dd/mm/yyyy
    private Long [] dateSetArray= {1471824000000L,1471910400000L,1471996800000L,1472083200000L,1472169600000L,1472256000000L,1472342400000L,1472428800000L,1472515200000L,1472601600000L,1472688000000L,1472774400000L,1472860800000L,1472947200000L,1473033600000L,1473120000000L,1473206400000L,1473292800000L,1473379200000L,1473552000000L,1473638400000L,1473724800000L,1473811200000L,1473897600000L,1473984000000L,1474070400000L,1474156800000L,1474243200000L,1474329600000L,1474416000000L};
    private OnFragmentInteractionListener mListener;
    private static final String CheckBoxSchedule = "currentCheckBox";
    private static final String mypreference="mypref";
    public static volatile Integer slotNumber=-1;
    public static volatile Long slotDate=-1L;
    public static volatile String slotTime=null;

    private Button buttonRefresh;
    private Long appNumber;

    public SelectSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectSchedule newInstance(String param1, String param2) {
        SelectSchedule fragment = new SelectSchedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedpreferences = getActivity().getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_select_schedule, container, false);
        hideKeyboard(getContext());
//        sharedpreferences;
        pager = (ViewPager) view.findViewById(R.id.pager);
        buttonRefresh = (Button) view.findViewById(R.id.buttonRefreshSelectSchedule);
        buttonRefresh.setOnClickListener(this);

        try {
            JSONObject jsonObject = new JSONObject(sharedpreferences.getString(PGInfo,""));
            rtoCode = jsonObject.getString("rtocodeReal");
            appNumber = jsonObject.getLong("applicantNum");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Bind the tabs to the ViewPager
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
//        tabs.setViewPager(pager);

        initalize(view);
        Calendar c= Calendar.getInstance();

        Log.d("**slotDt**","Current Date: " + c.getTimeInMillis());
        if(ConValidation.isNetworkAvailable(getActivity()))
            getSlot();
        else
            Toast.makeText(getActivity(),"Please check network connection...",Toast.LENGTH_SHORT).show();
        return view;
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        progress.dismiss();
    }


    private void initalize(View fragmentSchedule) {
        ImageView buttonBack = (ImageView) fragmentSchedule.findViewById(R.id.buttonBackSelectSchedule);
        ImageView buttonNext = (ImageView) fragmentSchedule.findViewById(R.id.buttonNextSelectSchedule);
        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);
     }


    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public class MyPagerAdapter extends FragmentStatePagerAdapter {

        List<SlotData> data;
        List<Long> Title;
        Calendar c ;
        SimpleDateFormat ft;

        public MyPagerAdapter(FragmentManager fm, List<SlotData> posts) {
            super(fm);
            data = new ArrayList<SlotData>(posts);
            Title = new ArrayList<>();
            c = new GregorianCalendar();
            ft= new SimpleDateFormat("dd/MM");
            Title = getUniqueDate();
        }

        private void getDataArray()
        {
            //       Calendar cal = new GregorianCalendar(2016,7,14);
            //       calendar.setTimeInMillis(Long.parseLong(String.valueOf("1473332113073")));
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            Log.d("**slotDate**",""+calendar.getTimeInMillis());
            Long meter = calendar.getTimeInMillis();
        }

        private List<Long> getUniqueDate()
        {
            List<Long> temp = new ArrayList<>();
            for(int j=0;j<dateSetArray.length-1;j++)
            {
                for(int i=0;i<data.size();i++)
                {
                    if(data.get(i)!=null)
                    {
                        if(dateSetArray[j]<=data.get(i).getSlotdate() && data.get(i).getSlotdate()<dateSetArray[j+1])
                        {
                                temp.add(dateSetArray[j]);
                                Log.d("unique Date",""+data.get(i).getSlotdate());
                                if(temp.size()==6)
                                    return temp;
                                break;
                        }
                    }
                }
            }
            return temp;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            c.setTimeInMillis(Title.get(position));
            return ft.format(c.getTime());
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Fragment getItem(int position) {

            return SuperAwesomeCardFragment.newInstance(jsonData,Title.get(position),position);
//            return SuperAwesomeCardFragment.newInstance(position);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void Save() {
//        SharedPreferences.Editor editor = sharedpreferences.edit();
//        editor.putInt(CheckBoxSchedule,currentChecked);
//        editor.commit();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.buttonNextSelectSchedule:
                if(val())
                {
//                    Toast.makeText(getActivity(),"dateSlot= "+slotDate+" || slot Number= "+slotNumber,Toast.LENGTH_SHORT).show();
                    saveSlot(1,"",appNumber);
                }
                break;

            case R.id.buttonBackSelectSchedule:
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("2", "1")).commit();
                break;

            case R.id.buttonRefreshSelectSchedule:
                if(statusServer==0)
                {
                    getSlot();
                }
                else
                {
                    Toast.makeText(getActivity(),"No need to refresh",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private String jsonString() {
        JSONObject js=null;

        try {
            js = new JSONObject(sharedpreferences.getString(PGInfo,""));
            js.put("slotDate",slotDate);
            js.put("slotNumber",slotNumber);
            js.put("slotTime",slotTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e)
        {
            Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_SHORT).show();
        }

        return js.toString();
    }

    private boolean val() {
        if(!ConValidation.isNetworkAvailable(getActivity()))
        {
            Toast.makeText(getActivity(),"No internet avaliable",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(statusServer==0)
        {
            Toast.makeText(getActivity(),"No appointment slot available for selected date,time",Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity(),"Please Refresh Data",Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(slotDate==-1 || slotNumber==-1L || slotTime==null) {
            Toast.makeText(getActivity(),"Please select atleast one slot",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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

    private void getSlot()
    {
//        new scheduleSlot(getActivity()).execute();
        new scheduleSlotServer(getActivity()).execute();
    }

    //for Nic server
    private class scheduleSlot extends AsyncTask<Void, Integer, Long>
    {
        private final Context context;
        private ProgressDialog progressSendMail;

        public scheduleSlot(Context c) {
            this.context = c;
        }
        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Please Wait");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
        HttpURLConnection connection=null;
        try{

//             URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/ApplcntDetails/getApplDet");
               URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/FetchSlotDet/getSltDet");
//                URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/SaveSlotDetServ/insSltDet");

            connection = (HttpURLConnection) url.openConnection();
            //Creating json object.

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("applno", 102532);
            jsonObject.put("dob", "20/08/1984");
            jsonObject.put("servType", "LL");
            jsonObject.put("usrName", "smartchip");
            jsonObject.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
            jsonObject.put("serviceName","LLSlotBook");


            JSONObject jsonObject1 = new JSONObject();

//            jsonObject1.put("dob", "20/02/1983");
            jsonObject1.put("servtype", "LL");
            jsonObject1.put("agentId", "smartchip");
            jsonObject1.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
            jsonObject1.put("serviceName","LLSlotBook");
            jsonObject1.put("rtocode",rtoCode);

            JSONObject jsonObject2 = new JSONObject();


            Calendar cal = new GregorianCalendar(2016,7,20);
            jsonObject2.put("applno", 102526);
            jsonObject2.put("serviceType", "LL");
            jsonObject2.put("agentId", "smartchip");
            jsonObject2.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
            jsonObject2.put("serviceName","LLSlotBook");
            jsonObject2.put("rtocode","GJ01");
            jsonObject2.put("slotDate","1471631400000");
            jsonObject2.put("slotNo",1);

            String json = jsonObject1.toString();
            System.out.println(json);
//            String json = "{\"applno\":102526,\"rtocode\":\"GJ01\",\"slotDate\":1471631400000,\"serviceType\":\"LL\",\"slotNo\":1,\"agentId\":\"smartchip\",\"pwd\":\"3998151263B55EB10F7AE1A974FD036E\",\"serviceName\":\"LLSlotBook\"}";
            connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
            connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(200000);
            connection.setReadTimeout(200000);
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
            System.out.println(responseOutput.toString());
            jsonData=responseOutput.toString();
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
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progress.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statusServer=1;
                        buttonRefresh.setEnabled(false);

                        Gson gson = new Gson();
                        Type listType = new TypeToken<List<SlotData>>(){}.getType();
                        List<SlotData> posts = (List<SlotData>) gson.fromJson(jsonData, listType);

                        pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(),posts));
                        tabs.setViewPager(pager);
                        progress.hide();
                    }
                });
            }
            else
            {
                statusServer=0;
                buttonRefresh.setEnabled(true);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.hide();
                        Toast.makeText(getActivity(),"Cannot Fetch data from Server. Please wait for sometime then refresh",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }

    //For converge server
    private class scheduleSlotServer extends AsyncTask<Void, Integer, Long>
    {
        private final Context context;
        private ProgressDialog progressSendMail;

        public scheduleSlotServer(Context c) {
            this.context = c;
        }
        protected void onPreExecute() {
            progress = new ProgressDialog(this.context);
            progress.setMessage("Please Wait");
            progress.setCancelable(false);
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setProgress(0);
            progress.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{

                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/savetime_slot/fetch_slot.php");

                connection = (HttpURLConnection) url.openConnection();
                //Creating json object.

                String json ="rtocode="+rtoCode;
                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(200000);
                connection.setReadTimeout(200000);
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
                System.out.println(responseOutput.toString());
                jsonData=responseOutput.toString();
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
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progress.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Gson gson = new Gson();
                            Type listType = new TypeToken<List<SlotData>>() {
                            }.getType();
                            List<SlotData> posts = (List<SlotData>) gson.fromJson(jsonData, listType);

                            pager.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager(), posts));
                            tabs.setViewPager(pager);
                            progress.hide();
                            statusServer = 1;
                            buttonRefresh.setEnabled(false);
                        } catch (Exception e) {

                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    progress.hide();
                                    Toast.makeText(getActivity(),"Data Not available",Toast.LENGTH_SHORT).show();
                                    statusServer=0;
                                    buttonRefresh.setEnabled(false);
                                }
                            });
                        }
                    }
                });
            }
            else
            {
                statusServer=0;
                buttonRefresh.setEnabled(true);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.hide();
                        Toast.makeText(getActivity(),"Cannot Fetch data from Server. Please wait for sometime then refresh",Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }
    }


    private void saveSlot(int i, String dob, long l)
    {
        new saveApplicantSlot(getActivity(),i,dob,l).execute();
    }

    //For Nic server
    private class saveApplicantSlot extends AsyncTask<Void, Integer, Long>
    {
        private ProgressDialog progress;
        private final Context context;
        private ProgressDialog progressSendMail;
        private int i;
        private String dob;
        private Long appNum;

        public saveApplicantSlot(Context c, int i, String dob, Long appNum) {
            this.context = c;
            this.i=i;
            this.dob=dob;
            this.appNum=appNum;
        }

        protected void onPreExecute() {
            progressSendMail = new ProgressDialog(this.context);
            progressSendMail.setMessage("Getting appointment slot please wait...");
            progressSendMail.setCancelable(false);
            progressSendMail.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressSendMail.setProgress(0);
            progressSendMail.show();
        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{
                URL url = new URL("http://164.100.148.109:8080/SOWSlotBookServices/rsServices/SaveSlotDetServ/insSltDet");
                connection = (HttpURLConnection) url.openConnection();

                //Creating json object.
                JSONObject jsonObject2 = new JSONObject();

                jsonObject2.put("applno", appNum);
                jsonObject2.put("serviceType", "LL");
                jsonObject2.put("agentId", "smartchip");
                jsonObject2.put("pwd", "3998151263B55EB10F7AE1A974FD036E");
                jsonObject2.put("serviceName","LLSlotBook");
                jsonObject2.put("rtocode",rtoCode);
                jsonObject2.put("slotDate",slotDate);
                jsonObject2.put("slotNo",slotNumber);

                String json = jsonObject2.toString();
                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(30000);
                connection.setReadTimeout(30000);
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
                System.out.println(responseOutput.toString());

                    JSONObject jsonObject = new JSONObject(responseOutput.toString());
                    if(jsonObject.getInt("errorCd")!=0)
                    {
                        if(jsonObject.getInt("errorCd")==-2)
                        {
                            jsonDataSaveSlot = jsonObject.getString("msg");
                            return 2L;
                        }
                        jsonDataSaveSlot = jsonObject.getString("msg");
                        return 0L;
                    }
                    else
                    {
                        jsonDataSaveSlot = jsonObject.getString("msg");
                        return 1L;
                    }
            }catch (FileNotFoundException e) {
                jsonDataSaveSlot =e.toString();
                return 0L;
            } catch (MalformedURLException e) {
                jsonDataSaveSlot =e.toString();
                return 0L;
            } catch (IOException e) {
                jsonDataSaveSlot =e.toString();
                return 0L;
            } catch (JSONException e) {
                jsonDataSaveSlot =e.toString();
                return 0L;
            }
            catch (Exception e)
            {
                jsonDataSaveSlot =e.toString();
                return 0L;
            }

        }

        protected void onProgressUpdate(Integer... percent) {
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progressSendMail.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {
            progressSendMail.hide();
            if(result==1)
            {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity(),jsonDataSaveSlot,Toast.LENGTH_SHORT).show();

                        new saveApplicantSlotToServer(getActivity(),1).execute();
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(PGInfo,jsonString());
                        editor.apply();
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_home,LicenseApplication.newInstance("4", "1")).commit();
                    }
                });
            }
            else if(result==2)
            {

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new saveApplicantSlotToServer(getActivity(),0).execute();
                        Toast.makeText(getActivity(),jsonDataSaveSlot,Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else
            {
//                new saveApplicantSlotToServer(getActivity(),0).execute();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(getActivity(),jsonDataSaveSlot,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    //For Converge server
    private class saveApplicantSlotToServer extends AsyncTask<Void, Integer, Long>
    {
        private ProgressDialog progress;
        private final Context context;
        private ProgressDialog progressSendMail;
        private int i;
        private String dob;
        private Long appNum;

        public saveApplicantSlotToServer(Context c, int i) {
            this.context = c;
            this.i=i;
        }

        protected void onPreExecute() {

        }

        @Override
        protected Long doInBackground(Void... params) {
            HttpURLConnection connection=null;
            try{
                URL url = new URL("http://103.27.233.206/M-Parivahan-Odisha/savetime_slot/assgn_slot.php?");
                connection = (HttpURLConnection) url.openConnection();

                String json = "rtocode="+rtoCode+"&slotNum="+slotNumber+"&slotdate="+slotDate+"&code="+i;

                System.out.println(json);

                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");
//                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(200000);
                connection.setReadTimeout(200000);
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
//        Log.d("ANDRO_ASYNC",Integer.toString(progressInt));
            progressSendMail.setProgress(percent[0]);
        }

        protected void onPostExecute(Long result) {

            if(result==1)
            {

            }
            else
            {

            }
        }
    }

}

