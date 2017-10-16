package com.commin.pro.gangwon.page;

import android.app.Activity;
import android.content.Context;

import com.commin.pro.gangwon.page.home.Page2Home;

import java.util.ArrayList;

/**
 * Created by user on 2017-09-08.
 */
public class ApplicationProperty {
    /****************************
     * COMMON
     */
    public static Context HOME_CONTEXT = null;

    /***********************************
     * CODE
     */
    public static final int CODE_SUN_LIGHT = 0x01;
    public static final int CODE_SUN_FIRE = 0x02;
    public static final int CODE_WIND_FORCE = 0x03;
    public static final int CODE_WATER_FIRE = 0x04;


    public static final int CODE_SUMMARY = 0x05;
    public static final int CODE_CONTENT = 0x06;
    public static final int CODE_DETAIL_SUMMARY = 0x07;
    public static final int CODE_LINK= 0x08;


    /*************************************
     * SIZE
     */
    public static final int SIZE_NEWS = 4;

    /**************************************
     * URL
     */
    public static final String ADDR_PYEONGCHANG = "https://www.pyeongchang2018.com/ko/index";
    public static final String ADDR_SIBER = "http://www.gangwon.to/";
    public static final String ADDR_ARTICLE = "http://www.kado.net/?mod=search&act=engine&cust_div_code=&searchContType=article&searchWord=%EC%8B%A0%EC%9E%AC%EC%83%9D&fromDate=&toDate=&sfield=&article_type=&sort=date";
    public static final String ADDR_SMP = "http://www.kpx.or.kr/";


    /***************************************
     * MENU
     */
    public static String[] title = {"에너지 역사", "에너지 발전", "관련 링크"};
    public static String[] energy = {"개요", "내용", "요약","관련링크"};
    public static String[] energy2 = {"태양광", "태양열", "풍 력", "지열/수열"};
    public static String[] links = {"동계올림픽페이지", "강원사이버관광페이지"};
}
