package com.dtu.engifest.sponsors;

/**
 * Created by naman on 11/01/15.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public  class SponsorsActivity extends ActionBarActivity {


    private static final String TAG = SponsorsActivity.class.getSimpleName();
    protected AbsListView listView;
    DisplayImageOptions options;
    private ImageAdapter mImageAdapter;
    public String imageUrls[],updatedImageUrls[];
    private SmoothProgressBar progressBar;
    private LinearLayout errorLayout;
    private String URL_SPONSORS = "http://engifesttest.comlu.com/sponsors";



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sponsor);
        listView = (GridView) findViewById(R.id.grid);
        progressBar =(SmoothProgressBar) findViewById(R.id.google_now);
        errorLayout=(LinearLayout) findViewById(R.id.error);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        final Cache.Entry entry = cache.get(URL_SPONSORS);
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

        } if (NetworkUtil.isNetworkConnected(this)){

            JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET,
                    URL_SPONSORS, null, new Response.Listener<JSONObject>() {

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


            AppController.getInstance().addToRequestQueue(jsonReq);
        }

        if (entry==null&&!NetworkUtil.isNetworkConnected(this)){
            progressBar.setVisibility(View.GONE);
            errorLayout.setVisibility(View.VISIBLE);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.error_view_cloud)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();



    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("images");
            imageUrls = new String[feedArray.length()];
            for (int i = 0; i < feedArray.length(); i++) {

                imageUrls[i]=feedArray.getString(i);

            }
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("images");
            updatedImageUrls = new String[feedArray.length()];
            for (int i = 0; i < feedArray.length(); i++) {

                updatedImageUrls[i]=feedArray.getString(i);

            }
            imageUrls=updatedImageUrls;
            progressBar.setVisibility(View.GONE);

            //for first time when cache will be null(entry==null),adapter will be null so
            //we set the adapter here in that case
            if (mImageAdapter==null){
                mImageAdapter = new ImageAdapter();
                listView.setAdapter(mImageAdapter);
            }
            mImageAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case R.id.item_clear_memory_cache:
                ImageLoader.getInstance().clearMemoryCache();
                return true;
            case R.id.item_clear_disc_cache:
                ImageLoader.getInstance().clearDiskCache();
                return true;
            default:
                return false;
        }

    }


    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        ImageAdapter() {
            inflater = LayoutInflater.from(getApplicationContext());
        }

        @Override
        public int getCount() {
            return imageUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            View view = convertView;
            if (view == null) {
                view = inflater.inflate(R.layout.grid_image, parent, false);
                holder = new ViewHolder();
                assert view != null;
                holder.imageView = (ImageView) view.findViewById(R.id.image);
                holder.progressBar = (ProgressBar) view.findViewById(R.id.progress);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            ImageLoader.getInstance()
                    .displayImage(imageUrls[position], holder.imageView, options, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingStarted(String imageUri, View view) {
                            holder.progressBar.setProgress(0);
                            holder.progressBar.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    }, new ImageLoadingProgressListener() {
                        @Override
                        public void onProgressUpdate(String imageUri, View view, int current, int total) {
                            holder.progressBar.setProgress(Math.round(100.0f * current / total));
                        }
                    });

            return view;
        }
    }
    protected void startImagePagerActivity(int position) {
        Intent intent = new Intent(this, DetailImage.class);
        intent.putExtra(Extra.IMAGE_POSITION, position);
        startActivity(intent);
    }
    static class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }
    public static class Extra {
        public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
    }
}