package com.example.todou.testkotlinlibrary.validator

import java.io.Serializable

open class ObservableModel : Serializable {

    override fun toString(): String {
        val clazz = javaClass
        val fields = clazz.declaredFields

        val sb = StringBuilder(clazz.name)
        sb.append("{")

        val len = fields.size
        for (index in 0..len - 1) {
            try {
                val field = fields[index]
                if (field.get(this) == null) continue
                sb.append(field.name).append("=").append(field.get(this).toString())
                if (index < len - 1) {
                    sb.append(", ")
                }
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        }

        sb.append("}")
        return sb.toString()
    }
}
