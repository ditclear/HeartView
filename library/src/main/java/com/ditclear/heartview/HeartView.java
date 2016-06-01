package com.ditclear.heartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

/**
 * HeartView
 * Created by vienan on 16/5/31.
 */
public class HeartView extends View {
    private Paint paint;
    private Path path;
    private int px,py;
    private int size=2;
    private int mDuration=3000;
    private Context mContext;
    private int mDistance=200;
    private ValueAnimator mAnim=null;
    private int mColor= Color.RED;

    public HeartView(Context context) {
        super(context);

        init(context);
    }

    public HeartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HeartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public HeartView(Context context, int size, int distance, int color) {
        super(context);

        this.size=size;
        this.mDistance=distance;
        this.mColor=color;

        init(context);
    }

    public HeartView(Context context,int... params){
        super(context);
        this.size=params[0];
        this.mDistance=params[1];
        this.mColor=params[2];
        this.mDuration=params[3];
        init(context);

    }

    private void init(Context context) {

        mContext=context;
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        // 创建一个路径
        path = new Path();

    }



    public HeartView showOnView(View view){
        this.px= (int) view.getX()+view.getWidth()/2;
        this.py= (int) view.getY();
        setPivotX(this.px);
        setPivotY(this.py);
        //启动动画
        onAnimationStart();
        return this;
    }



    private HeartView randomShow(){

        //获取屏幕宽高
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        //随机位置

        this.px=(int)(Math.random() * width);
        this.py=(int)(Math.random() * height);

        setPivotX(width/2);
        setPivotY(height/2);
        //启动动画
        onAnimationStart();
        return this;
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

        PropertyValuesHolder fadeAnim = PropertyValuesHolder.ofFloat("alpha", 1f,
                0f);
        PropertyValuesHolder transAnim = PropertyValuesHolder.ofFloat("y", -mDistance);
        PropertyValuesHolder scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", 0f,2f);
        PropertyValuesHolder scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", 0f,2f);
        ValueAnimator animator=ObjectAnimator.ofPropertyValuesHolder(this,fadeAnim,transAnim,scaleXAnim,scaleYAnim);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(mDuration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时移除
                ViewGroup group= (ViewGroup) getParent();
                if (group!=null)
                    group.removeView((android.view.View) ((ObjectAnimator)animation).getTarget());
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

    public HeartView setSize(int size) {
        this.size = size;
        return this;
    }

    public HeartView setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public HeartView setDistance(int distance) {
        mDistance = distance;
        return this;
    }


    public HeartView setMColor(int color) {
        mColor = color;
        paint.setColor(color);
        return this;
    }

}