package com.commin.pro.exerciseproject.page.calendar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Toast;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.dao.Dao2Excercise;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.page.calendar_view.Page2CalendarView;
import com.commin.pro.exerciseproject.page.menu_list.Page2MenuList;
import com.commin.pro.exerciseproject.util.UtilDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
/*
이 클래스는 Calendar 의 Activity 클래스이며,
화면을 구성합니다.
 */
public class Page2Calendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_calendar_layout);
        createGUI();
    }

    private void createGUI() {
        CalendarView cv = ((CalendarView) findViewById(R.id.calendar_view));
        cv.setEventHandler(new CalendarView.EventHandler() {//interface를 만들어서 등록하면 observer 패턴처럼 이용 할 수있습니다.
            @Override
            public void onDayLongPress(Date date) {//달력화면의 grid를 long 클릭 하면 이 메서드가 실행됩니다.
                DateFormat df = SimpleDateFormat.getDateInstance();
                UtilDialog.showToast(Page2Calendar.this, df.format(date));
            }

            @Override
            public void onDayClick(Date date) {//달력화면의 grid를 한번 클릭하면 이메서드가 실행됩니다.

                Model2Excercise model =  Model2Excercise.getModel(date);//date가 Map의 key 이므로, 클릭한 날의 date로 Map에저장되잇는 Model을 가져옵니다.

                if(model == null){//모델이 없다는건 해당 날의 운동 데이터가 없다는 뜻이므로 return 합니다.
                    UtilDialog.showToast(Page2Calendar.this,"운동한 기록이 없습니다.");
                    return;
                }
                DateFormat df = SimpleDateFormat.getDateInstance();
                if(!df.format(model.getDate()).equals(df.format(date))){//이건 date의 형태가 같은 달 같은 요일은 같으므로 String 값으로 변환시켜서 비교를 한번더합니다.
                    UtilDialog.showToast(Page2Calendar.this,"운동한 기록이 없습니다.");
                    return;
                }

                Intent intent = new Intent(Page2Calendar.this, Page2CalendarView.class);
                intent.putExtra("Model2Excercise", model);
                startActivityForResult(intent, ApplicationProperty.REQUEST_CODE_FOR_CALENDAR_VIEW);//해당 날의 모델을 가지고 View Acrivity 로 이동합니다.
            }
        });
    }

}
