package com.pzl.demo.activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pzl.demo.R;
import com.pzl.demo.utils.MyDatabaseHelper;

import net.sqlcipher.Cursor;
import net.sqlcipher.database.SQLiteDatabase;

public class SQLiteActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this, "demo.db", null, 1);
        db = dbHelper.getWritableDatabase("secret_key");
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                ContentValues values = new ContentValues();
                values.put("name", "达芬奇密码");
                values.put("pages", 566);
                db.insert("Book", null, values);
                break;
            case R.id.btnQuery:
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        Log.d("TAG", "book name is " + name);
                        Log.d("TAG", "book pages is " + pages);
                    }
                }
                cursor.close();
                break;
        }
    }


}
