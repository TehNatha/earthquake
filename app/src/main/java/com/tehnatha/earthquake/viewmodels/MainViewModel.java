package com.tehnatha.earthquake.viewmodels;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.tehnatha.earthquake.datamodel.EarthquakeRepository;

public class MainViewModel extends ViewModel {

    private EarthquakeRepository earthquakeRepository;

    private LiveData<String> message;

    private LiveData<Boolean> hasMessage = Transformations.map(getMessage(), new Function<String, Boolean>() {
        @Override
        public Boolean apply(String message) {
            return message != null && message.length() == 0;
        }
    });

    public MainViewModel(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
        MutableLiveData<String> text = new MutableLiveData<>();
        text.setValue("This is a test of the emergency broadcast system");
        message = text;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> getHasMessage() {
        return hasMessage;
    }
}
