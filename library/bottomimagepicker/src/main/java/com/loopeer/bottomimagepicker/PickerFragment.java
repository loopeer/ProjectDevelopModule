package com.loopeer.bottomimagepicker;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.List;

public class PickerFragment extends Fragment {

    private static final String IMAGE_LIST = "image_list";

    public static final int IMAGE_SIZE_UNIT = 10;
    public static final int DECORATION_SIZE_UNIT = 1;
    public static final int IMAGE_COUNT = 5;
    public static final int DECORATION_COUNT = 6;

    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private List<Image> mImages;
    private int mUnit;
    private int mPosition;

    public static PickerFragment newInstance(List<Image> images, int position) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IMAGE_LIST, (ArrayList<? extends Parcelable>) images);
        PickerFragment fragment = new PickerFragment();
        fragment.mPosition = position;
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = getArguments().getParcelableArrayList(IMAGE_LIST);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mUnit = getUnitSize(wm);
        mImageAdapter = new ImageAdapter(mImages, mUnit * IMAGE_SIZE_UNIT);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picker, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_picker);
        mRecyclerView.setTag(mPosition + " : ");
        mRecyclerView.setAdapter(mImageAdapter);
        //weak reference
        mRecyclerView.setLayoutManager(
            new GridLayoutManager(getContext(), 5, GridLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(
            new GridLayoutItemDecoration(mUnit * DECORATION_SIZE_UNIT, IMAGE_COUNT));
        mRecyclerView.setPadding(mUnit * DECORATION_SIZE_UNIT, 0, 0, mUnit * DECORATION_SIZE_UNIT);
    }

    public static int getUnitSize(WindowManager wm) {
        Display display = wm.getDefaultDisplay();
        int screenWidth = display.getWidth();
        return screenWidth / (DECORATION_COUNT + IMAGE_COUNT * IMAGE_SIZE_UNIT);
    }

    public void setImages(List<Image> images) {
        mImages = images;
        mImageAdapter.setImages(images);
    }
}
