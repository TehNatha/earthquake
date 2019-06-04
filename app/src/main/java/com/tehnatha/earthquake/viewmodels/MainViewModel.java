package com.tehnatha.earthquake.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tehnatha.earthquake.datamodel.Earthquake;
import com.tehnatha.earthquake.datamodel.EarthquakeRepository;

import java.util.List;

public class MainViewModel extends ViewModel {

    private LiveData<List<Earthquake>> earthquakes;
    private LiveData<Boolean> showMessage;

    public MainViewModel(EarthquakeRepository earthquakeRepository) {
        earthquakes = earthquakeRepository.getAllEarthQuakes();
        showMessage = Transformations.map(getEarthquakes(), new Function<List<Earthquake>, Boolean>() {
            @Override
            public Boolean apply(List<Earthquake> earthquakes) {
                return earthquakes == null || earthquakes.size() == 0;
            }
        });
    }

    public LiveData<List<Earthquake>> getEarthquakes() {
        return earthquakes;
    }

    public LiveData<Boolean> getShowMessage() {
        return showMessage;
    }
}
