package commin.pro.buttonflash.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by hyungwoo on 2016-08-02.
 */
public class UtilShare {

    public final static int INT_TYPE = 0;
    public final static int STRING_TYPE = 1;
    public final static int FLOAT_TYPE = 2;
    public final static int LONG_TYPE = 3;
    public final static int BOOL_TYPE = 4;


    //filne_name
    public final static String SAHRE_STATUS = "flash_button_setting_status";


    //file_data_key_name
    public final static String KEY_VALUE_FLASH_STATUS_bool = "flash_status";
    public final static String KEY_VALUE_SERVICE_STATUS_bool = "service_status";
    public final static String KEY_VALUE_RESPONSIVE_int = "responsive";


    //file_fata_defalt_value
    public final static int RESPONSIVE_DEFALT_VALUE = 5;
    public final static boolean FLASH_STATUS_DEFALT_VALUE = false;
    public final static boolean SERVICE_STATUS_DEFALT_VALUE = false;

    public static void savePreferences(SharedPreferences.Editor editor, String key, String value, int type) {
        switch (type) {
            case INT_TYPE:
                editor.putInt(key, Integer.parseInt(value));
                break;
            case STRING_TYPE:
                editor.putString(key, value);
                break;
            case FLOAT_TYPE:
                break;
            case LONG_TYPE:
                editor.putLong(key, Long.valueOf(value));
                break;
            case BOOL_TYPE:
                if (value.equals("true")) {
                    editor.putBoolean(key, true);
                    Log.w("abc", "true");
                } else {
                    editor.putBoolean(key, false);
                    Log.w("abc", "false");
                }
                break;
        }

        editor.commit();
    }

    public static void removePreferences(SharedPreferences.Editor editor, String key) {
        editor.remove(key);
    }


    public static SharedPreferences getSharedPreferences(String file_name, Context context) {
        if (file_name.equals(SAHRE_STATUS)) {
            return context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
        }

        return context.getSharedPreferences(file_name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(SharedPreferences sharedPreferences) {

        return sharedPreferences.edit();
    }


}
