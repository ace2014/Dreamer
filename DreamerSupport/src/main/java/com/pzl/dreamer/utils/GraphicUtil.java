package com.pzl.dreamer.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.TextPaint;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class GraphicUtil {
    public static int subStringLength(String str, int maxPix, TextPaint paint) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        int currentIndex = 0;
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(0, i + 1);
            float valueLength = paint.measureText(temp);
            if (valueLength > maxPix) {
                currentIndex = i - 1;
                break;
            }
            if (valueLength == maxPix) {
                currentIndex = i;
                break;
            }
        }

        if (currentIndex == 0) {
            currentIndex = str.length() - 1;
        }
        return currentIndex;
    }

    public static float getStringWidth(String str, TextPaint paint) {
        float strWidth = paint.measureText(str);
        return strWidth;
    }

    public static float getDesiredWidth(String str, TextPaint paint) {
        float strWidth = Layout.getDesiredWidth(str, paint);
        return strWidth;
    }

    public static float getDesiredHeight(TextPaint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (float) Math.ceil(fm.descent - fm.ascent);
    }

    public static List<String> getDrawRowStr(String text, int maxWPix, TextPaint paint) {
        String[] texts = null;
        if (text.indexOf("\n") != -1) {
            texts = text.split("\n");
        } else {
            texts = new String[1];
            texts[0] = text;
        }

        List mStrList = new ArrayList();

        for (int i = 0; i < texts.length; i++) {
            String textLine = texts[i];
            while (true) {
                int endIndex = subStringLength(textLine, maxWPix, paint);
                if (endIndex <= 0) {
                    mStrList.add(textLine);
                } else if (endIndex == textLine.length() - 1)
                    mStrList.add(textLine);
                else {
                    mStrList.add(textLine.substring(0, endIndex + 1));
                }

                if (textLine.length() <= endIndex + 1)
                    break;
                textLine = textLine.substring(endIndex + 1);
            }

        }

        return mStrList;
    }

    public static int getDrawRowCount(String text, int maxWPix, TextPaint paint) {
        String[] texts = null;
        if (text.indexOf("\n") != -1) {
            texts = text.split("\n");
        } else {
            texts = new String[1];
            texts[0] = text;
        }

        List mStrList = new ArrayList();

        for (int i = 0; i < texts.length; i++) {
            String textLine = texts[i];
            while (true) {
                int endIndex = subStringLength(textLine, maxWPix, paint);
                if (endIndex <= 0) {
                    mStrList.add(textLine);
                } else if (endIndex == textLine.length() - 1)
                    mStrList.add(textLine);
                else {
                    mStrList.add(textLine.substring(0, endIndex + 1));
                }

                if (textLine.length() <= endIndex + 1)
                    break;
                textLine = textLine.substring(endIndex + 1);
            }

        }

        return mStrList.size();
    }

    public static int drawText(Canvas canvas, String text, int maxWPix, TextPaint paint, int left, int top) {
        if (TextUtils.isEmpty(text)) {
            return 1;
        }

        List mStrList = getDrawRowStr(text, maxWPix, paint);

        int hSize = (int) getDesiredHeight(paint);

        for (int i = 0; i < mStrList.size(); i++) {
            int x = left;
            int y = top + hSize / 2 + hSize * i;
            String textLine = (String) mStrList.get(i);
            canvas.drawText(textLine, x, y, paint);
        }

        return mStrList.size();
    }
}