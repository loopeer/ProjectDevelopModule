package com.loopeer.bottomimagepicker;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.LoaderManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class BottomImagePickerView extends LinearLayout {

    private static final int LOADER_ID_FOLDER = 10001;

    private ImageView mIcon;
    private TabLayout mTabLayout;
    private SlideCustomViewPager mViewPager;

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
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getContext())
            .inflate(R.layout.view_bottom_image_picker, this, true);
        mIcon = (ImageView) view.findViewById(R.id.icon);
        mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (SlideCustomViewPager) view.findViewById(R.id.view_pager);

        mTitles = new ArrayList<>();
        mFragments = new ArrayList<>();

        mLoaderManager = new LoaderManagerImpl(this);
        mFragmentAdapter = new PickerFragmentAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setOffscreenPageLimit(3);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.setSlideEnable(true);

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

    public int getPeekHeight() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return PickerFragment.getUnitSize(wm) * PickerFragment.IMAGE_SIZE_UNIT +
            mTabLayout.getHeight() + mIcon.getHeight();
    }

    private class PickerFragmentAdapter extends FragmentStatePagerAdapter {

        public PickerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override public Fragment getItem(int position) {
            return mFragments == null ? null : mFragments.get(position);
        }

        @Override public int getCount() {
            return mTitles == null ? 0 : mTitles.size();
        }

        @Override public CharSequence getPageTitle(int position) {
            return mTitles == null ? null : mTitles.get(position);
        }
    }

}
