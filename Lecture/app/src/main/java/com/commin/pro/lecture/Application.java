package com.commin.pro.lecture;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.commin.pro.lecture.model.Model2User;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.page.lecture.Page2Lecture;
import com.commin.pro.lecture.page.login.Page2Login;
import com.commin.pro.lecture.util.UtilShare;

public class Application extends AppCompatActivity {
    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;

    //////////////////////////////////////////////////
    /// Lecture Color
    /////////////////////////////////////////////////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        try {
            loadPreferences();
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        ApplicationProperty.time_title = getResources().getStringArray(R.array.time_title);

        if(sharedPreferences.getBoolean(UtilShare.KEY_VALUE_ISCHECKED,false)){
            Model2User model = new Model2User();
            model.setUser_id(sharedPreferences.getString(UtilShare.KEY_VALUE_LOGINED_USER_ID,""));
            ApplicationProperty.model2User = model;
            ApplicationProperty.isLogined = true;
            startActivity(new Intent(Application.this, Page2Lecture.class));
        }else{
            startActivity(new Intent(Application.this, Page2Login.class));
        }

        finish();



    }

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_USER, Application.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }
}
