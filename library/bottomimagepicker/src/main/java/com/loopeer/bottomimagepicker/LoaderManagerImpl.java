package com.loopeer.bottomimagepicker;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LoaderManagerImpl implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String[] IMAGE_PROJECTION = {
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DISPLAY_NAME,
        MediaStore.Images.Media.DATE_ADDED,
        MediaStore.Images.Media._ID };

    private BottomImagePickerView mBottomImagePickerView;

    public LoaderManagerImpl(BottomImagePickerView view) {
        mBottomImagePickerView = view;
    }

    @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(mBottomImagePickerView.getContext(),
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,//uri
            IMAGE_PROJECTION,//projection
            null,//selection
            null,//selectionArgs
            IMAGE_PROJECTION[2] + " DESC");
    }

    @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        new LoaderTask().execute(data);
    }

    @Override public void onLoaderReset(Loader<Cursor> loader) {

    }

    private class LoaderTask extends AsyncTask<Cursor, Void, List<ImageFolder>> {

        @Override protected List<ImageFolder> doInBackground(Cursor... params) {
            Cursor cursor = params[0];
            List<ImageFolder> folders = new ArrayList<>();
            if (cursor != null) {
                int count = cursor.getCount();
                if (count > 0) {
                    if (cursor.moveToFirst()) {
                        do {
                            String path = cursor.getString(
                                cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                            String name = cursor.getString(
                                cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                            long time = cursor.getLong(
                                cursor.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                            Image image = new Image(path, name, time);
                            File imageFile = new File(path);
                            File folderFile = imageFile.getParentFile();
                            ImageFolder imageFolder = new ImageFolder();
                            imageFolder.name = folderFile.getName();
                            imageFolder.dir = folderFile.getAbsolutePath();
                            imageFolder.firstImagePath = path;
                            if (!folders.contains(imageFolder)) {
                                List<Image> imageList = new ArrayList<>();
                                imageList.add(image);
                                imageFolder.count++;
                                imageFolder.images = imageList;
                                folders.add(imageFolder);
                            } else {
                                ImageFolder f = folders.get(folders.indexOf(imageFolder));
                                f.images.add(image);
                                f.count++;
                            }
                        } while (cursor.moveToNext());
                    }
                }
            }
            return folders;
        }

        @Override protected void onPostExecute(List<ImageFolder> list) {
            super.onPostExecute(list);
            mBottomImagePickerView.setData(list);
        }
    }
}
