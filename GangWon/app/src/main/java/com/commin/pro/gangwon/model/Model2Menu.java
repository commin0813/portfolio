package com.commin.pro.gangwon.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2017-09-12.
 */

public class Model2Menu implements Serializable{
    private String group_name;
    private ArrayList<String> child_list;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }


    public ArrayList<String> getChild_list() {
        return child_list;
    }

    public void setChild_list(ArrayList<String> child_list) {
        this.child_list = child_list;
    }
}
