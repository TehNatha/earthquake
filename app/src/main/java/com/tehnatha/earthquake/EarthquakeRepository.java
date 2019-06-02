package com.tehnatha.earthquake;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class EarthquakeRepository {
    private EarthquakeDAO earthquakeDAO;
    private EarthquakeWebSource earthquakeWebSource;

    EarthquakeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.earthquakeDAO = db.earthquakeDAO();
        this.earthquakeWebSource = new EarthquakeWebSource();
    }

    public LiveData<List<Earthquake>> getAllEarthQuakes() {
        new FetchEarthQuakesAsyncTask(earthquakeDAO).execute(earthquakeWebSource);
        return earthquakeDAO.getAll();
    }

    private static class FetchEarthQuakesAsyncTask extends AsyncTask<EarthquakeWebSource, Void, Void> {

        private EarthquakeDAO dao;

        FetchEarthQuakesAsyncTask(EarthquakeDAO dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(EarthquakeWebSource... sources) {
            if (sources.length > 0) {
                Earthquake[] earthquakes = sources[0].getAll();
                if (earthquakes.length > 0)
                    dao.insertAll(earthquakes);
            }
            return null;
        }
    }
}
