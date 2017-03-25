package com.loopeer.projectdevelopmodule.sample;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.loopeer.developutils.PermissionUtils;
import com.loopeer.projectdevelopmodule.R;

public class BottomImagePickerViewActivity extends AppCompatActivity {

    public static final String[] PERMISSION_WRITE_START_REQUEST = new String[] {
        Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public static final String[] PERMISSION_READ_START_REQUEST = new String[] {
        Manifest.permission.READ_EXTERNAL_STORAGE };

    public static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 2;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //check before layout set
        checkPermission();
        setContentView(R.layout.activity_bottom_image_picker_view);
    }

    private void checkPermission() {
        if (!PermissionUtils.hasSelfPermissions(this, PERMISSION_WRITE_START_REQUEST)) {
            ActivityCompat.requestPermissions(this, PERMISSION_WRITE_START_REQUEST,
                REQUEST_WRITE_EXTERNAL_STORAGE);
        }
        if (!PermissionUtils.hasSelfPermissions(this, PERMISSION_WRITE_START_REQUEST)) {
            ActivityCompat.requestPermissions(this, PERMISSION_READ_START_REQUEST,
                REQUEST_READ_EXTERNAL_STORAGE);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        onRequestPermissionsResult(this, requestCode, grantResults);
    }

    public void onRequestPermissionsResult(Activity target, int requestCode, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_WRITE_EXTERNAL_STORAGE:
                if (PermissionUtils.getTargetSdkVersion(target) < 23 &&
                    !PermissionUtils.hasSelfPermissions(target, PERMISSION_WRITE_START_REQUEST)) {
                    finish();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    if (!PermissionUtils.hasSelfPermissions(this, PERMISSION_WRITE_START_REQUEST)) {
                        ActivityCompat.requestPermissions(this, PERMISSION_WRITE_START_REQUEST,
                            REQUEST_WRITE_EXTERNAL_STORAGE);
                    }
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(target,
                        PERMISSION_WRITE_START_REQUEST)) {
                        Toast.makeText(this, "Please set the permission in the app settings",
                            Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                break;

            case REQUEST_READ_EXTERNAL_STORAGE:
                if (PermissionUtils.getTargetSdkVersion(target) < 23 &&
                    !PermissionUtils.hasSelfPermissions(target, PERMISSION_READ_START_REQUEST)) {
                    finish();
                    return;
                }
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    if (!PermissionUtils.hasSelfPermissions(this, PERMISSION_READ_START_REQUEST)) {
                        ActivityCompat.requestPermissions(this, PERMISSION_READ_START_REQUEST,
                            REQUEST_READ_EXTERNAL_STORAGE);
                    }
                } else {
                    if (!PermissionUtils.shouldShowRequestPermissionRationale(target,
                        PERMISSION_READ_START_REQUEST)) {
                        Toast.makeText(this, "Please set the permission in the app settings",
                            Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
