package commin.pro.lectureschedule.page.lecture_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.model.Model2Lecture;
import commin.pro.lectureschedule.util.UtilCheck;

public class Page2LectureView extends AppCompatActivity {
    private RadioGroup radio_group;
    private RadioButton radio_btn_mon, radio_btn_the, radio_btn_wed, radio_btn_thu, radio_btn_frd, radio_btn_sat, radio_btn_sun;
    private EditText ed_class_name_view, ed_professor_name_view, ed_classroom_name_view;
    private TextView tv_day_name;
    private Spinner sp_start_time_view, sp_end_time_view;

    private TextView btn_back_lecture_view;
    private String[] time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_page_lecture_view);
        createGUI();
        init_data();
    }

    private void createGUI() {
        time = getResources().getStringArray(R.array.time_08_19);

        btn_back_lecture_view = (TextView) findViewById(R.id.btn_back_lecture_view);
        btn_back_lecture_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_btn_mon = (RadioButton) findViewById(R.id.radio_btn_mon);
        radio_btn_the = (RadioButton) findViewById(R.id.radio_btn_the);
        radio_btn_wed = (RadioButton) findViewById(R.id.radio_btn_wed);
        radio_btn_thu = (RadioButton) findViewById(R.id.radio_btn_thu);
        radio_btn_frd = (RadioButton) findViewById(R.id.radio_btn_frd);
        radio_btn_sat = (RadioButton) findViewById(R.id.radio_btn_sat);
        radio_btn_sun = (RadioButton) findViewById(R.id.radio_btn_sun);

        tv_day_name = (TextView) findViewById(R.id.tv_day_name);
        ed_class_name_view = (EditText) findViewById(R.id.ed_class_name_view);
        ed_professor_name_view = (EditText) findViewById(R.id.ed_professor_name_view);
        ed_classroom_name_view = (EditText) findViewById(R.id.ed_classroom_name_view);


        sp_start_time_view = (Spinner) findViewById(R.id.sp_start_time_view);
        sp_end_time_view = (Spinner) findViewById(R.id.sp_end_time_view);


    }

    private void init_data() {
        Model2Lecture model = (Model2Lecture) getIntent().getSerializableExtra("model");
        ed_class_name_view.setText(model.getClass_name().toString());
        ed_classroom_name_view.setText(model.getClassroom_name().toString());
        ed_professor_name_view.setText(model.getProfessor_name());
        String day = UtilCheck.checkDay(model.getId());
        tv_day_name.setText(day);
        switch (day) {
            case "월":
                radio_btn_mon.setChecked(true);
                break;
            case "화":
                radio_btn_the.setChecked(true);
                break;
            case "수":
                radio_btn_wed.setChecked(true);
                break;
            case "목":
                radio_btn_thu.setChecked(true);
                break;
            case "금":
                radio_btn_frd.setChecked(true);
                break;
            case "토":
                radio_btn_sat.setChecked(true);
                break;
            case "일":
                radio_btn_sun.setChecked(true);
                break;
        }
        ArrayList<String> items = new ArrayList<String>();
        for (String ss : time) {
            items.add(ss);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Page2LectureView.this, android.R.layout.simple_spinner_dropdown_item, items);
        sp_start_time_view.setAdapter(adapter);
        sp_end_time_view.setAdapter(adapter);

        sp_start_time_view.setSelection(items.indexOf(model.getStart_time()));
        sp_end_time_view.setSelection(items.indexOf(model.getEnd_time()));

    }
}
