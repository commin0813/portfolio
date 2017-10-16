package com.commin.pro.exerciseproject.page.do_excercise_expert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.util.UtilDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
/*
운동 Activitiy 입니다.
체크된 값과 초심자 Acitivity이면 Model에 isBeginner 에 true 를 넣고 아니면 false를 넣습니다.
 */
public class Page2DoExcerciseExpert extends AppCompatActivity {

    private static String LOG_TAG = "Page2DoExcerciseExpert";

    private CheckBox check_excercize_fisrt, check_excercize_second, check_excercize_third;
    private Button btn_expert_save, btn_expert_cancel;
    private TextView tv_excercize_current_date;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_do_excercise_expert_layout);
        createGUI();
    }

    private void createGUI() {
        init_elements();
        init_click_handler();
        SimpleDateFormat df = new SimpleDateFormat("MM월 dd일");
        tv_excercize_current_date.setText(df.format(date));
    }

    private void init_elements() {
        check_excercize_fisrt = (CheckBox) findViewById(R.id.check_excercize_fisrt);
        check_excercize_second = (CheckBox) findViewById(R.id.check_excercize_second);
        check_excercize_third = (CheckBox) findViewById(R.id.check_excercize_third);
        btn_expert_save = (Button) findViewById(R.id.btn_expert_save);
        btn_expert_cancel = (Button) findViewById(R.id.btn_expert_cancel);
        tv_excercize_current_date = (TextView) findViewById(R.id.tv_excercize_current_date);
        date = new Date();
    }

    private void init_click_handler() {
        btn_expert_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model2Excercise model = createModel();
                UtilDialog.showToast(Page2DoExcerciseExpert.this, "저장완료");
                Intent intent = new Intent();
                intent.putExtra("Model2Excercise", model);
                setResult(ApplicationProperty.RESULT_CODE_FOR_EXCERCIZE_EXPERT, intent);
                finish();
            }
        });
        btn_expert_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private Model2Excercise createModel() {
        Model2Excercise model = new Model2Excercise();
        model.setDate(date);
        model.setBeginner(false);
        HashMap<String, Boolean> map = new HashMap<>();
        map.put(ApplicationProperty.FIRST_CHECK, check_excercize_fisrt.isChecked());
        map.put(ApplicationProperty.SECOND_CHECK, check_excercize_second.isChecked());
        map.put(ApplicationProperty.THIRD_CHECK, check_excercize_third.isChecked());
        model.setCheck(map);

        return model;
    }
}
