package com.tehnatha.earthquake;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

class EarthquakesMapViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakes;

    public EarthquakesMapViewModel(EarthquakeRepository earthquakeRepository) {
        earthquakes = earthquakeRepository.getAllEarthQuakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }
}
