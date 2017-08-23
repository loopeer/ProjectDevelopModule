package com.example.todou.testkotlinlibrary.validator

import android.widget.TextView
import kotlin.properties.ObservableProperty
import kotlin.reflect.KProperty


class TextDelegate(initialValue : String = "", val textView: TextView) : ObservableProperty<String>(initialValue) {

    init {

    }

    override fun afterChange(property: KProperty<*>, oldValue: String, newValue: String) {
        super.afterChange(property, oldValue, newValue)
        textView.text = newValue
    }

    override fun beforeChange(property: KProperty<*>, oldValue: String, newValue: String): Boolean {
        return super.beforeChange(property, oldValue, newValue)
    }
}