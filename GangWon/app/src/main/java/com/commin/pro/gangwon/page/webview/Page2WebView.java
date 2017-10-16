package com.commin.pro.gangwon.page.webview;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.commin.pro.gangwon.R;
import com.commin.pro.gangwon.page.company.Page2Company;
import com.commin.pro.gangwon.page.energy.Page2Energy;
import com.commin.pro.gangwon.page.map.Page2Map;
import com.commin.pro.gangwon.page.menu.CustomMenu;
import com.commin.pro.gangwon.page.util.Util2Menu;

public class Page2WebView extends AppCompatActivity {

    private static final String LOG_TAG = "Page2WebView";
    private WebView wb_content = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_web_view_layout);
        init();
        init_menu();
    }

    private void init() {
        wb_content = (WebView) findViewById(R.id.wb_content);
        wb_content.setWebViewClient(new WebClientHelper());

        wb_content.getSettings().setJavaScriptEnabled(true);
        wb_content.getSettings().setLoadWithOverviewMode(true);
        wb_content.getSettings().setUseWideViewPort(true);
        wb_content.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb_content.setVerticalScrollBarEnabled(false);
        wb_content.setHorizontalScrollBarEnabled(false);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            wb_content.getSettings().setTextZoom(90);
        }
        wb_content.loadUrl(getIntent().getStringExtra("url").toString());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wb_content.canGoBack()) {
            wb_content.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    class WebClientHelper extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }


    /***************************************
     * Footer
     */
    private LinearLayout ll_nav_menu = null;
    private CustomMenu customMenu = null;

    private void init_menu() {
        customMenu = new CustomMenu(Page2WebView.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customMenu.setLayoutParams(lp);

        ImageView imageView = (ImageView) customMenu.findViewById(R.id.iv_item_cancel_menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util2Menu.isNavMenuShowing(Page2WebView.this, ll_nav_menu);
            }
        });
    }

    public void call_menu(View view) {
        if (ll_nav_menu == null)
            ll_nav_menu = (LinearLayout) findViewById(R.id.ll_nav_menu);
        else
            ll_nav_menu.removeAllViews();

        ll_nav_menu.addView(customMenu);
        Util2Menu.isNavMenuShowing(Page2WebView.this, ll_nav_menu);
    }

    public void call_home(View view) {
        finish();
    }

    public void startCompany(View view) {
        startActivity(new Intent(Page2WebView.this, Page2Company.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (ll_nav_menu != null && ll_nav_menu.isShown()) {
            Util2Menu.isNavMenuShowing(Page2WebView.this, ll_nav_menu);
            return;
        }
        super.onBackPressed();
    }

}
