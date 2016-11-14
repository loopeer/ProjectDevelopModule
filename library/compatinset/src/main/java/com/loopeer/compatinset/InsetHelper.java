package com.loopeer.compatinset;


import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class InsetHelper {
    public static void setupForInsets(final View view) {
        if (Build.VERSION.SDK_INT < 21) {
            return;
        }
        if (ViewCompat.getFitsSystemWindows(view)) {
            android.support.v4.view.OnApplyWindowInsetsListener applyWindowInsetsListener =
                    new android.support.v4.view.OnApplyWindowInsetsListener() {
                        @Override
                        public WindowInsetsCompat onApplyWindowInsets(View v, WindowInsetsCompat insets) {
                            view.requestLayout();
                            return insets;
                        }
                    };

            ViewCompat.setOnApplyWindowInsetsListener(view, applyWindowInsetsListener);
            view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            ViewCompat.setOnApplyWindowInsetsListener(view, null);
        }
    }

    public static void requestApplyInsets(View view) {
        ViewCompat.requestApplyInsets(view);
    }

    public static Rect clearInset(Rect insets) {
        insets.top = 0;
        insets.right = 0;
        insets.right = 0;
        return insets;
    }

    public static void translucentStatus(Context context) {
        if (context instanceof Activity) {
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            throw new ClassCastException("To translucentStatus context must be instanceof of Activity");
        }
    }

}
