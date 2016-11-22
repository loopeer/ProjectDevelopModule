
package com.loopeer.imageswitcher;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.loopeer.imageswitcher.photodraweeview.PhotoDraweeView;

public final class ImageDisplayHelper {

    public static Uri createNetWorkImageUri(String path) {
        return Uri.parse(path);
    }

    public static void displayImage(final SimpleDraweeView draweeView, String path, int width, int height) {
        if (draweeView == null || TextUtils.isEmpty(path)) {
            return;
        }

        Uri uri = createNetWorkImageUri(path);
        ImageRequest
                request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .setAutoRotateEnabled(true)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        draweeView.setController(controller);
    }

    public static void displayImage(final PhotoDraweeView draweeView, String path) {
        if (draweeView == null || TextUtils.isEmpty(path)) {
            return;
        }

        Uri uri = createNetWorkImageUri(path);

        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(uri);
        controller.setOldController(draweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || draweeView == null) {
                    return;
                }
                draweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        draweeView.setController(controller.build());
    }


}
