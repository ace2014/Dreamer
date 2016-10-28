package com.pzl.dreamer.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class AppUtil {


    public static String genUUid() {
        UUID uuid = UUID.randomUUID();
        return String.valueOf(uuid).replace("-", "");
    }

    /**
     * 重启 activity
     *
     * @param context
     * @param activityCls
     */
    public static void exitAndRestart(Context context, Class<?> activityCls) {
        Intent intent = new Intent(context, activityCls);
        PendingIntent restartIntent = PendingIntent.getActivity(context, 0, intent, 268435456);
        AlarmManager mgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        mgr.set(1, System.currentTimeMillis() + 1000L, restartIntent);
    }

    /**
     * 安装app
     *
     * @param context
     * @param file
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(268435456);
        intent.setAction("android.intent.action.VIEW");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 卸载 app
     *
     * @param context
     * @param packageName
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent("android.intent.action.DELETE");
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    public static int getAppVersionCode(Context context) {
        int verCode = -1;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }

    public static String getAppVersionName(Context context) {
        String verCode = null;
        try {
            verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return verCode;
    }

    public static String getAppPackageName(Context context) {
        String pkName = null;
        try {
            pkName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return pkName;
    }

    /**
     * 按资源id获取名字
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getResourceNameById(Context context, int resId) {
        return context.getResources().getResourceName(resId);
    }

    /**
     * @param context
     * @param name    :资源的名称
     * @param defType 资源的类型(drawable,string等)
     * @return
     */
    public static int getResourcesIdByName(Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }


    public static void initScreenOrientation(Activity activity) {
        if (DeviceUtil.isPad(activity.getApplicationContext())) {
            if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        } else if (activity.getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 是否是竖屏
     *
     * @param activity
     * @return
     */
    public static boolean isPortrait(Activity activity) {
        return activity.getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    /**
     * 服务是否运行中
     *
     * @param ctx
     * @param className
     * @return
     */
    public static boolean isServiceRunning(Context ctx, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        List servicesList = activityManager.getRunningServices(2147483647);
        Iterator l = servicesList.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = (ActivityManager.RunningServiceInfo) l.next();
            if (className.equals(si.service.getClassName())) {
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 停止指定服务
     *
     * @param ctx
     * @param className
     * @return
     */
    public static boolean stopRunningService(Context ctx, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(ctx, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = ctx.stopService(intent_service);
        }
        return ret;
    }

    /**
     * 显示软键盘
     *
     * @param context
     */
    public static void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, 2);
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     */
    public static void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if ((inputMethodManager != null) && (((Activity) context).getCurrentFocus() != null))
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), 2);
    }

}
