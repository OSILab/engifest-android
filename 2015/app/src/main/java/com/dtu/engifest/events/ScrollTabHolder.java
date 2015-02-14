package com.dtu.engifest.events;


import android.widget.ScrollView;
/**
 * Created by naman on 14/12/14.
 */
public interface ScrollTabHolder {

	void adjustScroll(int scrollHeight, int headerTranslationY);
    void onScroll(ScrollView view, int x, int y, int oldX, int oldY, int pagePosition);


}
