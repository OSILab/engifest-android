package com.dtu.engifest.sponsors;

/**
 * Created by naman on 11/01/15.
 */
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;


public  class SponsorsActivity extends ActionBarActivity {




    protected AbsListView listView;
    DisplayImageOptions options;
    private ReadFromJSON mReadFromJSON;
    private ImageAdapter mImageAdapter;
    public List<String> list;
    private SmoothProgressBar progressBar;


     
    public String loadJSONFromAsset() {
        String jsonString = "";
        try {
            String currentLine;
            File cacheFile = new File(getApplicationContext().getFilesDir(), "sponsors.json");

            BufferedReader br = new BufferedReader(new FileReader(cacheFile));
            while ((currentLine = br.readLine()) != null) {
                jsonString += currentLine + '\n';
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return jsonString;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_sponsor);
        listView = (GridView) findViewById(R.id.grid);
        progressBar =(SmoothProgressBar) findViewById(R.id.google_now);
        
      //if network connected, initiate the async task,json will be updated in background
        if (NetworkUtil.isNetworkConnected(this)) {
            updateView();
        }
      //if network not connected, load items from json stored in data
      //we always tries to load json from data even if theres is network to ensure
      //that the content is loaded instatnly
         try {
             JSONObject obj = new JSONObject(loadJSONFromAsset());
             JSONArray images = obj.getJSONArray("images");

             list = new ArrayList<String>();
             for (int i = 0; i < images.length(); i++) {
                  list.add(images.getString(i));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        //if user opens activity for first time without internet connection then list will be null and we dont want
        // the adpter to set as it will throw nullpointer on list.size()
        if (list==null){
            progressBar.setVisibility(View.VISIBLE);
        }
        else {
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        
        }
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_stub)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.error_view_cloud)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery, menu);
        return true;
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

    public void updateView() {
        mReadFromJSON = new ReadFromJSON();
        mReadFromJSON.execute();
    }

    private class ReadFromJSON extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... v) {



            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(new HttpGet("http://engifesttest.comlu.com/sponsors"));
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                return result;
            } catch (Exception e) {

                Log.d("[GET REQUEST]", "Network exception", e);
                return null;
            }
        }




        protected void onPostExecute(String r) {


            File cacheFile = new File(getFilesDir(), "sponsors.json");

            BufferedWriter bw = null;


            try {
                if (!cacheFile.exists()) {
                    cacheFile.createNewFile();
                }

                FileWriter fw = new FileWriter(cacheFile.getAbsoluteFile());
                bw = new BufferedWriter(fw);


                bw.write(r);
            } catch (Exception e) {
                e.printStackTrace();


            } finally {
                try {
                    bw.close();
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
            
           
            try {
                JSONObject obj = new JSONObject(loadJSONFromAsset());
                JSONArray images  = obj.getJSONArray("images");

                list = new ArrayList<String>();
                for(int i = 0; i < images.length(); i++){
                    list.add(images.getString(i));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

          //adapter will be null if list==null, so we set the adpter in that case
          if (mImageAdapter==null){
              mImageAdapter = new ImageAdapter();
              listView.setAdapter(mImageAdapter);
          }
           //notifies the adapter that data has changed and to update the views
            mImageAdapter.notifyDataSetChanged();
            
            progressBar.setVisibility(View.GONE);

            


        }
    }
    public class ImageAdapter extends BaseAdapter {



        public  String imageUrls[]=list.toArray(new String[list.size()]);
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