package com.tehnatha.earthquake;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EarthquakeRepository repo = new EarthquakeRepository(getApplication());
        LiveData<List<Earthquake>> earthquakes = repo.getAllEarthQuakes();
        final TextView v = new TextView(this);
        earthquakes.observe(this, new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(@Nullable List<Earthquake> earthquakes) {
                StringBuilder builder = new StringBuilder();
                if (earthquakes == null) builder.append("failed");
                else
                    for (Earthquake quake : earthquakes)
                        builder.append(quake.getEqid()).append("\n");
                v.setText(builder.toString());
            }
        });
        setContentView(v);
    }
}
