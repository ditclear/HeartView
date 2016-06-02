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
 * HeartView custom ❤ view,auto exit with anim
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
        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
        // init path
        path = new Path();

        //int anim attr
        fromAlpha=1.0f;
        toAlpha=0.0f;
        fromScale=0.0f;
        toScale=1.0f;
        mDistance=300;

    }

    //show on view
    public HeartView showOnView(View view){
        this.px= (int) view.getX()+view.getWidth()/2;
        this.py= (int) view.getY();
        setPivotX(this.px);
        setPivotY(this.py);
        if (mDuration==0){
            mDuration=Math.abs(mDistance*4);
            Log.i("duration",""+mDuration);
            if (mAnim!=null) mAnim.setDuration(mDuration);

        }
        //start anim
        onAnimationStart();
        return this;
    }



    private HeartView randomShow(){

        //get screenWidth&&Height
        Resources resources = mContext.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = px2dp(dm.widthPixels,dm.density);
        int height = px2dp(dm.heightPixels,dm.density);
        //random x&&y

        this.px=(int)(Math.random() * width);
        this.py=(int)(Math.random() * height);

        setPivotX(width/2);
        setPivotY(height/2);
        //start anim
        onAnimationStart();
        return this;
    }

    public static int px2dp(float pxValue,float scale){
        return (int)(pxValue/scale+0.5f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // reset path
        path.reset();
        // start point
        path.moveTo(px, py - 5 * size);
        // draw heart
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

    //create animator
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
                //remove heartView form parent
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

    //viewSize,default 2
    public HeartView setSize(int size) {
        this.size = size;
        return this;
    }

    //anim duration
    public HeartView setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    //move distance,default 300
    public HeartView setDistance(int distance) {
        mDistance = distance;
        return this;
    }

     //view color,default red
    public HeartView setColor(int color) {
        mColor = color;
        paint.setColor(mColor);
        return this;
    }

    //timeInterpolator
    public HeartView setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
        if (mAnim==null) mAnim=createAnimator();
        mAnim.setInterpolator(mInterpolator);
        return this;
    }

    //alpha form -> to
    public HeartView setTransAlpha(float fromAlpha,float toAlpha) {
        this.fromAlpha = fromAlpha;
        this.toAlpha = toAlpha;
        return this;
    }


    //scale form -> to
    public HeartView setTransScale(float fromScale,float toScale) {
        this.fromScale = fromScale;
        this.toScale = toScale;
        return this;
    }

}