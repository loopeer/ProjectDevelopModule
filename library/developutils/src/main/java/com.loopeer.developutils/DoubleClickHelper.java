package com.loopeer.developutils;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class DoubleClickHelper {

    public void apply(Params params) {
        final GestureDetector gestureDetector = new GestureDetector(params.view.getContext()
                , new GestureListener(params.doubleClickListener, params.singleClickListener));
        params.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private DoubleClickListener mDoubleClickListener;
        private SingleClickListener mSingleClickListener;

        public GestureListener() {
        }

        public GestureListener(DoubleClickListener doubleClickListener, SingleClickListener singleClickListener) {
            mDoubleClickListener = doubleClickListener;
            mSingleClickListener = singleClickListener;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mDoubleClickListener != null) mDoubleClickListener.onDoubleClick(e);
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mSingleClickListener != null) mSingleClickListener.onClick(e);
            return super.onSingleTapConfirmed(e);
        }
    }

    public interface DoubleClickListener {
        void onDoubleClick(MotionEvent e);
    }

    public interface SingleClickListener {
        void onClick(MotionEvent event);
    }

    public static class Builder{

        Params mParams;

        public Builder(View view) {
            mParams = new Params();
            mParams.view = view;
        }

        public Builder setDoubleClickListener(DoubleClickListener listener) {
            mParams.doubleClickListener = listener;
            return this;
        }

        public Builder setSingleClickListener(SingleClickListener listener) {
            mParams.singleClickListener = listener;
            return this;
        }

        public void build() {
            DoubleClickHelper helper = new DoubleClickHelper();
            helper.apply(mParams);
        }
    }

    static class Params{
        View view;
        DoubleClickListener doubleClickListener;
        SingleClickListener singleClickListener;
    }
}
