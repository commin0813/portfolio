package com.commin.pro.gangwon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.commin.pro.gangwon.page.home.Page2Home;

public class Application extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_layout);

        startActivity(new Intent(Application.this, Page2Home.class));
        finish();

    }
}
