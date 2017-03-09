package com.loopeer.formitemview.databinding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.text.Editable;
import android.text.TextWatcher;
import com.loopeer.formitemview.FormTextItem;

@InverseBindingMethods({ @InverseBindingMethod(type = FormTextItem.class,
                                               attribute = "contentText")
                       })
public class FormTextItemBindingAdapter {

    @BindingAdapter("imageRightVisible")
    public static void setImageRightVisible(FormTextItem view,int visibility) {
        view.setImageRightVisible(visibility);
    }

    @BindingAdapter("contentDrawableRight")
    public static void setContentDrawableRight(FormTextItem view, int resDrawable) {
        view.setContentTextDrawableRight(resDrawable);
    }

    @BindingAdapter("contentText")
    public static void setContentText(FormTextItem view, String content) {
        if (!view.getContentText().equals(content)) {
            view.setContentText(content);
        }
    }

    @BindingAdapter("contentTextAttrChanged")
    public static void setOnContentTextChanged(FormTextItem view, final InverseBindingListener listener) {
        final TextWatcher newValue;
        newValue = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listener != null) {
                    listener.onChange();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        final TextWatcher oldValue = ListenerUtil.trackListener(view, newValue, view.getId());
        if (oldValue != null) {
            view.removeContentTextChangedListener(oldValue);
        }
        view.addContentTextChangedListener(newValue);

    }

    @InverseBindingAdapter(attribute = "contentText", event = "contentTextAttrChanged")
    public static String getContentText(FormTextItem view) {
        return view.getContentText();
    }
}
