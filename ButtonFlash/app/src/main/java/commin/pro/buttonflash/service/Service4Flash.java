package commin.pro.buttonflash.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import commin.pro.buttonflash.R;
import commin.pro.buttonflash.receiver.PackageReceiver;
import commin.pro.buttonflash.receiver.RemoteControlReceiver;
import commin.pro.buttonflash.receiver.RemoteControlReceiver_OFF;
import commin.pro.buttonflash.util.UtilConfig;
import commin.pro.buttonflash.util.UtilShare;


public class Service4Flash extends Service {
    private static String LOG_TAG = "Service4Flash";

    private RemoteControlReceiver remoteControlReceiver;
    private RemoteControlReceiver_OFF remoteControlReceiver_off;
    private PackageReceiver packageReceiver;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, Service4Flash.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }



    public Service4Flash() {
        Log.w(LOG_TAG, "Service4Flash");

    }

    @Override
    public void onCreate() {
        loadPreferences();
        UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_SERVICE_STATUS_bool, "true", UtilShare.BOOL_TYPE);
        Log.w(LOG_TAG, "onCreate" + sharedPreferences.getBoolean(UtilShare.KEY_VALUE_SERVICE_STATUS_bool, false));

        Toast.makeText(Service4Flash.this, "ButtonFlash start!", Toast.LENGTH_LONG).show();
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        remoteControlReceiver = new RemoteControlReceiver();
        registerReceiver(remoteControlReceiver, filter);

        IntentFilter filter2 = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        remoteControlReceiver_off = new RemoteControlReceiver_OFF();
        registerReceiver(remoteControlReceiver_off, filter2);

        packageReceiver = new PackageReceiver();
        IntentFilter pFilter = new IntentFilter(Intent.ACTION_PACKAGE_ADDED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        pFilter.addAction(Intent.ACTION_PACKAGE_REPLACED);
        pFilter.addDataScheme("package");
        registerReceiver(packageReceiver, pFilter);


        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.w(LOG_TAG, "onStartCommand");
        UtilConfig.SERVIECE_STATUS = true;
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(getApplicationContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("Flash Service on!!");
        builder.setWhen(System.currentTimeMillis());
        builder.setNumber(10);
        builder.setContentTitle("Flash Service");
        builder.setContentText("Flash Service가 켜져있습니다.");
        Notification notification = builder.build();
        startForeground(1, notification);
        if (intent == null) {
//            Toast.makeText(Service4Flash.this, "서비스 시작", Toast.LENGTH_LONG).show();
//            IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
//            filter.addAction(Intent.ACTION_SCREEN_OFF);
//            registerReceiver(remoteControlReceiver, filter);
        }

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(Service4Flash.this, "서비스 종료", Toast.LENGTH_LONG).show();
        UtilConfig.SERVIECE_STATUS = false;
        UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_SERVICE_STATUS_bool, "false", UtilShare.BOOL_TYPE);

        if (remoteControlReceiver != null) {
            unregisterReceiver(remoteControlReceiver);
        }

        if (remoteControlReceiver_off != null) {
            unregisterReceiver(remoteControlReceiver_off);
        }
        if (packageReceiver != null) {
            unregisterReceiver(packageReceiver);
        }
        Log.w(LOG_TAG, "onDestroy" + sharedPreferences.getBoolean(UtilShare.KEY_VALUE_SERVICE_STATUS_bool, true));
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.w(LOG_TAG, "onBind");
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
