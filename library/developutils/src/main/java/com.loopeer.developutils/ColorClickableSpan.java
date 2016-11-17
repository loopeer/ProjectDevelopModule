package com.loopeer.developutils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.text.style.ClickableSpan;

public abstract class ColorClickableSpan extends ClickableSpan {
    public int mColorRes;
    public Context mContext;
    public boolean mUnderline;
    public boolean mBold;

    public ColorClickableSpan(Context context, @ColorRes int color, boolean underline, boolean bold) {
        mContext = context;
        mColorRes = color;
        mUnderline = underline;
        mBold = bold;
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(ContextCompat.getColor(mContext, mColorRes));
        tp.setUnderlineText(mUnderline);
        tp.setFakeBoldText(mBold);
    }
}
