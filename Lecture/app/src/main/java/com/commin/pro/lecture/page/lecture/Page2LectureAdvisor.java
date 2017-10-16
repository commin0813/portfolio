package com.commin.pro.lecture.page.lecture;

import com.commin.pro.lecture.Driver.Driver2SQL;

/**
 * Created by user on 2017-08-21.
 */
public class Page2LectureAdvisor {
    /************************
     * 강의 정보를 만들고 저장하는 메서드 호출
     * @param user_id
     * @throws Exception
     */
    public void setLectureData(String user_id) throws Exception{
        try{
            Driver2SQL driver = new Driver2SQL();
            driver.setLectureData(user_id);
            return;
        }catch (Exception e){
            throw e;
        }
    }

    /***************************
     * 유저를 지우는 쿼리를 하는 메서드 호출
     * @param course_id
     * @param user_id
     * @throws Exception
     */
    public void deleteLectureData(String course_id,String user_id) throws Exception{
        try{
            Driver2SQL driver = new Driver2SQL();
            driver.deleteLectureData(course_id,user_id);
            return;
        }catch (Exception e){
            throw e;
        }
    }

}
