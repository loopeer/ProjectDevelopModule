package com.loopeer.developutils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class CacheUtils {

    public static long getCacheSize(Context context) {
        long size = getDirSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            size += getDirSize(context.getExternalCacheDir());
        }
        return size;
    }

    public static void clearCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static long getDirSize(File file) {
        long size = 0;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                size += getDirSize(f);
            }
        } else {
            size += file.length();
        }
        return size;
    }

    private static void deleteDir(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                deleteDir(f);
            }
        } else {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
    }
}
