package com.pzl.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pzl.demo.R;
import com.pzl.demo.bean.resp.ResponseBean;
import com.pzl.demo.interfaces.BaiDuSearch;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private Button btnAddPram;
    private Button btnSubPram;
    private Button btnRetrofitGet;
    private LinearLayout llGetParams;
    private EditText etUrl;
    private EditText etKeyWords;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {
        btnAddPram = (Button) findViewById(R.id.btnAddPram);
        btnSubPram = (Button) findViewById(R.id.btnSubPram);
        btnRetrofitGet = (Button) findViewById(R.id.btnRetrofitGet);
        llGetParams = (LinearLayout) findViewById(R.id.llGetParams);
        etUrl = (EditText) findViewById(R.id.etUrl);
        etKeyWords = (EditText) findViewById(R.id.etKeyWords);
        tvResult = (TextView) findViewById(R.id.tvResult);
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.btnAddPram:
                llGetParams.addView(LayoutInflater.from(this).inflate(R.layout.layout_get_params, null));
                break;
            case R.id.btnSubPram:
                if (llGetParams.getChildCount() > 0)
                    llGetParams.removeViewAt(llGetParams.getChildCount() - 1);
                break;
            case R.id.btnRetrofitGet:
                for (int i = 0; i <= llGetParams.getChildCount() - 1; i++) {
                    View param = llGetParams.getChildAt(i);
                    String name = ((EditText) param.findViewById(R.id.etParamName)).getText().toString().trim();
                    String value = ((EditText) param.findViewById(R.id.etParamValue)).getText().toString().trim();
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(etUrl.getText().toString().trim())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                BaiDuSearch baiDuSearch = retrofit.create(BaiDuSearch.class);
                Call<ResponseBean> call = baiDuSearch.search("UTF-8", etKeyWords.getText().toString().trim());
                call.enqueue(new Callback<ResponseBean>() {
                    @Override
                    public void onResponse(Call<ResponseBean> call, Response<ResponseBean> response) {
                        tvResult.setText("异步请求结果: " + response.body());
                    }

                    @Override
                    public void onFailure(Call<ResponseBean> call, Throwable t) {

                    }
                });
                break;
        }
    }

}
