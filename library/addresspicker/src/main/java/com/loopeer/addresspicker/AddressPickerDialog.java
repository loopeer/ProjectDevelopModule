package com.loopeer.addresspicker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;

public class AddressPickerDialog extends DialogFragment {

    private int mCurrentProvince;
    private int mCurrentCity;
    private int mCurrentDistrict;

    private Address mAddress;

    private String[] mProvinces;
    private String[] mCities;
    private String[] mDistricts;

    private StringPicker mProvincePicker;
    private StringPicker mCityPicker;
    private StringPicker mDistrictPicker;
    private String mTitle;

    private OnPickListener mOnPickListener;

    private boolean mShowDistrict = true;


    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }


    public void setShowDistrict(boolean showDistrict) {
        mShowDistrict = showDistrict;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentProvince = 0;
        mCurrentCity = 0;
        mCurrentDistrict = 0;

        mAddress = AddressUtils.getInstance().getAddress();
        mProvinces = AddressUtils.getInstance().getProvinces(mAddress);
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(mTitle);

        View v = View.inflate(getContext(), R.layout.view_address_picker, null);
        mProvincePicker = (StringPicker) v.findViewById(R.id.picker_province);
        mCityPicker = (StringPicker) v.findViewById(R.id.picker_city);
        mDistrictPicker = (StringPicker) v.findViewById(R.id.picker_district);
        mDistrictPicker.setVisibility(mShowDistrict ? View.VISIBLE : View.GONE);

        mProvincePicker.setMinValue(0);
        mCityPicker.setMinValue(0);
        mDistrictPicker.setMaxValue(0);

        mProvincePicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mCityPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        mDistrictPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);

        updateProvincePicker();
        updateCityPicker();
        updateDistrictPicker();

        mProvincePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentProvince = newVal;
                mCurrentCity = 0;
                updateCityPicker();
                updateDistrictPicker();
            }
        });

        mCityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentCity = newVal;
                mCurrentDistrict = 0;
                updateDistrictPicker();
            }
        });

        mDistrictPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentDistrict = newVal;
            }
        });

        builder.setView(v);

        builder.setPositiveButton(R.string.common_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOnPickListener.onConfirm(mCurrentProvince, mCurrentCity, mCurrentDistrict);
            }
        });

        builder.setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mOnPickListener.onCancel();
            }
        });

        return builder.create();
    }


    public  void updateProvincePicker() {
        mProvincePicker.setValue(0);
        mProvincePicker.setMaxValue(getMaxValue(mProvinces));
        mProvincePicker.setDisplayedValues(mProvinces);
        if (mCurrentProvince == 0 || mCurrentProvince == mProvinces.length - 1) {
            mProvincePicker.setWrapSelectorWheel(false);
        }
    }


    public  void updateCityPicker() {
        mCities = AddressUtils.getInstance().getCities(mAddress.cityList.get(mCurrentProvince));
        mCityPicker.setDisplayedValues(null);
        mCityPicker.setValue(0);
        mCityPicker.setMaxValue(getMaxValue(mCities));
        mCityPicker.setDisplayedValues(mCities);

        if (mCurrentCity == 0 || mCurrentCity == mCities.length - 1) {
            mCityPicker.setWrapSelectorWheel(false);
        }
    }


    public  void updateDistrictPicker() {
        mDistricts = AddressUtils.getInstance()
            .getDistricts(mAddress.cityList.get(mCurrentProvince).c.get(mCurrentCity));
        mDistrictPicker.setDisplayedValues(null);
        mDistrictPicker.setValue(0);
        mDistrictPicker.setMaxValue(getMaxValue(mDistricts));
        mDistrictPicker.setDisplayedValues(mDistricts);

        if (mCurrentDistrict == 0 || mCurrentDistrict == mDistricts.length - 1) {
            mDistrictPicker.setWrapSelectorWheel(false);
        }
    }


    private int getMaxValue(String[] str) {
        return str == null ? 0 : str.length - 1;
    }


    public OnPickListener getOnPickListener() {
        return mOnPickListener;
    }


    public void setOnPickListener(OnPickListener onPickListener) {
        mOnPickListener = onPickListener;
    }


    public interface OnPickListener {

        abstract void onConfirm(int provinceIndex, int cityIndex, int districtIndex);

        abstract void onCancel();
    }
}
