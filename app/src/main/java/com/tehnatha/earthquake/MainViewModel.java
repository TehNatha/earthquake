package com.tehnatha.earthquake;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    EarthquakeRepository earthquakeRepository;

    LiveData<String> message;

    LiveData<Boolean> hasMessage = Transformations.map(getMessage(), new Function<String, Boolean>() {
        @Override
        public Boolean apply(String message) {
            return message != null && message.length() == 0;
        }
    });

    public MainViewModel(EarthquakeRepository earthquakeRepository) {
        this.earthquakeRepository = earthquakeRepository;
        MutableLiveData<String> text = new MutableLiveData<>();
        text.setValue("This is a test of the emergency broadcast system");
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<Boolean> getHasMessage() {
        return hasMessage;
    }
}
