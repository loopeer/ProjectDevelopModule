package com.loopeer.imageswitcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.loopeer.imageswitcher.photodraweeview.OnViewTapListener;
import com.loopeer.imageswitcher.photodraweeview.PhotoDraweeView;

public class ScaleImageFragment extends Fragment {

    private PhotoDraweeView mViewScale;
    private OnTabOneClickListener mListener;
    private String mImageUrl;
    private int mPlaceholderDrawable;
    private SimpleDraweeView mViewPlaceholder;

    public static ScaleImageFragment newInstance(String url, int placeholderDrawable) {
        ScaleImageFragment scaleImageFragment = new ScaleImageFragment();
        scaleImageFragment.mImageUrl = url;
        scaleImageFragment.mPlaceholderDrawable = placeholderDrawable;
        return scaleImageFragment;
    }

    public void setOneTabListener(OnTabOneClickListener listener) {
        this.mListener = listener;
    }

    public void setImageUrl(String imageUrl) {
        this.mImageUrl = imageUrl;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scale_image, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpPlaceHolderView(view);
        updateView(view);
    }

    private void updateView(View view) {
        mViewScale = (PhotoDraweeView) view.findViewById(R.id.image_scale_image);
        mViewScale.getAttacher().setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                if (mViewScale.getScale() == mViewScale.getMinimumScale() && mListener != null) {
                    mListener.onTabOneClick();
                } else {
                    mViewScale.getAttacher().setScale(mViewScale.getMinimumScale(), x, y, true);
                }
            }
        });

        ImageDisplayHelper.displayImage(mViewPlaceholder, mImageUrl, 200, 200);
        ImageDisplayHelper.displayImage(mViewScale, mImageUrl);
    }

    public void setUpPlaceHolderView(View view) {
        mViewPlaceholder = (SimpleDraweeView) view.findViewById(R.id.image_scale_placeholder);
        GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder
                .setFadeDuration(300)
                .setPlaceholderImage(ContextCompat.getDrawable(getContext(), mPlaceholderDrawable))
                .build();
        mViewPlaceholder.setHierarchy(hierarchy);
    }
}
