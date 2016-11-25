package com.loopeer.projectdevelopmodule.validator;


import android.databinding.Bindable;
import android.text.TextUtils;

import com.loopeer.databindpack.validator.ObservableValidator;

public class NamePhoneValidator extends ObservableValidator{

    public String name;
    public String phone;

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyEnable();
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyEnable();
    }

    @Override
    public boolean checkEnable() {
        return !TextUtils.isEmpty(name)
                && !TextUtils.isEmpty(phone);
    }
}
