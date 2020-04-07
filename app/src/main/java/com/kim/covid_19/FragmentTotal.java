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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FragmentTotal extends Fragment {

    private TextView cases;
    private TextView deaths;
    private TextView recovered;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        cases = view.findViewById(R.id.cases);
        deaths = view.findViewById(R.id.deaths);
        recovered = view.findViewById(R.id.recovered);

        globalData task = new globalData();
        task.execute();

        return view;
    }

    private class globalData extends AsyncTask<Void, Void, Map<String,String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            Map<String,String> result = new HashMap<>();
            try {
                Document document = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Elements cases = document.select("#main_table_countries_today tbody tr.total_row_world td:eq(1)");
                Elements deaths = document.select("#main_table_countries_today tbody tr.total_row_world td:eq(3)");
                Elements recovered = document.select("#main_table_countries_today tbody tr.total_row_world td:eq(5)");

                result.put("world_cases", cases.text());
                result.put("world_deaths", deaths.text());
                result.put("world_recovered", recovered.text());

            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> map) {

            cases.setText(map.get("world_cases"));
            deaths.setText(map.get("world_deaths"));
            recovered.setText(map.get("world_recovered"));
        }
    }
}
