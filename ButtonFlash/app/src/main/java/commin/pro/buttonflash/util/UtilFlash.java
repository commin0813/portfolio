package commin.pro.buttonflash.util;

import android.hardware.Camera;
import android.util.Log;

import commin.pro.buttonflash.driver.Driver4Camera;


/**
 * Created by hyungwoo on 2016-07-31.
 */
public class UtilFlash {
    public static boolean FLASH_STATUS = false;
    //    private static Camera camera = null;
//    private static Camera.Parameters p = null;
    private static Driver4Camera driver4Camera = null;

    public static void flash_on() {
        try {
            Log.w("UtilFlash", "flash_on!!");
            if (!FLASH_STATUS) {
//                camera = Camera.open();
                camera_release();
                driver4Camera = new Driver4Camera();
                Camera camera = driver4Camera.getCamera();
                if (camera == null) {
                    return;
                }
                Camera.Parameters p = camera.getParameters();
                p.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                camera.setParameters(p);
                camera.startPreview();
                FLASH_STATUS = true;
            }
        } catch (Exception e) {
            Log.w("UtilFlash", e);

        }

    }

    public static void flash_off() {
        Log.w("UtilFlash", "flash_off!!");
        if (driver4Camera == null) {
            Log.w("UtilFlash", "driver4Camera null");
                camera_release();
            return;
        }
        try {
            if (FLASH_STATUS) {
                if (null != driver4Camera.getCamera()) {
                    camera_release();

                }
                FLASH_STATUS = false;
            }
        } catch (Exception e) {
            Log.w("UtilFlash", e);
        }
    }

    public static void camera_release() {
        if (driver4Camera == null)
            return;
        if (null != driver4Camera.getCamera()) {
            driver4Camera.getCamera().release();
            driver4Camera = null;

        }
    }

}
