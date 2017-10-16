package commin.pro.buttonflash.page;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import commin.pro.buttonflash.R;
import commin.pro.buttonflash.service.Service4Flash;
import commin.pro.buttonflash.util.UtilFlash;
import commin.pro.buttonflash.util.UtilShare;


public class Page4FlashSetting extends AppCompatActivity {
    Button btn_flash_service, btn_flash_service_stop;
    ImageView btn_flash_on;
    Drawable drawable_flash_btn_pressed;
    Drawable drawable_flash_btn_normal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flash_setting_layout);

        loadPreferences();
//        UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_RESPONSIVE_int, 7 + "", UtilShare.INT_TYPE);


        drawable_flash_btn_pressed = this.getResources().getDrawable(R.drawable.flash_pressed_btn);
        drawable_flash_btn_normal = this.getResources().getDrawable(R.drawable.flash_normal_btn);

        btn_flash_on = (ImageView) findViewById(R.id.btn_flash_on);
        btn_flash_service = (Button) findViewById(R.id.btn_flash_service);
        btn_flash_service_stop = (Button) findViewById(R.id.btn_flash_service_stop);

        btn_flash_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((sharedPreferences.getBoolean(UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS)) == false) {
                    UtilFlash.flash_on();
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS + "", UtilShare.BOOL_TYPE);
                    imageChange(Page4FlashSetting.this, btn_flash_on);
                    Log.w("Page4FlashSetting", "on");
                } else if ((sharedPreferences.getBoolean(UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS)) == true) {
                    UtilFlash.flash_off();
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS + "", UtilShare.BOOL_TYPE);
                    imageChange(Page4FlashSetting.this, btn_flash_on);
                    Log.w("Page4FlashSetting", "off");
                }
            }

        });

        final Intent intent = new Intent(Page4FlashSetting.this, Service4Flash.class);
        btn_flash_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
//                UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_SERVICE_STATUS_bool, "true", UtilShare.BOOL_TYPE);
            }
        });
        btn_flash_service_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
//                UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_SERVICE_STATUS_bool, "false", UtilShare.BOOL_TYPE);
            }
        });

    }

    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, Page4FlashSetting.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }

    private void imageChange(final Activity activity, final ImageView imageView) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((sharedPreferences.getBoolean(UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS)) == true) {
                            imageView.setImageDrawable(drawable_flash_btn_pressed);
                        } else if ((sharedPreferences.getBoolean(UtilShare.KEY_VALUE_FLASH_STATUS_bool, UtilFlash.FLASH_STATUS)) == false) {
                            imageView.setImageDrawable(drawable_flash_btn_normal);
                        }
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UtilFlash.camera_release();
    }
}
