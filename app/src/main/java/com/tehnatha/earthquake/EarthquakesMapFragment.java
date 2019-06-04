package com.tehnatha.earthquake;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.viewmodels.EarthquakesMapViewModel;
import com.tehnatha.earthquake.viewmodels.ViewModelFactory;

import java.util.List;

public class EarthquakesMapFragment extends SupportMapFragment {

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        EarthquakesMapViewModel viewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getActivity().getApplication()))
                .get(EarthquakesMapViewModel.class);

        viewModel.getEarthquakes().observe(this, getEarthquakesObserver(this));
    }

    private static Observer<List<Earthquake>> getEarthquakesObserver(final SupportMapFragment mapFragment) {
        return new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(final List<Earthquake> earthquakes) {
                if (earthquakes != null && !earthquakes.isEmpty())
                    mapFragment.getMapAsync(setMarkersForEarthquakes(earthquakes));
            }
        };
    }

    private static OnMapReadyCallback setMarkersForEarthquakes(final List<Earthquake> earthquakes) {
        return new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                for (Earthquake earthquake : earthquakes)
                    googleMap.addMarker(new MarkerOptions()
                            .title(earthquake.getLocation())
                            .position(new LatLng(earthquake.getLat(), earthquake.getLng()))
                    );
            }
        };
    }
}
