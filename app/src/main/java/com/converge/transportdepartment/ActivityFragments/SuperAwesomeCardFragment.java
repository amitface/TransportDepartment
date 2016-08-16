package com.converge.transportdepartment.ActivityFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.converge.transportdepartment.Adapter.SlotAdapter;
import com.converge.transportdepartment.R;
import com.converge.transportdepartment.SelectSchedule;
import com.converge.transportdepartment.Utility.DividerItemDecoration;
import com.converge.transportdepartment.Utility.SlotData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SuperAwesomeCardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SuperAwesomeCardFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_POSITION = "position";
    private static final String ARG_POSITION2 = "position2";
    private static final String ARG3 = "arg3";

    private String jsonData;
    private Long dateLong;
    private int Id;
    private List<SlotData> data;
    private List<SlotData> dataTrimed;
    private RecyclerView recyclerView;
    private SlotAdapter slotAdapter;
    private static int lastItemClicked=-1;

    private String itemSelected;


    public static SuperAwesomeCardFragment newInstance(String data,Long position,int Id) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putString(ARG_POSITION, data);
        b.putLong(ARG_POSITION2, position);
        b.putInt(ARG3,Id);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonData = getArguments().getString(ARG_POSITION);
        dateLong = getArguments().getLong(ARG_POSITION2);
        Id = getArguments().getInt(ARG3);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_super_awesome_card, container, false);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<SlotData>>(){}.getType();
        data = (List<SlotData>) gson.fromJson(jsonData, listType);
        dataTrimed= new ArrayList<>();

        recyclerView =(RecyclerView) view.findViewById(R.id.slotRecycler);
        slotAdapter = new SlotAdapter(dataTrimed);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(slotAdapter);
        prepareSlotData(dateLong);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new RtoLocatorFragment.ClickListener() {
            @Override
            public void onClick(View view, int position) {
//                Toast.makeText(getActivity(),"Click "+position,Toast.LENGTH_SHORT).show();
                dataTrimed.get(position).setStatus(true);
                SelectSchedule.slotNumber=dataTrimed.get(position).getSlotno();
                SelectSchedule.slotDate=dataTrimed.get(position).getSlotdate();
                SelectSchedule.slotTime=dataTrimed.get(position).slottime();
                if(lastItemClicked>-1)
                dataTrimed.get(lastItemClicked).setStatus(false);
                lastItemClicked=position;
                slotAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(View view, int position) {
//                Toast.makeText(getActivity(),"LongClick",Toast.LENGTH_SHORT).show();
            }
        }));

        return view;
    }

    @Override
    public void onStop()
    {
        super.onStop();
        Log.d("Fragment => ",Id+" Stoped");
    }

    @Override
    public void onPause()
    {
        super.onPause();
        Log.d("Fragment => ",Id+" Paused");
    }

    private void prepareSlotData(Long l) {
       SlotData temp;
        for(int i=0;i<data.size();i++)
        {
            int count=0;
            if(data.get(i)!=null)
            {
                if(l<=data.get(i).getSlotdate() && data.get(i).getSlotdate()<(l+86400000))
                {
                    temp=new SlotData(data.get(i).slottime(),data.get(i).avilablequota(),false,data.get(i).getSlotno(),data.get(i).getSlotdate());
                    dataTrimed.add(temp);
                    count++;
                }
            }
            if(count==20)
            {
                break;
            }
        }
        slotAdapter.notifyDataSetChanged();
    }

    private List<SlotData> getDataAtDate(Long l)
    {
        List<SlotData> temp = new ArrayList<>();
        for(int i=0;i<data.size();i++)
        {
            int count=0;
            if(data.get(i)!=null)
            {
                if(l==data.get(i).getSlotdate())
                {
                    temp.add(data.get(i));
                    count++;
                }
            }
            if(count==20)
            {
                break;
            }
        }
        return temp;
    }

    @Override
    public void onClick(View v) {

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
