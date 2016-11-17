package com.loopeer.developutils;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class ClickSpanHelper {
    private static final int INT_DEFAULT_VALUE = -1;

    private void apply(final Params params) {
        SpannableString contentSpan = new SpannableString(params.content);
        for (final Map.Entry<String, OnClickListener> entry : params.clickMap.entrySet()) {
            int[] startEnd = calculateTextStartEnd(params.content, entry.getKey());
            contentSpan.setSpan(new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    entry.getValue().onClick(widget);
                }

                @Override
                public void updateDrawState(TextPaint tp) {
                    tp.setColor(params.color == 0 ? tp.linkColor : ContextCompat.getColor(params.context, params.color));
                    tp.setUnderlineText(params.underline);
                    tp.setFakeBoldText(params.bold);
                    if (params.textPaint != null) tp.set(params.textPaint);
                }
            }, startEnd[0], startEnd[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        for (final Map.Entry<String, ClickableSpan> entry : params.clickSpanMap.entrySet()) {
            int[] startEnd = calculateTextStartEnd(params.content, entry.getKey());
            contentSpan.setSpan(entry.getValue(), startEnd[0], startEnd[1], Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        if (params.highlightColor != INT_DEFAULT_VALUE) params.textView.setHighlightColor(
                ContextCompat.getColor(params.context, params.highlightColor)
        );
        params.textView.setText(contentSpan);
        params.textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static int[] calculateTextStartEnd(String strings, String target) {
        int[] result = new int[2];
        result[0] = strings.indexOf(target);
        result[1] = result[0] + target.length();
        return result;
    }

    public static class Builder {

        Context mContext;
        Params mParams;
        ClickSpanHelper mClickSpanHelper;

        public Builder(TextView textView, @StringRes int contenRes) {
            this(textView, null);
            mParams.content = mContext.getString(contenRes);
        }

        public Builder(TextView textView, String content) {
            mContext = textView.getContext();
            mParams = new Params();
            mClickSpanHelper = new ClickSpanHelper();
            mParams.content = content;
            mParams.textView = textView;
            mParams.context = mContext;
        }

        public Builder addClickSpanParam(@StringRes int res, ClickableSpan clickableSpan) {
            addClickSpanParam(mContext.getString(res), clickableSpan);
            return this;
        }

        public Builder addClickSpanParam(@StringRes int res, OnClickListener clickListener) {
            addClickSpanParam(mContext.getString(res), clickListener);
            return this;
        }

        public Builder addClickSpanParam(String res, ClickableSpan clickableSpan) {
            mParams.clickSpanMap.put(res, clickableSpan);
            return this;
        }

        public Builder addClickSpanParam(String res, OnClickListener clickListener) {
            mParams.clickMap.put(res, clickListener);
            return this;
        }

        public Builder setColor(@ColorRes int res) {
            mParams.color = res;
            return this;
        }

        public Builder setSpanUnderLine(boolean show) {
            mParams.underline = show;
            return this;
        }

        public Builder setSpanBold(boolean bold) {
            mParams.bold = bold;
            return this;
        }

        public Builder setTextPaint(TextPaint textPaint) {
            mParams.textPaint = textPaint;
            return this;
        }

        public Builder setHighlightColor(@ColorRes int color) {
            mParams.highlightColor = color;
            return this;
        }

        public void build() {
            mClickSpanHelper.apply(mParams);
        }
    }

    static class Params {
        TextView textView;
        Context context;
        String content;
        boolean underline;
        HashMap<String, ClickableSpan> clickSpanMap = new HashMap<>();
        HashMap<String, OnClickListener> clickMap = new HashMap<>();
        int color;
        boolean bold;
        TextPaint textPaint;
        @ColorRes int highlightColor = INT_DEFAULT_VALUE;
    }

    public static interface OnClickListener {
        void onClick(View widget);
    }

}
