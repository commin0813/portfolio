package com.commin.pro.gangwon.page.map;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.commin.pro.gangwon.R;
import com.commin.pro.gangwon.page.company.Page2Company;
import com.commin.pro.gangwon.page.energy.Page2Energy;
import com.commin.pro.gangwon.page.menu.CustomMenu;
import com.commin.pro.gangwon.page.util.Util2Menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class Page2Map extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_map_layout);

        init_menu();
        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment) fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**************************
     *
     * @param map
     */
    @Override
    public void onMapReady(GoogleMap map) {

        LatLng GangWon = new LatLng(37.731583, 128.592556);
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(GangWon);
//        markerOptions.title("강원도");
//        markerOptions.snippet("강원도 지도");
//        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(GangWon));
        map.animateCamera(CameraUpdateFactory.zoomTo(8));
    }


    /***************************************
     * Footer
     */
    private LinearLayout ll_nav_menu = null;
    private CustomMenu customMenu = null;

    private void init_menu() {
        customMenu = new CustomMenu(Page2Map.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customMenu.setLayoutParams(lp);

        ImageView imageView = (ImageView) customMenu.findViewById(R.id.iv_item_cancel_menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util2Menu.isNavMenuShowing(Page2Map.this, ll_nav_menu);
            }
        });
    }

    public void call_menu(View view) {
        if (ll_nav_menu == null)
            ll_nav_menu = (LinearLayout) findViewById(R.id.ll_nav_menu);
        else
            ll_nav_menu.removeAllViews();

        ll_nav_menu.addView(customMenu);
        Util2Menu.isNavMenuShowing(Page2Map.this, ll_nav_menu);
    }

    public void call_home(View view) {
        finish();
    }

    public void startCompany(View view) {
        startActivity(new Intent(Page2Map.this, Page2Company.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        if (ll_nav_menu != null && ll_nav_menu.isShown()) {
            Util2Menu.isNavMenuShowing(Page2Map.this, ll_nav_menu);
            return;
        }
        super.onBackPressed();
    }
}
