package cn.cfanr.izhihudaily.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.URLUtil;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import static android.R.attr.path;

/**
 * author: xifan
 * date: 2016/11/1
 * desc:
 */

public class DownLoadImageUtils {
    private static String TAG = "DownLoadImage";
    private static Bitmap mBitmap = null;

    public static void downLoad(final Context context, final String imgUrl){
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();
                try {
                    mBitmap = Glide.
                            with(context).
                            load(imgUrl).
                            asBitmap().
                            into(-1,-1).
                            get();
                } catch (final ExecutionException e) {
                    Log.e(TAG, e.getMessage());
                } catch (final InterruptedException e) {
                    Log.e(TAG, e.getMessage());
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (null != mBitmap) {
                    Log.d(TAG, "Image loaded");
                    createExternalStoragePublicPicture(context, imgUrl, mBitmap);
                }
            }
        }.execute();
    }

    /**
     * 保存图片到sd卡
     * @param context
     * @param bmp
     */
    private static void saveImage(Context context, Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "iZhihuDaily");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存图片到系统相册
     * @param context
     * @param bmp
     */
    public static void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File appDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
    }

    private static void createExternalStoragePublicPicture(Context context, String imgUrl, Bitmap bitmap) {
//        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File path = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String fileName = URLUtil.guessFileName(imgUrl, null, null);//imgUrl.substring(imgUrl.lastIndexOf('/') + 1);
        File file = new File(path, fileName);
        try {
            path.mkdirs();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        } catch (IOException e) {
            Log.w("ExternalStorage", "Error writing " + file, e);
        }
    }
}
