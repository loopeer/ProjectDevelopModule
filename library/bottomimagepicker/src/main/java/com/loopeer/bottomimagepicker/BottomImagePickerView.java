package com.loopeer.bottomimagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class BottomImagePickerView extends LinearLayout{

    private static final int LOADER_ID_FOLDER = 10001 ;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private List<String> mTitles;
    private List<PickerFragment> mFragments;
    private PickerFragmentAdapter mFragmentAdapter;

    private LoaderManagerImpl mLoaderManager;

    public BottomImagePickerView(Context context) {
        super(context);
    }

    public BottomImagePickerView(Context context,
                                 @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomImagePickerView(Context context,
                                 @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext())
            .inflate(R.layout.view_bottom_image_picker, this, true);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);

        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        mLoaderManager = new LoaderManagerImpl(this);
        mFragmentAdapter = new PickerFragmentAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mTabLayout.setupWithViewPager(mViewPager);

        getSupportLoaderManager().initLoader(LOADER_ID_FOLDER, null, mLoaderManager);

    }


    public void setData(List<ImageFolder> folders) {
        mTitles.clear();
        mFragments.clear();
        for (int i = 0; i < folders.size(); i++) {
            ImageFolder folder = folders.get(i);
            mTitles.add(folder.name);
            mFragments.add(PickerFragment.newInstance(folder.images));
        }
        mFragmentAdapter.notifyDataSetChanged();
    }

    private LoaderManager getSupportLoaderManager() {
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof FragmentActivity)) {
            throw new IllegalStateException("check out the activity.is it a FragmentActivity ?");
        } else {
            return ((FragmentActivity) activity).getSupportLoaderManager();
        }
    }

    private FragmentManager getSupportFragmentManager() {
        Activity activity = getActivity();
        if (activity == null || !(activity instanceof FragmentActivity)) {
            throw new IllegalStateException("check out the activity.is it a FragmentActivity ?");
        } else {
            return ((FragmentActivity) activity).getSupportFragmentManager();
        }
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private class PickerFragmentAdapter extends FragmentPagerAdapter {

        public PickerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return mFragments == null ? null : mFragments.get(position);
        }

        @Override public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        @Override public CharSequence getPageTitle(int position) {
            return mTitles == null ? null : mTitles.get(position);
        }
    }

}
