package com.loopeer.compatinset;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ViewAnimator;

public class InsetViewAnimator extends ViewAnimator {
    public InsetViewAnimator(Context context) {
        this(context, null);
    }

    public InsetViewAnimator(Context context, AttributeSet attrs) {
        super(context, attrs);
        InsetHelper.setupForInsets(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected boolean fitSystemWindows(Rect insets) {
        return super.fitSystemWindows(InsetHelper.clearInset(insets));
    }
}
