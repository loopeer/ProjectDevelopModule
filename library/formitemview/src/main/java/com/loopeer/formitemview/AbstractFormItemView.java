package com.loopeer.formitemview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Px;
import android.support.annotation.StringRes;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

public abstract class AbstractFormItemView extends ForegroundLinearLayout implements FormItemView {
    protected TextView mDescText;
    protected TextView mContentText;
    protected ImageView mImageRight;


    public AbstractFormItemView(Context context) {
        super(context);
    }

    public AbstractFormItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AbstractFormItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = LayoutInflater.from(context).inflate(getLayoutId(), this, true);
        mDescText = (TextView) view.findViewById(R.id.txtDesc);
        mContentText = (TextView) view.findViewById(R.id.txtContent);
        mImageRight = (ImageView) view.findViewById(R.id.imgArrow);

    }

    abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public void setDescText(CharSequence desc) {
        mDescText.setText(desc);
    }

    @Override
    public void setDecText(@StringRes int StringResId) {
        mDescText.setText(StringResId);
    }

    @Override
    public void setDescTextColor(@ColorInt int color) {
        mDescText.setTextColor(color);
    }

    @Override
    public Editable getDescText() {
        return (Editable) mDescText.getText();
    }

    @Override
    public void setDescTextDrawableLeft(Drawable drawable) {
        mDescText.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    @Override
    public void setDescTextDrawableLeft(@DrawableRes int resDrawable) {
        mDescText.setCompoundDrawablesWithIntrinsicBounds(resDrawable, 0, 0, 0);
    }

    @Override
    public void setDescMinWidth(@Px int minWidth) {
        mDescText.setMinWidth(minWidth);
    }

    @Override
    public void setDescTextMinEms(int minEms) {
        mDescText.setMinEms(minEms);
    }

    @Override
    public void setContentHint(CharSequence hint) {
        mContentText.setHint(hint);
    }

    @Override
    public void setContentMaxLines(int max) {
        mContentText.setMaxLines(max);
    }

    @Override
    public void setContentHintTextColor(@ColorInt int color) {
        mContentText.setHintTextColor(color);
    }

    @Override
    public void setContentTextColor(@ColorInt int color) {
        mContentText.setTextColor(color);
    }

    @Override
    public void setContentText(CharSequence content) {
        mContentText.setText(content);
    }

    @Override
    public String getContentText() {
        return mContentText.getText().toString();
    }

    @Override
    public void setContentTextSize(float size) {
        mContentText.setTextSize(COMPLEX_UNIT_PX, size);
    }

    @Override
    public void setContentTextDrawableRight(Drawable drawable) {
        mContentText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }

    @Override
    public void setContentTextDrawableRight(@DrawableRes int resDrawable) {
        mContentText.setCompoundDrawablesWithIntrinsicBounds(0, 0, resDrawable, 0);
    }

    @Override
    public void setContentMaxLength(int max) {
        mContentText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max)});
    }

    @Override
    public void removeContentTextChangedListener(TextWatcher watcher) {
        mContentText.removeTextChangedListener(watcher);
    }

    @Override
    public void addContentTextChangedListener(TextWatcher watcher) {
        mContentText.addTextChangedListener(watcher);
    }

    @Override
    public void setDescTextSize(float size) {
        mDescText.setTextSize(COMPLEX_UNIT_PX, size);
    }
}


