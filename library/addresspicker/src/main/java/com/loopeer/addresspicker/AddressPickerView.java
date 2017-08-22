package com.loopeer.addresspicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.security.PublicKey;

public class AddressPickerView extends LinearLayout {

    private int mMode = PROVINCE_CITY;

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

    public final static int PROVINCE = 1;
    public final static int PROVINCE_CITY = 3;
    public final static int PROVINCE_CITY_DISTRICT = 7;

    private final static int PROVINCE_MASK = 1;
    private final static int CITY_MASK = 2;
    private final static int DISTRICT_MASK = 4;


    @Retention(RetentionPolicy.RUNTIME)
    @IntDef(value = {
        PROVINCE, PROVINCE_CITY, PROVINCE_CITY_DISTRICT,
    })
    public @interface AddressMode {
    }


    public AddressPickerView(Context context) {
        this(context, null);
    }


    public AddressPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public AddressPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
        initView();
        updateView();
    }


    private void initData() {
        mCurProvinceIndex = 0;
        mCurCityIndex = 0;
        mCurDistrictIndex = 0;
        mAddress = AddressUtils.getInstance().getAddress();
        mProvinces = AddressUtils.getInstance().getProvinces(mAddress);
    }

    public void updateAddress(Address address) {
        if (address == null) return;
        mAddress = address;
        mProvinces = AddressUtils.getInstance().getProvinces(mAddress);
        updateView();
    }

    private void initView() {
        View v = LayoutInflater.from(getContext())
            .inflate(R.layout.view_address_picker, this, true);
        mProvincePicker = (StringPicker) v.findViewById(R.id.picker_province);
        mCityPicker = (StringPicker) v.findViewById(R.id.picker_city);
        mDistrictPicker = (StringPicker) v.findViewById(R.id.picker_district);
    }


    private void updateView() {
        switch (mMode) {
            case PROVINCE:
                setupProvince();
                mProvincePicker.setVisibility(VISIBLE);
                mCityPicker.setVisibility(GONE);
                mDistrictPicker.setVisibility(GONE);
                break;
            case PROVINCE_CITY:
                setupProvince();
                setupCity();
                mProvincePicker.setVisibility(VISIBLE);
                mCityPicker.setVisibility(VISIBLE);
                mDistrictPicker.setVisibility(GONE);
                break;
            case PROVINCE_CITY_DISTRICT:
                setupProvince();
                setupCity();
                setupDistrict();
                mProvincePicker.setVisibility(VISIBLE);
                mCityPicker.setVisibility(VISIBLE);
                mDistrictPicker.setVisibility(VISIBLE);
                break;
            default:
        }

    }


    private void setupProvince() {
        mProvincePicker.setMinValue(0);
        mProvincePicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        updateProvincePicker(0);
        mProvincePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurProvinceIndex = newVal;
                mCurProvinceStr = picker.getDisplayedValues()[newVal];
                mCurCityIndex = 0;
                updateCityPicker(0);
                updateDistrictPicker(0);
            }
        });
    }


    private void setupCity() {
        mCityPicker.setMinValue(0);
        mCityPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        updateCityPicker(0);
        mCityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurCityIndex = newVal;
                mCurCityStr = picker.getDisplayedValues()[newVal];
                mCurDistrictIndex = 0;
                updateDistrictPicker(0);
            }
        });

    }


    private void setupDistrict() {
        mDistrictPicker.setMaxValue(0);
        mDistrictPicker.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        updateDistrictPicker(0);
        mDistrictPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurDistrictIndex = newVal;
                mCurDistrictStr = picker.getDisplayedValues()[newVal];
            }
        });
    }


    private void updateProvincePicker(int index) {
        mProvincePicker.setValue(index);
        mProvincePicker.setMaxValue(getMaxValue(mProvinces));
        mProvincePicker.setDisplayedValues(mProvinces);
        mCurProvinceStr = index < mProvinces.length ? mProvinces[index] : "";
        if (mCurProvinceIndex == 0 || mCurProvinceIndex == mProvinces.length - 1) {
            mProvincePicker.setWrapSelectorWheel(false);
        }
    }


    private void updateCityPicker(int index) {
        mCities = AddressUtils.getInstance().getCities(mAddress.cityList.get(mCurProvinceIndex));
        if (mCities == null || mCities.length == 0) return;
        mCityPicker.setDisplayedValues(null);
        mCityPicker.setValue(index);
        mCityPicker.setMaxValue(getMaxValue(mCities));
        mCityPicker.setDisplayedValues(mCities);
        mCurCityStr = index < mCities.length ? mCities[index] : "";
        if (mCurCityIndex == 0 || mCurCityIndex == mCities.length - 1) {
            mCityPicker.setWrapSelectorWheel(false);
        }
    }


    private void updateDistrictPicker(int index) {
        mDistricts = AddressUtils.getInstance()
            .getDistricts(mAddress.cityList.get(mCurProvinceIndex).c.get(mCurCityIndex));
        if (mDistricts == null || mDistricts.length == 0) return;
        mDistrictPicker.setDisplayedValues(null);
        mDistrictPicker.setValue(index);
        mDistrictPicker.setMaxValue(getMaxValue(mDistricts));
        mDistrictPicker.setDisplayedValues(mDistricts);
        mCurDistrictStr = index < mDistricts.length ? mDistricts[index] : "";
        if (mCurDistrictIndex == 0 || mCurDistrictIndex == mDistricts.length - 1) {
            mDistrictPicker.setWrapSelectorWheel(false);
        }
    }


    public String getProvince() {
        return (mMode & PROVINCE_MASK) == PROVINCE_MASK ? mCurProvinceStr : "";
    }

    public int getProvinceIndex() {
        return mCurProvinceIndex;
    }

    public Address.Province getProvinceModel() {
        return mAddress.cityList.get(mCurProvinceIndex);
    }

    public Address.Province.City getCityModel() {
        return mAddress.cityList.get(mCurProvinceIndex).c.get(mCurCityIndex);
    }

    public Address.Province.City.District getDistrictModel() {
        return mAddress.cityList.get(mCurProvinceIndex).c.get(mCurDistrictIndex).a.get(mCurDistrictIndex);
    }

    public void setProvince(int index) {
        mCurProvinceIndex = index;
        updateProvincePicker(index);
    }


    public String getCity() {
        return (mMode & CITY_MASK) == CITY_MASK ? mCurCityStr : "";
    }


    public void setCity(int index) {
        mCurCityIndex = index;
        updateCityPicker(index);
    }


    public int getCityIndex() {
        return mCurCityIndex;
    }


    public String getDistrict() {
        return (mMode & DISTRICT_MASK) == DISTRICT_MASK ? mCurDistrictStr : "";
    }


    public int getDistrictIndex() {
        return mCurDistrictIndex;
    }


    public void setDistrict(int index) {
        mCurDistrictIndex = index;
        updateDistrictPicker(index);
    }


    public void setMode(@AddressMode int mode) {
        if (mode == PROVINCE || mode == PROVINCE_CITY || mode == PROVINCE_CITY_DISTRICT) {
            if (mMode != mode) {
                mMode = mode;
                updateView();
            }
        } else {
            mMode = PROVINCE_CITY;
            updateView();
        }
    }


    public int getMode() {
        return mMode;
    }


    private int getMaxValue(String[] str) {
        return str == null || str.length == 0 ? 0 : str.length - 1;
    }

}
