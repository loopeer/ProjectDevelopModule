package com.loopeer.databindpack.validator;

import android.databinding.Bindable;

import com.loopeer.databindpack.BR;

public abstract class ObservableValidator extends ObservableModel implements IValidator,IFormValidator {

    public boolean enable;
    int oldHash;
    private EnableListener mEnableListener;

    /**
     * if you want check one item was edited, you should set the old hashCode after first set the value
     */
    public void notifyOlderHash(){
        this.oldHash = hashCode();
    }

    public String getContent(){
        return null;
    }

    public void notifyEnable(){
        enable = checkEnable();
        notifyPropertyChanged(BR.enable);
        if (mEnableListener != null) mEnableListener.onEnableChange(enable);
    }

    @Override public boolean isEdited() {
        return oldHash == hashCode();
    }

    @Bindable
    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public abstract boolean checkEnable();

    @Override
    public boolean isValidated() {
        return false;
    }

    public void setEnableListener(EnableListener enableListener) {
        mEnableListener = enableListener;
    }

    public interface EnableListener{
        void onEnableChange(boolean enable);
    }

    @Override public int hashCode() {
        int result = 17;
        result = 31 * result + (enable ? 1 : 0);
        /*result = 31 * result + (int)f; //byte, char, short, or int
        result = 31 * result + (int)(f^(f>>>32)); //long
        result = 31 * result + Float.floatToIntBits(f); //float
        result = 31 * result + Double.doubleToLongBits(f); //double
        //Arrays.hashCode
        //String.hashCode
        //object.hashCode*/
        return result;
    }
}
