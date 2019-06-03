package com.tehnatha.earthquake;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EarthquakesViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakes;

    public EarthquakesViewModel(EarthquakeRepository earthquakeRepository) {
        earthquakes = earthquakeRepository.getAllEarthQuakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }
}
