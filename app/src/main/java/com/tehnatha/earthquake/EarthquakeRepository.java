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

public class EarthquakeRepository {
    private EarthquakeDAO earthquakeDAO;
    private EarthquakeWebSource earthquakeWebSource;
    private Geocoder geocoder;

    EarthquakeRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.earthquakeDAO = db.earthquakeDAO();
        this.earthquakeWebSource = new EarthquakeWebSource();
        this.geocoder = new Geocoder(application, Locale.getDefault());
    }

    public LiveData<List<Earthquake>> getAllEarthQuakes() {
        new FetchEarthQuakesAsyncTask(earthquakeDAO, geocoder).execute(earthquakeWebSource);
        return earthquakeDAO.getAll();
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
