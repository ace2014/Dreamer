package com.pzl.dreamer.utils;

import android.content.Context;

import net.sqlcipher.database.SQLiteDatabase.CursorFactory;
import net.sqlcipher.database.SQLiteOpenHelper;

public abstract class SQLCipherDatabaseHelper extends SQLiteOpenHelper {


    public SQLCipherDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


}
