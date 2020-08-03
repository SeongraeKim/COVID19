package com.kim.covid19;

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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Collections;

public class FragmentCountry extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CountryRecyclerAdapter countryAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        // RecyclerView 객체 생성 및 연결
        recyclerView = view.findViewById(R.id.country_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

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

                        // 국가 순위
                        country_num = Integer.parseInt(country_data[0].replace(",", ""));
                        // 확진자
                        country_cases = country_data[2];
                        // 전일대비 증가한 확진자
                        if(country_data[3].equals("")){
                            country_cases_p = "";
                        }else {
                            country_cases_p = "(" + country_data[3] + ")";
                        }
                        // 사망자
                        if(country_data[4].equals("")){
                            country_deaths = "0";
                        }else {
                            country_deaths = country_data[4];
                        }
                        // 전일대비 증가한 사망자
                        if (country_data[5].equals("")){
                            country_deaths_p = "";
                        }else {
                            country_deaths_p = "(" + country_data[5] + ")";
                        }
                        // 완치자
                        if (country_data[6].equals("")){
                            country_recovered = "0";
                        }else {
                            country_recovered = country_data[6];
                        }

                        arrayList.add(new ListData(
                                country_num,        // 국가 순위
                                country_data[1],    // 국가명
                                country_cases,      // 확진자
                                country_cases_p,    // 전일대비 증가한 확진자
                                country_deaths,     // 사망자
                                country_deaths_p,   // 전일대비 증가한 사망자
                                country_recovered   // 완치자
                        ));
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
            //Collections.reverse(arrayList);
            countryAdapter = new CountryRecyclerAdapter(arrayList);
            recyclerView.setAdapter(countryAdapter);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
