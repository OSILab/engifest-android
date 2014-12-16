package com.dtu.engifest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dtu.engifest.R;
import com.dtu.engifest.events.EventsActivity;

/**
 * Created by naman on 16/12/14.
 */
public class HomeFragment extends Fragment {

    Button buttonEvents;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_home, container, false);

Button buttonEvents =(Button) v.findViewById(R.id.buttonEvents);
        buttonEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),EventsActivity.class);
                startActivity(i);
            }
        });


        return v;
    }}


