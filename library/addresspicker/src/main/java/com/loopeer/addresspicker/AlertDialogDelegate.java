package com.loopeer.addresspicker;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import static com.loopeer.addresspicker.AddressPickerView.AddressMode;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY_DISTRICT;

public class AlertDialogDelegate extends AddressPickerDelegate {

    private AlertDialog.Builder mBuilder;


    public AlertDialogDelegate(final AddressPickerDialog.AddressPickerParams params) {
        setPicker(new AddressPickerView(params.context));
        mBuilder = new AlertDialog.Builder(params.context)
            .setView(mPicker)
            .setPositiveButton(R.string.common_sure, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    if (params.listener != null) {
                        params.listener.onConfirm(mPicker);
                    }
                }
            }).setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    if (params.listener != null) {
                        params.listener.onCancel();
                    }
                }
            });
    }


    @Override public void show() {
        mBuilder.show();
    }


    @Override public void setTitle(CharSequence title) {
        mBuilder.setTitle(title);
    }

}
