package com.kim.covid_19;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FragmentTotal extends Fragment {

    private AdView mAdView;
    private TextView world_cases, world_deaths, world_recovered;
    private TextView kr_cases, kr_deaths, kr_recovered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        world_cases = view.findViewById(R.id.world_cases);
        world_deaths = view.findViewById(R.id.world_deaths);
        world_recovered = view.findViewById(R.id.world_recovered);
        kr_cases = view.findViewById(R.id.kr_cases);
        kr_deaths = view.findViewById(R.id.kr_deaths);
        kr_recovered = view.findViewById(R.id.kr_recovered);

        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        globalData task = new globalData();
        task.execute();

        return view;
    }

    private class globalData extends AsyncTask<Void, Void, Map<String,String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String,String> result = new HashMap<>();
            try {
                /* 전세계 통계 */
                /*Document document1 = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements world = document1.select("#main_table_countries_today tbody tr.total_row_world td");
                result.put("world_cases", world.text().split(" ")[1] + " (" + world.text().split(" ")[2] + ")");
                result.put("world_deaths", world.text().split(" ")[3] + " (" + world.text().split(" ")[4] + ")");
                result.put("world_recovered", world.text().split(" ")[5] + "");*/

                /* 국내 통계 */
                Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements doc = document.select("table#main_table_countries_today tr");

                String[] world_data = null;
                String[] kr_data = null;

                for(int i=0; i<doc.size(); i++){
                    if(doc.get(i).text().contains("World")){
                        world_data = doc.get(i).text().split(" ");

                    } else if(doc.get(i).text().contains("S. Korea")){
                        kr_data = doc.get(i).text().split(" ");
                    }
                }
                result.put("world_cases", world_data[1] + " (" + world_data[2] + ")");
                result.put("world_deaths", world_data[3] + " (" + world_data[4] + ")");
                result.put("world_recovered", world_data[5] + "");
                result.put("kr_cases", kr_data[2] + " (" + kr_data[3] + ")");
                result.put("kr_deaths", kr_data[4] + " (" + kr_data[5] + ")");
                result.put("kr_recovered", kr_data[6] + "");

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {

            /* 전세계 통계값 Textview에 추가 */
            world_cases.setText(map.get("world_cases"));
            world_deaths.setText(map.get("world_deaths"));
            world_recovered.setText(map.get("world_recovered"));
            /* 국내 통계값 Textview에 추가 */
            kr_cases.setText(map.get("kr_cases"));
            kr_deaths.setText(map.get("kr_deaths"));
            kr_recovered.setText(map.get("kr_recovered"));
        }
    }
}
