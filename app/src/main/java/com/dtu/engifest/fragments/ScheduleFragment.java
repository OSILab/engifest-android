package com.dtu.engifest.fragments;

/**
 * Created by naman on 17/12/14.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;
import com.dtu.engifest.R;


public class ScheduleFragment extends Fragment{

    private PagerSlidingTabStrip mPagerSlidingTabStrip;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_schedule_viewpager, container, false);
        mPagerSlidingTabStrip = (PagerSlidingTabStrip) v.findViewById(R.id.tabs);
        mViewPager = (ViewPager) v.findViewById(R.id.pager);
        mViewPager.setOffscreenPageLimit(4);
        mPagerAdapter = new PagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
    public class PagerAdapter extends FragmentPagerAdapter {


        private final String[] TITLES = { "Day 1", "Day 2", "Day 3", "Day 4"};

        public PagerAdapter(FragmentManager fm) {
            super(fm);

        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment =new DaysScheduleFragment();
            Bundle b = new Bundle();
            b.putInt(DaysScheduleFragment.ARG_POSITION, position);
            fragment.setArguments(b);
            return fragment;
        }

    }
}
