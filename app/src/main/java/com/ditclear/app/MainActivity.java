package com.ditclear.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import com.ditclear.heartview.HeartView;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout container;
    private HeartView mHeartView;
    int viewColor = Color.RED;
    int distance = 300;
    int viewSize = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.container = (RelativeLayout) findViewById(R.id.container);
    }


    // add heartView
    void addHeart(final View v) {

        mHeartView = new HeartView(MainActivity.this)
                .setColor(viewColor)        //颜色,default red
                .setDistance(distance)      //向上移动距离,default 300
                .setSize(viewSize)          //view大小,default 2
                .setTransAlpha(1.0f, 0.0f)  //透明度变化,default 1.0f 0.0f
                .setTransScale(0.0f, 1.0f)  //大小变化,default 0.0f 1.0f
                .setDuration(2000)          //动画时长
                .setInterpolator(new AccelerateInterpolator()) //设置时间插值器
                .showOnView(v);             //最后调用，开启动画

        container.addView(mHeartView);

        Log.i("ChildCount", "" + container.getChildCount());


    }
}
