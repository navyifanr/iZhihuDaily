package cn.cfanr.izhihudaily.utils;

import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * @author xifan
 * @time 2016/5/24
 * @desc 动画工具类
 */
public class AnimUtils {

    public static void rotateAnim(float fromDegrees, float toDegrees, ImageView imageView){
        RotateAnimation animation = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setFillAfter(true);
        imageView.startAnimation(animation);
    }
}
