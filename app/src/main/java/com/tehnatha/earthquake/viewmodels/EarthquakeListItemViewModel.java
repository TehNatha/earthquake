package com.tehnatha.earthquake.viewmodels;

import com.tehnatha.earthquake.datamodel.Earthquake;

public class EarthquakeListItemViewModel {

    private String title;
    private String subtitle;
    private double magnitude;

    public EarthquakeListItemViewModel(Earthquake earthquake) {
        title = earthquake.getLocation();
        subtitle = earthquake.getDatetime();
        magnitude = earthquake.getMagnitude();
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public double getMagnitude() {
        return magnitude;
    }
}
