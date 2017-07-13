package com.loopeer.formitemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

public class FormTextItem extends AbstractFormItemView {
    private Drawable mDrawableRight;
    private int mImageRightVisible;

    public FormTextItem(Context context) {
        this(context, null);
    }


    public FormTextItem(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.formTextItemStyle);
    }


    public FormTextItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override int getLayoutId() {
        return R.layout.form_text_item;
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormTextItem,
            defStyleAttr, 0);

        setDescText(a.getString(R.styleable.FormTextItem_descText));
        setDescTextColor(a.getColor(R.styleable.FormTextItem_descTextColor,
            ContextCompat.getColor(context, R.color.form_item_default_desc_text_color)));
        setDescTextSize(a.getDimensionPixelSize(R.styleable.FormTextItem_descTextSize,
            getResources().getDimensionPixelSize(R.dimen.small_padding)));
        setDescTextDrawableLeft(a.getDrawable(R.styleable.FormTextItem_descDrawableLeft));

        setContentText(a.getString(R.styleable.FormTextItem_contentText));
        setContentTextColor(a.getColor(R.styleable.FormTextItem_contentTextColor,
            ContextCompat.getColor(context, R.color.form_item_default_content_text_color)));
        setContentTextSize(a.getDimensionPixelSize(R.styleable.FormTextItem_contentTextSize,
            getResources().getDimensionPixelSize(R.dimen.text_size_large)));
        setContentHint(a.getString(R.styleable.FormTextItem_contentHint));
        setDescMinWidth(a.getDimensionPixelSize(R.styleable.FormTextItem_descTextMinWidth,80));

        Drawable contentDrawableRight = a.getDrawable(
            R.styleable.FormTextItem_contentDrawableRight);
        setContentTextDrawableRight(contentDrawableRight);

        mDrawableRight = a.getDrawable(R.styleable.FormTextItem_drawableRight);
        setImageRight(mDrawableRight);

        mImageRightVisible = a.getInt(R.styleable.FormTextItem_imageRightVisible, VISIBLE);

        setFormItemEnabled(a.getBoolean(R.styleable.FormTextItem_formEnable,true));

        int index = a.getInt(R.styleable.FormTextItem_contentGravity, Gravity.LEFT|Gravity.CENTER_VERTICAL);
        if (index >= 0) {
            mContentText.setGravity(index);
        }

        a.recycle();

        updateDrawableRight();
    }

    public void setDescMinWidth(@Px int minWidth){
        mDescText.setMinWidth(minWidth);
    }

    @Override public void setInputType(int type) {

    }

    @Override public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mContentText.setEnabled(enabled);
        if (!enabled) {
            mImageRight.setVisibility(View.GONE);
        } else {
            updateDrawableRight();
        }
    }

    @Override public void setFormItemEnabled(boolean enable) {
        setEnabled(enable);
        setClickable(enable);
    }

    private void updateDrawableRight() {
        if (mDrawableRight == null) {
            setImageRight(R.drawable.form_item_default_drawable_right);
        } else {
            setImageRight(mDrawableRight);
        }
        mImageRight.setVisibility(mImageRightVisible);
    }


    public void setImageRight(Drawable drawable) {
        mImageRight.setImageDrawable(drawable);
    }

    public void setImageRight(@DrawableRes int resDrawable) {
        mImageRight.setImageResource(resDrawable);
    }

    public void setImageRightVisible(int visibility){
        mImageRight.setVisibility(visibility);
    }

}
