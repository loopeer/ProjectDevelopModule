package com.loopeer.projectdevelopmodule.sample;

import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.PluralsRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.loopeer.projectdevelopmodule.R;
import com.loopeer.projectdevelopmodule.databinding.ActivityFormItemViewBinding;
import com.loopeer.projectdevelopmodule.validator.PersonInfoValidator;

public class FormItemViewActivity extends AppCompatActivity {

    private PersonInfoValidator mInfoValidator;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFormItemViewBinding binding = DataBindingUtil.setContentView(this,
            R.layout.activity_form_item_view);
        mInfoValidator = new PersonInfoValidator();
        mInfoValidator.notifyOlderHash();
        binding.setValidator(mInfoValidator);
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if(mInfoValidator.isEdited()){
                showAlertDialog();
            }else {
                onBackPressed();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mInfoValidator != null && mInfoValidator.isEdited()) {
                showAlertDialog();
            } else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showAlertDialog() {
        new AlertDialog.Builder(this)
            .setMessage("表单已被编辑，确定退出？")
            .setNegativeButton("否", null)
            .setPositiveButton("是", (dialog, which) -> finish())
            .show();
    }

    public void onMarriageStatusClick(View item) {
        String[] items = new String[] { "已婚", "未婚", "其它" };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("请选择婚姻状况：")
            .setItems(items, (dialog, which) -> {
                mInfoValidator.setStatus(items[which]);
            })
            .show();
    }

    public void onWorkInfoClick(View item) {
        mInfoValidator.hasWorkInfo = !mInfoValidator.hasWorkInfo;
        mInfoValidator.setHasWorkInfo(mInfoValidator.hasWorkInfo);
    }

    public void onBtnClick(View view) {
        if (mInfoValidator.isValidated()) {
            Toast.makeText(this, mInfoValidator.toString(), Toast.LENGTH_LONG).show();
        }
    }

}
