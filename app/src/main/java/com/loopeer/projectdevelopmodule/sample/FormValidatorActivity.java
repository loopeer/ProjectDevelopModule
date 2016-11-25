package com.loopeer.projectdevelopmodule.sample;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.loopeer.projectdevelopmodule.R;
import com.loopeer.projectdevelopmodule.databinding.ActivityFormValidatorBinding;
import com.loopeer.projectdevelopmodule.validator.NamePhoneValidator;

public class FormValidatorActivity extends AppCompatActivity {

    private NamePhoneValidator mValidator;
    private ActivityFormValidatorBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_form_validator);
        mValidator = new NamePhoneValidator();
        mBinding.setValidator(mValidator);

        mValidator.setEnableListener(enable -> invalidateOptionsMenu());
    }

    public void onBtnClick(View view) {
        Toast.makeText(this, mValidator.getName()  + " : " + mValidator.getPhone(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_validator_submit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.menu_validator_submit).setEnabled(mValidator.enable);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }


}
