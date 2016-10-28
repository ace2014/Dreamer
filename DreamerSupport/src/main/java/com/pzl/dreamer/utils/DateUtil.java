package com.pzl.dreamer.utils;

import android.util.Log;

import com.pzl.dreamer.BuildConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class DateUtil {
    public static String TAG = "DateUtil";

    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String dateFormatYMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static final String dateFormatYMD = "yyyy-MM-dd";
    public static final String dateFormatYM = "yyyy-MM";
    public static final String dateFormatY = "yyyy";
    public static final String dateFormatYMDHM = "yyyy-MM-dd HH:mm";
    public static final String dateFormatMD = "MM/dd";
    public static final String dateFormatHMS = "HH:mm:ss";
    public static final String dateFormatHM = "HH:mm";
    public static final String AM = "AM";
    public static final String PM = "PM";


    public static Date getDateByFormat(String strDate, String format) {
        if (strDate == null) return null;

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = mSimpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date getDateByOffset(Date date, int calendarField, int offset) {
        Calendar c = new GregorianCalendar();
        try {
            c.setTime(date);
            c.add(calendarField, offset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c.getTime();
    }

    public static String getStringByOffset(String strDate, String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(mSimpleDateFormat.parse(strDate));
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    public static String getStringByOffset(Date date, String format, int calendarField, int offset) {
        String strDate = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            c.setTime(date);
            c.add(calendarField, offset);
            strDate = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getStringByFormat(Date date, String format) {
        if (date == null) return null;

        SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
        String strDate = null;
        try {
            strDate = mSimpleDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strDate;
    }

    public static String getStringByFormat(String strDate, String format) {
        String mDateTime = null;
        try {
            Calendar c = new GregorianCalendar();
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            c.setTime(mSimpleDateFormat.parse(strDate));
            SimpleDateFormat mSimpleDateFormat2 = new SimpleDateFormat(format);
            mDateTime = mSimpleDateFormat2.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }

    public static String getStringByFormat(long milliseconds, String format) {
        String thisDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            thisDateTime = mSimpleDateFormat.format(Long.valueOf(milliseconds));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thisDateTime;
    }

    public static String getCurrentDate(String format) {
        if (DEBUG)
            Log.d(TAG, "getCurrentDate:" + format);
        String curDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            curDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curDateTime;
    }

    public static String getCurrentDateByOffset(String format, int calendarField, int offset) {
        String mDateTime = null;
        try {
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat(format);
            Calendar c = new GregorianCalendar();
            c.add(calendarField, offset);
            mDateTime = mSimpleDateFormat.format(c.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mDateTime;
    }


    public static long getFirstTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate("yyyy-MM-dd");
            date = getDateByFormat(currentDate + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
            return date.getTime();
        } catch (Exception e) {
        }
        return -1L;
    }

    public static long getLastTimeOfDay() {
        Date date = null;
        try {
            String currentDate = getCurrentDate("yyyy-MM-dd");
            date = getDateByFormat(currentDate + " 24:00:00", "yyyy-MM-dd HH:mm:ss");
            return date.getTime();
        } catch (Exception e) {
        }
        return -1L;
    }

    public static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 400 != 0)) || (year % 400 == 0);
    }


    public static String getTimeDescription(long milliseconds) {
        if (milliseconds > 1000L) {
            if (milliseconds / 1000L / 60L > 1L) {
                long minute = milliseconds / 1000L / 60L;
                long second = milliseconds / 1000L % 60L;
                return minute + "分" + second + "秒";
            }

            return milliseconds / 1000L + "秒";
        }

        return milliseconds + "毫秒";
    }
}