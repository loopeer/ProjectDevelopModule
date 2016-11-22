package com.loopeer.imageswitcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;


public class ImageSwitcherActivity extends AppCompatActivity implements OnTabOneClickListener {

    private MutipleTouchViewPager mViewPager;
    private ArrayList<String> mImageList;
    private ImagesSwitcherAdapter mAdapter;
    private int mCurrentPosition;
    private int mPlaceholderDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switcher);

        parseIntent();
        initView();
        updateData();
    }

    private void parseIntent() {
        Intent intent = getIntent();
        mImageList = new ArrayList<>();
        mImageList = intent.getStringArrayListExtra(NavigatorImage.EXTRA_IMAGE_URL);
        mCurrentPosition = intent.getIntExtra(NavigatorImage.EXTRA_IMAGE_URL_POSITION, 0);
        mPlaceholderDrawable = intent.getIntExtra(NavigatorImage.EXTRA_IMAGE_PLACE_DRAWABLE_ID, R.drawable.ic_image_default);
    }

    private void initView() {
        mViewPager = (MutipleTouchViewPager) findViewById(R.id.view_pager);
        mAdapter = new ImagesSwitcherAdapter(getSupportFragmentManager(), mPlaceholderDrawable);
        mAdapter.setOnTabOneClickListener(this);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateData() {
        mAdapter.setImageList(mImageList);
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    public void onTabOneClick() {
        finish();
    }
}
