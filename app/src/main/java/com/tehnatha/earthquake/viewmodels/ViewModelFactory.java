package com.tehnatha.earthquake.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.tehnatha.earthquake.datamodel.EarthquakeRepository;

public class ViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    private EarthquakeRepository earthquakeRepository;

    public ViewModelFactory(Application application) {
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
        else if (modelClass == MainViewModel.class)
            result = new MainViewModel(earthquakeRepository);

        return (T) result;
    }
}
