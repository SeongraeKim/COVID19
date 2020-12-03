package com.kim.covid19;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RegionRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView region, cases, cases_p, deaths, recovered;

        MyViewHolder(View view){
            super(view);
            region = view.findViewById(R.id.tv_region);
            cases = view.findViewById(R.id.tv_region_cases);
            cases_p = view.findViewById(R.id.tv_region_cases_p);
            deaths = view.findViewById(R.id.tv_region_deaths);
            recovered = view.findViewById(R.id.tv_region_recovered);
        }
    }

    private ArrayList<ListData_2> arrayList2;
    RegionRecyclerAdapter(ArrayList<ListData_2> arrayList2){
        this.arrayList2 = arrayList2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.region.setText(arrayList2.get(position).getTv_name());
        myViewHolder.cases.setText(arrayList2.get(position).getTv_cases() + "");
        myViewHolder.cases_p.setText(arrayList2.get(position).getTv_cases_p());
        myViewHolder.deaths.setText(arrayList2.get(position).getTv_deaths());
        myViewHolder.recovered.setText(arrayList2.get(position).getTv_recovered());
    }

    @Override
    public int getItemCount() {
        return arrayList2.size();
    }
}