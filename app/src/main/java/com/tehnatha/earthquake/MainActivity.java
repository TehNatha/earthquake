package com.tehnatha.earthquake;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openMap() {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.mainView, new EarthquakeMapFragment(),"map").addToBackStack("Back to list").commit();
    }

}
