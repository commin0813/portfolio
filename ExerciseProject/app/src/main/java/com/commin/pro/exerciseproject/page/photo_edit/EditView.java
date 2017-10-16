package com.commin.pro.exerciseproject.page.photo_edit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.commin.pro.exerciseproject.ApplicationProperty;

/**
 * Created by user on 2016-12-02.
 */
public class EditView extends View implements Page2PhotoEdit.EditHandler {
    private static final String LOG_TAG = "EditView";
    private Paint text_paint;
    private Paint line_paint;
    private Path path = new Path();
    private Bitmap bitmap;
    private String text = null;
    private int code = 0;


    public EditView(Context context) {
        super(context);
        text_paint = new Paint();
        text_paint.setTextSize(50);
        text_paint.setColor(Color.BLUE);
        line_paint = new Paint();
        line_paint.setStyle(Paint.Style.STROKE);
        line_paint.setStrokeWidth(10f);
        line_paint.setColor(Color.CYAN);
        this.bitmap = Page2PhotoEdit.user_photo;


    }

//    public Bitmap getBitmap() {
//        return bitmap;
//    }

    @Override
    protected void onDraw(Canvas canvas) {

        Bitmap copyBitmap = null;

        copyBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        Rect src = new Rect(0, 0, w, h);
        Rect dst = new Rect(0, 0, getWidth(), getHeight());

        switch (code) {
            case ApplicationProperty.DRAW_LINE: {
                copyBitmap = Page2PhotoEdit.user_photo.copy(Bitmap.Config.ARGB_8888, true);
                bitmap = copyBitmap;
                Canvas line_canvas = new Canvas(copyBitmap);

                line_canvas.drawPath(path, line_paint);
                break;
            }
            case ApplicationProperty.ADD_TEXT: {
                if (text != null) {
                    copyBitmap = Page2PhotoEdit.user_photo.copy(Bitmap.Config.ARGB_8888, true);
                    bitmap = copyBitmap;
                    Canvas text_canvas = new Canvas(copyBitmap);
                    text_canvas.drawText(text, 10, 50, text_paint);
                }

                break;
            }

        }

        if (copyBitmap == null) {
            return;
        }

        canvas.drawBitmap(copyBitmap, src, dst, new Paint());

    }

    @Override
    public void onDrawText(String text) {
        this.text = text;
        code = ApplicationProperty.ADD_TEXT;
        invalidate();
    }

    @Override
    public void onSaveBitmap() {
//        code = ApplicationProperty.SAVE_BITMAP;
        Page2PhotoEdit.user_photo = bitmap;
    }

    @Override
    public void onDrawLine() {
        Page2PhotoEdit.user_photo = bitmap;
        code = ApplicationProperty.DRAW_LINE;
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (code != ApplicationProperty.DRAW_LINE) {
            return false;
        }
        float x = event.getX();
        float y = event.getY();
        x = x / (getWidth() / (float)bitmap.getWidth());
        y = y / (getHeight() / (float)bitmap.getHeight());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                Page2PhotoEdit.user_photo = bitmap;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;

    }
}
