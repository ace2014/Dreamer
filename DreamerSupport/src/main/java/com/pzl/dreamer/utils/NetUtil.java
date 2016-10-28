package com.pzl.dreamer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class NetUtil {

    /**
     * 当前是否是3g网络
     *
     * @param context
     * @return
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetInfo != null) && (activeNetInfo.getType() == 0);
    }

    /**
     * wifi是否可用
     *
     * @param context
     * @return
     */
    public static boolean isWifiEnabled(Context context) {
        ConnectivityManager mgrConn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager mgrTel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ((mgrConn.getActiveNetworkInfo() != null) && (mgrConn.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED)) || (mgrTel.getNetworkType() == 3);
    }

    /**
     * 当前网络是否是wifi
     *
     * @param context
     * @return
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetInfo != null) && (activeNetInfo.getType() == 1);
    }

    /**
     * 网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if ((info != null) && (info.isConnected()) &&
                        (info.getState() == NetworkInfo.State.CONNECTED))
                    return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
