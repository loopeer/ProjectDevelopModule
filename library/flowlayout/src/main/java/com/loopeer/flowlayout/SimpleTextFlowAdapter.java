package com.loopeer.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

public abstract class SimpleTextFlowAdapter extends FlowAdapter {

    int mStyleRes;

    float mTextSize;
    int mTextColor;
    int mPaddingTop;

    public SimpleTextFlowAdapter(FlowLayout flowLayout, Context context, int styleRes) {
        super(flowLayout, context);
        mStyleRes = styleRes;
        TypedArray array = getContext().obtainStyledAttributes(mStyleRes,
                R.styleable.FlowLayoutDescText);
        mTextSize = array.getDimensionPixelSize(
                R.styleable.FlowLayoutDescText_android_textSize, 0);
        mTextColor = array.getColor(
                R.styleable.FlowLayoutDescText_android_textColor, Color.BLACK);
        mPaddingTop = array.getDimensionPixelSize(
                R.styleable.FlowLayoutDescText_android_paddingTop, 0);
        array.recycle();
    }

    @Override
    public View getItemView(int position) {
        if (TextUtils.isEmpty(getDesc(position)))
            return null;
        TextView view = new TextView(getContext());
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        view.setTextColor(mTextColor);
        view.setText(getDesc(position));
        view.setGravity(Gravity.CENTER);
        view.setPadding(0, mPaddingTop, 0, 0);
        return view;
    }

    public abstract String getDesc(int position);

}
