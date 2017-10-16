package com.commin.pro.exerciseproject.page.calendar_view;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.util.UtilDialog;
import com.commin.pro.exerciseproject.util.UtilImage;

import java.text.SimpleDateFormat;
import java.util.Date;
/*
달력을 클릭하였을때 호출되는 View Activity입니다.
 */
public class Page2CalendarView extends AppCompatActivity {

    private static final String LOG_TAG = "Page2CalendarView";

    private TextView tv_calendar_view_the_date, tv_calendar_view_level, tv_calendar_view_count_1, tv_calendar_view_count_2, tv_calendar_view_count_3;
    private CheckBox check_view_fisrt, check_view_second, check_view_third;
    private ImageView iv_calendar_user_image;
    private Button btn_back;
    private Model2Excercise model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_calendar_view_layout);
        createGUI();
    }

    private void createGUI() {

        init_elements();
        set_elements();

    }

    private void init_elements() {
        model = (Model2Excercise) getIntent().getSerializableExtra("Model2Excercise");//Intent 데이터로 model을 생성.
        tv_calendar_view_the_date = (TextView) findViewById(R.id.tv_calendar_view_the_date);
        SimpleDateFormat df = new SimpleDateFormat("MM월 dd일");
        tv_calendar_view_the_date.setText(df.format(model.getDate()));
        tv_calendar_view_level = (TextView) findViewById(R.id.tv_calendar_view_level);
        tv_calendar_view_count_1 = (TextView) findViewById(R.id.tv_calendar_view_count_1);
        tv_calendar_view_count_2 = (TextView) findViewById(R.id.tv_calendar_view_count_2);
        tv_calendar_view_count_3 = (TextView) findViewById(R.id.tv_calendar_view_count_3);

        check_view_fisrt = (CheckBox) findViewById(R.id.check_view_fisrt);
        check_view_second = (CheckBox) findViewById(R.id.check_view_second);
        check_view_third = (CheckBox) findViewById(R.id.check_view_third);

        iv_calendar_user_image = (ImageView) findViewById(R.id.iv_calendar_user_image);
        iv_calendar_user_image.setScaleType(ImageView.ScaleType.FIT_XY);
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void set_elements() {
        if (!model.isBeginner()) {
            tv_calendar_view_level.setText("- 고급자코스");
            tv_calendar_view_count_1.setText("20");
            tv_calendar_view_count_2.setText("25");
            tv_calendar_view_count_3.setText("30");
        }

        check_view_fisrt.setChecked(Boolean.valueOf(model.getCheck().get(ApplicationProperty.FIRST_CHECK)));
        check_view_second.setChecked(Boolean.valueOf(model.getCheck().get(ApplicationProperty.SECOND_CHECK)));
        check_view_third.setChecked(Boolean.valueOf(model.getCheck().get(ApplicationProperty.THIRD_CHECK)));

        if (model.getUser_photo_path() != null) {
            Bitmap user_photo = null;
            user_photo = UtilImage.getBitmap(model.getUser_photo_path());


            if (user_photo != null) {
                iv_calendar_user_image.setImageBitmap(user_photo);
            } else {
                UtilDialog.openError(Page2CalendarView.this, getResources().getString(R.string.load_image_file_fail), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
            }
        }

    }
}
