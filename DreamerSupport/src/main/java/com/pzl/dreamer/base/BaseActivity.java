package com.pzl.dreamer.base;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.pzl.dreamer.receiver.CommonReceiver;


/**
 * @author zl.peng
 * @version [1.0, 2016-10-17]
 */
public class BaseActivity extends AppCompatActivity {
    private CommonReceiver commonReceiver;//难得统一抽象

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    protected void registerBroadcastReceiver(String[] actions) {
        if (actions == null || actions.length == 0) return;

        commonReceiver = new CommonReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        for (String action : actions) {
            registerReceiver(commonReceiver, new IntentFilter(action));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (commonReceiver != null) unregisterReceiver(commonReceiver);
    }

}
