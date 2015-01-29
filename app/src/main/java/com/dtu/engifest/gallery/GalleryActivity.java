package com.dtu.engifest.gallery;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by naman on 29/01/15.
 */
public class GalleryActivity extends ActionBarActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = GalleryActivity.class.getSimpleName();
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private String URL_GALLERY = "http://engifesttest.comlu.com/gallery";
    public String gallery[],updatedGallery[];

    private SmoothProgressBar progressBar;
    private LinearLayout errorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getSupportActionBar().setElevation(0);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(0);

        mPagerSlidingTabStrip.setOnPageChangeListener(this);

        progressBar =(SmoothProgressBar) findViewById(R.id.google_now);
        errorLayout=(LinearLayout) findViewById(R.id.error);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        final Cache.Entry entry = cache.get(URL_GALLERY);
        if (entry != null) {
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

        }

        if (NetworkUtil.isNetworkConnected(this)){

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_GALLERY, null, new Response.Listener<JSONObject>() {


                @Override
                public void onResponse(JSONObject response) {
                    VolleyLog.d(TAG, "Response: " + response.toString());
                    if (response != null) {
                        updateJsonFeed(response);

                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(View.GONE);
                    if (entry==null)
                        errorLayout.setVisibility(View.VISIBLE);

                }
            });
            jsonReq.setRetryPolicy(new DefaultRetryPolicy(
                    11000,
                    2,
                    2));

            AppController.getInstance().addToRequestQueue(jsonReq);

        }
        if (entry==null&&!NetworkUtil.isNetworkConnected(this)){
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);

        }
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray galleryArray = response.getJSONArray("gallery");
            gallery = new String[galleryArray.length()];
            for (int i = 0; i < galleryArray.length(); i++) {

                gallery[i]=galleryArray.getString(i);

            }
            mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

            mViewPager.setAdapter(mPagerAdapter);
            mPagerSlidingTabStrip.setViewPager(mViewPager);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateJsonFeed(JSONObject response) {
        try {
            JSONArray galleryArray = response.getJSONArray("gallery");
            updatedGallery= new String[galleryArray.length()];
            for (int i = 0; i < galleryArray.length(); i++) {

                updatedGallery[i]=galleryArray.getString(i);

            }
            gallery=updatedGallery;
            progressBar.setVisibility(View.GONE);

            if (mPagerAdapter==null){
                mPagerAdapter = new PagerAdapter(getSupportFragmentManager());


                mViewPager.setAdapter(mPagerAdapter);
                mPagerSlidingTabStrip.setViewPager(mViewPager);
            }
            mPagerAdapter.notifyDataSetChanged();



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // nothing
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // nothing
    }

    @Override
    public void onPageSelected(int position) {


    }

    public class PagerAdapter extends FragmentPagerAdapter {


        private final String[] TITLES = { "Anushthaan", "Spandan", "Natya", "Nukkad", "Arpeggio", "SoundTrack", "Switch The Funk Up", "Livewire","Paridhan","Sahitya"};


        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }



        @Override
        public CharSequence getPageTitle(int position) {
            return gallery[position];
        }

        @Override
        public int getCount() {
            return gallery.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =(Fragment) GalleryFragment.newInstance(position);

            return fragment;
        }




    }
}
