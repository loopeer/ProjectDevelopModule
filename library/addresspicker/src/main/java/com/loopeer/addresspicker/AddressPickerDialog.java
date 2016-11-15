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

    private int mCurProvinceIndex;
    private int mCurCityIndex;
    private int mCurDistrictIndex;
    
    private String mCurProvinceStr;
    private String mCurCityStr;
    private String mCurDistrictStr;

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

        mCurProvinceIndex = 0;
        mCurCityIndex = 0;
        mCurDistrictIndex = 0;

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
                mCurProvinceIndex = newVal;
                mCurProvinceStr = picker.getDisplayedValues()[newVal];
                mCurCityIndex = 0;
                updateCityPicker();
                updateDistrictPicker();
            }
        });

        mCityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurCityIndex = newVal;
                mCurCityStr = picker.getDisplayedValues()[newVal];
                mCurDistrictIndex = 0;
                updateDistrictPicker();
            }
        });

        mDistrictPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurDistrictIndex = newVal;
                mCurDistrictStr = picker.getDisplayedValues()[newVal];
            }
        });

        builder.setView(v);

        builder.setPositiveButton(R.string.common_sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mOnPickListener != null)
                    mOnPickListener.onConfirm(mCurProvinceStr, mCurCityStr, mCurDistrictStr);
            }
        });

        builder.setNegativeButton(R.string.common_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(mOnPickListener != null)
                 mOnPickListener.onCancel();
            }
        });

        return builder.create();
    }


    public  void updateProvincePicker() {
        mProvincePicker.setValue(0);
        mProvincePicker.setMaxValue(getMaxValue(mProvinces));
        mProvincePicker.setDisplayedValues(mProvinces);
        mCurProvinceStr = mProvinces[0];
        if (mCurProvinceIndex == 0 || mCurProvinceIndex == mProvinces.length - 1) {
            mProvincePicker.setWrapSelectorWheel(false);
        }
    }


    public  void updateCityPicker() {
        mCities = AddressUtils.getInstance().getCities(mAddress.cityList.get(mCurProvinceIndex));
        mCityPicker.setDisplayedValues(null);
        mCityPicker.setValue(0);
        mCityPicker.setMaxValue(getMaxValue(mCities));
        mCityPicker.setDisplayedValues(mCities);
        mCurCityStr = mCities[0];
        if (mCurCityIndex == 0 || mCurCityIndex == mCities.length - 1) {
            mCityPicker.setWrapSelectorWheel(false);
        }
    }


    public  void updateDistrictPicker() {
        mDistricts = AddressUtils.getInstance()
            .getDistricts(mAddress.cityList.get(mCurProvinceIndex).c.get(mCurCityIndex));
        mDistrictPicker.setDisplayedValues(null);
        mDistrictPicker.setValue(0);
        mDistrictPicker.setMaxValue(getMaxValue(mDistricts));
        mDistrictPicker.setDisplayedValues(mDistricts);
        mCurDistrictStr = mDistricts[0];
        if (mCurDistrictIndex == 0 || mCurDistrictIndex == mDistricts.length - 1) {
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

        abstract void onConfirm(String province,String city,String district);

        abstract void onCancel();
    }
}
