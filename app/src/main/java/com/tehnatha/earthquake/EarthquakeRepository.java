package com.tehnatha.earthquake;

import android.app.Application;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EarthquakeRepository {
    private EarthquakeDAO earthquakeDAO;

    EarthquakeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.earthquakeDAO = db.earthquakeDAO();
    }

    public LiveData<List<Earthquake>> getAllEarthQuakes() {
        return earthquakeDAO.getAll();
    }
}
