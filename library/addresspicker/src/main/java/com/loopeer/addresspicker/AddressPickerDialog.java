package com.loopeer.addresspicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntDef;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

public class AddressPickerDialog {

    private AddressPickerDelegate mDelegate;

    public final static int ALERT = 0;
    public final static int BOTTOM = 1;

    @SuppressLint("UniqueConstants")
    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(value = {
        ALERT, BOTTOM
    })
    public @interface DialogType {
    }

    private void applyView(final AddressPickerParams params) {
        mDelegate = params.type == ALERT
                    ? new AlertDialogDelegate(params)
                    : new BottomDialogDelegate(params);
        mDelegate.setTitle(params.title);
        mDelegate.setAddressMode(params.mode);
        mDelegate.setAddress(params.address);
        mDelegate.setProvince(params.provinceIndex);
        mDelegate.setCity(params.cityIndex);
        mDelegate.setDistrict(params.districtIndex);
    }

    public static class Builder {
        private AddressPickerParams mParams;

        public Builder(Context context) {
            mParams = new AddressPickerParams();
            mParams.context = context;
            mParams.provinceIndex = 0;
            mParams.cityIndex = 0;
            mParams.districtIndex = 0;
            mParams.type = ALERT;
            mParams.mode = AddressPickerView.PROVINCE_CITY;
        }

        public AddressPickerDelegate show(){
            AddressPickerDelegate delegate = create();
            delegate.show();
            return delegate;
        }

        public AddressPickerDelegate create(){
            AddressPickerDialog dialog = new AddressPickerDialog();
            dialog.applyView(mParams);
            return dialog.mDelegate;
        }

        public Builder setTitle(String title) {
            if (mParams.type == ALERT) {
                mParams.title = title;
            }
            return this;
        }

        public Builder setProvinceIndex(int index) {
            mParams.provinceIndex = index;
            return this;
        }

        public Builder setCityIndex(int index) {
            mParams.cityIndex = index;
            return this;
        }

        public Builder setDistrictIndex(int index) {
            mParams.districtIndex = index;
            return this;
        }

        public Builder setAddress(Address address) {
            mParams.address = address;
            return this;
        }

        public Builder setOnPickerListener(OnAddressPickListener listener) {
            mParams.listener = listener;
            return this;
        }

        public Builder setDialogType(@DialogType int type) {
            mParams.type = type;
            return this;
        }

        public Builder setAddressMode(@AddressPickerView.AddressMode int mode) {
            mParams.mode = mode;
            return this;
        }
    }

    public static class AddressPickerParams {

        public Context context;
        public String title;
        public int provinceIndex;
        public int cityIndex;
        public int districtIndex;
        public Address address;
        public OnAddressPickListener listener;
        public @DialogType int type;
        public @AddressPickerView.AddressMode int mode;
    }

}
