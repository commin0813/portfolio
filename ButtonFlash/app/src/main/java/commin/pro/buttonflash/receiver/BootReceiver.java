package commin.pro.buttonflash.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import commin.pro.buttonflash.service.Service4Flash;


/**
 * Created by hyungwoo on 2016-08-01.
 */
public class BootReceiver extends BroadcastReceiver {


    @Override

    public void onReceive(Context context, Intent data) {
        if (data.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent intent = new Intent(context, Service4Flash.class);
            context.startService(intent);
        }

    }

}
