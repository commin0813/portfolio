package com.commin.pro.lecture.page.sign_up;

import com.commin.pro.lecture.Driver.Driver2SQL;
import com.commin.pro.lecture.model.Model2User;

public class Page2SignUpAdvisor {
    /********************
     * 유저 아이디와 패스워드를 가져와서 Driver에게 넘겨 결과값을 받습니다.
     * @param user_id
     * @param user_password
     * @return
     * @throws Exception
     */
    public String cretaeUser(String user_id, String user_password) throws Exception {
        try {
            Model2User model = new Model2User();
            model.setUser_id(user_id);
            model.setUser_password(user_password);
            Driver2SQL driver = new Driver2SQL();
            driver.createUser(model);
            return null;
        } catch (Exception e) {
            throw e;
        }
    }
}
