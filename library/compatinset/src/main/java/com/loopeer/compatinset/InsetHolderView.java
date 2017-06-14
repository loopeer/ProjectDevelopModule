package com.loopeer.compatinset;


import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;

import com.loopeer.compatinset.statusbar.StatusBarFontHelper;

import java.lang.reflect.Field;

public class InsetHolderView extends View {
    WindowInsetsCompat mLastInsets;
    static final boolean SHOW_INSET_HOLDER;
    private int mStatusBarColor;
    private int mStatusBarDarkColor;

    static {
        if (Build.VERSION.SDK_INT >= 19) {
            SHOW_INSET_HOLDER = true;
        } else {
            SHOW_INSET_HOLDER = false;
        }
    }

    public InsetHolderView(Context context) {
        this(context, null);
    }

    public InsetHolderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public InsetHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InsetHolderView,
                defStyleAttr, R.style.Widget_CompatInset_InsetHolderView);
        mStatusBarColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor
                , ContextCompat.getColor(context, android.R.color.transparent));

        mStatusBarDarkColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor
                , -1);
        int style = a.getInt(R.styleable.InsetHolderView_insetStatusBarStyle
                , 0);
        if (style == 1) {
            StatusBarFontHelper.setStatusBarMode(((Activity) context), false);
        } else if (style == 2) {
            StatusBarFontHelper.setStatusBarMode(((Activity) context), true);
        }

        if (mStatusBarDarkColor != -1 && style == 0) {
            int result = StatusBarFontHelper.setStatusBarMode(((Activity) context), false);
            if (result < 1)
                mStatusBarColor = mStatusBarDarkColor;
        }
        ViewCompat.setOnApplyWindowInsetsListener(this,
                new android.support.v4.view.OnApplyWindowInsetsListener() {
                    @Override
                    public WindowInsetsCompat onApplyWindowInsets(View v,
                                                                  WindowInsetsCompat insets) {
                        return onWindowInsetChanged(insets);
                    }
                });
    }

    WindowInsetsCompat onWindowInsetChanged(final WindowInsetsCompat insets) {
        WindowInsetsCompat newInsets = null;
        if (ViewCompat.getFitsSystemWindows(this)) {
            newInsets = insets;
        }
        if (!ViewUtils.objectEquals(mLastInsets, newInsets)) {
            mLastInsets = newInsets;
            requestLayout();
        }
        return insets.consumeSystemWindowInsets();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        InsetHelper.requestApplyInsets(this);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getInsetHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(mStatusBarColor);
    }

    private int getInsetHeight() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            return getStatusBarHeight(getContext());
        }
        return SHOW_INSET_HOLDER && mLastInsets != null ? mLastInsets.getSystemWindowInsetTop() : 0;
    }

    public static int getStatusBarHeight(Context context) {
        Class<?> c;
        Object obj;
        Field field;

        int x, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    public void setStatusBarColor(int statusBarColor) {
        mStatusBarColor = statusBarColor;
        invalidate();
    }

    public void setStatusBarDarkColor(int statusBarDarkColor) {
        mStatusBarDarkColor = statusBarDarkColor;
        invalidate();
    }
}
