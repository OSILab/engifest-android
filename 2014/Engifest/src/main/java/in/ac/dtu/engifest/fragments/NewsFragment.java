package in.ac.dtu.engifest.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardAdapter;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import in.ac.dtu.engifest.MainActivity;
import in.ac.dtu.engifest.R;
import in.ac.dtu.engifest.UpdateNews;
import in.ac.dtu.engifest.Utils;

/**
 * Created by omerjerk on 19/12/13.
 */
public class NewsFragment extends Fragment {


    private static final String TAG = "NewsFragment";
    View rootView;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    private ReadFromJSON mReadFromJSON;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static NewsFragment newInstance(int sectionNumber) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public NewsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        updateView();
        return rootView;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar. See also
        // showGlobalContextActionBar, which controls the top-left area of the action bar.
            inflater.inflate(R.menu.news, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    public void updateView() {
        mReadFromJSON = new ReadFromJSON();
        mReadFromJSON.execute();
    }

    private class ReadFromJSON extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... v) {

            String jsonString = "";

            try {
                //Log.d(TAG, getActivity().getFilesDir() + "data.json");
                File cacheFile = new File(getActivity().getFilesDir(), "data.json");

                BufferedReader br = new BufferedReader(new FileReader(cacheFile));
                jsonString = br.readLine();

            } catch (Exception e) {
                e.printStackTrace();
                if(Utils.isNetworkConnected(getActivity())){
                    new UpdateNews(getActivity()){
                        @Override
                        protected void onPostExecute(String result) {
                            super.onPostExecute(result);
                            new ReadFromJSON().execute();
                        }
                    }.execute();
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "Please turn on your internet connection get the latest news!", Toast.LENGTH_SHORT)
                                    .show();
                            RelativeLayout loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);
                            loadingLayout.setVisibility(View.GONE);
                        }
                    });

                }
            }

            ArrayList<String> newsList = new ArrayList<String>();

            //Log.d(TAG, "jsonString" + jsonString);

            try {
                JSONArray jsonArray = new JSONArray(jsonString);

                for (int i = 0; i < jsonArray.length(); ++i) {
                    String newsItem = jsonArray.getString(i);
                    newsList.add(newsItem);
                    Log.d(TAG, "News = " + newsItem);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return newsList;
        }

        protected void onPostExecute(ArrayList<String> newsList) {

            CardListView newsListView = (CardListView)rootView.findViewById(android.R.id.list);

            CardAdapter adapter = new CardAdapter(getActivity(), R.layout.custom_card_news)
                    // This sets the color displayed for card titles and header actions by default
                    .setAccentColorRes(android.R.color.holo_blue_dark);
            adapter.add(new CardHeader("News"));
            for(String news : newsList) {
                adapter.add(new Card(news).setLayout(R.layout.custom_card_news));
            }
            newsListView.setAdapter(adapter);

            if(newsList.size() != 0) {
                RelativeLayout loadingLayout = (RelativeLayout) rootView.findViewById(R.id.loading_layout);
                loadingLayout.setVisibility(View.GONE);
                newsListView.setVisibility(View.VISIBLE);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mReadFromJSON.cancel(true);
    }
}
