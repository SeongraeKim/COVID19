package com.kim.covid19;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView country, cases, cases_p, deaths, deaths_p, recovered;

        MyViewHolder(View view){
            super(view);
            country = view.findViewById(R.id.tv_country);
            cases = view.findViewById(R.id.tv_cases);
            cases_p = view.findViewById(R.id.tv_cases_p);
            deaths = view.findViewById(R.id.tv_deaths);
            deaths_p = view.findViewById(R.id.tv_deaths_p);
            recovered = view.findViewById(R.id.tv_recovered);
        }
    }

    private ArrayList<ListData> arrayList;
    CountryRecyclerAdapter(ArrayList<ListData> arrayList){
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.country.setText(arrayList.get(position).getTv_name());
        myViewHolder.cases.setText(arrayList.get(position).getTv_cases() + "");
        myViewHolder.cases_p.setText(arrayList.get(position).getTv_cases_p());
        myViewHolder.deaths.setText(arrayList.get(position).getTv_deaths());
        myViewHolder.deaths_p.setText(arrayList.get(position).getTv_deaths_p());
        myViewHolder.recovered.setText(arrayList.get(position).getTv_recovered());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}