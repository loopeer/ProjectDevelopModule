package com.loopeer.appupdate;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApkDownloadService extends Service {
    public static final String EXTRA_APK_URL = "extra_apk_url";
    public static final String EXTRA_APP_NAME = "extra_app_name";
    public static final String EXTRA_DRAWABLE_ID = "extra_drawable_id";

    private String url = null;
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;
    private Notification.Builder mBuilder;

    private String appName = null;
    private String fileName = null;
    private String updateDir = null;
    private int drawableId;

    public static void startDownloadApkService(Context context, String url, String appName,
                                               @DrawableRes int drawableId) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(context, ApkDownloadService.class);
        intent.putExtra(EXTRA_APK_URL, url);
        intent.putExtra(EXTRA_APP_NAME, appName);
        intent.putExtra(EXTRA_DRAWABLE_ID, drawableId);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, 0, 0);
        url = intent.getStringExtra(EXTRA_APK_URL);
        appName = intent.getStringExtra(EXTRA_APP_NAME);
        drawableId = intent.getIntExtra(EXTRA_DRAWABLE_ID, 0);

        if (url != null) {
            fileName = url.substring(url.lastIndexOf("/") + 1);
            updateDir = getApplicationContext().getFilesDir().toString();
            Intent nullIntent = new Intent();
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, nullIntent, 0);
            updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            mBuilder = new Notification.Builder(this);
            mBuilder.setContentTitle(getApplication().getResources().getString(R.string.download)).setContentText("0%")
                    .setSmallIcon(drawableId);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                updateNotification = mBuilder.build();
            } else {
                updateNotification = mBuilder.getNotification();
            }
            updateNotification.tickerText = getApplication().getResources().getString(R.string.download);
            updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
            updateNotification.contentIntent = pendingIntent;
            updateNotificationManager.notify(101, updateNotification);
            new Thread(new updateRunnable()).start();
        }
        return super.onStartCommand(intent, 0, 0);
    }

    private class updateRunnable implements Runnable {
        Message message = updateHandler.obtainMessage();

        public void run() {
            message.what = 0;
            try {
                long downloadSize = downloadUpdateFile(url);
                if (downloadSize > 0) {
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = 1;
                updateHandler.sendMessage(message);
            }
        }
    }

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Uri apkUri;
                    // Android 7.0 以上不支持 file://协议 需要通过 FileProvider 访问 sd卡 下面的文件，所以 Uri 需要通过 FileProvider 构造，协议为 content://
                    if (Build.VERSION.SDK_INT >= 24) {
                        apkUri = FileProvider.getUriForFile(getApplicationContext(), "com.loopeer.projectdevelopmodule.fileProvider", new File(updateDir, fileName));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        apkUri = Uri.fromFile(new File(updateDir, fileName));
                    }
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    startActivity(intent);

                    updateNotificationManager.cancel(101);
                    stopSelf();
                    break;
                case 1:
                    Intent nullIntent = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(ApkDownloadService.this, 10, nullIntent, 0);
                    mBuilder.setContentTitle(getApplication().getResources().getString(R.string.download_failed))
                            .setContentText(appName)
                            .setSmallIcon(drawableId);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        updateNotification = mBuilder.build();
                    } else {
                        updateNotification = mBuilder.getNotification();
                    }
                    updateNotification.flags = Notification.FLAG_AUTO_CANCEL;
                    updateNotificationManager.notify(101, updateNotification);
                    break;
                default:
                    stopSelf();
            }
        }
    };

    public long downloadUpdateFile(String downloadUrl) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = getApplicationContext().openFileOutput(fileName, MODE_PRIVATE);

            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                if ((downloadCount == 0) || (int) (totalSize * 100 / updateTotalSize) - 10 > downloadCount) {
                    downloadCount += 10;
                    Intent nullIntent = new Intent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, nullIntent, 0);
                    mBuilder.setContentIntent(pendingIntent)
                            .setContentTitle(getApplication().getResources().getString(R.string.download))
                            .setContentText((int) totalSize * 100 / updateTotalSize + "%")
                            .setSmallIcon(drawableId);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        updateNotification = mBuilder.build();
                    } else {
                        updateNotification = mBuilder.getNotification();
                    }
                    updateNotificationManager.notify(101, updateNotification);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
