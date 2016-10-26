package com.pzl.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.pzl.demo.R;
import com.pzl.dreamer.net.HTTPClientUtil;

public class NetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void click(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btnHTTPClientUtil:
                intent.setClass(this, HTTPClientUtilActivity.class);
                break;
        }
        startActivity(intent);
    }

}
