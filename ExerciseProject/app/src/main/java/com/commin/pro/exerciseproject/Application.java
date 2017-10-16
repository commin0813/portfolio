package com.commin.pro.exerciseproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.commin.pro.exerciseproject.page.menu_list.Page2MenuList;

public class Application extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.application_layout);
        createGUI();
    }

    private void createGUI() {
        Intent intent = new Intent(Application.this, Page2MenuList.class);
        startActivity(intent);
        finish();
    }

}
