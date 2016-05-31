package com.ditclear.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import com.ditclear.heartview.HeartView;

public class MainActivity extends AppCompatActivity {

    private android.widget.RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.container = (RelativeLayout) findViewById(R.id.container);
    }

    void addHeart(View v){
        container.addView(new HeartView(MainActivity.this));
    }
}
