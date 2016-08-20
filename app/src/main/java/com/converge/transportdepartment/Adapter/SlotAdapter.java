package com.converge.transportdepartment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.converge.transportdepartment.R;
import com.converge.transportdepartment.Utility.SlotData;

import java.util.List;

/**
 * Created by converge on 11/8/16.
 */

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.MyViewHolder> {

    private List<SlotData> slotList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, quota, genre;
        public RadioButton slotRadio;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.slotTime);
            quota = (TextView) view.findViewById(R.id.slotQuota);
            slotRadio = (RadioButton) view.findViewById(R.id.slotRadio);
        }
    }

    public SlotAdapter(List<SlotData> slotList) {
        this.slotList = slotList;
    }

    @Override
    public SlotAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slot_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SlotAdapter.MyViewHolder holder, int position) {
        SlotData slot = slotList.get(position);
        holder.title.setText(slot.slottime());
        holder.quota.setText(Integer.toString(slot.avilablequota()));
        holder.slotRadio.setChecked(slot.getStatus());
    }

    @Override
    public int getItemCount() {
        return slotList.size();
    }
}