package com.tehnatha.earthquake;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tehnatha.earthquake.databinding.ActivityMainBinding;
import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.viewmodels.MainViewModel;
import com.tehnatha.earthquake.viewmodels.ViewModelFactory;

public class MainActivity extends AppCompatActivity {

    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders
                .of(this, new ViewModelFactory(getApplication()))
                .get(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setViewmodel(viewModel);
    }

    public void openMap(final Earthquake earthquake) {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment =  new EarthquakesMapFragment();

        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(earthquake.getLat(), earthquake.getLng()), 6
                ));
            }
        });
        
        fm.beginTransaction()
                .replace(R.id.mainView, fragment,"map")
                .addToBackStack("Back to list")
                .commit();
    }

}
