package com.loopeer.projectdevelopmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.loopeer.addresspicker.Address;
import com.loopeer.addresspicker.AddressPickerDialog;
import com.loopeer.addresspicker.AddressUtils;

public class AddressPickerActivity extends AppCompatActivity{

    private TextView mTextAddress;

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, AddressPickerActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_picker);
        mTextAddress = (TextView) findViewById(R.id.text_address);
    }

    public void onAddressItemClick(View view){
        showDialog();
    }

    private void showDialog(){
        final AddressPickerDialog dialog = new AddressPickerDialog();
        dialog.setTitle("please choose your address");
        dialog.setOnPickListener(new AddressPickerDialog.OnPickListener() {

            @Override public void onConfirm(String province, String city, String district) {
                mTextAddress.setText(getString(R.string.address_format,province,city,district));
            }


            @Override public void onCancel() {

            }
        });
        dialog.show(getSupportFragmentManager(), null);

    }

}
