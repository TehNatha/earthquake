package com.tehnatha.earthquake;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EarthquakesViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private EarthquakeRepository earthquakeRepository;

    EarthquakesViewModelFactory(Application application) {
        super(application);
        this.earthquakeRepository = EarthquakeRepository.getInstance(application);
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        ViewModel result = null;

        if (modelClass == EarthquakesListViewModel.class)
            result = new EarthquakesListViewModel(earthquakeRepository);
        else if (modelClass == EarthquakesMapViewModel.class)
            result = new EarthquakesMapViewModel(earthquakeRepository);

        return (T) result;
    }
}
