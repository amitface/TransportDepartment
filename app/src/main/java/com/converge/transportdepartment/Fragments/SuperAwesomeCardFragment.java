package com.converge.transportdepartment.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.converge.transportdepartment.Adapter.SlotAdapter;
import com.converge.transportdepartment.R;
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
public class SuperAwesomeCardFragment extends Fragment {
    private static final String ARG_POSITION = "position";
    private static final String ARG_POSITION2 = "position2";

    private String jsonData;
    private Long dateLong;
    private List<SlotData> data;
    private List<SlotData> dataTrimed;
    private RecyclerView recyclerView;
    private SlotAdapter slotAdapter;


    public static SuperAwesomeCardFragment newInstance(String data,Long position) {
        SuperAwesomeCardFragment f = new SuperAwesomeCardFragment();
        Bundle b = new Bundle();
        b.putString(ARG_POSITION, data);
        b.putLong(ARG_POSITION2, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        jsonData = getArguments().getString(ARG_POSITION);
        dateLong = getArguments().getLong(ARG_POSITION2);
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

        return view;
    }

    private void prepareSlotData(Long l) {
       SlotData temp;
        for(int i=0;i<data.size();i++)
        {
            int count=0;
            if(data.get(i)!=null)
            {
                if(l<=data.get(i).getslotdate() && data.get(i).getslotdate()<(l+86400000))
                {
                    temp=new SlotData(data.get(i).slottime(),data.get(i).avilablequota());
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
                if(l==data.get(i).getslotdate())
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
}
