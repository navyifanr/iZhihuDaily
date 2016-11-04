package cn.cfanr.izhihudaily.utils;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import cn.cfanr.izhihudaily.app.AppController;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * @author xifan
 * @time 2016/5/6
 * @desc 图片加载工具类
 */
public class ImageUtils {

    public static void loadImage(ImageView imageView, String url, @DrawableRes int defaultImgId, @DrawableRes int failedImgId){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, defaultImgId, failedImgId);
        ImageLoader imageLoader=AppController.getInstance().getImageLoader();
        imageLoader.get(url, listener);
    }

    public static void loadImage(ImageView imageView, String url){
        loadImage(imageView, url, 0, 0);
    }

    public static void loadImageBySize(ImageView imageView, String url, int imgWidth, int imgHeight){
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView, 0, 0);
        ImageLoader imageLoader=AppController.getInstance().getImageLoader();
        imageLoader.get(url, listener,imgWidth, imgHeight);
    }

    public static void displayScaleImage(Context context, final ImageView imageView, String url, final PhotoViewAttacher photoViewAttacher) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).
                load(url)
//                .placeholder(R.drawable.avatar_default)
//                .error(R.drawable.image_default_rect)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        if (photoViewAttacher != null) {
                            photoViewAttacher.update();
                        }
                    }
                });
    }
}
