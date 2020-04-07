package com.kim.covid_19;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FragmentTotal extends Fragment {

    private TextView global;

    /*@Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);

        global = view.findViewById(R.id.global);

        new Thread(){
            @Override
            public void run() {
                //super.run();


            }
        }.start();

        return view;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            //super.handleMessage(msg);
            Bundle bundle = msg.getData();

            ArrayList<String> arrayList = bundle.getStringArrayList(lotto);

            String str = "";


        }
    };

    /*private Elements lotto(){

        Elements elements;

        try {
            Document doc = Jsoup.connect("https://dhlottery.co.kr/common.do?method=main").get();
            elements = doc.select("#lottoDrwNo");

        }catch (IOException e){
            Log.d("에러 : ", e.getMessage());
        }

        return elements;
    }*/
}
