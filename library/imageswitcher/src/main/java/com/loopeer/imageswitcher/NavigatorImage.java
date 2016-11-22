package com.loopeer.imageswitcher;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

public class NavigatorImage {
    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String EXTRA_IMAGE_URL_POSITION = "image_position";
    public static final String EXTRA_IMAGE_PLACE_DRAWABLE_ID = "extra_image_place_drawable_id";

    public static void startImageSwitchActivity(Context context, List<String> list, int position, int placeDrawableId) {
        Intent intent = new Intent(context, ImageSwitcherActivity.class);
        intent.putStringArrayListExtra(EXTRA_IMAGE_URL, new ArrayList<>(list));
        intent.putExtra(EXTRA_IMAGE_URL_POSITION, position);
        intent.putExtra(EXTRA_IMAGE_PLACE_DRAWABLE_ID, placeDrawableId);
        context.startActivity(intent);
    }
}
