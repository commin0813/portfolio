package com.commin.pro.exerciseproject.page.calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.dao.Dao2Excercise;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by user on 2016-11-30.
 * 이 클래스는 CalendarView를 Cusotomizing 하고자 만든 클래스입니다.
 */
public class CalendarView extends LinearLayout {
    private static final String LOG_TAG = "CalendarView";

    private static final int DAYS_COUNT = 42;//화면에 출력되는 날의 총 갯수입니다.
    private static final String DATE_FORMAT = "MMM yyyy";//제목에들어가는 Date 출력 형태는 00월 0000년 입니다.
    private String dateFormat;

    private static Calendar currentDate = Calendar.getInstance(); // 현재 시간을 가져옵니다.
    private EventHandler eventHandler = null;// interface 를 객체화 시킵니다.

    private LinearLayout header;
    private ImageView btn_prev;
    private ImageView btn_next;

    private TextView tv_date;
    private GridView grid;

    int[] rainbow = new int[]{ //요건 달력 상단 배경 색을 계절마다 바꾸고자 만들었습니다.
            R.color.summer,
            R.color.fall,
            R.color.winter,
            R.color.spring
    };

    int[] monthSeason = new int[]{2, 2, 3, 3, 3, 0, 0, 0, 1, 1, 1, 2};// 계절의 Code 값입니다. 0 : 여름 1:가을 2:겨울 3: 봄

    public static Calendar getCalendarInstance() {
        return currentDate;
    }

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.page_calendar_item, this);

        init_date_format(attrs);//date format을 초기화합니다.
        init_elements();//클래스에서사용하는 요소들을 초기화합니다.
        init_click_handler();//클릭 이벤트를 초기화합니다.

        updateCalendar();//달력을 갱신합니다.
    }

    private void init_date_format(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        } finally {
            ta.recycle();
        }
    }

    private void init_elements() {
        header = (LinearLayout) findViewById(R.id.calendar_header);
        btn_prev = (ImageView) findViewById(R.id.calendar_prev_button);
        btn_next = (ImageView) findViewById(R.id.calendar_next_button);
        tv_date = (TextView) findViewById(R.id.calendar_date_display);
        grid = (GridView) findViewById(R.id.calendar_grid);
    }

    private void init_click_handler() {
        btn_next.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);//next 버튼을 누르면 달을 +1 하여 달력을 갱신합니다.
                updateCalendar();
            }
        });

        btn_prev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);//prev 버튼을 누르면 달을  -1 하여 달력을 갱신합니다.
                updateCalendar();
            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View cell, int position, long id) {
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date) adapter.getItemAtPosition(position));
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View cell, int position, long id) {

                if (eventHandler == null)
                    return;
                Date date = (Date) adapter.getItemAtPosition(position);
                int month = date.getMonth();
                if (month != currentDate.get(Calendar.MONTH)) {
                    return;
                }

                Date clicked_date = (Date) adapter.getItemAtPosition(position);
                eventHandler.onDayClick(clicked_date); //inteface 에 등록된 메서드로 activity에 있는 onDayClick 메서드를 호출합니다.
                updateCalendar();
                return;
            }
        });
    }

    public void updateCalendar() {//운동 데이터가 있을 수 있고 없을 수 있으므로, 매개변수가없는 메서드를 만들어서 분기하여 updateCalendar(events)를 호출합니다.
        HashSet<Date> events = new HashSet<>();
        if (!Dao2Excercise.getHashMap().isEmpty()) {
            for (Date date : Dao2Excercise.getHashMap().keySet()) {
                events.add(date);
            }
            updateCalendar(events);
        } else {
            updateCalendar(null);
        }

    }


    public void updateCalendar(HashSet<Date> events) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) currentDate.clone();


        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;


        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        grid.setAdapter(new Adapter2Calendar(getContext(), cells, events));

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        tv_date.setText(sdf.format(currentDate.getTime()));

        int month = currentDate.get(Calendar.MONTH);
        int season = monthSeason[month];
        int color = rainbow[season];

        header.setBackgroundColor(getResources().getColor(color));
    }

    public void setEventHandler(EventHandler eventHandler) {//interface를 등록합니다. activity에서 CalendarView의 객체를 만들어 객체에 등록하면 됩니다.
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayLongPress(Date date);

        void onDayClick(Date date);
    }

}
