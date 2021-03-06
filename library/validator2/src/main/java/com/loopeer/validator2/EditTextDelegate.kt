package com.loopeer.validator2

import android.widget.EditText
import com.loopeer.androidext.afterTextChanged
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class EditTextDelegate(private val editText: EditText, private val observableValidator: ObservableValidator, initialValue: String = "")
    : ReadWriteProperty<Any?, String> {
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
        editText.setText(value)
    }

}