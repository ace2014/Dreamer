package com.pzl.dreamer.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.io.FileFilter;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class DeviceUtil {

    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        DisplayMetrics outSize = new DisplayMetrics();
        display.getMetrics(outSize);
        return outSize;
    }

    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled("gps");
    }

    public static boolean hasGPSDevice(Context context) {
        LocationManager mgr = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        List providers = mgr.getAllProviders();
        if (providers == null)
            return false;
        return providers.contains("gps");
    }

    /**
     * 获得cpu核心数
     *
     * @return
     */
    public static int getNumCores() {
        try {
            File dir = new File("/sys/devices/system/cpu/");

            File[] files = dir.listFiles(new FileFilter() {
                public boolean accept(File pathname) {
                    return Pattern.matches("cpu[0-9]", pathname.getName());
                }
            });
            return files.length;
        } catch (Exception e) {
        }
        return 1;
    }

    /**
     * 获得wifi mac 地址
     *
     * @param context
     * @return
     */
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info == null ? "" : info.getMacAddress();
    }

    /**
     * 获得屏幕尺寸大小（对角线）
     * 受导航栏的影响
     *
     * @param context
     * @return
     */
    public static double getScreenInches(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2.0D);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2.0D);
        return Math.sqrt(x + y);
    }

    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 0xF) >= 3;
    }

    public static boolean isPad2(Context context) {
        return getScreenInches(context) >= 6.0D;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String result = tm.getDeviceId();
        return result;
    }

    public static float dip2px(Context context, float dipValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return applyDimension(1, dipValue, mDisplayMetrics);
    }

    public static float px2dip(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.density;
    }

    public static float sp2px(Context context, float spValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return applyDimension(2, spValue, mDisplayMetrics);
    }

    public static float px2sp(Context context, float pxValue) {
        DisplayMetrics mDisplayMetrics = getDisplayMetrics(context);
        return pxValue / mDisplayMetrics.scaledDensity;
    }

    public static float applyDimension(int unit, float value, DisplayMetrics metrics) {
        switch (unit) {
            case 0:
                return value;
            case 1:
                return value * metrics.density;
            case 2:
                return value * metrics.scaledDensity;
            case 3:
                return value * metrics.xdpi * 0.01388889F;
            case 4:
                return value * metrics.xdpi;
            case 5:
                return value * metrics.xdpi * 0.03937008F;
        }
        return 0.0F;
    }

}
