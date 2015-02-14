package in.ac.dtu.engifest.fragments;

import android.app.ActionBar;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.astuetz.PagerSlidingTabStrip.IconTabProvider;

import in.ac.dtu.engifest.R;
import in.ac.dtu.engifest.Utils;

/**
 * Created by omerjerk on 20/1/14.
 */
public class EventDetailsFragment extends DialogFragment {

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private ContactPagerAdapter adapter;

    private int i;
    private int j;

    public static EventDetailsFragment newInstance(int i, int j) {
        EventDetailsFragment f = new EventDetailsFragment(i, j);
        return f;
    }

    public EventDetailsFragment(int i, int j) {
        this.i = i;
        this.j = j;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        View root = inflater.inflate(R.layout.fragment_event_details, container, false);

        tabs = (PagerSlidingTabStrip) root.findViewById(R.id.tabs);
        pager = (ViewPager) root.findViewById(R.id.pager);
        adapter = new ContactPagerAdapter();

        pager.setAdapter(adapter);

        tabs.setViewPager(pager);

        ImageView mImageView = (ImageView) root.findViewById(R.id.image);
        mImageView.setBackgroundDrawable(Utils.getEventDrawable(getActivity(), Utils.eventNamesDay[i][j]));

        return root;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onStart() {
        super.onStart();

        // change dialog width
        if (getDialog() != null) {

            int fullWidth;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);
                fullWidth = size.x;
            } else {
                Display display = getActivity().getWindowManager().getDefaultDisplay();
                fullWidth = display.getWidth();
            }

            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                    .getDisplayMetrics());

            int w = fullWidth - padding;
            int h = getDialog().getWindow().getAttributes().height;

            getDialog().getWindow().setLayout(w, h);
        }
    }

    public class ContactPagerAdapter extends PagerAdapter implements IconTabProvider {

        private final int[] ICONS = { R.drawable.ic_launcher, R.drawable.contact};

        public ContactPagerAdapter() {
            super();
        }

        @Override
        public int getCount() {
            return ICONS.length;
        }

        @Override
        public int getPageIconResId(int position) {
            return ICONS[position];
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // looks a little bit messy here
            TextView v = new TextView(getActivity());
            v.setBackgroundResource(R.color.background_window);
            switch(position) {
                case 0:
                    v.setText(Utils.eventDesc[i][j]);
                    /*
                    ViewGroup.LayoutParams params = container.getLayoutParams();
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT; */
                    break;
                case 1:
                    v.setText(Utils.contacts[i][j]);
            }
            final int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 11, getResources()
                    .getDisplayMetrics());
            v.setPadding(padding, padding, padding, padding);
            v.setGravity(Gravity.LEFT);
            container.addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object view) {
            container.removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View v, Object o) {
            return v == ((View) o);
        }

    }
}
