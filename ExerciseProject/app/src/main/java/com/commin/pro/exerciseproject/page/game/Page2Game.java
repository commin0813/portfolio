package com.commin.pro.exerciseproject.page.game;

import android.graphics.Canvas;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.page.photo.Page2Photo;
/*
 게임 Acitivity 입니다. View를 화면에 만들어서 출력합니다.
 */
public class Page2Game extends AppCompatActivity {
    private TextView tv_game_title;
    private LinearLayout ll_container;
    private BarView barView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_game_layout);
        createGUI();
    }

    private void createGUI() {
        init_elements();
    }

    private void init_elements() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = dm.widthPixels;

        barView = new BarView(Page2Game.this, Float.valueOf(width));
        tv_game_title = (TextView) findViewById(R.id.tv_game_title);
        tv_game_title.setSelected(true);
        ll_container = (LinearLayout) findViewById(R.id.ll_container);
        barView.setVisibility(View.VISIBLE);
        ll_container.addView(barView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //Layout에 View를 추가합니다.

    }
}
