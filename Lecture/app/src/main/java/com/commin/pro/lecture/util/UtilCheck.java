package com.commin.pro.lecture.util;

import android.util.Log;

import java.util.StringTokenizer;

public class UtilCheck {
    public static int checkPeriod(String start_time, String end_time) {
        return Integer.parseInt(end_time) - Integer.parseInt(start_time);
    }

    public static String checkTime(String time) {
        return Integer.parseInt(time) < 12 ? "오전" : "오후";
    }

    public static int checkTimeForId(String id) {
        StringTokenizer st = new StringTokenizer(id, "+");
        int row_index = Integer.parseInt(st.nextToken());
        int time = -1;
        switch (row_index) {
            case 0:
                time = 8;
                break;
            case 1:
                time = 9;
                break;
            case 2:
                time = 10;
                break;
            case 3:
                time = 11;
                break;
            case 4:
                time = 12;
                break;
            case 5:
                time = 13;
                break;
            case 6:
                time = 14;
                break;
            case 7:
                time = 15;
                break;
            case 8:
                time = 16;
                break;
            case 9:
                time = 17;
                break;
            case 10:
                time = 18;
                break;
            case 11:
                time = 19;
                break;
            case 12:
                time = 20;
                break;
            case 13:
                time = 21;
                break;
            case 14:
                time = 22;
                break;
        }

        return time;
    }

    public static int checkTimeNameNumber(String id) {

        StringTokenizer st = new StringTokenizer(id, "+");
        int row_index = Integer.parseInt(st.nextToken());
        int time = -1;

        return row_index;
    }

    public static String checkDay(String id) {

        StringTokenizer st = new StringTokenizer(id, "+");
        String ss = st.nextToken();
        Log.w("", "");
        int a = Integer.parseInt(st.nextToken());
        switch (a) {
            case 1:
                return "월";
            case 2:
                return "화";
            case 3:
                return "수";
            case 4:
                return "목";
            case 5:
                return "금";
            case 6:
                return "토";
            case 7:
                return "일";

        }
        return null;

    }

}
