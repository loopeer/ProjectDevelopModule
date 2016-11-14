package com.loopeer.compatinset;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class InsetLinearLayout extends LinearLayout {

    public InsetLinearLayout(Context context) {
        this(context, null);
    }

    public InsetLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
