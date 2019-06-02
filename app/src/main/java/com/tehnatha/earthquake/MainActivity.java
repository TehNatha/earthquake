package com.tehnatha.earthquake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView v = new TextView(this);
        v.setText(BuildConfig.END_POINT);
        setContentView(v);
    }
}
