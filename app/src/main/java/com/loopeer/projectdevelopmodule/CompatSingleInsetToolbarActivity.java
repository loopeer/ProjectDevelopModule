package com.loopeer.projectdevelopmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import loopeer.com.projectdevelopmodule.R;

public class CompatSingleInsetToolbarActivity extends AppCompatActivity {

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CompatSingleInsetToolbarActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_compat_inset_toolbar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
