package in.ac.dtu.engifest.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.cardsui.Card;
import com.afollestad.cardsui.CardBase;
import com.afollestad.cardsui.CardHeader;
import com.afollestad.cardsui.CardListView;
import com.astuetz.PagerSlidingTabStrip;

import in.ac.dtu.engifest.EventsCardAdapter;
import in.ac.dtu.engifest.MainActivity;
import in.ac.dtu.engifest.R;

/**
 * Created by omerjerk on 19/12/13.
 */
public class EventsFragment extends Fragment {

    private static final String TAG = "EventsFragment";

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * The number of pages to show.
     */
    private static final int NUM_PAGES = 3;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static EventsFragment newInstance(int sectionNumber) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public EventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) rootView.findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        // Bind the tabs to the ViewPager
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
        tabs.setViewPager(mPager);

        return rootView;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {

        String[] tabsItems = {"Day I", "Day II", "Day III"};
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabsItems[position];
        }

        @Override
        public Fragment getItem(int position) {
            return new EventsSlidingFragment(position);
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    private class EventsSlidingFragment extends Fragment {

        int position;

        public EventsSlidingFragment() {}

        public EventsSlidingFragment(int position) {
            this.position = position;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View v = inflater.inflate(R.layout.fragment_sliding_events, container, false);

            CardListView eventList = (CardListView) v.findViewById(android.R.id.list);

            EventsCardAdapter adapter = new EventsCardAdapter(getActivity(), position);
            switch(position) {
                case 0:
                    adapter.add(new CardHeader("14th February"));
                    adapter.add(new Card("THE CLASSICAL & FOLK DANCE COMPETITION", "Dr. B. R. Ambedkar Auditorium"));
                    adapter.add(new Card("THE WESTERN DANCE COMPETITION", "Dr. B. R. Ambedkar Auditorium"));
                    break;
                case 1:
                    adapter.add(new CardHeader("15th February"));
                    adapter.add(new Card("ARPEGGIO", "Sports Complex, DTU"));
                    adapter.add(new Card("THE STAGE PLAY COMPETITION", "Dr. B. R. Ambedkar Auditorium"));
                    adapter.add(new Card("THE STREET DANCE COMPETITION", "Open Amphitheatre (OAT)"));
                    break;
                case 2:
                    adapter.add(new CardHeader("16th February"));
                    adapter.add(new Card("THE STREET PLAY COMPETITION", "Mini OAT, DTU"));
                    adapter.add(new Card("THE MUSIC COMPETITION", "Dr. B. R. Ambedkar Auditorium"));
            }
            eventList.setAdapter(adapter);

            eventList.setOnCardClickListener(new CardListView.CardClickListener() {
                @Override
                public void onCardClick(int index, CardBase card, View view) {
                    EventDetailsFragment dialog = EventDetailsFragment.newInstance(position, index - 1);
                    dialog.show(getActivity().getSupportFragmentManager(), "EventsDetailsFragment");
                }
            });

            return v;
        }

    }
}
