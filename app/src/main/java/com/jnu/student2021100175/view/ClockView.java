package com.jnu.student2021100175.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.jnu.student.R;

import java.util.Calendar;
import android.graphics.Matrix;
public class ClockView extends View {
    Bitmap clockDialBitmap;
    Bitmap secondDialBitmap;
    Bitmap hourDialBitmap;
    Bitmap minuteDialBitmap;
    private int viewWidth;
    private int viewHeight;
    private int centerX;
    private int centerY;
    private int radius;
    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context,AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化表盘
        clockDialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clock);
        //秒针
        secondDialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.second);
        //时针
        hourDialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.hour);
        //分针
        minuteDialBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.minute);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        viewWidth = w;
        viewHeight = h;
        centerX = viewWidth / 2;
        centerY = viewHeight / 2;
        radius = Math.min(viewWidth, viewHeight) / 2 - 20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制表盘图片
        //canvas.drawBitmap(clockDialBitmap, centerX - clockDialBitmap.getWidth() / 2, centerY - clockDialBitmap.getHeight() / 2, null);
        // 计算表盘应该绘制的大小
        float scaledWidth = viewWidth ; // 调整表盘宽度
        float scaledHeight = clockDialBitmap.getHeight() * (scaledWidth / clockDialBitmap.getWidth());


        // 缩放表盘图片
        Bitmap scaledClockDialBitmap = Bitmap.createScaledBitmap(clockDialBitmap, (int) scaledWidth, (int) scaledHeight, true);

        // 绘制表盘图片
        canvas.drawBitmap(scaledClockDialBitmap, centerX - scaledWidth / 2, centerY - scaledHeight / 2, null);

// 获取当前时间
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        // 缩放时针 Bitmap
        Bitmap scaledHourDialBitmap = Bitmap.createScaledBitmap(hourDialBitmap, hourDialBitmap.getWidth() * 1, hourDialBitmap.getHeight() * 1, false);

        // 缩放分针 Bitmap
        Bitmap scaledMinuteDialBitmap = Bitmap.createScaledBitmap(minuteDialBitmap, minuteDialBitmap.getWidth() * 1, minuteDialBitmap.getHeight()* 1, false);

        // 缩放秒针 Bitmap
        Bitmap scaledSecondDialBitmap = Bitmap.createScaledBitmap(secondDialBitmap, secondDialBitmap.getWidth() * 1, secondDialBitmap.getHeight()* 1, false);

        // 计算指针的角度
        float hourAngle = (hour % 12 + minute / 60.0f) * 360 / 12;
        float minuteAngle = (minute + second / 60.0f) * 360 / 60;
        float secondAngle = second * 360 / 60;

        // 旋转并绘制时针
        Matrix hourMatrix = new Matrix();
        hourMatrix.setRotate(hourAngle, scaledHourDialBitmap.getWidth() / 2, scaledHourDialBitmap.getHeight() / 2);
        hourMatrix.postTranslate(centerX - scaledHourDialBitmap.getWidth() / 2, centerY - scaledHourDialBitmap.getHeight() / 2);
        canvas.drawBitmap(scaledHourDialBitmap, hourMatrix, null);

        // 旋转并绘制分针
        Matrix minuteMatrix = new Matrix();
        minuteMatrix.setRotate(minuteAngle, scaledMinuteDialBitmap.getWidth() / 2, scaledMinuteDialBitmap.getHeight() / 2);
        minuteMatrix.postTranslate(centerX - scaledMinuteDialBitmap.getWidth() / 2, centerY - scaledMinuteDialBitmap.getHeight() / 2);
        canvas.drawBitmap(scaledMinuteDialBitmap, minuteMatrix, null);

        // 旋转并绘制秒针
        Matrix secondMatrix = new Matrix();
        secondMatrix.setRotate(secondAngle, scaledSecondDialBitmap.getWidth() / 2, scaledSecondDialBitmap.getHeight() / 2);
        secondMatrix.postTranslate(centerX - scaledSecondDialBitmap.getWidth() / 2, centerY - scaledSecondDialBitmap.getHeight() / 2);
        canvas.drawBitmap(scaledSecondDialBitmap, secondMatrix, null);

        // 每秒钟刷新界面
        postInvalidateDelayed(1000);
    }
}