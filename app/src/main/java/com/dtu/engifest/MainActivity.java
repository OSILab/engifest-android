package com.dtu.engifest;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dtu.engifest.about.AboutActivity;
import com.dtu.engifest.fragments.HomeFragment;
import com.dtu.engifest.fragments.MapsFragment;
import com.dtu.engifest.fragments.ScheduleFragment;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;


    @Override
	protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();


        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);



        navDrawerItems = new ArrayList<NavDrawerItem>();


        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));

        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));

        navMenuIcons.recycle();


        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());


        adapter = new NavDrawerListAdapter(getApplicationContext(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);


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

        if (savedInstanceState == null) {

            displayView(0);
        }
    }


    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, final int position,
                                long id) {
            mDrawerLayout.closeDrawer(mDrawerList);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    displayView(position);
                }
            }, 300);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);

        return super.onPrepareOptionsMenu(menu);
    }


    private void displayView(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new ScheduleFragment();
                break;
            case 2:
                fragment = new MapsFragment();
                break;
            case 3:
                Intent intentAbout = new Intent(getApplication(),AboutActivity.class);
                startActivity(intentAbout);
                break;
            case 4:

                Intent intentFeedback = new Intent(getApplication(),FeedbackActivity.class);
                startActivity(intentFeedback);
                break;
            case 5:

                break;
            case 6:
                fragment = new HomeFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_container, fragment).commit();


            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
        } else {

            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
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

}