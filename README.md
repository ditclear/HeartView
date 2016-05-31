# HeartView
自定义❤型view,屏幕上可随机出现 自动消失

###ScreenShot

![gif](https://github.com/vienan/HeartView/blob/master/screenshot.gif)

###画❤型

```java

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

```

[HeartView 代码](https://github.com/vienan/HeartView/blob/master/library/src/main/java/com/ditclear/heartview/HeartView.java)

###Usage

```java
  
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

```

###Contact me

  >E-mail:1940692836@qq.com  
  >Location:成都软件园
