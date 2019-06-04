package com.tehnatha.earthquake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.datamodel.EarthquakeRepository;

import java.util.List;

public class EarthquakesListViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakes;

    public EarthquakesListViewModel(EarthquakeRepository earthquakeRepository) {
        earthquakes = earthquakeRepository.getAllEarthQuakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }
}
