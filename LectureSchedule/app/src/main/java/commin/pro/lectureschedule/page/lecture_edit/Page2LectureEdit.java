package commin.pro.lectureschedule.page.lecture_edit;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.util.UtilShare;

public class Page2LectureEdit extends Activity {
    private Spinner sp_edit_last_day, sp_edit_last_time;
    private Button btn_save_setting;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_page_lecture_edit);
        loadPreferences();
        createGUI();
        setLastDay();
        setLastTime();
    }

    private void createGUI() {
        sp_edit_last_day = (Spinner) findViewById(R.id.sp_edit_last_day);

        sp_edit_last_time = (Spinner) findViewById(R.id.sp_edit_last_time);


        btn_save_setting = (Button) findViewById(R.id.btn_save_setting);
        btn_save_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int timeResourceValue = getTimeResourceValue();
                int dayResourceValue = getDayResourceValue();
                if (timeResourceValue != 0 && dayResourceValue != 0) {
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_DAY_RESOURCE, String.valueOf(dayResourceValue), UtilShare.INT_TYPE);
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_TIME_RESOURCE, String.valueOf(timeResourceValue), UtilShare.INT_TYPE);
                    setResult(RESULT_OK, null);
                } else {
                    setResult(RESULT_CANCELED, null);
                }
                finish();
            }
        });
    }

    private int getTimeResourceIndex(int time_resource) {
        int index = 0;
        switch (time_resource) {
            case R.array.time_08_19:
                index = 0;
                break;//
            case R.array.time_08_18:
                index = 1;
                break;//
            case R.array.time_08_17:
                index = 2;
                break;//
            case R.array.time_08_16:
                index = 3;
                break;//
            case R.array.time_08_15:
                index = 4;
                break;//
            case R.array.time_08_14:
                index = 5;
                break;//
            case R.array.time_08_13:
                index = 6;
                break;//
        }
        return index;
    }

    private int getTimeResourceValue() {
        int position = sp_edit_last_time.getSelectedItemPosition();
        switch (position) {
            case 0:
                return R.array.time_08_19;//
            case 1:
                return R.array.time_08_18;//
            case 2:
                return R.array.time_08_17;//
            case 3:
                return R.array.time_08_16;//
            case 4:
                return R.array.time_08_15;//
            case 5:
                return R.array.time_08_14;//
            case 6:
                return R.array.time_08_13;//
        }
        return 0;
    }

    private int getDayResourceIndex(int resource_day) {
        int index = 0;
        switch (resource_day) {
            case R.array.days_7:
                index = 0;
                break;//
            case R.array.days_6:
                index = 1;
                break;//
            case R.array.days_5:
                index = 2;
                break;//
        }
        return index;
    }

    private int getDayResourceValue() {
        int position = sp_edit_last_day.getSelectedItemPosition();
        switch (position) {
            case 0:
                return R.array.days_7;//
            case 1:
                return R.array.days_6;//
            case 2:
                return R.array.days_5;//
        }
        return 0;
    }

    private void setLastDay() {
        ArrayList<String> items = new ArrayList<String>();
        String[] day = getResources().getStringArray(R.array.days_7);
        for (int i = day.length - 1; i > 3; i--) {
            items.add(day[i] + "요일");
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Page2LectureEdit.this, android.R.layout.simple_spinner_item, items);
        sp_edit_last_day.setAdapter(adapter);
        int resource_day = sharedPreferences.getInt(UtilShare.KEY_VALUE_DAY_RESOURCE, R.array.days_7);
        int index = getDayResourceIndex(resource_day);
        sp_edit_last_day.setSelection(getDayResourceIndex(resource_day));
    }

    private void setLastTime() {
        ArrayList<String> items = new ArrayList<String>();
        String[] time = getResources().getStringArray(R.array.time_08_19);
        for (int i = time.length - 1; i > 4; i--) {
            items.add(time[i] + "시");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Page2LectureEdit.this, android.R.layout.simple_spinner_item, items);
        sp_edit_last_time.setAdapter(adapter);
        int resource_time = sharedPreferences.getInt(UtilShare.KEY_VALUE_TIME_RESOURCE, R.array.time_08_19);
        sp_edit_last_time.setSelection(getTimeResourceIndex(resource_time));
    }

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, Page2LectureEdit.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }
}
