package com.commin.pro.lecture.page.login;

import com.commin.pro.lecture.Driver.Driver2SQL;
import com.commin.pro.lecture.model.Model2Course;
import com.commin.pro.lecture.model.Model2User;

public class Page2LoginAdvisor {
    /*********************
     * 유저 정보를 가지고 DB 테이블과 비교하여 맞으면 로그인을 시키고
     * 틀리면 Exception이 발생하게 합니다.
     * @param user_id
     * @param user_password
     * @return
     * @throws Exception
     */
    public boolean login(String user_id,String user_password) throws Exception{
        try{
            Model2User model = new Model2User();
            model.setUser_id(user_id);
            model.setUser_password(user_password);
            Driver2SQL driver = new Driver2SQL();
            return driver.login(model);
        }catch (Exception e){
            throw e;
        }
    }

}
