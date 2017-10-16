package com.commin.pro.exerciseproject.page.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;


import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.model.Model2Excercise;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

/**
 * Created by user on 2016-11-28.
 */
public class Adapter2Calendar extends ArrayAdapter<Date> {

    private HashSet<Date> eventDays;
    private LayoutInflater inflater;
    private Context context;


    public Adapter2Calendar(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
        super(context, R.layout.page_calendar_item_day, days);
        this.context = context;
        this.eventDays = eventDays;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Date date = getItem(position);
        int day = date.getDate();
        int month = date.getMonth();
        int year = date.getYear();

        Date today = new Date();

        if (view == null) {
            view = inflater.inflate(R.layout.page_calendar_item_day, parent, false);

        }

        view.setBackgroundResource(0);
        if (eventDays != null) {
            for (Date eventDate : eventDays) {
               final Model2Excercise model =Model2Excercise.getModel(eventDate);
                if (eventDate.getDate() == day &&
                        eventDate.getMonth() == month &&
                        eventDate.getYear() == year) {
                    if(model.isBeginner()){
                        view.setBackgroundResource(R.drawable.marker_yellow);
                    }else{
                        view.setBackgroundResource(R.drawable.marker_red);
                    }
                    break;
                }
            }
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_calendar_day);
        textView.setTypeface(null, Typeface.NORMAL);
        textView.setTextColor(context.getResources().getColor(R.color.greyed_out));

        Calendar currentDate =CalendarView.getCalendarInstance();
        if (month == currentDate.get(Calendar.MONTH)) {
            // if this day is outside current month, grey it out
            textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            if (year == today.getYear() && month == today.getMonth() && day == today.getDate()) {
                textView.setTypeface(null, Typeface.BOLD);
                textView.setTextColor(context.getResources().getColor(R.color.today));
            }
        }

        textView.setText(String.valueOf(date.getDate()));

        return view;
    }


}
