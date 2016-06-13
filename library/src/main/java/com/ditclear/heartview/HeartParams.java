package com.ditclear.heartview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by vienan on 16/6/3.
 */

public  class HeartParams {

    private Context mContext;
    private Path mPath;
    private Paint paint;
    private int size;
    private int mDuration;
    private int mDistance;
    private int mColor;
    private float fromAlpha, toAlpha, fromScale, toScale;

    public HeartParams(Context context) {

        this.mContext = context;

        size = 2;
        mColor = Color.RED;
        // init paint
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(mColor);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);

        //int anim attr
        fromAlpha = 1.0f;
        toAlpha = 0.0f;
        fromScale = 0.0f;
        toScale = 1.0f;
        mDistance = 300;
    }

    public Context getContext() {
        return mContext;
    }

    public Path getPath() {
        return new Path();
    }

    public Paint getPaint() {
        return paint;
    }


    public int getSize() {
        return size;
    }

    public HeartParams setSize(int size) {
        this.size = size;
        return this;
    }

    public int getDuration() {
        return mDuration==0?Math.abs(mDistance * 4):mDuration;
    }

    public HeartParams setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    public int getDistance() {
        return mDistance;
    }

    public HeartParams setDistance(int distance) {
        mDistance = distance;
        return this;
    }

    public HeartParams setColor(int color) {
        mColor = color;
        paint.setColor(mColor);
        return this;
    }

    public float getFromAlpha() {
        return fromAlpha;
    }

    public HeartParams setFromAlpha(float fromAlpha) {
        this.fromAlpha = fromAlpha;
        return this;
    }

    public float getToAlpha() {
        return toAlpha;
    }

    public HeartParams setToAlpha(float toAlpha) {
        this.toAlpha = toAlpha;
        return this;
    }

    public float getFromScale() {
        return fromScale;
    }

    public HeartParams setFromScale(float fromScale) {
        this.fromScale = fromScale;
        return this;
    }

    public float getToScale() {
        return toScale;
    }

    public HeartParams setToScale(float toScale) {
        this.toScale = toScale;
        return this;
    }


}
