package com.loopeer.projectdevelopmodule.validator

import android.text.TextUtils
import android.view.View
import com.loopeer.projectdevelopmodule.sample.FormValidator2Activity
import com.loopeer.validator2.EditTextDelegate

import com.loopeer.validator2.ObservableValidator
import kotlinx.android.synthetic.main.activity_form_validator2.*

class NamePhoneValidator2(val activity: FormValidator2Activity) : ObservableValidator() {

    var name: String by EditTextDelegate(activity.edit_name, this)
    var phone: String  by EditTextDelegate(activity.edit_phone, this)

    override fun checkEnable(): Boolean {
        return !TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)
    }

    override fun getEnableView(): Array<View>? {
        return arrayOf(activity.btn_submit)
    }
}
