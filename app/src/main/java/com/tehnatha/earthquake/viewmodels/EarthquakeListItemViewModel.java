package com.tehnatha.earthquake.viewmodels;

import android.graphics.Color;

import androidx.lifecycle.LiveData;

import com.tehnatha.earthquake.R;
import com.tehnatha.earthquake.datamodel.Earthquake;

public class EarthquakeListItemViewModel {

    String title;
    String subtitle;
    int magnitudeColor;

    public EarthquakeListItemViewModel(Earthquake earthquake) {
        title = earthquake.getLocation();
        subtitle = earthquake.getDatetime();

        if (earthquake.getMagnitude() >= 8)
            magnitudeColor = R.color.eightMagnitude;
        else if (earthquake.getMagnitude() >= 7)
            magnitudeColor = R.color.sevenMagnitude;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public int getMagnitudeColor() {
        return magnitudeColor;
    }
}
