package com.pzl.demo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pzl.demo.R;
import com.pzl.dreamer.anim.Rotate3dAnimation;

public class AnimActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener, Animation.AnimationListener {
    static final String TAG = "AnimActivity";

    private SeekBar sbFromDegrees;
    private SeekBar sbToDegrees;
    private SeekBar sbCenterX;
    private SeekBar sbCenterY;
    private SeekBar sbDepthZ;

    private TextView tvFromDegrees;
    private TextView tvToDegrees;
    private TextView tvCenterX;
    private TextView tvCenterY;
    private TextView tvDepthZ;
    private ImageView iv;

    private CheckBox cb;

    private int fromDegress;
    private int toDegrees;
    private int centerY;
    private int centerX;
    private int depthZ;
    private boolean isReverse;

    private Rotate3dAnimation rotate3dAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();
    }

    private void initView() {

        sbFromDegrees = (SeekBar) findViewById(R.id.sbFromDegrees);
        sbToDegrees = (SeekBar) findViewById(R.id.sbToDegrees);
        sbCenterY = (SeekBar) findViewById(R.id.sbCenterY);
        sbCenterX = (SeekBar) findViewById(R.id.sbCenterX);
        sbDepthZ = (SeekBar) findViewById(R.id.sbDepthZ);

        tvFromDegrees = (TextView) findViewById(R.id.tvFromDegrees);
        tvToDegrees = (TextView) findViewById(R.id.tvToDegrees);
        tvCenterX = (TextView) findViewById(R.id.tvCenterX);
        tvCenterY = (TextView) findViewById(R.id.tvCenterY);
        tvDepthZ = (TextView) findViewById(R.id.tvDepthZ);
        iv = (ImageView) findViewById(R.id.iv);

        cb = (CheckBox) findViewById(R.id.cb);
        cb.setOnCheckedChangeListener(this);

        sbFromDegrees.setOnSeekBarChangeListener(this);
        sbToDegrees.setOnSeekBarChangeListener(this);
        sbCenterY.setOnSeekBarChangeListener(this);
        sbCenterX.setOnSeekBarChangeListener(this);
        sbDepthZ.setOnSeekBarChangeListener(this);

        sbFromDegrees.setMax(90);
        sbToDegrees.setMax(90);
        sbCenterY.setMax(400);
        sbCenterX.setMax(400);
        sbDepthZ.setMax(100);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sbFromDegrees:
                fromDegress = 0 + progress;
                tvFromDegrees.setText("fromDegrees:" + fromDegress);
                break;
            case R.id.sbToDegrees:
                toDegrees = 90 + progress;
                tvToDegrees.setText("toDegrees:" + toDegrees);
                break;
            case R.id.sbCenterY:
                centerY = 0 + progress;
                tvCenterY.setText("centerY:" + centerY);
                break;
            case R.id.sbCenterX:
                centerX = 0 + progress;
                tvCenterX.setText("centerX:" + centerX);
                break;
            case R.id.sbDepthZ:
                depthZ = -50 + progress;
                tvDepthZ.setText("depthZ:" + depthZ);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void click(View view) {
        rotate3dAnimation = null;
        rotate3dAnimation = new Rotate3dAnimation(fromDegress, toDegrees, centerX, centerY, depthZ, isReverse);
        rotate3dAnimation.setDuration(500);
        // 动画完成后保持完成的状态
        rotate3dAnimation.setFillAfter(true);
        rotate3dAnimation.setInterpolator(new AccelerateInterpolator());
        // 设置动画的监听器
        rotate3dAnimation.setAnimationListener(this);
        iv.startAnimation(rotate3dAnimation);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        isReverse = isChecked;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
