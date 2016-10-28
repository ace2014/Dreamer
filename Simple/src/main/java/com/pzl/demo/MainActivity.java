package com.pzl.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.pzl.demo.activity.NetActivity;
import com.pzl.dreamer.utils.AppUtil;
import com.pzl.dreamer.utils.AssetsUtil;
import com.pzl.dreamer.utils.DeviceUtil;

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
        }
        startActivity(intent);
    }

}
