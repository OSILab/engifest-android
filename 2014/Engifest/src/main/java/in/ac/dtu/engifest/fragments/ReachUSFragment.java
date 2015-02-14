package in.ac.dtu.engifest.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.ref.WeakReference;

import in.ac.dtu.engifest.MainActivity;
import in.ac.dtu.engifest.R;

/**
 * Created by omerjerk on 30/1/14.
 */
public class ReachUSFragment extends Fragment {

    private static WeakReference<MainActivity> refMainActivity = null;

    private static final String TAG = "ReachUSFragment";

    /**
     * The Map object
     */
    private GoogleMap mMap;

    View rootView;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReachUSFragment newInstance(int sectionNumber) {
        ReachUSFragment fragment = new ReachUSFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ReachUSFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            rootView = inflater.inflate(R.layout.fragment_reach_us, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
            Log.d(TAG, "Map is already there");
            Toast.makeText(getActivity(), "Please restart the app to see the map.", Toast.LENGTH_SHORT).show();
        }
        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        /*
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                //TODO: Don't know. Blank for now.
                }
        });*/

        MarkerOptions markerOptions;
        LatLng position;

        markerOptions = new MarkerOptions();
        //Couldn't think of a better solution for now
        position = new LatLng(28.749783333f, 77.1172f);
        markerOptions.position(position);
        markerOptions.title("Delhi Technological University");
        mMap.addMarker(markerOptions);
        CameraUpdate cameraPosition = CameraUpdateFactory.newLatLngZoom(position, 15.0f);
        mMap.animateCamera(cameraPosition);

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Fragment f = getFragmentManager()
                .findFragmentById(R.id.map);
        if (f != null && !getActivity().isFinishing())
            getFragmentManager().beginTransaction().remove(f).commit();
    }
}
