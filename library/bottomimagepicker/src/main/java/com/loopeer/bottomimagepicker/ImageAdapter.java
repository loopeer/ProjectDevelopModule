package com.loopeer.bottomimagepicker;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import com.facebook.drawee.view.SimpleDraweeView;
import java.io.File;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageHolder> {

    private List<Image> mImages;
    private int mImageSize;

    public void setImages() {
    }

    public ImageAdapter(List<Image> images,int imageSize) {
        mImages = images;
        mImageSize = imageSize;
    }

    @Override public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.list_item_image, parent, false);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mImageSize,mImageSize);
        itemView.setLayoutParams(params);
        return new ImageHolder(itemView);
    }

    @Override public void onBindViewHolder(ImageHolder holder, int position) {
        Image image = mImages.get(position);
        holder.bind(image);
    }

    @Override public int getItemCount() {
        return mImages == null ? 0 : mImages.size();
    }

    class ImageHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView mImageView;

        public ImageHolder(View itemView) {
            super(itemView);
            mImageView = (SimpleDraweeView) itemView.findViewById(R.id.image);
        }

        public void bind(Image image){
            mImageView.setImageURI(Uri.fromFile(new File(image.url)));
        }
    }
}
