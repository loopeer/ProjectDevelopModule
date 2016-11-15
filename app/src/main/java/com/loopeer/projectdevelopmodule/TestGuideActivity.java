package com.loopeer.projectdevelopmodule;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.loopeer.guideactivity.AbsGuideActivity;

public class TestGuideActivity extends AbsGuideActivity{

    private Button mBtnEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mContainer = (FrameLayout) findViewById(R.id.container);
        mBtnEnter=(Button)findViewById(R.id.btn_enter);
        setPics(new int[]{R.mipmap.ic_guide_1, R.mipmap.ic_guide_2});

        super.onCreate(savedInstanceState);

        setIndicatorDrawable(true, R.drawable.indicator_shape_selected_guide,
                R.drawable.indicator_shape_unselected_guide);
    }

    @Override
    public void onPageSelected(int position) {
        super.onPageSelected(position);
        if (position == sPics.length - 1) {
            mBtnEnter.setVisibility(View.VISIBLE);
        } else {
            mBtnEnter.setVisibility(View.INVISIBLE);
        }
    }
}
