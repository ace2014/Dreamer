package com.pzl.demo.activity;

import android.app.PendingIntent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.pzl.demo.R;

public class OtherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pzl", "PendingIntent.FLAG_NO_CREATE=" + PendingIntent.FLAG_NO_CREATE);
                Log.d("pzl", "PendingIntent.FLAG_ONE_SHOT=" + PendingIntent.FLAG_ONE_SHOT);
                Log.d("pzl", "PendingIntent.FLAG_CANCEL_CURRENT=" + PendingIntent.FLAG_CANCEL_CURRENT);
                Log.d("pzl", "PendingIntent.FLAG_UPDATE_CURRENT=" + PendingIntent.FLAG_UPDATE_CURRENT);
                Log.d("pzl", "PendingIntent.FLAG_IMMUTABLE=" + PendingIntent.FLAG_IMMUTABLE);
            }
        });
    }

}
