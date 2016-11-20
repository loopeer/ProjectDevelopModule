package com.loopeer.guideactivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class AbsGuideActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    public ViewPager mViewPager;
    public FrameLayout mContainer;

    private List<ImageView> mGuideViewList = new ArrayList<>();
    public static int[] sPics;
    private PageIndicator mPageIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initGuideView();
        initViewPager();
    }

    public void setPics(int[] pics) {
        sPics = pics;
    }

    public void setIndicatorDrawable(boolean show, @DrawableRes int selected, @DrawableRes int unSelected) {
        if (!show) {
            return;
        }
        mPageIndicator = new PageIndicator(this);
        Drawable selectedDrawable = ContextCompat.getDrawable(this, selected);
        Drawable unSelectedDrawable = ContextCompat.getDrawable(this, unSelected);
        mPageIndicator.updateDrawable(unSelectedDrawable, selectedDrawable);
        mPageIndicator.setIndicatorMargin(getResources().getDimensionPixelSize(R.dimen.default_padding));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.bottomMargin = getResources().getDimensionPixelSize(R.dimen.larger_padding);
        mContainer.addView(mPageIndicator, params);
        mPageIndicator.updateCount(sPics.length);
    }

    private void initGuideView() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int pic : sPics) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(pic);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            mGuideViewList.add(imageView);
        }
    }

    private void initViewPager() {
        GuidePagerAdapter mGuidePagerAdapter = new GuidePagerAdapter(mGuideViewList);
        mViewPager.setAdapter(mGuidePagerAdapter);
        mViewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mPageIndicator != null) {
            mPageIndicator.updatePosition(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    static class GuidePagerAdapter extends PagerAdapter {

        public List<ImageView> mImageViews;

        public GuidePagerAdapter(List<ImageView> list) {
            mImageViews = list;
        }

        @Override
        public int getCount() {
            return sPics.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViews.get(position));
            return mImageViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImageViews.get(position));
        }
    }

    @Override
    protected void onDestroy() {
        mViewPager.removeOnPageChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


}
