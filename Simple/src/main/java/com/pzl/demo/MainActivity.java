package com.pzl.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pzl.demo.activity.AnimActivity;
import com.pzl.demo.activity.NetActivity;
import com.pzl.demo.activity.OtherActivity;
import com.pzl.demo.activity.SQLiteActivity;
import com.pzl.dreamer.base.BaseActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnNet:
                intent.setClass(this, NetActivity.class);
                break;
            case R.id.btnAnim:
                intent.setClass(this, AnimActivity.class);
                break;
            case R.id.btnSqlite:
                intent.setClass(this, SQLiteActivity.class);
                break;
            case R.id.btnOther:
                intent.setClass(this, OtherActivity.class);
                break;
        }
        startActivity(intent);
    }

}
