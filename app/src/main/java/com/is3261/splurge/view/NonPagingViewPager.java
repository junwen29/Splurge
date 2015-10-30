package com.is3261.splurge.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by junwen29 on 10/30/2015.
 *
 * custom viewpager to disable paging
 */
public class NonPagingViewPager extends ViewPager {

    private boolean mSwipeEnabled;

    public NonPagingViewPager(Context context) {
        super(context);
    }

    public NonPagingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mSwipeEnabled && super.onInterceptTouchEvent(ev);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return mSwipeEnabled && super.onTouchEvent(ev);
    }

    public void setPagingEnabled(boolean enabled){
        mSwipeEnabled = enabled;
    }
}
