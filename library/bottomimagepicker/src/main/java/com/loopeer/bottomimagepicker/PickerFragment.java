package com.loopeer.bottomimagepicker;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PickerFragment extends Fragment {

    private static final String IMAGE_LIST = "image_list";

    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private GridLayoutManager mGridLayoutManager;
    private RecyclerView.ItemDecoration mHorizontalItemDecoration;
    private RecyclerView.ItemDecoration mVerticalItemDecoration;
    private List<Image> mImages;

    public static PickerFragment newInstance(List<Image> images) {
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(IMAGE_LIST, (ArrayList<? extends Parcelable>) images);
        PickerFragment fragment = new PickerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImages = getArguments().getParcelableArrayList(IMAGE_LIST);
        mImageAdapter = new ImageAdapter(mImages);
        mGridLayoutManager = new GridLayoutManager(getContext(), 5, GridLayoutManager.HORIZONTAL,
            false);
        mHorizontalItemDecoration = new DividerItemDecoration(getContext(),
            DividerItemDecoration.HORIZONTAL);
        mVerticalItemDecoration = new DividerItemDecoration(getContext(),
            DividerItemDecoration.VERTICAL);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_picker, container, false);
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_picker);
        mRecyclerView.setAdapter(mImageAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //mRecyclerView.addItemDecoration(mHorizontalItemDecoration);
        //mRecyclerView.addItemDecoration(mVerticalItemDecoration);
    }

}
