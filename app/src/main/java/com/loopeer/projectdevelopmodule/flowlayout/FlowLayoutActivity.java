package com.loopeer.projectdevelopmodule.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.loopeer.flowlayout.FlowLayout;
import com.loopeer.projectdevelopmodule.R;

public class FlowLayoutActivity extends AppCompatActivity {

    private static final String[] FLOW_CONTENTS1 = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};
    private static final String[] FLOW_DESCS1 = {"+1",
            "+2", "+5", "+3"};

    private static final String[] FLOW_CONTENTS2 = {"1", "2", "3", "4", "5", "6", "7"};
    private static final String[] FLOW_DESCS2 = {"+3",
            "+4", "+4", "+5"};

    FlowLayout mFlowLayout1;
    FlowLayout mFlowLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);
        mFlowLayout1 = (FlowLayout) findViewById(R.id.layout_flow1);
        mFlowLayout1.setAdapter(new SampleFlowAdapter(mFlowLayout1, this, R.style.FlowDescStyle, FLOW_CONTENTS1, FLOW_DESCS1));
        mFlowLayout2 = (FlowLayout) findViewById(R.id.layout_flow2);
        mFlowLayout2.setAdapter(new SampleFlowAdapter(mFlowLayout2, this, R.style.FlowDescStyle, FLOW_CONTENTS2, FLOW_DESCS2));
    }

}
