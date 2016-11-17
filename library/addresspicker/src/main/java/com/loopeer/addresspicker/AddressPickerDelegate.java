package com.loopeer.addresspicker;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;

import static com.loopeer.addresspicker.AddressPickerView.PROVINCE;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY;
import static com.loopeer.addresspicker.AddressPickerView.PROVINCE_CITY_DISTRICT;

public abstract class AddressPickerDelegate {
    AddressPickerView mPicker;


    void setPicker(AddressPickerView view) {
        this.mPicker = view;
    }


    abstract void show();
    abstract void setTitle(CharSequence title);


    void setProvince(int index) {
        mPicker.setProvince(index);
    }


    void setCity(int index) {
        mPicker.setCity(index);
    }


    void setDistrict(int index) {
        mPicker.setDistrict(index);
    }


    void setAddressMode(@AddressPickerView.AddressMode int mode) {
        if (mPicker != null) {
            mPicker.setMode(mode);
        }
    }

}
