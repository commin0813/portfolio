package com.commin.pro.exerciseproject.page.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by user on 2016-12-02.
 * 게이지를 터치이벤트가 발생할때마다 일정비율로 줄이고
 * 화면을 갱신하여 다시 그립니다.
 */
public class BarView extends View {
    private float width = 50.0f;
    private float X = 0;
    private float X_END = 0;
    private float device_width = 0;
    private int count = 0;
    private Paint mPaint;

    public BarView(Context context, float device_width) {
        super(context);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(width);
        this.device_width = device_width;
        this.X_END = device_width;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (X_END >= 2) {

            if (X_END <= 100.0f) {//게이지의 남은 길이에 따라 게이지가 많이 줄어들고 적게줄어듭니다.
                X_END = X_END - count * 0.1f;
                mPaint.setColor(Color.RED);
            } else if (X_END <= device_width / 2) {
                X_END = X_END - count * 0.2f;
                mPaint.setColor(Color.YELLOW);
            } else {
                X_END = X_END - count * 0.5f;
                mPaint.setColor(Color.GREEN);
            }
            canvas.drawLine(X, getHeight() / 2, X_END, getHeight() / 2, mPaint);
        } else {
            X_END = device_width;//게이지의 길이가 2보다 줄어들면 다시 게이지를 채우고 count 값을 초기화합니다.
            count = 0;
        }
        count++;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                invalidate();
                break;
        }
        return true;
    }
}
