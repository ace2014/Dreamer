package com.pzl.dreamer.utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class AssetsUtil {

    /**
     * 把文件从 asset 拷贝到sd卡
     *
     * @param context
     * @param assetPath
     * @param sdcardPath
     * @return
     */
    public static boolean copyFile(Context context, String assetPath, String sdcardPath) {
        InputStream is = null;
        FileOutputStream fs = null;

        boolean result = false;
        try {
            int byteread = 0;
            is = context.getAssets().open(assetPath);
            fs = new FileOutputStream(sdcardPath);
            byte[] buffer = new byte[1024];
            while ((byteread = is.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static String[] getListFile(Context context, String assetPath) {
        try {
            String[] list = context.getAssets().list(assetPath);
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把assert所有文件拷贝到sd卡
     *
     * @param context
     * @param assetPath
     * @param sdcardPath
     */
    public static void copyFolder(Context context, String assetPath, String sdcardPath) {
        try {
            String[] list = context.getAssets().list(assetPath);

            File f = new File(sdcardPath);
            if (!f.exists()) f.mkdirs();

            for (String s : list)
                copyFile(context, assetPath + "/" + s, sdcardPath + "/" + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}