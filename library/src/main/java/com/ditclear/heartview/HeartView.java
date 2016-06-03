package com.ditclear.heartview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * HeartView custom ❤ view,auto exit with anim
 *
 * Created by vienan on 16/5/31.
 */
public class HeartView extends View {
    private Paint paint;
    private Path path;
    private int px, py;
    private int size;
    private int mDuration;
    private int mDistance;
    private ValueAnimator mAnim = null;
    private float fromAlpha, toAlpha, fromScale, toScale;
    private TimeInterpolator mInterpolator;

    public HeartView(Context context) {
        super(context);

    }


    //show on view
    public HeartView showOnView(View view) {
        this.px = (int) view.getX() + view.getWidth() / 2;
        this.py = (int) view.getY();
        setPivotX(this.px);
        setPivotY(this.py);
        //start anim
        onAnimationStart();
        return this;
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
    private ValueAnimator createAnimator() {

        PropertyValuesHolder fadeAnim = PropertyValuesHolder.ofFloat("alpha", fromAlpha,
                toAlpha);
        PropertyValuesHolder transAnim = PropertyValuesHolder.ofFloat("y", -mDistance);
        PropertyValuesHolder scaleXAnim = PropertyValuesHolder.ofFloat("scaleX", fromScale, toScale);
        PropertyValuesHolder scaleYAnim = PropertyValuesHolder.ofFloat("scaleY", fromScale, toScale);
        ValueAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, fadeAnim, transAnim, scaleXAnim, scaleYAnim);
        animator.setDuration(mDuration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //remove heartView form parent
                ViewGroup group = (ViewGroup) getParent();
                if (group != null)
                    group.removeView((View) ((ObjectAnimator) animation).getTarget());
            }
        });
        return animator;
    }


    @Override
    protected void onAnimationStart() {
        super.onAnimationStart();

        if (mAnim == null) {
            mAnim = createAnimator();
            Log.i("anim", "create");
        }
        mAnim.start();

    }


    //timeInterpolator
//    public HeartView setInterpolator(TimeInterpolator interpolator) {
//        mInterpolator = interpolator;
//        if (mAnim == null) mAnim = createAnimator();
//        mAnim.setInterpolator(mInterpolator);
//        return this;
//    }


    public static class Builder {

        private final HeartParams mParams;


        public Builder(Context context) {
            mParams = new HeartParams(context);
        }

        //viewSize,default 2
        public Builder setSize(int size) {
            mParams.setSize(size);
            return this;
        }

        //anim duration
        public Builder setDuration(int duration) {
            mParams.setDuration(duration);
            return this;
        }

        //move distance,default 300
        public Builder setDistance(int distance) {
            mParams.setDistance(distance);
            return this;
        }

        //view color,default red
        public Builder setColor(int color) {
            mParams.setColor(color);
            return this;
        }


        //alpha form -> to
        public Builder setTransAlpha(float fromAlpha, float toAlpha) {
            mParams.setFromAlpha(fromAlpha);
            mParams.setToAlpha(toAlpha);
            return this;
        }


        //scale form -> to
        public Builder setTransScale(float fromScale, float toScale) {
            mParams.setFromScale(fromScale);
            mParams.setToScale(toScale);
            return this;
        }

        public HeartView create() {

            HeartView heartView = new HeartView(mParams.getContext());
            heartView.paint = mParams.getPaint();
            heartView.path=mParams.getPath();
            heartView.size=mParams.getSize();
            heartView.mDistance=mParams.getDistance();
            heartView.mDuration=mParams.getDuration();
            heartView.fromScale=mParams.getFromScale();
            heartView.toScale=mParams.getToScale();
            heartView.fromAlpha=mParams.getFromAlpha();
            heartView.toAlpha=mParams.getToAlpha();

            return heartView;
        }
    }

}