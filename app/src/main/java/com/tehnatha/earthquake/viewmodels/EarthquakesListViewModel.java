package com.tehnatha.earthquake.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.datamodel.EarthquakeRepository;

import java.util.List;

public class EarthquakesListViewModel extends ViewModel {

    private final EarthquakeRepository earthquakeRepository;
    private LiveData<List<Earthquake>> earthquakes;

    public EarthquakesListViewModel(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
        earthquakes = earthquakeRepository.getAllEarthQuakes();
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }

    public void refresh() {
        earthquakeRepository.refresh();
    }
}
