package com.dtu.engifest.events;



import android.annotation.TargetApi;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.astuetz.PagerSlidingTabStrip;
import com.dtu.engifest.AppController;
import com.dtu.engifest.R;
import com.dtu.engifest.util.NetworkUtil;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.nineoldandroids.view.ViewHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;

/**
 * Created by naman on 14/12/14.
 */

public class EventsActivity extends ActionBarActivity implements ScrollTabHolder, ViewPager.OnPageChangeListener {

    private static AccelerateDecelerateInterpolator sSmoothInterpolator = new AccelerateDecelerateInterpolator();

    private KenBurnsView mHeaderPicture;
    private View mHeader;
    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private Toolbar toolbar;
    private TextView title;
    private ImageView icon;
    private int mActionBarHeight;
    private int mMinHeaderHeight;
    private int mHeaderHeight;
    private int mMinHeaderTranslation;
    private ImageView mHeaderLogo;
    private RectF mRect1 = new RectF();
    private RectF mRect2 = new RectF();
    private TypedValue mTypedValue = new TypedValue();
    private SpannableString mSpannableString;
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;

    private static final String TAG = EventsActivity.class.getSimpleName();
    private String URL_EVENTS = "http://engifesttest.comlu.com/events";

    public String events[],updatedEvents[];

    int[] photos={R.drawable.photo6, R.drawable.switchthefunkup,R.drawable.photo2,R.drawable.photo3,R.drawable.photo4,R.drawable.photo5};
    KenBurnsView imageView;
    private SmoothProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMinHeaderHeight = getResources().getDimensionPixelSize(R.dimen.min_header_height);
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mMinHeaderHeight + getActionBarHeight();

        setContentView(R.layout.activity_events);

        progressBar =(SmoothProgressBar) findViewById(R.id.google_now);
        imageView =(KenBurnsView) findViewById(R.id.header_picture);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        icon = (ImageView) findViewById(R.id.icon);
        title = (TextView) findViewById(R.id.title);
        mSpannableString = new SpannableString(getString(R.string.actionbar_title));

        mHeaderLogo = (ImageView) findViewById(R.id.header_logo);
        mHeader = findViewById(R.id.header);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(7);

        mPagerSlidingTabStrip.setOnPageChangeListener(this);
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(0xffffffff);
        ViewHelper.setAlpha(getActionBarIconView(), 0f);

        getSupportActionBar().setBackgroundDrawable(null);

        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(URL_EVENTS);
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
                    URL_EVENTS, null, new Response.Listener<JSONObject>() {

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
                }
            });


            AppController.getInstance().addToRequestQueue(jsonReq);

        }


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int i=0;
            public void run() {
                // change images randomly
                Random ran=new Random();
                int i=ran.nextInt(photos.length);
                //set image resources
                imageView.setImageResource(photos[i]);
                i++;
                if(i>photos.length-1)
                {
                    i=0;
                }
                handler.postDelayed(this, 7000);  //for interval...
            }
        };
        handler.postDelayed(runnable, 7000); //for initial delay..
    }


    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray eventsArray = response.getJSONArray("events");
            events = new String[eventsArray.length()];
            for (int i = 0; i < eventsArray.length(); i++) {

                events[i]=eventsArray.getString(i);

            }
            mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
            mPagerAdapter.setTabHolderScrollingContent(this);
            mViewPager.setAdapter(mPagerAdapter);
            mPagerSlidingTabStrip.setViewPager(mViewPager);
            progressBar.setVisibility(View.GONE);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void updateJsonFeed(JSONObject response) {
        try {
            JSONArray eventsArray = response.getJSONArray("events");
            updatedEvents= new String[eventsArray.length()];
            for (int i = 0; i < eventsArray.length(); i++) {

                updatedEvents[i]=eventsArray.getString(i);

            }
            events=updatedEvents;
            progressBar.setVisibility(View.GONE);

            if (mPagerAdapter==null){
                mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

                mPagerAdapter.setTabHolderScrollingContent(this);
                mViewPager.setAdapter(mPagerAdapter);
                mPagerSlidingTabStrip.setViewPager(mViewPager);
            }
            mPagerAdapter.notifyDataSetChanged();
            mPagerAdapter.setTabHolderScrollingContent(this);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setAdapter(JSONObject response) {
        if (mPagerAdapter==null) {
            mPagerAdapter = new PagerAdapter(getSupportFragmentManager());
            mPagerAdapter.setTabHolderScrollingContent(this);
            mViewPager.setAdapter(mPagerAdapter);
            mPagerSlidingTabStrip.setViewPager(mViewPager);
        }
        else {
           mPagerAdapter.notifyDataSetChanged();
        }
        progressBar.setVisibility(View.GONE);
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
        SparseArrayCompat<ScrollTabHolder> scrollTabHolders = mPagerAdapter.getScrollTabHolders();
        ScrollTabHolder currentHolder = scrollTabHolders.valueAt(position);
        currentHolder.adjustScroll((int) (mHeader.getHeight() + ViewHelper.getTranslationY(mHeader)),(int)(mHeaderHeight));

    }

    @Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition)
    {
        if (mViewPager.getCurrentItem() == pagePosition)
        {
            mHeader.setTranslationY(Math.max(-view.getScrollY(), mMinHeaderTranslation));
            float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
            interpolate(mHeaderLogo, getActionBarIconView(), sSmoothInterpolator.getInterpolation(ratio));
            setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
        }
    }

    @Override
    public void adjustScroll(int scrollHeight,int headerTranslationY) {
        // nothing
    }



    public static float clamp(float value, float max, float min) {
        return Math.max(Math.min(value, min), max);
    }

    private void interpolate(View view1, View view2, float interpolation) {
        getOnScreenRect(mRect1, view1);
        getOnScreenRect(mRect2, view2);

        float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
        float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
        float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
        float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));

        ViewHelper.setTranslationX(view1, translationX);
        ViewHelper.setTranslationY(view1, translationY - ViewHelper.getTranslationY(mHeader));
        ViewHelper.setScaleX(view1, scaleX);
        ViewHelper.setScaleY(view1, scaleY);
    }

    private RectF getOnScreenRect(RectF rect, View view) {
        rect.set(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
        return rect;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB){
            getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        }else{
            getTheme().resolveAttribute(R.attr.actionBarSize, mTypedValue, true);
        }

        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());

        return mActionBarHeight;
    }

    private void setTitleAlpha(float alpha) {
        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        title.setText(mSpannableString);
    }

    private ImageView getActionBarIconView() {
        return icon;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        private SparseArrayCompat<ScrollTabHolder> mScrollTabHolders;
        private final String[] TITLES = { "Anushthaan", "Spandan", "Natya", "Nukkad", "Arpeggio", "SoundTrack", "Switch The Funk Up", "Livewire","Paridhan","Sahitya"};
        private ScrollTabHolder mListener;

        public PagerAdapter(FragmentManager fm) {
            super(fm);
            mScrollTabHolders = new SparseArrayCompat<ScrollTabHolder>();
        }

        public void setTabHolderScrollingContent(ScrollTabHolder listener) {
            mListener = listener;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return events[position];
        }

        @Override
        public int getCount() {
            return events.length;
        }

        @Override
        public Fragment getItem(int position) {
            ScrollTabHolderFragment fragment = (ScrollTabHolderFragment) EventsFragment.newInstance(position);
            mScrollTabHolders.put(position, fragment);
            if (mListener != null) {
                fragment.setScrollTabHolder(mListener);
            }
            return fragment;
        }


        public SparseArrayCompat<ScrollTabHolder> getScrollTabHolders() {
            return mScrollTabHolders;
        }

    }
}