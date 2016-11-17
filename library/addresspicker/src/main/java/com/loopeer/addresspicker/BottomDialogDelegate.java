package com.loopeer.addresspicker;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;

import static com.loopeer.addresspicker.AddressPickerView.PROVINCE;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY_DISTRICT;

public class BottomDialogDelegate extends AddressPickerDelegate {

    private BottomSheetDialog mBottomSheetDialog;


    public BottomDialogDelegate(final AddressPickerDialog.AddressPickerParams params) {
        mBottomSheetDialog = new BottomSheetDialog(params.context);
        View contentView = LayoutInflater.from(params.context)
            .inflate(R.layout.dialog_bottom_address_picker, null, false);
        mBottomSheetDialog.setContentView(contentView);
        setPicker((AddressPickerView) contentView.findViewById(
            R.id.view_picker));
        contentView.findViewById(R.id.btn_cancel)
            .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (params.listener != null) {
                            params.listener.onCancel();
                            mBottomSheetDialog.dismiss();
                        }
                    }
                });
        contentView.findViewById(R.id.btn_sure)
            .setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (params.listener != null) {
                            params.listener.onConfirm(mPicker);
                            mBottomSheetDialog.dismiss();
                        }
                    }
                }
            );

    }


    @Override public void show() {
        mBottomSheetDialog.show();
    }


    //no title
    @Override public void setTitle(CharSequence title) {

    }

}
