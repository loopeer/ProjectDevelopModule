package com.loopeer.appupdate;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AlertDialog;

public class AppUpdate {

    public static void apply(final Context context, String message, String description,
                             final String url, final String appName,
                             final @DrawableRes int drawableId, final String authorities) {
        new AlertDialog.Builder(context)
                .setTitle(message)
                .setMessage(description)
                .setNegativeButton(R.string.update_cancel, null)
                .setPositiveButton(R.string.update_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ApkDownloadService.startDownloadApkService(context, url, appName, drawableId, authorities);
                    }
                })
                .show();
    }
}
