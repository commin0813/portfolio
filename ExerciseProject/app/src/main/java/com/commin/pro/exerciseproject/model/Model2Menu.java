package com.commin.pro.exerciseproject.model;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by user on 2016-11-28.
 */
public class Model2Menu implements Serializable {
    private String menu_text;
    private Drawable icon;

    public String getMenu_text() {
        return menu_text;
    }

    public void setMenu_text(String menu_text) {
        this.menu_text = menu_text;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
