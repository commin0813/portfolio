package commin.pro.buttonflash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import commin.pro.buttonflash.page.Page4FlashSetting;


public class Application extends AppCompatActivity {
    public static int MY_PERMISSIONS_REQUEST_CAMERA = 1002;
    public static int MY_PERMISSIONS_REQUEST_BOOT = 1003;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        if (ContextCompat.checkSelfPermission(Application.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Application.this,
                    Manifest.permission.CAMERA)) {
            } else {
                ActivityCompat.requestPermissions(Application.this,
                        new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        if (ContextCompat.checkSelfPermission(Application.this,
                Manifest.permission.RECEIVE_BOOT_COMPLETED)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(Application.this,
                    Manifest.permission.RECEIVE_BOOT_COMPLETED)) {
            } else {
                ActivityCompat.requestPermissions(Application.this,
                        new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED}, MY_PERMISSIONS_REQUEST_BOOT);
            }
        }

//        ActivityManager manager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> tasks = manager.getRunningTasks(Integer.MAX_VALUE);
//
//        for (ActivityManager.RunningTaskInfo taskInfo : tasks) {
//            if (taskInfo.baseActivity.getClassName().equals("com.commin.pro.buttonflash") && (taskInfo.numActivities > 1)) {
//                finish();
//            }
//        }


        startActivity(new Intent(Application.this, Page4FlashSetting.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 1002: {//CAMERA
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    Toast.makeText(Application.this, "Flash가 정상적으로 동작하지 않을 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 1003: {//BOOT
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(Application.this, "Reboot시 서비스를 다시 실행시켜야 할 수 있습니다.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


}
