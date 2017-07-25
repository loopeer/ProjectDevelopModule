package com.loopeer.formitemview.databinding;


import android.databinding.BindingAdapter;
import android.view.View;

public class FormItemAdapter {

    @BindingAdapter("formEnable")
    public void setEnable(View view, boolean enable) {
        view.setEnabled(enable);
    }
}
