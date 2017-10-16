package commin.pro.buttonflash.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import commin.pro.buttonflash.page.Handler4Key;
import commin.pro.buttonflash.util.UtilShare;


/**
 * Created by hyungwoo on 2016-07-31.
 */
public class RemoteControlReceiver_OFF extends BroadcastReceiver {
    public static boolean screenoff;
    public static Handler4Key handler4Key;

    public RemoteControlReceiver_OFF() {
        Log.w("RemoteControlReceiver", " happended");
        handler4Key = new Handler4Key(this);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String intentAction = intent.getAction();
        SharedPreferences sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, context);
        Log.w("RemoteControlReceiver", intentAction.toString() + " RemoteControlReceiver_OFF");
        handler4Key.flash_on_off(sharedPreferences.getInt(UtilShare.KEY_VALUE_RESPONSIVE_int, 5));
    }


}