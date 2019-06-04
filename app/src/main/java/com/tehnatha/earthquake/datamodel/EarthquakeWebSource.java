package com.tehnatha.earthquake.datamodel;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;
import com.tehnatha.earthquake.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class EarthquakeWebSource {

    public Earthquake[] getAll() {
        return earthquakesFromJsonString(getEndpointString(BuildConfig.END_POINT));
    }

    private static String getEndpointString(String endpoint) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(endpoint);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();


            InputStream stream = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            StringBuilder buffer = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            return buffer.toString();


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Earthquake[] earthquakesFromJsonString(String json) {
        if (json == null || json.isEmpty()) return null;

        EarthQuakes earthquakes;
        Gson gson = new Gson();
        try {
            earthquakes = gson.fromJson(json, EarthQuakes.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
            return null;
        }
        return earthquakes.earthquakes;
    }

}
