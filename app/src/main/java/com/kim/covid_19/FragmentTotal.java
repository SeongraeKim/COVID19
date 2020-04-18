package com.kim.covid_19;

import android.graphics.Point;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.channels.OverlappingFileLockException;
import java.util.HashMap;
import java.util.Map;

public class FragmentTotal extends Fragment {

    private AdView mAdView;
    private int width, height;
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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = (int) (size.x / getResources().getDisplayMetrics().density);
        height = (int) (size.y / getResources().getDisplayMetrics().density);

        MobileAds.initialize(getContext(), getString(R.string.admob_app_id));
        mAdView = view.findViewById(R.id.adView);
        Bundle bundle = new Bundle();
        bundle.putString("max_ad_content_rating", "G");
        AdRequest adRequest = new AdRequest.Builder()
                .addNetworkExtrasBundle(AdMobAdapter.class, bundle)
                .build();
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
                /* Jsoup을 이용해 데이터 가져오기 */
                Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements doc = document.select("table#main_table_countries_today tr");

                String[] world_data = new String[6];
                String[] kr_data = new String[6];

                for(int i=0; i<doc.size(); i++){
                    if(doc.get(i).text().contains("World")){
                        for(int j=0; j<world_data.length; j++){
                            world_data[j] = doc.get(i).select("td").get(j).text();
                        }
                    } else if(doc.get(i).text().contains("S. Korea")){
                        for(int j=0; j<kr_data.length; j++){
                            kr_data[j] = doc.get(i).select("td").get(j).text();
                        }
                        break;
                    }
                }
                /* 세계 통계 */
                if(world_data[2] == ""){
                    result.put("world_cases", world_data[1]);
                }else {
                    result.put("world_cases", world_data[1] + " (" + world_data[2] + ")");
                }

                if(world_data[4] == ""){
                    result.put("world_deaths", world_data[3]);
                }else {
                    result.put("world_deaths", world_data[3] + " (" + world_data[4] + ")");
                }
                result.put("world_recovered", world_data[5]);

                /* 국내 통계 */
                if(kr_data[2] == ""){
                    result.put("kr_cases", kr_data[1]);
                }else {
                    result.put("kr_cases", kr_data[1] + " (" + kr_data[2] + ")");
                }
                if(kr_data[4] == ""){
                    result.put("kr_cases", kr_data[3]);
                }else {
                    result.put("kr_deaths", kr_data[3] + " (" + kr_data[4] + ")");
                }
                result.put("kr_recovered", kr_data[5] + "");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (OverlappingFileLockException e){
                e.printStackTrace();
            }
            return result;
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
