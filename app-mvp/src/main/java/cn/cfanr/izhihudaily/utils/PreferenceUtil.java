package cn.cfanr.izhihudaily.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * <li>putString(Context, String, String)			保存字符串数据 </li>
 * <li>getString(Context, String, String)			获取字符串数据 </li>
 * <li>putInt(Context, String, int)				保存整型数据 </li>
 * <li>getInt(Context, String, int)				获取整型数据 </li>
 * <li>putLong(Context, String, long)				获取长整型数据 </li>
 * <li>getLong(Context, String, long)				获取长整型数据 </li>
 * <li>putBoolean(Context, String, boolean)		获取布尔值数据 </li>
 * <li>getBoolean(Context, String, boolean)		获取布尔值数据 </li>
 * <li>putFloat(Context, String, float)			获取浮点型数据 </li>
 * <li>getFloat(Context, String, float)			获取浮点型数据 </li>
 */
public class PreferenceUtil {
    private static String PREFS_NAME = "iZhihuDaily";
    private static final String KEY_READ_ARTICLE_IDS = "key_read_article_ids";
    /**
     * 保存字符串数据
     */
    public static void putString(Context context, String key, String value, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 获取字符串数据
     */
    public static String getString(Context context, String key, String defValue, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        return pref.getString(key, defValue);
    }

    /**
     * 保存整型数据
     */
    public static void putInt(Context context, String key, int value, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * 保存整型数据
     */
    public static int getInt(Context context, String key, int defValue, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        return pref.getInt(key, defValue);
    }


    /**
     * 保存长整型数据
     */
    public static void putLong(Context context, String key, long value, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * 保存长整型数据
     */
    public static long getLong(Context context, String key, long defValue, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        return pref.getLong(key, defValue);
    }

    /**
     * 保存布尔值数据
     */
    public static void putBoolean(Context context, String key, boolean value, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * 保存布尔值数据
     */
    public static boolean getBoolean(Context context, String key, boolean defValue, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        return pref.getBoolean(key, defValue);
    }

    /**
     * 保存浮点型数据
     */
    public static void putFloat(Context context, String key, float value, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * 保存浮点型数据
     */
    public static float getFloat(Context context, String key, float defValue, String xmlName) {
        SharedPreferences pref = context.getSharedPreferences(xmlName, Context.MODE_PRIVATE);
        return pref.getFloat(key, defValue);
    }

    public static void setReadArticleIds(Context context, String articleId){
        String oldReadArticles = getReadArticleIds(context);
        if(!TextUtils.isEmpty(oldReadArticles)){
            oldReadArticles += ","+articleId;
        }else{
            oldReadArticles = articleId;
        }
        putString(context, KEY_READ_ARTICLE_IDS,  oldReadArticles, PREFS_NAME);
    }

    public static String getReadArticleIds(Context context){
        return getString(context, KEY_READ_ARTICLE_IDS, "", PREFS_NAME);
    }

    public static boolean isRead(Context context, String articleId){
        String readArticleIds = getReadArticleIds(context);
        return readArticleIds.contains(articleId);
    }
}
