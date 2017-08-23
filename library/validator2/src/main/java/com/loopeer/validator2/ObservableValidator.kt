package com.example.todou.testkotlinlibrary.validator

import android.view.View

abstract class ObservableValidator : ObservableModel(), IValidator, IFormValidator {

    var isEnable: Boolean = false
    internal var oldHash: Int = 0
    private var mEnableListener: EnableListener? = null

    /**
     * if you want check one item was edited, you should set the old hashCode after first set the value
     */
    fun notifyOlderHash() {
        this.oldHash = hashCode()
    }

    val content: String?
        get() = null

    fun notifyEnable() {
        isEnable = checkEnable()
        getEnableView()?.forEach {
            it.isEnabled = isEnable
        }
        if (mEnableListener != null) mEnableListener!!.onEnableChange(isEnable)
    }

    open fun getEnableView() : Array<View>? {
        return null
    }

    override val isEdited: Boolean
        get() = oldHash != hashCode()

    abstract override fun checkEnable(): Boolean

    override val isValidated: Boolean
        get() = false

    fun setEnableListener(enableListener: EnableListener) {
        mEnableListener = enableListener
    }

    interface EnableListener {
        fun onEnableChange(enable: Boolean)
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + if (isEnable) 1 else 0
        /*result = 31 * result + (int)f; //byte, char, short, or int
        result = 31 * result + (int)(f^(f>>>32)); //long
        result = 31 * result + Float.floatToIntBits(f); //float
        result = 31 * result + Double.doubleToLongBits(f); //double
        //Arrays.hashCode
        //String.hashCode
        //object.hashCode*/
        return result
    }
}
