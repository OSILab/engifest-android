package com.dtu.engifest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.dtu.engifest.util.NumberRatingBar;

/**
 * Created by naman on 18/12/14.
 */


public class FeedbackActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_feedback);

        final NumberRatingBar numberRatingBar2 =(NumberRatingBar) findViewById(R.id.rating_bar_2);



        final NumberRatingBar numberRatingBar1 =(NumberRatingBar) findViewById(R.id.rating_bar_1);



        final EditText feedback = (EditText) findViewById(R.id.feedback);



        FrameLayout button =(FrameLayout) findViewById(R.id.submit_feedback_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String finalFeedback = "RainggApp="+numberRatingBar1.getProgress()+"\n"+"RatingEngifest="+numberRatingBar2.getProgress()+"\n"+"Feedback="+feedback.getText();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setType("text/plain");
                emailIntent.setData(Uri.parse("mailto:" + "namandwivedi14@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback-Engifest");
                emailIntent.putExtra(Intent.EXTRA_TEXT, finalFeedback);
                emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(emailIntent);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }









}

