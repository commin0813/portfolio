package com.commin.pro.gangwon.page.company;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.commin.pro.gangwon.R;
import com.commin.pro.gangwon.page.home.Page2Home;
import com.commin.pro.gangwon.page.menu.CustomMenu;
import com.commin.pro.gangwon.page.util.Util2Menu;

public class Page2Company extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_company_layout);
        init();
        init_menu();
    }
    private void init(){

    }


    /***************************************
     * Footer
     */
    private LinearLayout ll_nav_menu = null;
    private CustomMenu customMenu = null;


    private void init_menu() {
        customMenu = new CustomMenu(Page2Company.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customMenu.setLayoutParams(lp);

        ImageView imageView = (ImageView) customMenu.findViewById(R.id.iv_item_cancel_menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util2Menu.isNavMenuShowing(Page2Company.this, ll_nav_menu);
            }
        });
    }
    public void call_menu(View view) {
        if (ll_nav_menu == null)
            ll_nav_menu = (LinearLayout) findViewById(R.id.ll_nav_menu);
        else
            ll_nav_menu.removeAllViews();

        ll_nav_menu.addView(customMenu);
        Util2Menu.isNavMenuShowing(Page2Company.this, ll_nav_menu);
    }

    public void startCompany(View view){
        startActivity(new Intent(Page2Company.this, Page2Company.class));
        finish();
    }

    public void call_home(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (ll_nav_menu != null && ll_nav_menu.isShown()) {
            Util2Menu.isNavMenuShowing(Page2Company.this, ll_nav_menu);
            return;
        }
        super.onBackPressed();
    }
}
