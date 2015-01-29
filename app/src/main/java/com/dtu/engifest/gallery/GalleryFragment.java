package com.dtu.engifest.gallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.Cache;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by naman on 29/01/15.
 */
public class GalleryFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    private static final String TAG = GalleryFragment.class.getSimpleName();
    protected AbsListView listView;
    DisplayImageOptions options;
    private ImageAdapter mImageAdapter;
    public static String imageUrls[],updatedImageUrls[];


    private String URL_GALLERY = "http://engifesttest.comlu.com/gallery";
    private int mPosition;
    JSONObject obj;
    View v;

    public static Fragment newInstance(int position) {
        GalleryFragment f = new GalleryFragment();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPosition = getArguments().getInt(ARG_POSITION);

    }
    public String loadJSONFRomCache() {

        String data = "";
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_GALLERY);
        if (entry != null) {
            // fetch the data from cache
            try {
                data = new String(entry.data, "UTF-8");

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        return data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_gallery,container,false);
        listView = (GridView) v.findViewById(R.id.grid);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startImagePagerActivity(position);
            }
        });

        if (mPosition==0) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(0);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==1) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(1);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==2) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(2);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==3) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(3);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==4) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(4);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==5) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(5);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==6) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(6);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==7) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(7);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==8) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(8);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==9) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(9);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==10) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(10);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==11) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(11);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==12) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(12);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==13) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(13);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }
        if (mPosition==14) {
            try {

                obj = new JSONObject(loadJSONFRomCache());
                JSONArray array = obj.getJSONArray("images");
                JSONArray images = array.getJSONArray(14);
                int length = images.length();
                imageUrls=new String[length];
                if(array!=null){
                    for(int i=0;i<length;i++){
                        imageUrls[i]= images.optString(i);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (mImageAdapter!=null)
                mImageAdapter=null;
            mImageAdapter = new ImageAdapter();
            listView.setAdapter(mImageAdapter);
        }


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.error_view_cloud)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        return v;
    }




    public class ImageAdapter extends BaseAdapter {

        private LayoutInflater inflater;

        ImageAdapter() {
            inflater = LayoutInflater.from(getActivity());
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
        Intent intent = new Intent(getActivity(), DetailGallery.class);
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
