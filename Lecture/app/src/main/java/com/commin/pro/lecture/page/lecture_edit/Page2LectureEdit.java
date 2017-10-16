package com.commin.pro.lecture.page.lecture_edit;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2User;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.util.UtilCustomDialog;
import com.commin.pro.lecture.util.UtilDialog;
import com.commin.pro.lecture.util.UtilShare;

import java.util.ArrayList;

/***************************
 * 끝 요일을 금요일, 토요일, 일요일로 설정할수있고
 * 로그아웃 기능이있는 액티비티 다이얼로그입니다.
 */
public class Page2LectureEdit extends Activity {
    private Spinner sp_edit_last_day;
    private TextView tv_edit_user_id;
    private Button btn_save_setting,btn_edit_logout;
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_lecture_edit_layout);
        loadPreferences();
        createGUI();
        setLastDay();

    }

    private void createGUI() {
        sp_edit_last_day = (Spinner) findViewById(R.id.sp_edit_last_day);

        tv_edit_user_id = (TextView)findViewById(R.id.tv_edit_user_id);
        tv_edit_user_id.setText(ApplicationProperty.model2User.getUser_id()+"로 로그인되었습니다.");

        btn_edit_logout = (Button) findViewById(R.id.btn_edit_logout);
        btn_edit_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /***************************
                 * 버튼을 누르면 로그아웃합니다.
                 * 설정들을 초기화하거나 저장합니다.
                 */
                UtilDialog.openCustomDialogConfirm(Page2LectureEdit.this, "로그아웃", "로그아웃 하시겠습니까?", "예", "아니오", new UtilCustomDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        ApplicationProperty.isLogined = false;
                        ApplicationProperty.model2User = new Model2User();
                        SharedPreferences sharedPreferences = loadPreferences(UtilShare.SAHRE_USER);
                        SharedPreferences.Editor editor = UtilShare.getEditor(sharedPreferences);
                        if(!sharedPreferences.getBoolean(UtilShare.KEY_VALUE_ISCHECKED,true)){
                            editor.clear();
                        }

                        editor.clear();
                        setResult(ApplicationProperty.RESULT_LOGOUT,null);
                        finish();
                    }
                }, new UtilCustomDialog.OnClickListener() {
                    @Override
                    public void onClick() {
                        return;
                    }
                });
            }
        });
        btn_save_setting = (Button) findViewById(R.id.btn_save_setting);
        btn_save_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int dayResourceValue = getDayResourceValue();
                if ( dayResourceValue != 0) {
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_DAY_RESOURCE, String.valueOf(dayResourceValue), UtilShare.INT_TYPE);
                    setResult(RESULT_OK, null);
                } else {
                    setResult(RESULT_CANCELED, null);
                }
                finish();
            }
        });
    }




    /**********************
     * 마지막 요일을 설정합니다.
     */
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

    /**************
     *
     * 요일을 선택하여 저장하는 역할을 합니다.
     * shared preference 문서에 저장할때는 index값으로 저장합니다.
     *
     * @param resource_day
     * @return
     */
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

    /*************************
     * shared preference 문서에서 가져온 인덱스값을
     * 날짜 array데이터로 바꿔 주는 메서드입니다.
     * @return
     */
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

    private SharedPreferences loadPreferences(String key) {
        SharedPreferences sharedPreferences = UtilShare.getSharedPreferences(key, Page2LectureEdit.this);
        return sharedPreferences;
    }
    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, Page2LectureEdit.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }
}
