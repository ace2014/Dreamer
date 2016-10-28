package com.pzl.dreamer.net.download;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * @author zl.peng
 * @version [1.0, 2016-10-28]
 */



public class FileService
{
    private DBOpenHelper openHelper;

    public FileService(Context context)
    {
        this.openHelper = new DBOpenHelper(context);
    }

    @SuppressLint({"UseSparseArrays"})
    public synchronized Map<Integer, Integer> getData(String path)
    {
        SQLiteDatabase db = this.openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select threadid, downlength from filedownlog where downpath=?", new String[] { path });

        Map data = new HashMap();
        while (cursor.moveToNext()) {
            data.put(Integer.valueOf(cursor.getInt(0)), Integer.valueOf(cursor.getInt(1)));
        }
        cursor.close();
        db.close();
        return data;
    }

    public synchronized void save(String path, Map<Integer, Integer> map)
    {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        for (Map.Entry entry : map.entrySet()) {
            db.execSQL("insert into filedownlog(downpath, threadid, downlength) values(?,?,?)", new Object[] { path, entry.getKey(), entry.getValue() });
        }

        db.close();
    }

    public void update(String path, Map<Integer, Integer> map)
    {
    }

    public synchronized void delete(String path)
    {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();
        db.execSQL("delete from filedownlog where downpath=?", new Object[] { path });

        db.close();
    }

    public synchronized ArrayList<DownMsgEntity> getDownLoadData() {
        SQLiteDatabase db = this.openHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from downmsg where state = 1", null);

        ArrayList data = new ArrayList();

        while (cursor.moveToNext()) {
            DownMsgEntity msg = new DownMsgEntity();
            msg.setId(cursor.getInt(cursor.getColumnIndex("id")));
            msg.setPath(cursor.getString(cursor.getColumnIndex("downpath")));
            msg.setDownstate(cursor.getInt(cursor.getColumnIndex("downstate")));
            msg.setName(cursor.getString(cursor.getColumnIndex("name")));
            data.add(msg);
        }
        cursor.close();
        db.close();
        return data;
    }

    public synchronized void saveDownLoadData(ArrayList<DownMsgEntity> list) {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        for (DownMsgEntity msg : list) {
            db.execSQL("insert into downmsg(downpath, name, downstate, state) values(?,?,?,?)", new Object[] { msg.getPath(), msg.getName(), Integer.valueOf(msg.getDownstate()), Integer.valueOf(1) });
        }

        db.close();
    }

    public synchronized void updateDownLoadData(DownMsgEntity msg)
    {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        db.execSQL("update downmsg set downstate=? where downpath = ?", new Object[] { Integer.valueOf(msg.getDownstate()), msg.getPath() });

        db.close();
    }

    public synchronized void updateDownLoadData()
    {
        SQLiteDatabase db = this.openHelper.getWritableDatabase();

        db.execSQL("update downmsg set state=0");

        db.close();
    }
}