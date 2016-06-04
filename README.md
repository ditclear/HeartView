# HeartView
自定义❤型view,可自定义多种属性以及自动淡出动画效果 
`custom ❤ view,auto exit with anim`
[http://blog.csdn.net/scuyttyuuy123/article/details/51570898](http://blog.csdn.net/scuyttyuuy123/article/details/51570898)

**精简的建筑者模式实践** [http://blog.csdn.net/scuyttyuuy123/article/details/51578874](http://blog.csdn.net/scuyttyuuy123/article/details/51578874)

###Installation

gradle:

    compile 'com.ditclear:heartview:1.0.0'

maven:
```java
<dependency>
  <groupId>com.ditclear</groupId>
  <artifactId>heartview</artifactId>
  <version>1.0.0</version>
  <type>pom</type>
</dependency>
```
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
  
mHeartView = new HeartView.Builder(MainActivity.this)
                .setColor(viewColor)        //颜色,default red
                .setDistance(distance)      //向上移动距离,default 300
                .setSize(viewSize)          //view大小,default 2
                .setTransAlpha(1.0f, 0.0f)  //透明度变化,default 1.0f 0.0f
                .setTransScale(0.0f, 1.0f)  //大小变化,default 0.0f 1.0f
                .setDuration(2000)          //动画时长
                .create()					
                .showOnView(v);
        container.addView(mHeartView);

```
###Method
| Method 方法        | Des 描述           |  Default 默认值 |
| ------------- |:-------------:| :----------:|
| setSize(int size)      | view大小  | 2 |
| setDuration(int duration)      | 动画时长      |  Math.abs(distance*4)|
| setDistance(int distance) | 向上移动距离     |  300  |
| setColor(int color)     | view颜色  | Color.Red |
| setTransAlpha(float fromAlpha,float toAlpha)      | 透明度变化  | 1.0f -> 0.0f |
| setTransScale(float fromScale,float toScale)      | 大小变化  | 0.0f -> 1.0f |
| showOnView(View view)      | 开启动画  | 最后调用 |

###About me
  * Blog:http://vienan.github.io/
  * E-mail:1940692836@qq.com  
  * Location:成都软件园

###License
```java
Copyright (C) 2016 1940692836@qq.com 

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
