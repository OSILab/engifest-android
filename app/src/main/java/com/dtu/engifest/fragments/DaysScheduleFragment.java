package com.dtu.engifest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.engifest.R;

/**
 * Created by naman on 17/12/14.
 */
public class DaysScheduleFragment extends Fragment {


    public static final String ARG_POSITION ="position";
    private int mPosition;
    private CardView card1;
    private CardView card2;
    private CardView card3;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_schedule_days, container, false);
        
        mPosition = getArguments().getInt(ARG_POSITION);
        card1 =(CardView) v.findViewById(R.id.cardSchedule1);
        card2 =(CardView)  v.findViewById(R.id.cardSchedule2);
        card3 =(CardView)  v.findViewById(R.id.cardSchedule3);


        //Layout contains 3 cardviews and 3rd one has visibility set to gone.
        // if there are 3 events in a particular day, then we can set
        // its visibility to visible for that day according to mPosition while rest will have 2 cards showing up

        if (mPosition==2)
            card3.setVisibility(View.VISIBLE);


        return v;
    }}


