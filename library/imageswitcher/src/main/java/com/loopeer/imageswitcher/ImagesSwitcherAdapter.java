package com.loopeer.imageswitcher;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class ImagesSwitcherAdapter extends FragmentPagerAdapter {

    private ArrayList<String> mImageList;
    private OnTabOneClickListener onTabOneClickListener;
    private int placeholderDrawable;

    public ImagesSwitcherAdapter(FragmentManager fm, int placeholderDrawable) {
        super(fm);
        this.placeholderDrawable = placeholderDrawable;
        mImageList = new ArrayList<>();
    }

    public void setOnTabOneClickListener(OnTabOneClickListener onTabOneClickListener) {
        this.onTabOneClickListener = onTabOneClickListener;
    }

    public void setImageList(List<String> list) {
        mImageList.clear();
        mImageList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        ScaleImageFragment scaleImageFragment = ScaleImageFragment.newInstance(mImageList.get(position), placeholderDrawable);
        scaleImageFragment.setOneTabListener(onTabOneClickListener);
        return scaleImageFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ScaleImageFragment scaleImageFragment = (ScaleImageFragment) super.instantiateItem(container, position);
        scaleImageFragment.setImageUrl(mImageList.get(position));
        return scaleImageFragment;
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }
}
