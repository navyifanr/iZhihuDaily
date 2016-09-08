package cn.cfanr.izhihudaily.utils;

import android.support.annotation.StringRes;
import android.widget.Toast;

import cn.cfanr.izhihudaily.app.AppController;

/**
 * @author xifan
 * @time 2016/5/19
 * @desc
 */
public class ToastUtils {

    public static void show(CharSequence text) {
        if (text.length() < 10) {
            Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_LONG).show();
        }
    }

    public static void showLongTime(CharSequence text){
        Toast.makeText(AppController.getInstance(), text, Toast.LENGTH_LONG).show();
    }

    public static void show(@StringRes int resId) {
        show(AppController.getInstance().getString(resId));
    }

}
