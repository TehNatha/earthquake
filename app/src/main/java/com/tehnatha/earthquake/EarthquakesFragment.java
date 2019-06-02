package com.tehnatha.earthquake;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;


public class EarthquakesFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EarthquakeRepository repo = new EarthquakeRepository(getActivity().getApplication());
        LiveData<List<Earthquake>> earthquakes = repo.getAllEarthQuakes();
        FrameLayout frame = (FrameLayout) inflater.inflate(R.layout.fragment_earthquakes, container, false);
        final TextView v = new TextView(getActivity());
        frame.addView(v);

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

        return frame;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
