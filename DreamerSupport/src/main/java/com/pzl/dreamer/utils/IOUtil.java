package com.pzl.dreamer.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-27]
 */
public class IOUtil {

    /**
     * @param is
     * @return
     * @throws IOException
     */
    public static String readString(InputStream is) throws IOException {//自己只是个工具类，自己处理异常没什么实际意义
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            builder.append(line);
        }
        br.close();//只需关闭封装的最外层
        is.close();
        return builder.toString();
    }

    /**
     * @param is
     * @return
     */
    public static byte[] readByteArr(InputStream is) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];//1kb 缓存
        int len;
        while ((len = is.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);//内置缓存32b,可扩展expand方法
        }
        byte[] data = outStream.toByteArray();//拷贝内置缓存数据返回。网页的二进制数据
        outStream.close();
        is.close();
        return data;
    }



}
