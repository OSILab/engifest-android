package com.dtu.engifest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by naman on 30/01/15.
 */
public class SettingsActivity extends ActionBarActivity {

    String Urlgithub="https://github.com/naman14/Engifest";
    String Urlwebsite="http://engifest.dce.edu";
    String Urlfacebook="https://www.facebook.com/engifest";
    String Urltwitter="https://twitter.com/engifest";
    String Urlgoogleplus="https://www.facebook.com/engifest";

    ImageView github,website,share,facebook,googleplus,twitter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            github=(ImageView) findViewById(R.id.github);
            website=(ImageView) findViewById(R.id.website);
            share=(ImageView) findViewById(R.id.share);
            facebook=(ImageView) findViewById(R.id.facebook);
            googleplus=(ImageView) findViewById(R.id.googleplus);
            twitter=(ImageView) findViewById(R.id.twitter);

            github.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Urlgithub));
                    startActivity(i);
                }
            });
            website.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Urlwebsite));
                    startActivity(i);
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Engifest");
                    String sAux = "\nGet the official Engifest app for latest updates and information about Engifest 2015\n\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.dtu.engifest \n\n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose one"));
                }
            });
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Urlfacebook));
                    startActivity(i);
                }
            });
            googleplus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Urlgoogleplus));
                    startActivity(i);
                }
            });
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(Urltwitter));
                    startActivity(i);
                }
            });

        }



}
