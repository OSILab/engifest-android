package com.dtu.engifest.about;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.dtu.engifest.R;
import com.dtu.engifest.util.DialogUtils;

/**
 * Created by naman on 17/12/14.
 */
public class AboutActivity extends ActionBarActivity {


    private SvgAnimator mSvgAnimator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSvgAnimator = (SvgAnimator) findViewById(R.id.oat);
        mSvgAnimator.setSvgResource(R.drawable.oat);

        LinearLayout aboutDtu = (LinearLayout) findViewById(R.id.aboutDtuLayout);
        aboutDtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showAboutDtu(AboutActivity.this);
            }
        });
        LinearLayout aboutEngifest = (LinearLayout) findViewById(R.id.aboutEngifestLayout);
        aboutEngifest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.showAboutEngifest(AboutActivity.this);
            }
        });


    }}
