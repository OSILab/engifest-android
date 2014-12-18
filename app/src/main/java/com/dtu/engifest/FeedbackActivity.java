package com.dtu.engifest;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Created by naman on 18/12/14.
 */
public class FeedbackActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_feedback);
    }
}

