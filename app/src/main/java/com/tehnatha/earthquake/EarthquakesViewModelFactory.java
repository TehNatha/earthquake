package com.tehnatha.earthquake;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EarthquakesViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    EarthquakeRepository earthquakeRepository;

    public EarthquakesViewModelFactory(Application application) {
        super(application);
        this.earthquakeRepository = new EarthquakeRepository(application);
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new EarthquakesViewModel(earthquakeRepository);
    }
}
