package com.commin.pro.lecture.page.lecture_search;


import com.commin.pro.lecture.Driver.Driver2SQL;
import com.commin.pro.lecture.model.Model2Course;

public class Page2LectureSearchAdvisor{
    public void setLectureData(String user_id) throws Exception{
        try{
            Driver2SQL driver = new Driver2SQL();
            driver.setLectureData(user_id);
            return;
        }catch (Exception e){
            throw e;
        }
    }
    public void insertCourse(Model2Course item) throws Exception{
        try{
            Model2Course model = item;
            Driver2SQL driver = new Driver2SQL();
            driver.insertCourse(model);
            return;
        }catch (Exception e){
            throw e;
        }
    }
}
