package com.loopeer.developutils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class DoubleTouchListener implements View.OnTouchListener {

    private GestureDetector mGestureDetector;
    private DoubleClickListener mDoubleClickListener;

    public DoubleTouchListener(Context context, DoubleClickListener doubleClickListener) {
        mGestureDetector = new GestureDetector(context, new GestureListener());
        mDoubleClickListener = doubleClickListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mDoubleClickListener.onDoubleClick(e);
            return true;
        }
    }

    public interface DoubleClickListener {
        void onDoubleClick(MotionEvent e);
    }

}
