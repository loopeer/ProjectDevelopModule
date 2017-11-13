package com.loopeer.appupdate;

import android.app.Notification;
import android.app.NotificationChannel;
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
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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
    public static final String EXTRA_FILE_PROVIDER_AUTHORITIES = "extra_file_provider_authorities";

    private static final int APP_NOTIFICATION_ID = 1001;
    private static final String CHANNEL_ID = "download file";

    private String url = null;
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;
    private NotificationCompat.Builder notifyBuilder;

    private String appName = null;
    private String fileName = null;
    private String updateDir = null;
    private String fileProviderAuthorities = null;
    private int drawableId;

    public static void startDownloadApkService(Context context, String url, String appName,
                                               @DrawableRes int drawableId, String authorities) {
        if (TextUtils.isEmpty(url)) return;
        Intent intent = new Intent(context, ApkDownloadService.class);
        intent.putExtra(EXTRA_APK_URL, url);
        intent.putExtra(EXTRA_APP_NAME, appName);
        intent.putExtra(EXTRA_DRAWABLE_ID, drawableId);
        intent.putExtra(EXTRA_FILE_PROVIDER_AUTHORITIES, authorities);
        ContextCompat.startForegroundService(context, intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) return super.onStartCommand(intent, 0, 0);
        url = intent.getStringExtra(EXTRA_APK_URL);
        appName = intent.getStringExtra(EXTRA_APP_NAME);
        drawableId = intent.getIntExtra(EXTRA_DRAWABLE_ID, 0);
        fileProviderAuthorities = intent.getStringExtra(EXTRA_FILE_PROVIDER_AUTHORITIES);

        if (url != null) {
            updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createChannel(updateNotificationManager);
                notifyBuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
            } else {
                notifyBuilder = new NotificationCompat.Builder(this,CHANNEL_ID);
            }

            fileName = url.substring(url.lastIndexOf("/") + 1);
            updateDir = getApplicationContext().getFilesDir().toString();

            notifyBuilder.setContentTitle(getResources().getString(R.string.download))
                .setContentText("0%")
                .setSmallIcon(drawableId)
                .setProgress(100, 0, false);
            notifyBuilder.setContentIntent(PendingIntent.getActivity(getApplicationContext(),
                0, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));

            updateNotification = notifyBuilder.build();
            updateNotification.tickerText = getApplication().getResources().getString(R.string.download);
            updateNotification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            startForeground(APP_NOTIFICATION_ID, updateNotification);
            updateNotificationManager.notify(APP_NOTIFICATION_ID, updateNotification);
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
                    if (versionAboveN()) {
                        apkUri = FileProvider.getUriForFile(getApplicationContext(), fileProviderAuthorities, new File(updateDir, fileName));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else {
                        apkUri = Uri.fromFile(new File(updateDir, fileName));
                    }
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                    startActivity(intent);

                    updateNotificationManager.cancel(APP_NOTIFICATION_ID);
                    stopSelf();
                    break;
                case 1:
                    notifyBuilder.setContentTitle(getApplication().getResources().getString(R.string.download_failed))
                        .setContentText(appName)
                        .setSmallIcon(drawableId)
                        .setContentIntent(PendingIntent.getActivity(ApkDownloadService.this,
                            10, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));

                    updateNotification = notifyBuilder.build();
                    updateNotification.flags = Notification.FLAG_FOREGROUND_SERVICE;
                    updateNotificationManager.notify(APP_NOTIFICATION_ID, updateNotification);
                    break;
                default:
                    stopSelf();
            }
        }
    };

    private boolean versionAboveN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public long downloadUpdateFile(String downloadUrl) throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long downloadedSize = 0;
        long fileSize = 0;

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
            fileSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = getApplicationContext().openFileOutput(fileName, versionAboveN() ? MODE_PRIVATE : MODE_WORLD_READABLE);

            byte buffer[] = new byte[4096];
            int readsize = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                downloadedSize += readsize;
                int downloadedPercent = (int) (downloadedSize * 100 / fileSize);
                if ((downloadCount == 0) || downloadedPercent - 10 > downloadCount) {
                    downloadCount += 10;
                    notifyBuilder.setContentTitle(getApplication().getResources().getString(R.string.download))
                        .setContentText(downloadedPercent + "%")
                        .setSmallIcon(drawableId)
                        .setContentIntent(PendingIntent.getActivity(ApkDownloadService.this, 0,
                            new Intent(), PendingIntent.FLAG_UPDATE_CURRENT))
                        .setProgress(100, downloadedPercent, false);
                    updateNotification = notifyBuilder.build();
                    updateNotification.flags = Notification.FLAG_FOREGROUND_SERVICE;
                    updateNotificationManager.notify(APP_NOTIFICATION_ID, updateNotification);
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
        return downloadedSize;
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private void createChannel(NotificationManager manager) {
        // The id of the channel.
        String id = CHANNEL_ID;
        // The user-visible name of the channel.
        CharSequence name = "download files";
        // The user-visible description of the channel.
        String description = "download some necessary files";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(id, name, importance);
        // Configure the notification channel.
        channel.setDescription(description);
        channel.setShowBadge(false);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        manager.createNotificationChannel(channel);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
