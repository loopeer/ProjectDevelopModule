package com.loopeer.projectdevelopmodule.sample;


import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.loopeer.bottomdialog.BottomDateTimeDialog;
import com.loopeer.bottomdialog.DateTimeView;
import com.loopeer.projectdevelopmodule.R;
import com.loopeer.projectdevelopmodule.databinding.AcitivtyBottomDialogDateTimeBinding;

import java.util.Calendar;

public class BottomDialogDateTimeViewActivity extends AppCompatActivity {
    private AcitivtyBottomDialogDateTimeBinding mBinding;
    @DateTimeView.DateTimeMode
    private int mDateTimeMode = DateTimeView.DATE_TIME_VIEW;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, BottomDialogDateTimeViewActivity.class));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.acitivty_bottom_dialog_date_time);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_time_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_bottom_date:
                mDateTimeMode = DateTimeView.DATE_VIEW;
                break;
            case R.id.menu_bottom_time:
                mDateTimeMode = DateTimeView.TIME_VIEW;
                break;
            case R.id.menu_bottom_date_time:
                mDateTimeMode = DateTimeView.DATE_TIME_VIEW;
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDateTimeItemClick(View view) {
        new BottomDateTimeDialog(this)
                .updateDateTime(Calendar.getInstance())
                .setDateSelectedListener(
                        new BottomDateTimeDialog.OnDateSelectListener() {
                            @Override
                            public void onDateSelected(Calendar calendar) {
                                updateDateTimeText(calendar);
                            }
                        })
                .setDateTimeMode(mDateTimeMode)
                .show();
    }

    private void updateDateTimeText(Calendar calendar) {
        String formatString;
        switch (mDateTimeMode) {
            case DateTimeView.DATE_VIEW:
                formatString = "yyyy-MM-dd";
                break;
            case DateTimeView.TIME_VIEW:
                formatString = "hh:mm";
                break;
            default:
                formatString = "yyyy-MM-dd hh:mm";
                break;
        }
        mBinding.textDate.setText(DateFormat.format(formatString, calendar));
    }
}
