package com.loopeer.formitemview.databinding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.databinding.InverseBindingMethod;
import android.databinding.InverseBindingMethods;
import android.databinding.adapters.ListenerUtil;
import android.text.Editable;
import android.text.TextWatcher;
import com.loopeer.formitemview.FormEditItem;

@InverseBindingMethods({
                           @InverseBindingMethod(type = FormEditItem.class,
                                                 attribute = "contentText")
                       })
public class FormEditItemBindingAdapter {

    @BindingAdapter("contentText")
    public static void setContentText(FormEditItem view, String content) {
        if (!view.getContentText().equals(content)) {
            view.setContentText(content);
            if (view.getContentText() != null) {
                view.setSelection(view.getContentText().length());
            }
        }
    }

    @BindingAdapter("contentTextAttrChanged")
    public static void setOnContentTextChanged(FormEditItem view, final InverseBindingListener listener) {
        final TextWatcher newValue;
        newValue = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (listener != null) {
                    listener.onChange();
                }
            }

            @Override public void afterTextChanged(Editable s) {

            }
        };
        final TextWatcher oldValue = ListenerUtil.trackListener(view, newValue, view.getId());
        if (oldValue != null) {
            view.removeContentTextChangedListener(oldValue);
        }
        view.addContentTextChangedListener(newValue);

    }

    @InverseBindingAdapter(attribute = "contentText", event = "contentTextAttrChanged")
    public static String getContentText(FormEditItem view) {
        return view.getContentText();
    }
}
