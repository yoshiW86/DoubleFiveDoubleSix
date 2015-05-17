package com.practice.rex.doublefivedoublesix;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CustomView(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    static class CustomView extends View {
        private final Path userDraw = new Path();//由觸摸手繪而產生的Path

        CustomView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            final Paint paint = new Paint();//Paint用來調整顏色、字型、線條等繪圖樣式
            paint.setStyle(Paint.Style.STROKE);//只畫線段，不填色
            paint.setStrokeWidth(5);
            paint.setColor(Color.BLUE);
            if (userDraw.isEmpty()) {//無手繪圖案
                canvas.drawLine(0, 0, canvas.getWidth(), canvas.getHeight(), paint);//左上到右下
                paint.setColor(Color.RED);
                canvas.drawPath(polygon(370, 26, 570, 366, 253, 154, 539, 122, 318, 400), paint);//五角星
            } else canvas.drawPath(userDraw, paint);//有手繪圖案則畫它
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN://開始觸摸
                    userDraw.moveTo(event.getX(), event.getY());
                    break;
                case MotionEvent.ACTION_MOVE://觸摸中移動
                    userDraw.lineTo(event.getX(), event.getY());
                    invalidate();//重繪
                    break;
                default:
                    return false;//其他Action的後續，不處理
            }
            return true;//DOWN和MOVE的後續，要處理
        }

        private static Path polygon(float... points) {//由座標們轉換為Path
            final int n = points.length;
            final Path path = new Path();
            path.moveTo(points[n - 2], points[n - 1]);//最後一個點
            for (int i = 0; i < n; ) path.lineTo(points[i++], points[i++]);
            return path;
        }
    }
}
