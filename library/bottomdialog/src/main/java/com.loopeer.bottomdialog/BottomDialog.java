package com.loopeer.bottomdialog;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;

public class BottomDialog extends AppCompatDialog {

    public BottomDialog(Context context) {
        this(context, R.style.CustomDialog_Bottom);
    }

    public BottomDialog(Context context, int theme) {
        super(context, theme);
        init();
    }

    protected BottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        this(context);
        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    private void init() {
        Window window = getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);
        window.setGravity(Gravity.BOTTOM);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
