package com.commin.pro.lecture.model;

import java.io.Serializable;

/*******************
 * 유저 정보를 담는 모델
 */
public class Model2User implements Serializable{
    private String user_id;
    private String user_password;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
