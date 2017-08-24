package com.loopeer.validator2

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class SimpleDelegate(private val observableValidator: ObservableValidator, initialValue: String = "")
    : ReadWriteProperty<Any?, String> {
    private var value = initialValue
        set(value) {
            field = value
            observableValidator.notifyEnable()
        }

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.value = value
    }

}