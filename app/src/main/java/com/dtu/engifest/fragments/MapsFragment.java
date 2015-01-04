package com.dtu.engifest.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dtu.engifest.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by naman on 22/12/14.
 */
public class MapsFragment extends Fragment {

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View v = inflater.inflate(R.layout.fragment_maps, container, false);
        getActivity().setTitle("Reach Us");

        mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();


        MarkerOptions markerOptions;
        LatLng position;

        markerOptions = new MarkerOptions();

        position = new LatLng(28.749783333f, 77.1172f);
        markerOptions.position(position);
        markerOptions.title("Delhi Technological University");
        mMap.addMarker(markerOptions);
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(position, 15.0f);
        mMap.animateCamera(cameraPosition);
        return v;
    }

}
