package com.ditclear.heartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

/**
 * HeartView
 * Created by vienan on 16/5/31.
 */
public class HeartView extends View {
    private Paint paint;
    private Path path;
    private int px,py;
    private int rate=2;
    public HeartView(Context context) {
        super(context);

        init(context);
    }
    public HeartView(Context context,int rate) {
        super(context);

        this.rate=rate;

        init(context);
    }

    private void init(Context context) {
        //随机位置
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        this.px=(int)(Math.random() * width);
        this.py=(int)(Math.random() * height);
        // 初始化画笔
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        // 创建一个路径
        path = new Path();
        //启动动画
        onAnimationStart();

    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 重置画板
        path.reset();
        // 路径的起始点
        path.moveTo(px, py - 5 * rate);
        // 根据心形函数画图
        for (double i = 0; i <= 2 * Math.PI; i += 0.001) {
            float x = (float) (16 * Math.sin(i) * Math.sin(i) * Math.sin(i));
            float y = (float) (13 * Math.cos(i) - 5 * Math.cos(2 * i) - 2 * Math.cos(3 * i) - Math.cos(4 * i));
            x *= rate;
            y *= rate;
            x = px - x;
            y = py - y;
            path.lineTo(x, y);
        }
        canvas.drawPath(path, paint);

    }

    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();

        ValueAnimator animator= ObjectAnimator.ofFloat(this,"alpha",1f,0f);
        animator.setRepeatCount(3);
        animator.setDuration(500*3);
        animator.start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //动画结束时移除
                ((ViewGroup)getParent()).removeView(
                        (View) ((ObjectAnimator)animation).getTarget());
            }
        });
    }
}