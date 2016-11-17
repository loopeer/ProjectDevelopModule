package com.loopeer.projectdevelopmodule.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.loopeer.addresspicker.Address;
import com.loopeer.addresspicker.AddressPickerDialog;
import com.loopeer.addresspicker.AddressPickerView;
import com.loopeer.addresspicker.OnAddressPickListener;
import com.loopeer.bottomdialog.DateTimeView;
import com.loopeer.projectdevelopmodule.R;

public class AddressPickerActivity extends AppCompatActivity {

    private TextView mTextAddress;
    private RadioGroup mRadioGroup;
    private int mType;
    private int mMode;
    private int mProvinceIndex;
    private int mCityIndex;
    private int mDistrictIndex;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_address_picker, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_province:
                mMode = AddressPickerView.PROVINCE;
                break;
            case R.id.menu_city:
                mMode = AddressPickerView.PROVINCE_CITY;
                break;
            case R.id.menu_district:
                mMode = AddressPickerView.PROVINCE_CITY_DISTRICT;
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_picker);
        mTextAddress = (TextView) findViewById(R.id.text_address);
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.btn_alert) {
                    mType = AddressPickerDialog.ALERT;
                } else {
                    mType = AddressPickerDialog.BOTTOM;
                }
            }
        });
    }


    public void onAddressItemClick(View view) {
        showDialog();
    }


    private void showDialog() {
        final AddressPickerDialog.Builder builder = new AddressPickerDialog.Builder(this);
        builder.setTitle("please choose your address")
            .setDialogType(mType)
            .setAddressMode(mMode)
            //if you want to show the last address you have picked,just record the params and set them
            .setProvinceIndex(mProvinceIndex)
            .setCityIndex(mCityIndex)
            .setDistrictIndex(mDistrictIndex)
            .setOnPickerListener(new OnAddressPickListener() {
                @Override public void onConfirm(AddressPickerView pickerView) {
                    mTextAddress.setText(getString(
                        R.string.address_format,pickerView.getProvince(), pickerView.getCity(),
                        pickerView.getDistrict()));
                    //record
                    mProvinceIndex = pickerView.getProvinceIndex();
                    mCityIndex = pickerView.getCityIndex();
                    mDistrictIndex = pickerView.getDistrictIndex();
                }


                @Override public void onCancel() {

                }
            })
            .show();

    }

}
