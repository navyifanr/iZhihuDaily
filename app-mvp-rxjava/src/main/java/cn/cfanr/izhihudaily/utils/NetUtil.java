package cn.cfanr.izhihudaily.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.cfanr.izhihudaily.app.AppController;

public class NetUtil {

    private NetUtil() {
    }

    public static boolean isNetworkConnected() {
        if (AppController.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppController.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected() {
        if (AppController.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppController.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected() {
        if (AppController.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppController.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType() {
        if (AppController.getInstance() != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) AppController.getInstance()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }
}
