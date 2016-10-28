package com.pzl.dreamer.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class SdCardUtil {
    public static String getFirstExterPath() {
        return isFirstSdcardMounted() ? Environment.getExternalStorageDirectory().getPath() : null;
    }

    @TargetApi(19)
    public static String getSecondExterPath(Context context) {
        if (Version.hasKitKat()) {
            File[] filesDirs = context.getExternalFilesDirs(null);
            if ((filesDirs == null) || (filesDirs.length == 0)) return null;

            File file = filesDirs[(filesDirs.length - 1)];
            if (file != null) {
                String path = file.getAbsolutePath();
                return path.substring(0, path.length() - 1);
            }
            return null;
        }
        List<String> paths = getAllExterSdcardPath();
        if (paths.size() == 2) {
            for (String path : paths) {
                if ((path != null) && (!path.equals(getFirstExterPath()))) {
                    return path;
                }
            }
            return null;
        }
        return null;
    }

    public static boolean isFirstSdcardMounted() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static boolean isSecondSDcardMounted(Context context) {
        String sd2 = getSecondExterPath(context);
        if (sd2 == null) {
            return false;
        }
        return checkFsWritable(sd2 + File.separator);
    }

    public static boolean isWritable(String dir) {
        return checkFsWritable(dir);
    }

    private static boolean checkFsWritable(String dir) {
        if (dir == null) {
            return false;
        }
        File directory = new File(dir);

        if ((!directory.isDirectory()) &&
                (!directory.mkdirs())) {
            return false;
        }

        File f = new File(directory, ".keysharetestgzc");
        try {
            if (f.exists()) {
                f.delete();
            }
            if (!f.createNewFile()) {
                return false;
            }
            f.delete();
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    private static List<String> getAllExterSdcardPath() {
        List sdList = new ArrayList();

        String firstPath = getFirstExterPath();

        Runtime runtime = Runtime.getRuntime();
        InputStream is = null;
        BufferedReader br = null;
        try {
            Process proc = runtime.exec("mount");
            is = proc.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                if ((line.contains("secure")) ||
                        (line.contains("asec")) ||
                        (line.contains("media")) ||
                        (line.contains("system")) || (line.contains("cache")) || (line.contains("sys")) || (line.contains("data")) || (line.contains("tmpfs")) || (line.contains("shell")) || (line.contains("root")) || (line.contains("acct")) || (line.contains("proc")) || (line.contains("misc")) || (line.contains("obb")) || (
                        (!line.contains("fat")) && (!line.contains("fuse")) && (!line.contains("ntfs")))) {
                    continue;
                }
                String[] columns = line.split(" ");
                if ((columns != null) && (columns.length > 1)) {
                    String path = columns[1];
                    if ((path != null) && (!sdList.contains(path)) && (path.toLowerCase().contains("sd"))) {
                        sdList.add(columns[1]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ((!sdList.contains(firstPath)) && (firstPath != null)) {
            sdList.add(firstPath);
        }

        return sdList;
    }
}