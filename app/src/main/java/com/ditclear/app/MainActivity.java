package com.ditclear.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ditclear.heartview.HeartView;

public class MainActivity extends AppCompatActivity {

    private android.widget.RelativeLayout container;
    HeartView mHeartView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.container = (RelativeLayout) findViewById(R.id.container);
    }

    void addHeart(final View v) {
        container.post(new Runnable() {
            @Override
            public void run() {
                //one
                mHeartView=new HeartView(MainActivity.this,4, (int) v.getY(), Color.RED).showOnView(v);
//                
                container.addView(mHeartView);
            }
        });


    }
}
