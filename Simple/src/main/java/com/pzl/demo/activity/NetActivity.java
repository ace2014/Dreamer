package com.pzl.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.pzl.demo.R;
import com.pzl.dreamer.net.HTTPUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class NetActivity extends AppCompatActivity {
    private ExecutorService pool = Executors.newFixedThreadPool(3);
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_RESP:
                    tvGetResult.setVisibility(View.VISIBLE);
                    tvGetResult.setText((CharSequence) msg.obj);
                    break;
            }
        }
    };
    private TextView tvGetResult;
    private EditText etGetUrl;
    private EditText etPostParams;

    private final int GET_HTTP_CLIENT = 1;
    private final int POST_HTTP_CLIENT = 4;
    private final int GET_HTTP_URLCONNECTION = 2;
    private final int POST_HTTP_URLCONNECTION = 3;
    private final int WHAT_RESP = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        tvGetResult = (TextView) findViewById(R.id.tvGetResult);
        etGetUrl = (EditText) findViewById(R.id.etUrl);
        etPostParams = (EditText) findViewById(R.id.etPostParams);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnHttpClientGet:
                pool.execute(new HttpTask(etGetUrl.getText().toString().trim(), null, GET_HTTP_CLIENT));
                break;
            case R.id.btnHttpClientPost:
                pool.execute(new HttpTask(etGetUrl.getText().toString().trim(), etPostParams.getText().toString().trim(), POST_HTTP_CLIENT));
                break;
            case R.id.btnHttpURLConnectionGet:
                pool.execute(new HttpTask(etGetUrl.getText().toString().trim(), null, GET_HTTP_URLCONNECTION));
                break;
            case R.id.btnHttpURLConnectionPost:
                pool.execute(new HttpTask(etGetUrl.getText().toString().trim(), etPostParams.getText().toString().trim(), POST_HTTP_URLCONNECTION));
                break;
        }
    }

    class HttpTask implements Runnable {
        private String url;
        private String params;
        private int netType;

        public HttpTask(String url, String params, int netType) {
            this.url = url;
            this.netType = netType;
            this.params = params;
        }

        @Override
        public void run() {
            switch (netType) {
                case GET_HTTP_CLIENT:
                    String result1 = HTTPUtil.getInstance().httpClientGet(url);
                    Message msg1 = handler.obtainMessage(WHAT_RESP, result1);
                    handler.sendMessage(msg1);
                    break;
                case POST_HTTP_CLIENT:
                    String result4 = HTTPUtil.getInstance().httpClientPost(url, params);
                    Message msg4 = handler.obtainMessage(WHAT_RESP, result4);
                    handler.sendMessage(msg4);
                    break;
                case GET_HTTP_URLCONNECTION:
                    String result2 = HTTPUtil.getInstance().doGet(url);
                    Message msg2 = handler.obtainMessage(WHAT_RESP, result2);
                    handler.sendMessage(msg2);
                    break;
                case POST_HTTP_URLCONNECTION:
                    String result3 = HTTPUtil.getInstance().doPost(url, params);
                    Message msg3 = handler.obtainMessage(WHAT_RESP, result3);
                    handler.sendMessage(msg3);
                    break;
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pool.shutdown();
    }
}
