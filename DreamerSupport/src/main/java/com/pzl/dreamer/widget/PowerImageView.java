package com.pzl.dreamer.widget;

import java.io.InputStream;
import java.lang.reflect.Field;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Build;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.pzl.dreamer.R;

public class PowerImageView extends ImageView implements View.OnClickListener {
    /**
     * 是否自动播放
     */
    private boolean isAutoPlay;
    /**
     * 播放GIF动画的关键类
     */
    private Movie mMovie;
    /**
     * gif宽高
     */
    private BitmapSize bitmapSize;
    /**
     * 播放按钮
     */
    private Bitmap mStartBotton;
    /**
     * 是否正在播放gif
     */
    private boolean isPlaying;
    /**
     * gif开始时间
     */
    private long mMovieStart;

    public PowerImageView(Context context) {
        super(context);
    }

    public PowerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PowerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        }
        obtainStyledAttr(context, attrs, defStyleAttr);
    }

    private void obtainStyledAttr(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a=context.getTheme().obtainStyledAttributes(attrs, R.styleable.PowerImageView, defStyleAttr, 0);
        int resId=getIdentifier(a);
        if(resId!=0){
            // 当资源id不等于0时，就去获取该资源的流
            InputStream is=getResources().openRawResource(resId);
            // 使用Movie类对流进行解码
            mMovie=Movie.decodeStream(is);
            //mMovie不等null说明这是一个GIF图片
            if(mMovie!=null){
                //是否自动播放
                isAutoPlay=a.getBoolean(R.styleable.PowerImageView_auto_play,false);
                /**
                 * 获取gif图片大小
                 */
                Bitmap bitmap= BitmapFactory.decodeStream(is);
                bitmapSize=new BitmapSize(bitmap.getWidth(),bitmap.getHeight());
                bitmap.recycle();
                if(!isAutoPlay){
                    // 当不允许自动播放的时候，得到开始播放按钮的图片，并注册点击事件
                    mStartBotton=BitmapFactory.decodeResource(getResources(),R.drawable.start_play);
                    setOnClickListener(this);
                }
            }
        }
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //当时gif图片的时候，控件宽高为gif文件大小
        if(mMovie!=null){
            setMeasuredDimension(bitmapSize.width, bitmapSize.height);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //当为一张普通的图片的时候
        if(mMovie==null){
            super.onDraw(canvas);
        }else{
            //如果自动播放的话，就直接播放
            if(isAutoPlay){
                playMovie(canvas);
                invalidate();
            }else{
                //如果已经点击了播放按钮的话就开始播放gif
                if(isPlaying){
                    if(playMovie(canvas)){
                        isPlaying=false;
                    }
                    invalidate();
                }else{
                    // 还没开始播放就只绘制GIF图片的第一帧，并绘制一个开始按钮
                    mMovie.setTime(0);
                    mMovie.draw(canvas, 0, 0);
                    int offsetW = (bitmapSize.width - mStartBotton.getWidth()) / 2;
                    int offsetH = (bitmapSize.height - mStartBotton.getHeight()) / 2;
                    canvas.drawBitmap(mStartBotton, offsetW, offsetH, null);
                }
            }
        }
    }
    /**
     * 开始播放GIF动画，播放完成返回true，未完成返回false。
     *
     * @param canvas
     * @return 播放完成返回true，未完成返回false。
     */
    private boolean playMovie(Canvas canvas) {
        //获取当前时间
        long now = SystemClock.uptimeMillis();
        if (mMovieStart == 0) {
            mMovieStart = now;
        }
        int duration = mMovie.duration();
        if (duration == 0) {
            duration = 1000;
        }
        int relTime = (int) ((now - mMovieStart) % duration);
        mMovie.setTime(relTime);//不断的设置gif的播放位置
        mMovie.draw(canvas, 0, 0);//将movie画在canvas上
        //如果（当前时间-gif开始的时间=gif总时长）说明播放完毕了
        if ((now - mMovieStart) >= duration) {
            mMovieStart = 0;
            return true;
        }
        return false;
    }
    /**
     * 通过反射获取src中的资源id
     * @param a
     */
    private int getIdentifier(TypedArray a) {
        try {
            Field mValueFiled = a.getClass().getDeclaredField("mValue");
            mValueFiled.setAccessible(true);
            TypedValue typedValue= (TypedValue) mValueFiled.get(a);
            return typedValue.resourceId;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 当点击图片的时候播放gif
     */
    @Override
    public void onClick(View v) {
        isPlaying = true;
        invalidate();
    }

    /**
     * BitmapSize
     */
    class BitmapSize{
        private int width;
        private int height;

        public BitmapSize(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }
}
