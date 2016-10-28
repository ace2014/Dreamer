package com.pzl.dreamer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class ZipUtil {
    private static final int ZIPUTIL_CACHE = 524288;

    public static boolean zipFile(String filepath, String zippath) {
        boolean result = false;

        InputStream input = null;
        ZipOutputStream zipOut = null;
        try {
            File file = new File(filepath);
            File zipFile = new File(zippath);
            input = new FileInputStream(file);
            zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));

            byte[] buffer = new byte[524288];
            int temp = 0;
            while ((temp = input.read(buffer)) != -1) {
                zipOut.write(buffer, 0, temp);
            }
            System.out.println("zip " + filepath + " to " + zippath);

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                zipOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static boolean zipMultiFile(String filepath, String zippath) {
        boolean result = false;
        try {
            File file = new File(filepath);
            File zipFile = new File(zippath);
            InputStream input = null;
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

            byte[] buffer = new byte[524288];
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    input = new FileInputStream(files[i]);
                    zipOut.putNextEntry(new ZipEntry(file.getName() + File.separator + files[i].getName()));

                    int temp = 0;
                    while ((temp = input.read(buffer)) != -1) {
                        zipOut.write(buffer, 0, temp);
                    }
                    input.close();
                }
            } else {
                zipFile(filepath, zippath);
            }
            zipOut.close();
            System.out.println("zip directory is success");

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean zipContraFile(String zippath, String outfilepath, String filename) {
        boolean result = false;
        try {
            File file = new File(zippath);
            File outFile = new File(outfilepath);
            ZipFile zipFile = new ZipFile(file);
            ZipEntry entry = zipFile.getEntry(filename);
            InputStream input = zipFile.getInputStream(entry);
            OutputStream output = new FileOutputStream(outFile);

            byte[] buffer = new byte[524288];
            int temp = 0;
            while ((temp = input.read(buffer)) != -1) {
                output.write(buffer, 0, temp);
            }

            output.close();
            input.close();
            zipFile.close();

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean ZipContraMultiFile(String zippath, String outzippath) {
        boolean result = false;

        ZipInputStream zipInput = null;
        ZipFile zipFile = null;
        InputStream input = null;
        OutputStream output = null;
        try {
            File file = new File(zippath);
            File outFile = null;
            zipFile = new ZipFile(file);
            zipInput = new ZipInputStream(new FileInputStream(file));
            ZipEntry entry = null;

            byte[] buffer = new byte[524288];

            while ((entry = zipInput.getNextEntry()) != null) {
                System.out.println("解压缩" + entry.getName() + "文件");
                outFile = new File(outzippath + File.separator + entry.getName());

                if (entry.isDirectory()) {
                    if (!outFile.exists()) {
                        outFile.mkdirs();
                        continue;
                    }
                }
                if (!outFile.getParentFile().exists()) {
                    outFile.getParentFile().mkdir();
                }
                if (!outFile.exists()) {
                    outFile.createNewFile();
                } else {
                    outFile.delete();
                    outFile.createNewFile();
                }
                input = zipFile.getInputStream(entry);
                output = new FileOutputStream(outFile);

                int temp = 0;
                while ((temp = input.read(buffer)) != -1) {
                    output.write(buffer, 0, temp);
                }

            }

            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                zipInput.close();
                zipFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}