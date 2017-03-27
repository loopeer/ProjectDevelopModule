package com.loopeer.bottomimagepicker;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SlideCustomViewPager extends ViewPager {
    private boolean mIsSlideEnable = true;

    public SlideCustomViewPager(Context context) {
        super(context);
    }

    public SlideCustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.mIsSlideEnable && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return this.mIsSlideEnable && super.onInterceptTouchEvent(event);
    }

    public void setSlideEnable(boolean b) {
        this.mIsSlideEnable = b;
    }
}
