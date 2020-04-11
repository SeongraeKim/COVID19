package com.kim.covid_19;

import android.annotation.SuppressLint;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentCountry extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter rcAdapter;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_country, container, false);

        // RecyclerView 객체 생성 및 연결
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //ArrayList<ListData> arrayList = new ArrayList<ListData>();
        //arrayList.add(new ListData("1", "2", "3"));
        //RecyclerAdapter rcAdapter = new RecyclerAdapter(arrayList);
        //recyclerView.setAdapter(rcAdapter);

        //  애드몹 광고
        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));
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
                Elements doc = document.select("table#main_table_countries_today tbody tr td");

                String[] country_data = null;

                for(int i=0; i<doc.size(); i++) {
                    if (doc.get(i).html().contains("country/")) {
                        country_data = doc.get(i).parent().text().split(" ");

                        arrayList.add(new ListData(country_data[0], country_data[1], country_data[3], country_data[4]));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return arrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ListData> arrayList) {

            RecyclerAdapter rcAdapter = new RecyclerAdapter(arrayList);
            recyclerView.setAdapter(rcAdapter);
        }
    }
}
