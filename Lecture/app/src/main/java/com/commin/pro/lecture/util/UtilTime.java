package com.commin.pro.lecture.util;

import com.commin.pro.lecture.model.Model2LectureTime;

import java.util.ArrayList;


public class UtilTime {

    public static boolean isStringInt(char str) {
        try {
            String ss = str + "";
            Double.parseDouble(ss);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }


    }

    public static ArrayList<Model2LectureTime> getTimeValue(String time) {
        ArrayList<Model2LectureTime> lectureTimes = new ArrayList<Model2LectureTime>();
        String day = "";
        String lecture_time = "";
        int number_count = 0;
        int count = 0;
        Model2LectureTime lectureTime = null;
        for (int i = 0; i < time.length(); i++) {
            char aa = time.charAt(i);
            if (aa == ',') {

                continue;
            } else {

                if (isStringInt(aa) == true) {
                    //숫자
                    if (number_count == 0)
                        lecture_time = String.valueOf(aa);
                    else {
                        lecture_time = lecture_time + aa;
                    }
                } else if (isStringInt(aa) == false) { //문자
                    if (Character.getType(aa) == 5) { //한글
                        if (count == 0) {
                            count++;
                        } else {
                            lectureTimes.add(lectureTime);
                        }


                        lectureTime = new Model2LectureTime();
                        day = String.valueOf(aa);
                        lectureTime.setDay(day);
                    } else if (aa >= 0x41 && aa <= 0x5A || aa >= 0x61 && aa <= 0x7A) {// 영문
                        lecture_time = lecture_time + aa;
                        lectureTime.addLecture_times(lecture_time);

                        lecture_time = "";
                        number_count = 0;
                    }

                }
            }
            if (i == time.length() - 1) {
                lectureTimes.add(lectureTime);
            }
        }
        if (lectureTimes.size() != 0)
            return lectureTimes;
        else
            return null;
    }
}
