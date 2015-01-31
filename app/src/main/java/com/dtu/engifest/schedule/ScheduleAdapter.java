package com.dtu.engifest.schedule;

/**
 * Created by naman on 16-01-2015.
 */


import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;

import java.util.List;

public class ScheduleAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ScheduleItem> feedItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public ScheduleAdapter(Activity activity, List<ScheduleItem> feedItems) {
        this.activity = activity;
        this.feedItems = feedItems;
    }

    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int location) {
        return feedItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.scheduleitem, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView date = (TextView) convertView
                .findViewById(R.id.date);
        TextView location = (TextView) convertView
                .findViewById(R.id.location);
        TextView time = (TextView) convertView.findViewById(R.id.time);



        ScheduleItem item = feedItems.get(position);

        name.setText(item.getName());



        date.setText(item.getDate());


        if (!TextUtils.isEmpty(item.getLocation())) {
            location.setText(item.getLocation());
            location.setVisibility(View.VISIBLE);
        } else {

            location.setVisibility(View.GONE);
        }


        if (item.getTime() != "null") {
            time.setText(item.getTime());
            time.setVisibility(View.VISIBLE);
        } else {

            time.setVisibility(View.GONE);
        }






        return convertView;
    }

}