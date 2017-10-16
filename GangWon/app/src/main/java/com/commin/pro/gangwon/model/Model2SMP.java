package com.commin.pro.gangwon.model;

import java.io.Serializable;

/**
 * Created by user on 2017-09-11.
 */

public class Model2SMP implements Serializable {
    private String date;
    private String max_value;
    private String avg_value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMax_value() {
        return max_value;
    }

    public void setMax_value(String max_value) {
        this.max_value = max_value;
    }

    public String getAvg_value() {
        return avg_value;
    }

    public void setAvg_value(String avg_value) {
        this.avg_value = avg_value;
    }
}
