package com.kim.covid_19;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Collections;

public class FragmentCountry extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CountryRecyclerAdapter countryAdapter;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        // RecyclerView 객체 생성 및 연결
        recyclerView = view.findViewById(R.id.country_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // 애드몹 광고
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        countryData task = new countryData();
        task.execute();

        return view;
    }

    private class countryData extends AsyncTask<Void, Void, ArrayList<ListData>> {

        @Override
        protected ArrayList<ListData> doInBackground(Void... voids) {

            ArrayList<ListData> arrayList = new ArrayList<ListData>();

            try {
                /* Jsoup을 이용해 데이터 가져오기 */
                Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements doc = document.select("table#main_table_countries_today tbody tr");

                String[] country_data = new String[10];
                int country_num = 0;
                String  country_cases = null;
                String country_cases_p = null;
                String country_deaths = null;
                String country_deaths_p = null;
                String country_recovered = null;

                for(int i=0; i<doc.size(); i++) {
                    if (doc.get(i).html().contains("country/")) {
                        for(int j=0; j<country_data.length; j++){
                            country_data[j] = doc.get(i).select("td").get(j).text();
                        }

                        country_num = Integer.parseInt(country_data[1].replace(",", ""));

                        country_cases = country_data[1];
                        if(country_data[2].equals("")){
                            country_cases_p = "";
                        }else {
                            country_cases_p = "(" + country_data[2] + ")";
                        }
                        if(country_data[3].equals("")){
                            country_deaths = "0";
                        }else {
                            country_deaths = country_data[3];
                        }
                        if (country_data[4].equals("")){
                            country_deaths_p = "";
                        }else {
                            country_deaths_p = "(" + country_data[4] + ")";
                        }
                        if (country_data[5].equals("")){
                            country_recovered = "0";
                        }else {
                            country_recovered = country_data[5];
                        }

                        arrayList.add(new ListData(country_num, country_data[0], country_cases, country_cases_p, country_deaths, country_deaths_p, country_recovered));
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList) {

            Collections.sort(arrayList);
            Collections.reverse(arrayList);
            countryAdapter = new CountryRecyclerAdapter(arrayList);
            recyclerView.setAdapter(countryAdapter);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
