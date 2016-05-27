package com.converge.transportdepartment;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class Home extends AppCompatActivity
        implements View.OnClickListener,PaymentSuccessfull.OnFragmentInteractionListener, PayablePayment.OnFragmentInteractionListener, ReadInstructionFragment.OnFragmentInteractionListener, NavigationView.OnNavigationItemSelectedListener,HomeFragment.OnFragmentInteractionListener,LicenseApplication.OnFragmentInteractionListener,PersonalDetails.OnFragmentInteractionListener, ConfirmAndPay.OnFragmentInteractionListener, SelectSchedule.OnFragmentInteractionListener, SelectApplicationType.OnFragmentInteractionListener {

    private FragmentTabHost mTabHost;

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
                 fragment = LicenseApplication.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.imageViewApply:
                 fragment = LicenseApplication.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.textViewReadInstructions:
                fragment = ReadInstructionFragment.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.imageViewReadInstructions:
                fragment = ReadInstructionFragment.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonReadBack:
                fragment = HomeFragment.newInstance("1","1");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonReadNext:
                fragment = LicenseApplication.newInstance("1","2");
                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonSelectApplication:

//                    LicenseApplication.
                break;
            case R.id.buttonConfirm:
                replaceFragment(PayablePayment.newInstance("1","2"));
//                getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
                break;
            case R.id.buttonPayNow:
                replaceFragment(PaymentSuccessfull.newInstance("1","2"));
                break;
            default : break;
        }
    }

    private  void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
    }
//    private void initView() {
//
//
//        mTabHost = new FragmentTabHost(this);
//        mTabHost.setup(this, getSupportFragmentManager(), R.id.content_home);
//
//        mTabHost.addTab(mTabHost.newTabSpec("Type").setIndicator(getTabIndicator(mTabHost.getContext(), android.R.drawable.star_on)),
//                SelectApplicationType.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("schedule").setIndicator("Select Schedule"),
//                SelectSchedule.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("details").setIndicator("Personal Details"),
//                PersonalDetails.class, null);
//        mTabHost.addTab(mTabHost.newTabSpec("pay").setIndicator("Confirm and Pay"),
//                ConfirmAndPay.class, null);
//            /* Increase tab height programatically
//             * tabs.getTabWidget().getChildAt(1).getLayoutParams().height = 150;
//             */
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.content_home,mTabHost).commit();
//        for (int i = 0; i < mTabHost.getTabWidget().getChildCount(); i++) {
//            final TextView tv = (TextView) mTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
//            if (tv == null)
//                continue;
//            else
//                tv.setTextSize(10);
//
//        }
//
//    }
//
//    private View getTabIndicator(Context context, int icon) {
//        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
//        ImageView iv = (ImageView) view.findViewById(R.id.imageView);
//        iv.setImageResource(icon);
//        return view;
//    }
    private void addDynamicFragment() {
        // TODO Auto-generated method stub
        // creating instance of the HelloWorldFragment.
        Fragment fg = HomeFragment.newInstance("1","1");
        // adding fragment to relative layout by using layout id
        getSupportFragmentManager().beginTransaction().add(R.id.content_home, fg).commit();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
            fragment = HomeFragment.newInstance("1","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();

        }
        else if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = LicenseApplication.newInstance("1","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {
            fragment = ReadInstructionFragment.newInstance("1","2");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_home,fragment).commit();
        } else if (id == R.id.nav_manage) {

//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

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
