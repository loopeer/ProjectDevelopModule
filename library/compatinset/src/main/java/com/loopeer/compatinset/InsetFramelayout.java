package com.loopeer.compatinset;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class InsetFramelayout extends FrameLayout {

    public InsetFramelayout(Context context) {
        this(context, null);
    }

    public InsetFramelayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetFramelayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InsetHelper.setupForInsets(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InsetHolderView,
                defStyleAttr, R.style.Widget_CompatInset_InsetHolderView);
        boolean softCompat = a.getBoolean(R.styleable.InsetHolderView_softCompat
                , false);
        if (softCompat) SoftInputHelper.applySoftCompat(this);
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
