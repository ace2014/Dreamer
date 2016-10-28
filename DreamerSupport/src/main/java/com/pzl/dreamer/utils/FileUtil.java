package com.pzl.dreamer.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class FileUtil {
    public static boolean newFolder(String folderPath) {
        boolean flag = false;
        try {
            File myFilePath = new File(folderPath);
            if (!myFilePath.exists())
                flag = myFilePath.mkdirs();
        } catch (Exception e) {
            Log.e("ERROR--------", "新建目录操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean newFile(String filePathAndName) {
        boolean flag = false;
        try {
            File myFilePath = new File(filePathAndName);
            if (!myFilePath.exists())
                flag = myFilePath.createNewFile();
        } catch (Exception e) {
            Log.e("ERROR--------", "新建文件操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    public static boolean delFile(String filePathAndName) {
        boolean flag = false;
        try {
            File myDelFile = new File(filePathAndName);
            flag = myDelFile.delete();
        } catch (Exception e) {
            Log.e("ERROR--------", "删除文件操作出错");
            e.printStackTrace();
        }
        return flag;
    }

    public static void delFolder(String folderPath) {
        try {
            delAllFile(folderPath);
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete();
        } catch (Exception e) {
            Log.e("ERROR--------", "删除文件夹操作出错");
            e.printStackTrace();
        }
    }

    private static void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator))
                temp = new File(path + tempList[i]);
            else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory())
                delFolder(path + "/" + tempList[i]);
        }
    }

    public static void copyFile(String oldPath, String newPath) {
        try {
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1024];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
                fs.close();
            } else {
                Log.e("ERROR--------", "复制单个文件操作出错：" + oldPath + "不存在");
            }
        } catch (Exception e) {
            Log.e("ERROR--------", "复制单个文件操作出错：" + oldPath);
            e.printStackTrace();
        }
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            if (newPath.endsWith(File.separator)) {
                newPath = newPath.replace(File.separator, "");
            }
            if (oldPath.endsWith(File.separator)) {
                oldPath = oldPath.replace(File.separator, "");
            }

            File oldFile = new File(oldPath);
            if (!oldFile.exists()) {
                Log.e("ERROR--------", "要复制的文件夹不存在：" + oldPath);
                return;
            }
            if (!oldFile.isDirectory()) {
                Log.e("ERROR--------", "要复制的路径不是文件夹：" + oldPath);
                return;
            }
            File newFile = new File(newPath);
            if (!newFile.exists()) {
                newFile.mkdirs();
            }

            String[] file = oldFile.list();
            for (int i = 0; i < file.length; i++) {
                File temp = new File(oldFile.getPath() + "/" + file[i]);
                if (temp.isFile())
                    copyFile(temp.getPath(), newPath + "/" + file[i]);
                else if (temp.isDirectory())
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
            }
        } catch (Exception e) {
            Log.e("ERROR--------", "复制整个文件夹内容操作出错：" + oldPath);
            e.printStackTrace();
        }
    }

    public static void moveFile(String oldPath, String newPath) {
        copyFile(oldPath, newPath);
        delFile(oldPath);
    }

    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public static void writeTxtFile(String filePath, String file_content, boolean isAllow_append)
            throws IOException {
        if (file_content == null) return;
        File f = new File(filePath);
        if ((f.exists()) && (!isAllow_append)) {
            f.delete();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(filePath, isAllow_append);

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

            bufferedWriter.append(file_content);
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            outputStreamWriter.close();
            fileOutputStream.close();
        }
    }

    public static String readTxtFile(String path)
            throws IOException {
        String result = "";
        FileInputStream fileInputStream = new FileInputStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        try {
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                result = result + line;
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            inputStreamReader.close();
            fileInputStream.close();
        }

        return "".equals(result) ? null : result;
    }

    public static boolean writeBytesFile(byte[] bytes, File file) {
        boolean result = false;

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] readBytesFile(File file) {
        byte[] bytes = null;
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream(1024);

            byte[] temp = new byte[1024];
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            bytes = out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }
}