package com.converge.transportdepartment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class Home extends AppCompatActivity
        implements View.OnClickListener,DownloadPDF.OnFragmentInteractionListener,PersonalDetails.OnFragmentInteractionListener, ConfirmAndPay.OnFragmentInteractionListener, SelectSchedule.OnFragmentInteractionListener,  CheckStatus.OnFragmentInteractionListener, PaymentSuccessfull.OnFragmentInteractionListener, PayablePayment.OnFragmentInteractionListener, ReadInstructionFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener, LicenseApplication.OnFragmentInteractionListener {

    private FragmentTabHost mTabHost;
    private LicenseApplication.OnFragmentInteractionListener mLicenseApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        addDynamicFragment();
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        switch (view.getId())
        {
            case R.id.textViewApply:
//                    initView();
                vibrate();
                 fragment = LicenseApplication.newInstance("0","2");

                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment,"App1").commit();
                break;
            case R.id.imageViewApply:
                vibrate();
                 fragment = LicenseApplication.newInstance("0","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment,"App1").commit();
                break;
            case R.id.textViewReadInstructions:
                vibrate();
                fragment = ReadInstructionFragment.newInstance("0","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.imageViewReadInstructions:
                vibrate();
                fragment = ReadInstructionFragment.newInstance("0","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonReadBack:
                vibrate();
                fragment = HomeFragment.newInstance("1","1");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonReadNext:
                vibrate();
                fragment = LicenseApplication.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.textViewDownloadApplication:
                vibrate();
                replaceFragment(DownloadPDF.newInstance("1","1"));
                break;
            case R.id.imageViewDownloadApplication:
                vibrate();
                replaceFragment(DownloadPDF.newInstance("1","1"));
                break;
            case R.id.textViewCheckStatus:
                vibrate();
                replaceFragment(CheckStatus.newInstance("1","1"));
                break;
            case R.id.imageViewCheckStatus:
                vibrate();
                replaceFragment(CheckStatus.newInstance("1","1"));
                break;

            /*Select Applicaiton Type */
            case R.id.buttonNextSelectApplication:
                vibrate();
                replaceFragment(LicenseApplication.newInstance("1","1"));
                break;

            /*Select Schedule */
            case R.id.buttonSelectSchedule:
                replaceFragment(LicenseApplication.newInstance("2","1"));
                break;
            case R.id.buttonBackSelectSchedule:
                replaceFragment(LicenseApplication.newInstance("0","1"));
                break;

            /*Personal Details */
            case R.id.buttonBackPersonalDetail:
                replaceFragment(LicenseApplication.newInstance("1","1"));
                break;
            case R.id.buttonNextPersonalDetail:
                replaceFragment(LicenseApplication.newInstance("3","1"));
                break;

            /*Confirm And Pay */
            case R.id.buttonBackConfirmAndPay:
                replaceFragment(LicenseApplication.newInstance("2","1"));
                break;
            case R.id.button_confirm_and_pay:
                replaceFragment(PayablePayment.newInstance("1","2"));
                break;

            /*Pay Now */
            case R.id.buttonPayNow:
                replaceFragment(PaymentSuccessfull.newInstance("1","2"));
                break;
            default : break;
        }
    }

    private void addDynamicFragment() {
        // TODO Auto-generated method stub
        // creating instance of the HelloWorldFragment.
        Fragment fg = HomeFragment.newInstance("1","1");
        // adding fragment to relative layout by using layout id
        getSupportFragmentManager().beginTransaction().add(R.id.content_home, fg).commit();
    }


    @Override
    public void onBackPressed() {
        try{


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else  if(getSupportFragmentManager().findFragmentByTag("App1")!=null && getSupportFragmentManager().findFragmentByTag("App1").isVisible()) {

            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,HomeFragment.newInstance("1","2"),"HomeFragment").commit();
        } else if(getSupportFragmentManager().findFragmentByTag("HomeFragment")!= null && getSupportFragmentManager().findFragmentByTag("HomeFragment").isVisible())
         {
            super.onBackPressed();
        }
            else
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,HomeFragment.newInstance("1","2"),"HomeFragment").commit();

        }catch (Exception e)
        {

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment;
        if(id == R.id.nav_home)
        {
            vibrate();
            fragment = HomeFragment.newInstance("1","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();

        }
        else if (id == R.id.nav_apply_license) {
            // Handle the camera action
            vibrate();
            fragment = LicenseApplication.newInstance("0","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment,"App1").commit();
        } else if (id == R.id.nav_check_status) {
            vibrate();
            replaceFragment(CheckStatus.newInstance("1","1"));

        } else if (id == R.id.nav_read_instruction) {
            vibrate();
            fragment = ReadInstructionFragment.newInstance("1","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
        } else if (id == R.id.nav_download) {
            vibrate();
            replaceFragment(DownloadPDF.newInstance("1","1"));
        } else if (id == R.id.nav_exit) {
                finish();
        }

//
// else if (id == R.id.nav_send) {
//}

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void vibrate()
    {
        // Get instance of Vibrator from current Context
        Vibrator mVibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// Vibrate for 300 milliseconds
        mVibrator.vibrate(20);
    }

    private  void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
    }

    private void showToast(String s)
    {
        Toast.makeText(this, s,Toast.LENGTH_LONG).show();
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
            showToast(uri.toString());
    }

//
//    @Override
//    public void onFragmentInteraction(int id) {
//        showToast("id :-"+id);
//    }

    @Override
    public void onFragmentInteraction(View view) {

    }
}



/**
 * A placeholder fragment containing a simple view.
 */
//public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
//    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */

//    public static PlaceholderFragment newInstance(int sectionNumber) {
//        PlaceholderFragment fragment = new PlaceholderFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public PlaceholderFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        return rootView;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((Home) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }
//}
//
//public static class InfoDirectFragment extends Fragment {
//    /**
//     * The fragment argument representing the section number for this
//     * fragment.
//     */
//    private static final String ARG_SECTION_NUMBER = "section_number";
//
//    /**
//     * Returns a new instance of this fragment for the given section
//     * number.
//     */
//    public static InfoDirectFragment newInstance(int sectionNumber) {
//        InfoDirectFragment fragment = new InfoDirectFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public InfoDirectFragment() {
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_infodirect, container, false);
//        return rootView;
//    }
//
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        ((Home) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
//    }
//}


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.home, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
