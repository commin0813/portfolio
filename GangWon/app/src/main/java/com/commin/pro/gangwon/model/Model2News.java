package com.commin.pro.gangwon.model;

import java.io.Serializable;

/**
 * Created by user on 2017-09-11.
 */

public class Model2News implements Serializable {

    private String title;
    private String target_url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        target_url =  target_url.replace("www","m");

        this.target_url = target_url;
    }
}
