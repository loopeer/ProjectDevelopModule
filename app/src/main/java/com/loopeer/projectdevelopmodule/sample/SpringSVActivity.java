package com.loopeer.projectdevelopmodule.sample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.loopeer.projectdevelopmodule.R;

public class SpringSVActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spring_sv);

        findViewById(R.id.item_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "one", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.item_two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "two", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
