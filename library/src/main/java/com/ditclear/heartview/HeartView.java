package com.ditclear.heartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * HeartView 自定义❤型view,可自定义多种属性以及自动淡出动画效果
 *
 * Created by vienan on 16/5/31.
 */
public class HeartView extends View {
    private Paint paint;
    private Path path;
    private int px,py;
    private int size;
    private int mDuration;
    private Context mContext;
    private int mDistance;
    private ValueAnimator mAnim=null;
    private int mColor;
    private float fromAlpha,toAlpha,fromScale,toScale;
    private TimeInterpolator mInterpolator;

    public HeartView(Context context) {
        super(context);

        init(context);
    }


    private void init(Context context) {

        mContext=context;
        size=2;
        mColor=Color.RED;
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        // 创建一个路径
        path = new Path();

        //初始化anim属性
        fromAlpha=1.0f;
        toAlpha=0.0f;
        fromScale=0.0f;
        toScale=1.0f;
        mDistance=300;

    }

    /**
     * 显示在view之上，可任意view
     * @param view
     * @return
     */
    public HeartView showOnView(View view){
        this.px= (int) view.getX()+view.getWidth()/2;
        this.py= (int) view.getY();
        setPivotX(this.px);
        setPivotY(this.py);
        if (mDuration==0){
            mDuration=Math.abs(mDistance*4);
            Log.i("dur",""+mDuration);
            if (mAnim!=null) mAnim.setDuration(mDuration);

        }
        //启动动画
        onAnimationStart();
        return this;
    }



    private HeartView randomShow(){

        //获取屏幕宽高
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = px2dp(dm.widthPixels,dm.density);
        int height = px2dp(dm.heightPixels,dm.density);
        //随机位置

        this.px=(int)(Math.random() * width);
        this.py=(int)(Math.random() * height);

        setPivotX(width/2);
        setPivotY(height/2);
        //启动动画
        onAnimationStart();
        return this;
    }

    public static int px2dp(float pxValue,float scale){
        return (int)(pxValue/scale+0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 重置画板
        path.reset();
        // 路径的起始点
        path.moveTo(px, py - 5 * size);
        // 根据心形函数画图
        for (double i = 0; i <= 2 * Math.PI; i += 0.001) {
            float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
            float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
            x *= size;
            y *= size;
            x = px - x;
            y = py - y;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, paint);

    }

    //创建动画
    ValueAnimator createAnimator(){

        PropertyValuesHolder fadeAnim = PropertyValuesHolder.ofFloat("alpha", fromAlpha,
                toAlpha);
        PropertyValuesHolder transAnim = PropertyValuesHolder.ofFloat("y", -mDistance);
        PropertyValuesHolder scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", fromScale,toScale);
        PropertyValuesHolder scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", fromScale,toScale);
        ValueAnimator animator=ObjectAnimator.ofPropertyValuesHolder(this,fadeAnim,transAnim,scaleXAnim,scaleYAnim);
        animator.setDuration(mDuration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时移除
                ViewGroup group= (ViewGroup) getParent();
                if (group!=null)
                    group.removeView((View) ((ObjectAnimator)animation).getTarget());
            }
        });
        return animator;
    }



    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();

        if (mAnim==null){
            mAnim=createAnimator();
            Log.i("anim","create");
        }
        mAnim.start();

    }
    /**
     * view大小,default 2
     * @param size
     * @return
     */
    public HeartView setSize(int size) {
        this.size = size;
        return this;
    }

    /**
     * 动画时长
     * @param duration
     * @return
     */
    public HeartView setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * 向上移动距离,default 300
     * @param distance
     * @return
     */
    public HeartView setDistance(int distance) {
        mDistance = distance;
        return this;
    }

    /**
     * 颜色,default red
     * @param color
     * @return
     */
    public HeartView setColor(int color) {
        mColor = color;
        paint.setColor(mColor);
        return this;
    }

    /**
     * 时间插值器
     * @param interpolator
     * @return
     */
    public HeartView setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
        if (mAnim==null) mAnim=createAnimator();
        mAnim.setInterpolator(mInterpolator);
        return this;
    }

    /**
     * 透明度变化
     * @param fromAlpha
     * @param toAlpha
     * @return
     */
    public HeartView setTransAlpha(float fromAlpha,float toAlpha) {
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        return this;
    }

    /**
     * 大小变化
     * @param fromScale
     * @param toScale
     * @return
     */
    public HeartView setTransScale(float fromScale,float toScale) {
        this.fromScale = fromScale;
        this.toScale = toScale;
        return this;
    }

}