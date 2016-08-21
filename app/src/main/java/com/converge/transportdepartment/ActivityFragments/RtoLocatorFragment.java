package com.converge.transportdepartment.ActivityFragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.converge.transportdepartment.Adapter.RtoLocatorAdapter;
import com.converge.transportdepartment.Been.RtoLocatorBeen;
import com.converge.transportdepartment.R;
import com.converge.transportdepartment.Utility.ConValidation;
import com.converge.transportdepartment.Utility.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RtoLocatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RtoLocatorFragment extends Fragment implements View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private RtoLocatorAdapter mAdapter;
    private List<RtoLocatorBeen> rtoList = new ArrayList<>();
    private String [] rtoArray,rtoLat,rtoLong;
    private String s;


    public RtoLocatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RtoLocatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RtoLocatorFragment newInstance(String param1, String param2) {
        RtoLocatorFragment fragment = new RtoLocatorFragment();
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
        View view = inflater.inflate(R.layout.fragment_rto_locator2, container, false);
        rtoArray=getResources().getStringArray(R.array.Rto_Location_Name);
        rtoLat=getResources().getStringArray(R.array.Rto_Lat);
        rtoLong=getResources().getStringArray(R.array.Rto_Long);

        System.out.println("rtoLat  =="+ rtoLat);
        System.out.println("rtoLong  =="+ rtoLong);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new RtoLocatorAdapter(rtoList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        prepareRtoData();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                if(ConValidation.isNetworkAvailable(getActivity())) {

                    if (!rtoLat[position].equals("nodata")) {
                        try {
                            String geoUri = "http://maps.google.com/maps?q=loc:" + Double.parseDouble(rtoLat[position]) + "," + Double.parseDouble(rtoLong[position]);
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
                            startActivity(intent);

                            System.out.println("geoUri  ==" + Uri.parse(geoUri));

                            System.out.println("rtoLat[position]  ==" + rtoLat[position]);
                            System.out.println("rtoLong[position]  ==" + rtoLong[position]);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
                        }

                    }
                }
                else
                {
                    Toast.makeText(getActivity(),"Please check network connection...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

//

        return view;
    }

    private void prepareRtoData() {
        RtoLocatorBeen rtoLocatorBeen ;
        List<String> list= new ArrayList(Arrays.asList(rtoArray));
        for (String s: list ) {
            rtoLocatorBeen = new RtoLocatorBeen(s);
            rtoList.add(rtoLocatorBeen);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private RtoLocatorFragment.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final RtoLocatorFragment.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
