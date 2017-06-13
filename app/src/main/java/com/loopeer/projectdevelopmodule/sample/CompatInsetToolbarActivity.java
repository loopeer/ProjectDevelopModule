package com.loopeer.projectdevelopmodule.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.loopeer.projectdevelopmodule.R;

public class CompatInsetToolbarActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CompatInsetToolbarActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compat_inset_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*TextView textView = (TextView) findViewById(R.id.text_content);
        textView.setText("Support libraries allow apps running on older versions of the Android platform to support features made available on newer versions of the platform. For example, an app running on a version of Android lower than 5.0 (API level 21) that relies on framework classes cannot display material-design elements, as that version of the Android framework doesn't support material design. However, if the app incorporates the Support Library's appcompat library, the app has access to many of the features available in API level 21, including support for material design. As a result, your app can deliver a more consistent experience across a broader range of platform versions.\n" +
                "\n" +
                "In some cases, the support library version of a class depends as much as possible on the functionality that the framework provides. In these cases, if an app calls one of the support class's methods, the support library's behavior depends on what version of Android the app is running on. If the framework provides the necessary functionality, the support library calls on the framework to perform the task. If the app is running on an earlier version of Android, and the framework doesn't expose the needed functionality, the support library may try to provide the functionality itself, or may act as a no-op. In either case, the app generally doesn't need to check what version of Android it's running o es, the support library version of a class depends as much as possible on the functionality that the framework provides. In these cases, if an app calls one of the support class's methods, the support library's behavior depends on what version of Android the app is running on. If the framework provides the necessary functionality, the support library calls on the framework to perform the task. If the app is running on an earlier version of Android, and the framework doesn't expose the needed functionality, the support library may try to provide the functionality itself, or may act as a no-op. In either case, the app generally doesn't need to check what version of Android it's running o ");*/
    }
}
