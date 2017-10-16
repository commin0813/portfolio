package commin.pro.buttonflash.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import commin.pro.buttonflash.service.Service4Flash;


/**
 * Created by hyungwoo on 2016-08-01.
 */
public class PackageReceiver extends BroadcastReceiver {

    @Override

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_PACKAGE_ADDED)) {
//            Intent i = new Intent(context, Service4Flash.class);
//            context.startService(i);
            // 앱이 설치되었을 때
        } else if (action.equals(Intent.ACTION_PACKAGE_REMOVED)) {
            // 앱이 삭제되었을 때
        } else if (action.equals(Intent.ACTION_PACKAGE_REPLACED)) {
            // 앱이 업데이트 되었을 때
            Intent i = new Intent(context, Service4Flash.class);

            context.startService(i);

        }

    }

}
