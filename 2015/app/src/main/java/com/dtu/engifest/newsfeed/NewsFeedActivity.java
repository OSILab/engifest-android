package com.dtu.engifest.newsfeed;

/**
 * Created by sikkavayu2013 on 15-01-2015.
 */

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.newsfeed.adapter.FeedListAdapter;
import com.dtu.engifest.newsfeed.data.FeedItem;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

public class NewsFeedActivity extends ActionBarActivity {
    private static final String TAG = NewsFeedActivity.class.getSimpleName();
    private ListView listView;
    private FeedListAdapter listAdapter;
    private SmoothProgressBar progressBar;
    private LinearLayout errorLayout;
    private List<FeedItem> feedItems,updatedFeedItems;

    private String URL_FEED = "https://graph.facebook.com/v2.2/Engifest/feed?access_token=1553859111540900|3NbvXMsb3k09Atai40fq0lQyB2s&limit=15&fields=message,object_id,created_time,picture";

    public String loadJSONFromAsset() {

        String jsonString = "";
        try {
            String currentLine;
            File cacheFile = new File(getFilesDir(), "news.json");

            BufferedReader br = new BufferedReader(new FileReader(cacheFile));
            while ((currentLine = br.readLine()) != null) {
                jsonString += currentLine + '\n';
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return jsonString;
    }

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.list);
        progressBar = (SmoothProgressBar) findViewById(R.id.google_now);
        errorLayout=(LinearLayout) findViewById(R.id.error);
        feedItems = new ArrayList<FeedItem>();
        updatedFeedItems = new ArrayList<FeedItem>();
        listAdapter = new FeedListAdapter(this, feedItems);
        listView.setAdapter(listAdapter);


        final File cacheFile = new File(getFilesDir(), "news.json");

        if (cacheFile.exists()) {

                    parseJsonFeed();

        } if (NetworkUtil.isNetworkConnected(this)){
            progressBar.setVisibility(View.VISIBLE);

            JsonObjectRequest jsonReq = new JsonObjectRequest(Method.GET,
                    URL_FEED, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        addJsonToCache(response);
                        updateJsonFeed();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(View.GONE);
                    if (!cacheFile.exists())
                        errorLayout.setVisibility(View.VISIBLE);
                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);
        }
        if (!cacheFile.exists()&&!NetworkUtil.isNetworkConnected(this)){
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
    }
private void addJsonToCache(JSONObject response){
    File cacheFile = new File(getFilesDir(), "news.json");
    String r=response.toString();

    BufferedWriter bw = null;
    try {
        if (!cacheFile.exists()) {
            cacheFile.createNewFile();
        }

        FileWriter fw = new FileWriter(cacheFile.getAbsoluteFile());
        bw = new BufferedWriter(fw);

        if (r!=null) {
            bw.write(r);
        }

    } catch (Exception e){
        e.printStackTrace();

    } finally {
        try {
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

}


    private void parseJsonFeed() {
        try {
            JSONObject response = new JSONObject(loadJSONFromAsset());
            JSONArray feedArray = response.getJSONArray("data");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.optString("object_id"));
                item.setName("Engifest,DTU");

                String objectId=feedObj.optString("object_id");
                String imageUrl="http://graph.facebook.com/"+objectId+"/picture";
                item.setImge(imageUrl);
                item.setStatus(feedObj.optString("message"));
                item.setProfilePic("https://graph.facebook.com/Engifest/picture?type=normal");
                item.setTimeStamp(feedObj.getString("created_time"));


                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(null);

                feedItems.add(item);
            }

            progressBar.setVisibility(View.GONE);
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateJsonFeed() {
        try {
            JSONObject response = new JSONObject(loadJSONFromAsset());
            JSONArray feedArray = response.getJSONArray("data");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                FeedItem item = new FeedItem();
                item.setId(feedObj.optString("object_id"));
                item.setName("Engifest,DTU");

                String objectId=feedObj.optString("object_id");

                String imageUrl="http://graph.facebook.com/"+objectId+"/picture";
                item.setImge(imageUrl);
                item.setStatus(feedObj.optString("message"));
                item.setProfilePic("https://graph.facebook.com/Engifest/picture?type=normal");

                String timeResponse=feedObj.optString("created_time");
                String time=timeResponse;

                if (timeResponse.length()>=10) {
                    time = timeResponse.substring(0, 10);
                }
                item.setTimeStamp(time);


                String feedUrl = feedObj.isNull("url") ? null : feedObj
                        .getString("url");
                item.setUrl(null);

                updatedFeedItems.add(item);
            }

            progressBar.setVisibility(View.GONE);
            feedItems.clear();
            feedItems.addAll(updatedFeedItems);
            listAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
