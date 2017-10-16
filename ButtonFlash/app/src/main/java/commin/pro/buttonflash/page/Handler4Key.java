package commin.pro.buttonflash.page;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import commin.pro.buttonflash.receiver.RemoteControlReceiver;
import commin.pro.buttonflash.receiver.RemoteControlReceiver_OFF;
import commin.pro.buttonflash.util.UtilFlash;


/**
 * Created by hyungwoo on 2016-07-31.
 */
public class Handler4Key {
    private static long keyPressedTime = 0;
    private Toast toast;
    RemoteControlReceiver remoteControlReceiver;
    RemoteControlReceiver_OFF remoteControlReceiver_off;
    private Activity activity;


    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;


    public Handler4Key(Activity context) {
        this.activity = context;
    }

    public Handler4Key(RemoteControlReceiver remoteControlReceiver) {
        this.remoteControlReceiver = remoteControlReceiver;
    }

    public Handler4Key(RemoteControlReceiver_OFF remoteControlReceiver_off) {
        this.remoteControlReceiver_off = remoteControlReceiver_off;
    }

    public void onPressed_up() {
        if (System.currentTimeMillis() > keyPressedTime + 500) {
            keyPressedTime = System.currentTimeMillis();
            return;
        }

    }

    public void onPressed_down() {
        if (System.currentTimeMillis() <= keyPressedTime + 500) {
            if (!UtilFlash.FLASH_STATUS)
                UtilFlash.flash_on();
            else if (UtilFlash.FLASH_STATUS) {
                UtilFlash.flash_off();
            }
        }
    }

    public static void flash_on_off(int responsive_value) {
        Log.w("abc", "RESOPONSIVE_VALUE : " + responsive_value + "");
        if (System.currentTimeMillis() > keyPressedTime + responsive_value * 100) {
            Log.w("Handler4Key", keyPressedTime + " : 11");
            keyPressedTime = System.currentTimeMillis();
            if (keyPressedTime >= Long.MAX_VALUE / 0.95) {
                keyPressedTime = 0;
            }
        } else if (System.currentTimeMillis() <= keyPressedTime + responsive_value * 100) {
            Log.w("Handler4Key", keyPressedTime + " : 22");
            if (!UtilFlash.FLASH_STATUS) {
                Log.w("Handler4Key", UtilFlash.FLASH_STATUS + " : flash_on!");
                UtilFlash.flash_on();
            } else if (UtilFlash.FLASH_STATUS) {
                Log.w("Handler4Key", UtilFlash.FLASH_STATUS + " : flash_off!");
                UtilFlash.flash_off();
            }
            keyPressedTime = 0;
        }
    }

}
