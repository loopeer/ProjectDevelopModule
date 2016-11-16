package com.loopeer.bottomdialog;
import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.Calendar;

import loopeer.loopeer.bottomdialog.R;

public class BottomDateTimeDialog {

    DateTimeView mDateTimeView;
    BottomSheetDialog mBottomSheetDialog;

    private OnDateSelectListener mOnDateSelectListener;

    private void applyView(BottomDateTimeDialogParams params) {
        LayoutInflater inflater = LayoutInflater.from(params.context);
        mBottomSheetDialog = new BottomSheetDialog(params.context);
        View contentView = inflater.inflate(R.layout.dialog_date_time, null, false);
        mBottomSheetDialog.setContentView(contentView);
        mDateTimeView = (DateTimeView) contentView.findViewById(R.id.view_date_time_picker);
        contentView.findViewById(R.id.btn_date_time_cancel)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mBottomSheetDialog.dismiss();
                            }
                        });
        contentView.findViewById(R.id.btn_date_time_sure)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mOnDateSelectListener.onDateSelected(mDateTimeView.getCurrentDate());
                                mBottomSheetDialog.dismiss();
                            }
                        }
                );

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(params.updateDateTime);
        mDateTimeView.update(calendar);
        mDateTimeView.setOnDateTimeChangedListener(params.dateTimeChangedListener);
        mDateTimeView.setMinDate(params.minDateTime);
        mDateTimeView.setDateTimeMode(params.mode);
        mOnDateSelectListener = params.dateSelectListener;
    }

    public interface OnDateSelectListener {
        void onDateSelected(Calendar calendar);
    }

    public static class Builder{

        BottomDateTimeDialogParams mParams;
        public Builder(Context context) {
            mParams = new BottomDateTimeDialogParams();
            mParams.context = context;
        }

        public BottomSheetDialog show() {
            BottomSheetDialog dialog = create();
            dialog.show();
            return dialog;
        }

        public BottomSheetDialog create() {
            BottomDateTimeDialog dialog = new BottomDateTimeDialog();
            dialog.applyView(mParams);
            return dialog.mBottomSheetDialog;
        }


        public Builder updateDateTime(long updateDateTime) {
            mParams.updateDateTime = updateDateTime;
            return this;
        }

        public Builder setMinDateTime(long minDateTime) {
            mParams.minDateTime = minDateTime;
            return this;
        }

        public Builder setDateTimeMode(@DateTimeView.DateTimeMode int mode) {
            mParams.mode = mode;
            return this;
        }

        public Builder setDateTimeChangedListener(DateTimeView.OnDateTimeChangedListener dateTimeChangedListener) {
            mParams.dateTimeChangedListener = dateTimeChangedListener;
            return this;
        }

        public Builder setDateSelectListener(OnDateSelectListener dateSelectListener) {
            mParams.dateSelectListener = dateSelectListener;
            return this;
        }
    }

    public static class BottomDateTimeDialogParams{
        Context context;
        long updateDateTime;
        long minDateTime;
        DateTimeView.OnDateTimeChangedListener dateTimeChangedListener;
        OnDateSelectListener dateSelectListener;
        @DateTimeView.DateTimeMode int mode;
    }
}

