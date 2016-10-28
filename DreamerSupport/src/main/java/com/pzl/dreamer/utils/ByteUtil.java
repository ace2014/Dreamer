package com.pzl.dreamer.utils;

import java.math.BigInteger;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class ByteUtil {
    public static void putShort(byte[] b, short s, int index) {
        b[(index + 1)] = (byte) (s >> 8);
        b[(index + 0)] = (byte) (s >> 0);
    }

    public static short getShort(byte[] b, int index) {
        return (short) (b[(index + 1)] << 8 | b[(index + 0)] & 0xFF);
    }

    public static void putInt(byte[] bb, int x, int index) {
        bb[(index + 3)] = (byte) (x >> 24);
        bb[(index + 2)] = (byte) (x >> 16);
        bb[(index + 1)] = (byte) (x >> 8);
        bb[(index + 0)] = (byte) (x >> 0);
    }

    public static int getInt(byte[] bb, int index) {
        return (bb[(index + 3)] & 0xFF) << 24 | (bb[(index + 2)] & 0xFF) << 16 | (bb[(index + 1)] & 0xFF) << 8 | (bb[(index + 0)] & 0xFF) << 0;
    }

    public static void putLong(byte[] bb, long x, int index) {
        bb[(index + 7)] = (byte) (int) (x >> 56);
        bb[(index + 6)] = (byte) (int) (x >> 48);
        bb[(index + 5)] = (byte) (int) (x >> 40);
        bb[(index + 4)] = (byte) (int) (x >> 32);
        bb[(index + 3)] = (byte) (int) (x >> 24);
        bb[(index + 2)] = (byte) (int) (x >> 16);
        bb[(index + 1)] = (byte) (int) (x >> 8);
        bb[(index + 0)] = (byte) (int) (x >> 0);
    }

    public static long getLong(byte[] bb, int index) {
        return (bb[(index + 7)] & 0xFF) << 56 | (bb[(index + 6)] & 0xFF) << 48 | (bb[(index + 5)] & 0xFF) << 40 | (bb[(index + 4)] & 0xFF) << 32 | (bb[(index + 3)] & 0xFF) << 24 | (bb[(index + 2)] & 0xFF) << 16 | (bb[(index + 1)] & 0xFF) << 8 | (bb[(index + 0)] & 0xFF) << 0;
    }

    public static void putChar(byte[] bb, char ch, int index) {
        int temp = ch;

        for (int i = 0; i < 2; i++) {
            bb[(index + i)] = Integer.valueOf(temp & 0xFF).byteValue();
            temp >>= 8;
        }
    }

    public static char getChar(byte[] b, int index) {
        int s = 0;
        if (b[(index + 1)] > 0)
            s += b[(index + 1)];
        else
            s += 256 + b[(index + 0)];
        s *= 256;
        if (b[(index + 0)] > 0)
            s += b[(index + 1)];
        else
            s += 256 + b[(index + 0)];
        char ch = (char) s;
        return ch;
    }

    public static void putFloat(byte[] bb, float x, int index) {
        int l = Float.floatToIntBits(x);
        for (int i = 0; i < 4; i++) {
            bb[(index + i)] = Integer.valueOf(l).byteValue();
            l >>= 8;
        }
    }

    public static float getFloat(byte[] b, int index) {
        int l = b[(index + 0)];
        l &= 255;
        l = (int) (l | b[(index + 1)] << 8);
        l &= 65535;
        l = (int) (l | b[(index + 2)] << 16);
        l &= 16777215;
        l = (int) (l | b[(index + 3)] << 24);
        return Float.intBitsToFloat(l);
    }

    public static void putDouble(byte[] bb, double x, int index) {
        long l = Double.doubleToLongBits(x);
        for (int i = 0; i < 4; i++) {
            bb[(index + i)] = Long.valueOf(l).byteValue();
            l >>= 8;
        }
    }

    public static double getDouble(byte[] b, int index) {
        long l = b[0];
        l &= 255L;
        l |= b[1] << 8;
        l &= 65535L;
        l |= b[2] << 16;
        l &= 16777215L;
        l |= b[3] << 24;
        l &= 4294967295L;
        l |= b[4] << 32;
        l &= 1099511627775L;
        l |= b[5] << 40;
        l &= 281474976710655L;
        l |= b[6] << 48;
        l &= 72057594037927935L;
        l |= b[7] << 56;
        return Double.longBitsToDouble(l);
    }

    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);
    }
}