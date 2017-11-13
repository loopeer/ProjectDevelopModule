package com.loopeer.projectdevelopmodule.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.loopeer.appupdate.AppUpdate;
import com.loopeer.projectdevelopmodule.BuildConfig;
import com.loopeer.projectdevelopmodule.R;

public class AppUpdateActivity extends AppCompatActivity {

    private static final String APP_URL = "https://pro-app-qn.fir.im/dfdb155d9337fe25a9a0a183e6ec1599802bf5b4.apk?attname=loopeerRelease-6.4.0_96-release.apk_6.4.0.apk&e=1510548228&token=LOvmia8oXF4xnLh0IdH05XMYpH6ENHNpARlmPc-T:t7oySraABVRkklHVwx2-wH7jx8I=";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppUpdate.apply(this, "this is message", "this is description",
            APP_URL, BuildConfig.APPLICATION_ID, R.mipmap.ic_launcher,
            "com.loopeer.projectdevelopmodule.apkDownload.fileProvider");
    }
}
