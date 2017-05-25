package com.loopeer.compatinset.statusbar.impl;

import android.app.Activity;
import android.os.Build;
import android.view.View;

import com.loopeer.compatinset.statusbar.IStatusBarFontHelper;

/**
 * Created by kzl on 2016/5/17
 * https://github.com/zouzhenglu/zouzhenglu.github.io
 */
public class AndroidMHelper implements IStatusBarFontHelper {
    /**
     * @return if version is lager than M
     */
    @Override
    public boolean setStatusBarLightMode(Activity activity, boolean isFontColorDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFontColorDark) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
            return true;
        }
        return false;
    }

}
