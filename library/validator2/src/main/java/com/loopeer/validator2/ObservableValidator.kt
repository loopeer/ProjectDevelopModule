package com.loopeer.validator2

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

    override fun isEdited(): Boolean {
        return oldHash != hashCode()
    }

    abstract override fun checkEnable(): Boolean

    override fun isValidated(): Boolean {
        return false
    }

    fun setEnableListener(enableListener:(enable: Boolean) -> Unit) {
        mEnableListener = object : EnableListener{
            override fun onEnableChange(enable: Boolean) {
                enableListener.invoke(enable)
            }
        }
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
