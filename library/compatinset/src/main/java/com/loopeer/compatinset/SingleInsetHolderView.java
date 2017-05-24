package com.loopeer.compatinset;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;

public class SingleInsetHolderView extends View {
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

    public SingleInsetHolderView(Context context) {
        this(context, null);
    }

    public SingleInsetHolderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SingleInsetHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.InsetHolderView,
                defStyleAttr, R.style.Widget_CompatInset_InsetHolderView);
        mStatusBarColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor
                , ContextCompat.getColor(context, android.R.color.transparent));
        mStatusBarDarkColor = a.getColor(R.styleable.InsetHolderView_insetStatusBarColor
                , ContextCompat.getColor(context,  -1));
        InsetHelper.translucentStatus(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), getInsetHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int color = mStatusBarColor;
        if (mStatusBarDarkColor != -1 && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            color = mStatusBarDarkColor;
        }
        if (SHOW_INSET_HOLDER) canvas.drawColor(color);
    }

    private int getInsetHeight() {
        return SHOW_INSET_HOLDER ? getStatusBarHeight(getContext()) : 0;
    }

    public static int getStatusBarHeight(Context context){
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
}
