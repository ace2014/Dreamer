package com.pzl.dreamer.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */
public class ZipDirUtil {


    public static void zip(File directory, File zipfile, String password)
            throws Exception {
        URI base = directory.toURI();
        Deque queue = new LinkedList();
        queue.push(directory);
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new CipherOutputStream(new FileOutputStream(zipfile), createCipher(1, password)));
            while (!queue.isEmpty()) {
                directory = (File) queue.pop();
                for (File kid : directory.listFiles()) {
                    String name = base.relativize(kid.toURI()).getPath();
                    if (kid.isDirectory()) {
                        queue.push(kid);
                        name = name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));
                        copy(kid, zout);
                        zout.closeEntry();
                    }
                }
            }

            if (null != zout) {
                zout.close();
            }
            if (null != zout)
                zout.close();
        } finally {
            if (null != zout)
                zout.close();
        }
    }

    public static void unzip(File zipfile, File directory, String password)
            throws Exception {
        if (!directory.exists()) {
            directory.mkdirs();
        }

        ZipInputStream input = null;
        try {
            input = new ZipInputStream(new CipherInputStream(new FileInputStream(zipfile), createCipher(2, password)));
            ZipEntry entry = null;
            while ((entry = input.getNextEntry()) != null) {
                File file = new File(directory, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    copy(input, file);
                }

                input.closeEntry();
            }

            if (null != input) {
                input.close();
            }
            if (null != input)
                input.close();
        } finally {
            if (null != input)
                input.close();
        }
    }

    private static Cipher createCipher(int mode, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        String alg = "PBEWithMD5AndDES";
        SecureRandom sr = new SecureRandom();

        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(alg);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        Cipher cipher = Cipher.getInstance(alg);
        cipher.init(mode, secretKey, new PBEParameterSpec("saltsalt".getBytes(), 2000));

        return cipher;
    }

    public static void zip(File directory, File zipfile)
            throws IOException {
        URI base = directory.toURI();
        Deque queue = new LinkedList();
        queue.push(directory);
        ZipOutputStream zout = null;
        try {
            zout = new ZipOutputStream(new FileOutputStream(zipfile));
            while (!queue.isEmpty()) {
                directory = (File) queue.pop();
                for (File kid : directory.listFiles()) {
                    String name = base.relativize(kid.toURI()).getPath();
                    if (kid.isDirectory()) {
                        queue.push(kid);
                        name = name + "/";
                        zout.putNextEntry(new ZipEntry(name));
                    } else {
                        zout.putNextEntry(new ZipEntry(name));
                        copy(kid, zout);
                        zout.closeEntry();
                    }
                }
            }

            if (null != zout) {
                zout.close();
            }
            if (null != zout)
                zout.close();
        } finally {
            if (null != zout)
                zout.close();
        }
    }

    public static void unzip(File zipfile, File directory)
            throws IOException {
        if (!directory.exists()) {
            directory.mkdirs();
        }

        ZipInputStream input = null;
        try {
            input = new ZipInputStream(new FileInputStream(zipfile));
            ZipEntry entry = null;
            while ((entry = input.getNextEntry()) != null) {
                File file = new File(directory, entry.getName());
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    copy(input, file);
                }

                input.closeEntry();
            }

            if (null != input) {
                input.close();
            }
            if (null != input)
                input.close();
        } finally {
            if (null != input)
                input.close();
        }
    }

    private static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        while (true) {
            int readCount = in.read(buffer);
            if (readCount < 0) {
                break;
            }
            out.write(buffer, 0, readCount);
        }
    }

    private static void copy(File file, OutputStream out) throws IOException {
        InputStream in = new FileInputStream(file);
        try {
            copy(in, out);

            in.close();
        } finally {
            in.close();
        }
    }

    private static void copy(InputStream in, File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        try {
            copy(in, out);

            out.close();
        } finally {
            out.close();
        }
    }
}
