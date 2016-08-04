package com.converge.transportdepartment.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.converge.transportdepartment.Been.RtoLocatorBeen;
import com.converge.transportdepartment.R;

import java.util.List;

/**
 * Created by converge on 21/7/16.
 */
public class RtoLocatorAdapter extends RecyclerView.Adapter<RtoLocatorAdapter.MyViewHolder> {

    private List<RtoLocatorBeen> rtoList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
//            genre = (TextView) view.findViewById(R.id.genre);
//            year = (TextView) view.findViewById(R.id.year);
        }
    }


    public RtoLocatorAdapter(List<RtoLocatorBeen> rtoList) {
        this.rtoList = rtoList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rto_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RtoLocatorBeen rtoLocatorBeen = rtoList.get(position);
        holder.title.setText(rtoLocatorBeen.getTitle());
//        holder.genre.setText(movie.getGenre());
//        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return rtoList.size();
    }
}
