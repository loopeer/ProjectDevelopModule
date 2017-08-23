package com.example.todou.testkotlinlibrary.validator

import android.widget.EditText
import com.example.todou.testkotlinlibrary.TestValidator
import javax.xml.validation.Validator
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EditTextDelegate(initialValue: String = "", editText: EditText, private val observableValidator: ObservableValidator) : ReadWriteProperty<Any?, String> {
    private var value = initialValue
        set(value) {
            field = value
            observableValidator.notifyEnable()
        }

    init {
        editText.afterTextChanged {
            value = it
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
    }

}