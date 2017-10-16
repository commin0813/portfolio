package commin.pro.lectureschedule.page.lecture_add;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Random;

import commin.pro.lectureschedule.ApplicationProperty;
import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.dao.Dao2Lecture;
import commin.pro.lectureschedule.model.Model2Lecture;
import commin.pro.lectureschedule.util.UtilCheck;
import commin.pro.lectureschedule.util.UtilDialog;
import commin.pro.lectureschedule.util.UtilShare;
import commin.pro.lectureschedule.widget.DialogProgress;

public class Page2LectureAdd extends AppCompatActivity {
    private static final String LOG_TAG = "Page2LectureAdd";
    private RadioGroup radio_group;
    private RadioButton radio_btn_mon, radio_btn_the, radio_btn_wed, radio_btn_thu, radio_btn_frd, radio_btn_sat, radio_btn_sun;
    private EditText ed_class_name, ed_professor_name, ed_classroom_name;
    private TextView tv_day_name;
    private LinearLayout ll_layer_box;
    private Spinner sp_start_time, sp_end_time;

    private TextView btn_complete_add;
    private String[] time;

    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    private ArrayList<Model2Lecture> dao_items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_page_lecture_add);
        loadPreferences();
        createGUI();
        init_listener();
    }


    private void createGUI() {
        int resource_time = sharedPreferences.getInt(UtilShare.KEY_VALUE_TIME_RESOURCE, R.array.time_08_19);
        time = getResources().getStringArray(resource_time);

        btn_complete_add = (TextView) findViewById(R.id.btn_complete_add);
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_btn_mon = (RadioButton) findViewById(R.id.radio_btn_mon);
        radio_btn_the = (RadioButton) findViewById(R.id.radio_btn_the);
        radio_btn_wed = (RadioButton) findViewById(R.id.radio_btn_wed);
        radio_btn_thu = (RadioButton) findViewById(R.id.radio_btn_thu);
        radio_btn_frd = (RadioButton) findViewById(R.id.radio_btn_frd);
        radio_btn_sat = (RadioButton) findViewById(R.id.radio_btn_sat);
        radio_btn_sun = (RadioButton) findViewById(R.id.radio_btn_sun);

        tv_day_name = (TextView) findViewById(R.id.tv_day_name);
        ll_layer_box = (LinearLayout) findViewById(R.id.ll_layer_box);
        ed_class_name = (EditText) findViewById(R.id.ed_class_name);
        ed_professor_name = (EditText) findViewById(R.id.ed_professor_name);
        ed_classroom_name = (EditText) findViewById(R.id.ed_classroom_name);

        sp_start_time = (Spinner) findViewById(R.id.sp_start_time);
        ArrayList<String> items = new ArrayList<String>();

        for (String ss : time) {
            items.add(ss);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Page2LectureAdd.this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_start_time.setAdapter(adapter);
        sp_end_time = (Spinner) findViewById(R.id.sp_end_time);
        sp_end_time.setAdapter(adapter);
        sp_end_time.setSelection(1);

    }

    private boolean checkNull() {
        if (ed_class_name.getText().toString().equals("") || ed_classroom_name.getText().toString().equals("") || ed_professor_name.getText().toString().equals("")) {
            return true;
        }

        return false;
    }

    private boolean checkSpinner(String start_time, String end_time) {
        if (UtilCheck.checkPeriod(start_time, end_time) <= 0) {
            return true;
        }

        return false;
    }


    private void saveData() {
        if (checkNull()) {
            UtilDialog.showToast(Page2LectureAdd.this, "값을 모두 입력하세요.");
            return;
        }

        String selected_start_time = sp_start_time.getSelectedItem().toString();
        String selected_end_time = sp_end_time.getSelectedItem().toString();

        if ((checkSpinner(selected_start_time, selected_end_time))) {
            UtilDialog.showToast(Page2LectureAdd.this, "정상적인 시간이 아닙니다.");
            return;
        }


        try {
            dao_items = (ArrayList<Model2Lecture>) DialogProgress.run(Page2LectureAdd.this, new DialogProgress.ProgressTaskIf() {
                @Override
                public Object run() throws Exception {
                    return Dao2Lecture.queryAllData();
                }
            });
        } catch (Exception e) {
            Log.w(LOG_TAG, e);
        }

        RadioButton radioButton = (RadioButton) findViewById(radio_group.getCheckedRadioButtonId());
        String selected_day = radioButton.getText().toString();
        int colum_index = 0;
        switch (selected_day) {
            case "월":
                colum_index = 1;
                break;
            case "화":
                colum_index = 2;
                break;
            case "수":
                colum_index = 3;
                break;
            case "목":
                colum_index = 4;
                break;
            case "금":
                colum_index = 5;
                break;
            case "토":
                colum_index = 6;
                break;
            case "일":
                colum_index = 7;
                break;
        }


        int period = UtilCheck.checkPeriod(selected_start_time, selected_end_time);


        int start_row_index = 0;

        for (int i = 0; i < time.length; i++) {
            if (selected_start_time.equals(time[i])) {
                start_row_index = i;
                break;
            }
        }

        String id = "";
//                String id = start_row_index + ApplicationProperty.OPERATOR_ID + colum_index;
        if (period > 1) {
            ArrayList<Model2Lecture> models = new ArrayList<Model2Lecture>();
            int groupid = new Random().nextInt();
            int row_index = 0;
            for (int i = 0; i < period; i++) {
                Model2Lecture model = new Model2Lecture();
                id = (start_row_index + i) + ApplicationProperty.OPERATOR_ID + colum_index;
                if (checkDuplicate(id)) {
                    return;
                }
                model.setId(id);
                model.setClass_name(ed_class_name.getText().toString());
                model.setClassroom_name(ed_classroom_name.getText().toString());
                model.setEvents(true);
                model.setData(true);
                model.setStart_time(selected_start_time);
                model.setEnd_time(selected_end_time);
                model.setProfessor_name(ed_professor_name.getText().toString());
                model.setGroupid(groupid);
                if (i == 0) {
                    model.setPosition("top");
                } else if (i == period - 1) {
                    model.setPosition("bottom");
                } else {
                    model.setPosition("middle");
                }
                models.add(model);
            }
            Dao2Lecture.insertDatabase(models);

        } else {
            Model2Lecture model = new Model2Lecture();
            int groupid = new Random().nextInt();
            id = start_row_index + ApplicationProperty.OPERATOR_ID + colum_index;
            if (checkDuplicate(id)) {
                return;
            }
            model.setId(id);
            model.setClass_name(ed_class_name.getText().toString());
            model.setClassroom_name(ed_classroom_name.getText().toString());
            model.setEvents(true);
            model.setData(true);
            model.setStart_time(selected_start_time);
            model.setEnd_time(selected_end_time);
            model.setProfessor_name(ed_professor_name.getText().toString());
            model.setPosition("top");
            model.setGroupid(groupid);
            Dao2Lecture.insertDatabase(model);

        }
        setResult(RESULT_OK, null);
        finish();
    }

    private boolean checkDuplicate(String id) {
        for (Model2Lecture mo : dao_items) {
            if (mo.getId().equals(id)) {
                UtilDialog.showToast(Page2LectureAdd.this, UtilCheck.checkDay(id) + "요일 " + UtilCheck.checkTimeForId(id) + "시" + "\n이미 등록된 시간표나 메모가 있습니다.");
                return true;//중복됨
            }
        }
        return false;//중복안됨
    }

    private void init_listener() {
        btn_complete_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int view) {
                if (!ll_layer_box.isShown()) {
                    ll_layer_box.setVisibility(View.VISIBLE);
                }

                switch (view) {
                    case R.id.radio_btn_mon: {//월
                        tv_day_name.setText(radio_btn_mon.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_the: {//화
                        tv_day_name.setText(radio_btn_the.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_wed: {//수
                        tv_day_name.setText(radio_btn_wed.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_thu: {//목
                        tv_day_name.setText(radio_btn_thu.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_frd: {//금
                        tv_day_name.setText(radio_btn_frd.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_sat: {//토
                        tv_day_name.setText(radio_btn_sat.getText().toString());
                        break;
                    }
                    case R.id.radio_btn_sun: {//일
                        tv_day_name.setText(radio_btn_sun.getText().toString());
                        break;
                    }
                }
            }
        });
    }

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_STATUS, Page2LectureAdd.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }
}
