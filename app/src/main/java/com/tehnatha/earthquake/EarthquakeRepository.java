package com.tehnatha.earthquake;

import android.app.Application;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

public class EarthquakeRepository {
    private static EarthquakeRepository INSTANCE;

    private EarthquakeDAO earthquakeDAO;
    private EarthquakeWebSource earthquakeWebSource;
    private Geocoder geocoder;

    public static EarthquakeRepository getInstance(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        EarthquakeDAO earthquakeDAO = db.earthquakeDAO();
        EarthquakeWebSource earthquakeWebSource = new EarthquakeWebSource();
        Geocoder geocoder = new Geocoder(application, Locale.getDefault());
        return INSTANCE == null
                ? INSTANCE = new EarthquakeRepository(earthquakeDAO, earthquakeWebSource, geocoder)
                : INSTANCE;
    }

    private EarthquakeRepository(EarthquakeDAO earthquakeDAO, EarthquakeWebSource earthquakeWebSource, Geocoder geocoder) {
        this.earthquakeDAO = earthquakeDAO;
        this.earthquakeWebSource = earthquakeWebSource;
        this.geocoder = geocoder;
    }

    public LiveData<List<Earthquake>> getAllEarthQuakes() {
        LiveData<List<Earthquake>> earthquakes = earthquakeDAO.getAll();

        final FetchEarthQuakesAsyncTask task = new FetchEarthQuakesAsyncTask(earthquakeDAO, geocoder);

        earthquakes.observeForever(new Observer<List<Earthquake>>() {
            @Override
            public void onChanged(List<Earthquake> earthquakes) {
                if (earthquakes == null || earthquakes.size() == 0)
                    if (task.getStatus() != AsyncTask.Status.RUNNING)
                        task.execute(earthquakeWebSource);
            }
        });
        return earthquakes;
    }

    private static class FetchEarthQuakesAsyncTask extends AsyncTask<EarthquakeWebSource, Void, Void> {

        final private EarthquakeDAO dao;
        final private Geocoder geocoder;

        FetchEarthQuakesAsyncTask(EarthquakeDAO dao, Geocoder geocoder) {
            this.dao = dao;
            this.geocoder = geocoder;
        }

        @Override
        protected Void doInBackground(EarthquakeWebSource... sources) {
            if (sources.length > 0) {
                Earthquake[] earthquakes = sources[0].getAll();
                if (earthquakes.length > 0) {
                    for (Earthquake earthquake : earthquakes)
                        if (earthquake.getLocation() == null || earthquake.getLocation().isEmpty())
                            lookupAndSetEarthquakeLocation(geocoder, earthquake);
                    dao.insertAll(earthquakes);
                }
            }
            return null;
        }

        static void lookupAndSetEarthquakeLocation(Geocoder geocoder, Earthquake earthquake) {
             {
                try {
                    List<Address> addresses = geocoder.getFromLocation(earthquake.getLat(), earthquake.getLng(),1);
                    if (addresses.size() > 0) {
                        Address address = addresses.get(0);
                        StringBuilder builder = new StringBuilder();
                        ArrayList<String> locationStrings = new ArrayList<>();

                        if (address.getSubLocality() != null && !address.getSubLocality().isEmpty())
                            locationStrings.add(address.getSubLocality());
                        if (address.getLocality() != null && !address.getLocality().isEmpty())
                            locationStrings.add(address.getLocality());
                        if (address.getSubAdminArea() != null && !address.getSubAdminArea().isEmpty())
                            locationStrings.add(address.getSubAdminArea());
                        if (address.getAdminArea() != null && !address.getAdminArea().isEmpty())
                            locationStrings.add(address.getAdminArea());
                        if (address.getCountryName() != null && !address.getCountryName().isEmpty())
                            locationStrings.add(address.getCountryName());

                        if (address.getFeatureName() != null && !address.getFeatureName().isEmpty()
                                && !locationStrings.contains(address.getFeatureName()))
                            locationStrings.add(0, address.getFeatureName());

                        for (int i = 0; i < locationStrings.size(); i++) {
                            if (i != 0) builder.append(", ");
                            builder.append(locationStrings.get(i));
                        }

                        earthquake.setLocation(builder.toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
