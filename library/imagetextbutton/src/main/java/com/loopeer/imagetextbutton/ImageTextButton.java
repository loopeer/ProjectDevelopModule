package com.example.owen.stud.viewPaint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.owen.stud.R;

import java.lang.ref.Reference;

/**
 * Created by owen on 2017/8/2.
 */

public class ImageTextButton extends FrameLayout {

    private TextView mTxt;

    Drawable drawable = null;


    public ImageTextButton(@NonNull Context context) {
        this(context, null);
    }

    public ImageTextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @SuppressLint("ResourceType")
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.image_text_button, this);
        mTxt = (TextView) findViewById(R.id.img_txt_btn_txt);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ImageTextButton, defStyleAttr, 0);
        setmTxt(a.getString(R.styleable.ImageTextButton_img_txt_btn_text));
        setmTxtPic(a.getDrawable(R.styleable.ImageTextButton_img_txt_btn_pic));
        setTxtColor(a.getColor(R.styleable.ImageTextButton_img_txt_btn_text_color,
                ContextCompat.getColor(context, R.color.colorAccent)));
        setTxtSize(a.getDimensionPixelSize(R.styleable.ImageTextButton_img_txt_btn_text_size, 36));
        setTxtPadding(a.getDimensionPixelSize(R.styleable.ImageTextButton_img_txt_btn_padding, 5));
        a.recycle();
    }

    /**
     * 按钮文字
     *
     * @param desc
     */
    public void setmTxt(CharSequence desc) {
        mTxt.setText(desc);

    }


    /**
     * 按钮左侧图标
     *
     * @param drawable
     */
    public void setmTxtPic(Drawable drawable) {
        mTxt.setCompoundDrawablesWithIntrinsicBounds(drawable, null, null, null);
    }

    /**
     * 按钮字体大小
     *
     * @param size
     */
    public void setTxtSize(@Px int size) {
        mTxt.setTextSize(size);
    }

    /**
     * 字体颜色
     *
     * @param color
     */
    public void setTxtColor(int color) {
        mTxt.setTextColor(color);
    }

    public void setTxtPadding(@Px int padding) {
        mTxt.setCompoundDrawablePadding(padding);
    }

}
