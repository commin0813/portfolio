package com.commin.pro.exerciseproject.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by user on 2016-04-11.
 */
public class UtilImage {
    public final static int image_width = 500;

    public final static int image_height = 300;

    public final static int image_quality = 70;

    /**
     * 비트맵의 모서리를 라운드 처리 한 후 Bitmap을 리턴
     *
     * @param bitmap bitmap handle
     * @return Bitmap
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 10;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();
        bitmap = output;

        return bitmap;
    }

    /**
     * 지정한 패스의 파일을 화면 크기에 맞게 읽어서 Bitmap을 리턴
     *
     * @param context     application context
     * @param imgFilePath bitmap file path
     * @return Bitmap
     * @throws IOException
     */
    public static Bitmap loadBackgroundBitmap(Context context, String imgFilePath) {
        File file = new File(imgFilePath);
        if (file.exists() == false) {
            return null;
        }

        // 폰의 화면 사이즈를 구한다.
        Display display = ((WindowManager) context.getSystemService(
                Context.WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth()/2;
        int displayHeight = display.getHeight()/2;

        // 읽어들일 이미지의 사이즈를 구한다.
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imgFilePath, options);

        // 화면 사이즈에 가장 근접하는 이미지의 스케일 팩터를 구한다.
        // 스케일 팩터는 이미지 손실을 최소화하기 위해 짝수로 한다.
        float widthScale = options.outWidth / displayWidth;
        float heightScale = options.outHeight / displayHeight;
        float scale = widthScale > heightScale ? widthScale : heightScale;

        if (scale >= 8)
            options.inSampleSize = 8;
        else if (scale >= 6)
            options.inSampleSize = 6;
        else if (scale >= 4)
            options.inSampleSize = 4;
        else if (scale >= 2)
            options.inSampleSize = 2;
        else
            options.inSampleSize = 1;
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(imgFilePath, options);
    }

    public synchronized static int getExifOrientation(String filepath) {
        int degree = 0;
        ExifInterface exif = null;

        try {
            exif = new ExifInterface(filepath);
        } catch (IOException e) {
            Log.e("TAG", "cannot read exif");
            e.printStackTrace();
        }

        if (exif != null) {
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);

            if (orientation != -1) {
                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        degree = 90;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        degree = 180;
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        degree = 270;
                        break;
                }
            }
        }

        return degree;
    }

    /**
     * 지정한 패스의 파일을 EXIF 정보에 맞춰 회전시키기
     *
     * @param bitmap bitmap handle
     * @return Bitmap
     */
    public synchronized static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees) {
        if (degrees != 0 && bitmap != null) {
            Matrix m = new Matrix();
            m.setRotate(degrees, (float) bitmap.getWidth(),
                    (float) bitmap.getHeight() );
            try {
                Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                        bitmap.getHeight(), m, true);
                if (bitmap != b2) {
                    bitmap.recycle();
                    bitmap = b2;
                }
            } catch (OutOfMemoryError ex) {
                // We have no memory to rotate. Return the original bitmap.
            }
        }

        return bitmap;
    }

    public static Bitmap getBitmap(String imageCode) {
        byte[] bytePlainOrg = Base64.decode(imageCode, Base64.NO_WRAP);
        ByteArrayInputStream inStream = new ByteArrayInputStream(bytePlainOrg);//byte[] 를 inputstream으로
        Bitmap bitmap = BitmapFactory.decodeStream(inStream);//inputstream을 bitmap으로
        return bitmap;
    }

    /**
     * return : bitmap setting width and height
     * bool == true?custom value:fixed value
     */
    public static Bitmap getBitmap(String imgFilePath, int width, int height, boolean bool) {
        File file = new File(imgFilePath);
        if (file.exists() == false) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
        if (bool) {
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        return Bitmap.createScaledBitmap(bitmap, image_width, image_height, true);

    }

    /**
     * return : bitmap setting width and height
     * bool == true?custom value:fixed value
     */
    public static Bitmap getBitmap(Bitmap bitmap, int width, int height, boolean bool) {

        if (bool) {
            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

        return Bitmap.createScaledBitmap(bitmap, image_width, image_height, true);

    }


    public static String getImageCode(Context context, String imgFilePath) {

        int dgree = getExifOrientation(imgFilePath);
        Bitmap bitmap_1 = loadBackgroundBitmap(context, imgFilePath);
        Bitmap bitmap_2 = getRotatedBitmap(bitmap_1, dgree);
        Bitmap bitmap_3 = Bitmap.createScaledBitmap(bitmap_2, image_width, image_height, true);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap_3.compress(Bitmap.CompressFormat.PNG, image_quality, byteArrayOutputStream); //저장된이미지를 jpeg로 포맷 품질100으로하여 출력
        byte[] image_byte_array = byteArrayOutputStream.toByteArray();//compress 로 bitmap을 byte[] 로만들어서 String 으로 저장

        String imageCode = Base64.encodeToString(image_byte_array, Base64.NO_WRAP);

        return imageCode;
    }

    public static String getImageCode(Context context, Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, image_quality, byteArrayOutputStream); //저장된이미지를 jpeg로 포맷 품질100으로하여 출력
        byte[] image_byte_array = byteArrayOutputStream.toByteArray();//compress 로 bitmap을 byte[] 로만들어서 String 으로 저장

        String imageCode = Base64.encodeToString(image_byte_array, Base64.NO_WRAP);

        return imageCode;
    }


}
