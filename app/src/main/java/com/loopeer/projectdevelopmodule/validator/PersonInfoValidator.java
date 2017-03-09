package com.loopeer.projectdevelopmodule.validator;

import android.databinding.Bindable;
import android.text.TextUtils;
import android.widget.Toast;
import com.loopeer.databindpack.validator.ObservableValidator;
import com.loopeer.projectdevelopmodule.BR;

public class PersonInfoValidator extends ObservableValidator {
    public String name;
    public String phone;
    public String status;
    public boolean hasWorkInfo;

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public String getPhone() {
        return phone;
    }

    @Bindable
    public String getStatus() {
        return status;
    }

    @Bindable
    public boolean isHasWorkInfo() {
        return hasWorkInfo;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
        notifyEnable();
    }

    public void setPhone(String phone) {
        this.phone = phone;
        notifyPropertyChanged(BR.phone);
        notifyEnable();
    }

    public void setStatus(String status) {
        this.status = status;
        notifyPropertyChanged(BR.status);
        notifyEnable();
    }

    public void setHasWorkInfo(boolean hasWorkInfo) {
        this.hasWorkInfo = hasWorkInfo;
        notifyPropertyChanged(BR.hasWorkInfo);
        notifyEnable();
    }

    @Override public boolean isValidated() {
        //add validate rules here
        return true;
    }

    @Override public boolean checkEnable() {
        return !TextUtils.isEmpty(name)
            && !TextUtils.isEmpty(phone)
            && !TextUtils.isEmpty(status)
            && hasWorkInfo;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonInfoValidator that = (PersonInfoValidator) o;

        if (hasWorkInfo != that.hasWorkInfo) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        return status != null ? status.equals(that.status) : that.status == null;

    }

    @Override public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (hasWorkInfo ? 1 : 0);
        return result;
    }
}
