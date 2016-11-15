package com.loopeer.bottomdialog;

import android.content.Context;
import android.view.View;

import java.util.Calendar;

import loopeer.loopeer.bottomdialog.R;

public class BottomDateTimeDialog extends BottomDialog {

    DateTimeView mDateTimeView;

    private OnDateSelectListener mOnDateSelectListener;

    public BottomDateTimeDialog(Context context) {
        this(context, R.style.CustomDialog_Bottom_NumberPicker);
    }

    public BottomDateTimeDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    protected BottomDateTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);

        setCancelable(cancelable);
        setOnCancelListener(cancelListener);
    }

    private void initView() {
        setContentView(R.layout.dialog_date_time);
        mDateTimeView = (DateTimeView) findViewById(R.id.view_date_time_picker);
        findViewById(R.id.btn_date_time_cancel)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onCancel();
                            }
                        });
        findViewById(R.id.btn_date_time_sure)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onSure();
                            }
                        }
                );

    }

    public BottomDateTimeDialog updateDateTime(Calendar calendar) {
        mDateTimeView.update(calendar);
        return this;
    }

    public BottomDateTimeDialog setDateTimeChangeListener(DateTimeView.OnDateTimeChangedListener listener) {
        mDateTimeView.setOnDateTimeChangedListener(listener);
        return this;
    }

    public BottomDateTimeDialog setMinDate(long date) {
        mDateTimeView.setMinDate(date);
        return this;
    }

    public BottomDateTimeDialog setDateSelectedListener(OnDateSelectListener listener) {
        mOnDateSelectListener = listener;
        return this;
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onCancel() {
        dismiss();
    }

    public void onSure() {
        mOnDateSelectListener.onDateSelected(mDateTimeView.getCurrentDate());
        dismiss();
    }

    public interface OnDateSelectListener {
        void onDateSelected(Calendar calendar);
    }


    public BottomDateTimeDialog setDateTimeMode(@DateTimeView.DateTimeMode int mode) {
        mDateTimeView.setDateTimeMode(mode);
        return this;
    }
}

