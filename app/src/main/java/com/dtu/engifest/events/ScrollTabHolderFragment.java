package com.dtu.engifest.events;

import android.support.v4.app.Fragment;
import android.widget.ScrollView;

import com.dtu.engifest.events.ScrollTabHolder;

public abstract class ScrollTabHolderFragment extends Fragment implements ScrollTabHolder {

	protected ScrollTabHolder mScrollTabHolder;

	public void setScrollTabHolder(ScrollTabHolder scrollTabHolder) {
		mScrollTabHolder = scrollTabHolder;
	}

	@Override
    public void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition) {
		// nothing
	}

}