package com.pzl.dreamer.utils;
import android.graphics.Color;
/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */



public class ColorUtil
{
    public static int[] colorToRGBA(int color)
    {
        int[] result = new int[4];

        result[0] = (color >> 16 & 0xFF);
        result[1] = (color >> 8 & 0xFF);
        result[2] = (color >> 0 & 0xFF);
        result[3] = (color >> 24 & 0xFF);

        return result;
    }

    public static int oppositeOpacityColor(int color)
    {
        int[] rgba = colorToRGBA(color ^ 0xFFFFFFFF);
        return Color.rgb(rgba[0], rgba[1], rgba[2]);
    }

    public static int oppositeColor(int color)
    {
        int[] rgba = colorToRGBA(color ^ 0xFFFFFFFF);
        return Color.argb(rgba[3], rgba[0], rgba[1], rgba[2]);
    }
}