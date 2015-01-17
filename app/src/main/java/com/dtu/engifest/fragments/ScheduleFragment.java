package com.dtu.engifest.fragments;

/**
 * Created by naman on 17/12/14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.schedule.ScheduleAdapter;
import com.dtu.engifest.schedule.ScheduleItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class ScheduleFragment extends Fragment{

    private ListView listView;
    private ScheduleAdapter listAdapter;
    private List<ScheduleItem> feedItems;
    private String URL_SCHEDULE = "http://engifesttest.comlu.com/schedule";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        listView = (ListView) v.findViewById(R.id.list);

        feedItems = new ArrayList<ScheduleItem>();

        listAdapter = new ScheduleAdapter(getActivity(), feedItems);
        listView.setAdapter(listAdapter);


        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_SCHEDULE);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    parseJsonFeed(new JSONObject(data));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        } else {

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_SCHEDULE, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {

                    if (response != null) {
                        parseJsonFeed(response);
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);
        }
return v;

    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("schedule");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject feedObj = (JSONObject) feedArray.get(i);

                ScheduleItem item = new ScheduleItem();
                item.setId(feedObj.getInt("id"));
                item.setName(feedObj.getString("name"));
                item.setLocation(feedObj.getString("location"));
                item.setDate(feedObj.getString("date"));
                item.setTime(feedObj.getString("time"));

                feedItems.add(item);
            }


            listAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }

}
