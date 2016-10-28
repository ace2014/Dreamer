package com.pzl.dreamer.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class Md5Util {
    public static final String md5(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = str.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] tmp = mdTemp.digest();

            char[] strs = new char[32];

            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = tmp[i];
                strs[(k++)] = hexDigits[(byte0 >>> 4 & 0xF)];

                strs[(k++)] = hexDigits[(byte0 & 0xF)];
            }
            return new String(strs);
        } catch (Exception e) {
        }
        return null;
    }

    public static final String MD5(String str) {
        String result = md5(str);
        return result == null ? null : result.toUpperCase(Locale.getDefault());
    }

    public static final String md5Second(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 16)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static final String MD5Second(String str) {
        String result = md5Second(str);
        return result == null ? null : result.toUpperCase(Locale.getDefault());
    }
}
