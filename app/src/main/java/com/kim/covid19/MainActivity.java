package com.kim.covid19;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private FragmentTotal fragmentTotal = new FragmentTotal();
    private FragmentCountry fragmentCountry = new FragmentCountry();
    private FragmentRegion fragmentRegion = new FragmentRegion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentTotal).commitAllowingStateLoss();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_total:
                    transaction.replace(R.id.frameLayout, fragmentTotal).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_country:
                    transaction.replace(R.id.frameLayout, fragmentCountry).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_region:
                    transaction.replace(R.id.frameLayout, fragmentRegion).commitAllowingStateLoss();
                    return true;
            }
            return false;
        }
    };
}