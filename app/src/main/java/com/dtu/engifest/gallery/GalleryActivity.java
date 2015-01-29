package com.dtu.engifest.gallery;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
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
public class GalleryActivity extends ActionBarActivity  {

    private static final String TAG = GalleryActivity.class.getSimpleName();

    private String URL_GALLERY = "http://engifesttest.comlu.com/gallery";
    public String gallery[],updatedGallery[];

    private SmoothProgressBar progressBar;
    private LinearLayout errorLayout;

    private DrawerLayout mDrawerLayout;
    private LinearLayout mLiearLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;



    private GalleryListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);



        progressBar =(SmoothProgressBar) findViewById(R.id.google_now);
        errorLayout=(LinearLayout) findViewById(R.id.error);


        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLiearLayout = (LinearLayout) findViewById(R.id.drawer_view);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());




        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);

                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);

                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);




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
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position,
                                long id) {
            mDrawerLayout.closeDrawer(mLiearLayout);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayView(position);
                }
            }, 300);
        }
    }
    private void displayView(int position) {

        Fragment fragment = null;
        switch (position) {

            default:
                fragment =GalleryFragment.newInstance(position);

                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();


            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(gallery[position]);
        } else {

            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }



    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray galleryArray = response.getJSONArray("gallery");
            gallery = new String[galleryArray.length()];
            for (int i = 0; i < galleryArray.length(); i++) {

                gallery[i]=galleryArray.getString(i);

            }
            displayView(0);
            adapter = new GalleryListAdapter(getApplicationContext(),
                    gallery);
            mDrawerList.setAdapter(adapter);

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

            if (adapter==null){
                adapter = new GalleryListAdapter(getApplicationContext(),
                        gallery);
                mDrawerList.setAdapter(adapter);
            }
            adapter.notifyDataSetChanged();

            displayView(0);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
    public class GalleryListAdapter extends BaseAdapter {

        private Context context;
        private String gallery[];

        public GalleryListAdapter(Context context, String gallery[]){
            this.context = context;
            this.gallery = gallery;
        }

        @Override
        public int getCount() {
            return gallery.length;
        }

        @Override
        public Object getItem(int position) {
            return gallery[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.drawer_gallery_item, null);
            }


            TextView txtTitle = (TextView) convertView.findViewById(R.id.title);



            txtTitle.setText(gallery[position]);

            return convertView;
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
