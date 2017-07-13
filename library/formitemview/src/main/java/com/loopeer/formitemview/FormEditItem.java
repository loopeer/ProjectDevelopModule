package com.loopeer.formitemview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class FormEditItem extends AbstractFormItemView {

    private static final int DEFAULT_IME_OPTIONS = EditorInfo.IME_NULL;
    private static final int DEFAULT_MIN_EMS = 4;
    private static final int DEFAULT_MAX_CONTENT_MAX_LENGTH = 10;
    private static final int DEFAULT_MAX_CONTENT_MAX_LINES = 1;

    public interface OnFormClickListener {
        void onFormClick(FormEditItem item);
    }

    public FormEditItem(Context context) {
        this(context, null);
    }

    public FormEditItem(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.formEditItemStyle);
    }

    public FormEditItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override int getLayoutId() {return R.layout.form_edit_item;}

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormEditItem,
            defStyleAttr, 0);

        setDescText(a.getString(R.styleable.FormEditItem_descText));
        setDescTextColor(a.getColor(R.styleable.FormEditItem_descTextColor,
            ContextCompat.getColor(context, R.color.form_item_default_desc_text_color)));
        setDescTextSize(a.getDimensionPixelSize(R.styleable.FormEditItem_descTextSize,
            getResources().getDimensionPixelSize(R.dimen.small_padding)));
        setContentText(a.getString(R.styleable.FormEditItem_contentText));
        setContentTextColor(a.getColor(R.styleable.FormEditItem_contentTextColor,
            ContextCompat.getColor(context, R.color.form_item_default_content_text_color)));

        setContentHint(a.getString(R.styleable.FormEditItem_contentHint));
        setContentHintTextColor(a.getColor(R.styleable.FormEditItem_contentHintTextColor,
            ContextCompat.getColor(context, R.color.form_item_default_content_text_hint_color)));
        setContentTextSize(a.getDimensionPixelSize(R.styleable.FormEditItem_contentTextSize,
            getResources().getDimensionPixelSize(R.dimen.small_padding)));

        Drawable drawable = a.getDrawable(R.styleable.FormEditItem_contentDrawableRight);
        setContentTextDrawableRight(drawable);

        setDescTextMinEms(a.getInt(R.styleable.FormEditItem_descTextMinEms, DEFAULT_MIN_EMS));

        setContentMaxLength(
            a.getInt(R.styleable.FormEditItem_contentMaxLength, DEFAULT_MAX_CONTENT_MAX_LENGTH));

      /*  setMaxLines(
            a.getInt(R.styleable.FormEditItem_contentMaxLines,DEFAULT_MAX_CONTENT_MAX_LINES));*/

        setFormItemEnabled(a.getBoolean(R.styleable.FormEditItem_formEnable, true));

        //setImeOptions(a.getInt(R.styleable.FormEditItem_imeOptions,DEFAULT_IME_OPTIONS));

        setDescMinWidth(a.getDimensionPixelSize(R.styleable.FormEditItem_descTextMinWidth, 90));

        final int type = a.getInt(R.styleable.FormEditItem_contentInputType, 0);
        if (type != 0) {
            setInputType(type);
        }

        int index = a.getInt(R.styleable.FormEditItem_contentGravity, Gravity.LEFT|Gravity.CENTER_VERTICAL);
        if (index >= 0) {
            mContentText.setGravity(index);
        }

        a.recycle();

        setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                mContentText.requestFocus();
                InputMethodManager iMM = (InputMethodManager) getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
                iMM.showSoftInput(FormEditItem.this, 0);
            }
        });

    }

    public void setImeOptions(int imeOptions) {
        mContentText.setImeOptions(imeOptions);
    }

    @Override
    public void setInputType(int type) {
        mContentText.setInputType(type);
    }

    public void setSelection(int index) {
        ((EditText) mContentText).setSelection(index);
    }

    @Override public void setFormItemEnabled(boolean enable) {
        mContentText.setFocusable(enable);
        mContentText.setClickable(enable);
    }

}
