package com.commin.pro.gangwon.page.util;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.commin.pro.gangwon.R;

/**
 * Created by user on 2017-09-12.
 */

public class Util2Menu {
    public static boolean isNavMenuShowing(Activity activity, View view){
        if(view == null){
            return false;
        }
        LinearLayout nav_menu = (LinearLayout)view;
        final Animation animTransRight = AnimationUtils.loadAnimation(activity, R.anim.anim_translate_right);
        final Animation animTransLeft = AnimationUtils.loadAnimation(activity, R.anim.anim_translate_left);
        if(nav_menu.isShown()){
            nav_menu.startAnimation(animTransLeft);
            nav_menu.setVisibility(View.INVISIBLE);
            return true;
        }else {
            nav_menu.startAnimation(animTransRight);
            nav_menu.setVisibility(View.VISIBLE);
            return false;
        }
    }
}
