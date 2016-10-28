package com.pzl.dreamer.net.download;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */


public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "itcast.db";
    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, "itcast.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement, downpath varchar(500), threadid INTEGER, downlength INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS downmsg (id integer primary key autoincrement, downpath varchar(500), name varchar(40),downstate int, state int)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS filedownlog");
        onCreate(db);
    }
}