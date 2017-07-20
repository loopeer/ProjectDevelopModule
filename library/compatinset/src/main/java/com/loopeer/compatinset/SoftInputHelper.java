package com.loopeer.compatinset;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

public class SoftInputHelper {

    private Context mContext;
    private View mView;

    public SoftInputHelper(View view) {
        mContext = view.getContext();
        mView = view;
    }

    public static void applySoftCompat(View view) {
        SoftInputHelper softInputHelper = new SoftInputHelper(view);
        softInputHelper.addListener();
    }

    private void addListener() {
        final View decorView = ((Activity) mContext).getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                decorView.getWindowVisibleDisplayFrame(rect);
                int screenHeight = decorView.getRootView().getHeight();
                int heightDifference = screenHeight - rect.bottom;
                int naviBarHeight = getNavBarHeight(mContext);
                heightDifference -= naviBarHeight;
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mView.getLayoutParams();
                if (layoutParams.bottomMargin == heightDifference) return;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    layoutParams.bottomMargin = heightDifference;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                        layoutParams.height = rect.bottom - mView.getTop();
                    }
                    mView.requestLayout();
                }
            }
        });
    }

    public int getNavBarHeight(Context c) {
        int result = 0;
        Resources resources = mContext.getResources();
        int orientation = mContext.getResources().getConfiguration().orientation;

        if (isNavigationBarShow()) {
            int resourceId;
            if (isTablet(c)) {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape", "dimen", "android");
            } else {
                resourceId = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_width", "dimen", "android");
            }

            if (resourceId > 0) {
                return mContext.getResources().getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    public boolean isNavigationBarShow() {
        final View decorView = ((Activity) mContext).getWindow().getDecorView();
        int screenHeight = decorView.getRootView().getHeight();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        if (screenHeight != height) return true;
        return false;
    }

    private boolean isTablet(Context c) {
        return (c.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }
}
