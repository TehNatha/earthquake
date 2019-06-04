package com.tehnatha.earthquake;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class EarthquakesListViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakes;

    public EarthquakesListViewModel(EarthquakeRepository earthquakeRepository) {
        earthquakes = earthquakeRepository.getAllEarthQuakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }
}
