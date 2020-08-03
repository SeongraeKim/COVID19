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

public class FragmentRegion extends Fragment {

    private RecyclerView recyclerView2;
    private RecyclerView.LayoutManager layoutManager;
    private RegionRecyclerAdapter regionAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_region, container, false);

        // RecyclerView 객체 생성 및 연결
        recyclerView2 = view.findViewById(R.id.region_recycler);
        recyclerView2.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager);

        regionData task = new regionData();
        task.execute();

        return view;
    }

    private class regionData extends AsyncTask<Void, Void, ArrayList<ListData_2>> {

        @Override
        protected ArrayList<ListData_2> doInBackground(Void... voids) {

            ArrayList<ListData_2> arrayList2 = new ArrayList<ListData_2>();

            try {
                /* Jsoup을 이용해 데이터 가져오기 */
                Document document = Jsoup.connect("http://ncov.mohw.go.kr/").get();
                Elements doc = document.select("div.rpsa_detail > div > div");

                int region_num = 0;
                String region = null;
                String region_cases = null;
                String region_cases_p = null;
                String region_deaths = null;
                String region_recovered = null;

                for(int i=1; i<doc.size(); i++) {

                        region = doc.get(i).select("h4").text();
                        region_cases = doc.get(i).select("li").get(0).select("div").get(1).select("span").text();
                        region_cases_p = doc.get(i).select("li").get(1).select("div").get(1).select("span").text();
                        region_deaths = doc.get(i).select("li").get(2).select("div").get(1).select("span").text();
                        region_recovered = doc.get(i).select("li").get(3).select("div").get(1).select("span").text();

                        region_num = Integer.parseInt(region_cases.replace(",", ""));   // 확진자 순으로 정렬하기 위해서

                        if(region_cases_p.equals("(0)")){
                            region_cases_p = "";
                        }

                        arrayList2.add(new ListData_2(region_num, region, region_cases, region_cases_p, region_deaths, region_recovered));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return arrayList2;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData_2> arrayList2) {

            Collections.sort(arrayList2);
            Collections.reverse(arrayList2);
            regionAdapter = new RegionRecyclerAdapter(arrayList2);
            recyclerView2.setAdapter(regionAdapter);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
