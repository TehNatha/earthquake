package com.tehnatha.earthquake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.tehnatha.earthquake.databinding.ActivityMainBinding;
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
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel.getShowMessage().observe(this, getShowMessageObserver(binding));

        binding.setViewmodel(viewModel);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        double lat = intent.getDoubleExtra("lat", 0);
        double lng = intent.getDoubleExtra("lng", 0);
        openMap(lat, lng);

    }

    private static Observer<Boolean> getShowMessageObserver(final ActivityMainBinding binding) {
        return new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean showMessage) {
                binding.message.setVisibility(showMessage ? View.VISIBLE : View.GONE);
            }
        };
    }

    public void openMap(final double lat, final double lng) {
        FragmentManager fm = getSupportFragmentManager();
        SupportMapFragment fragment =  new EarthquakesMapFragment();

        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(lat, lng), 6
                ));
            }
        });
        
        fm.beginTransaction()
                .replace(R.id.mainView, fragment,"map")
                .addToBackStack("Back to list")
                .commit();
    }

}
