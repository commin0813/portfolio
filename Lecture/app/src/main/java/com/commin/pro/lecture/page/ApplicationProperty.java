package com.commin.pro.lecture.page;

import android.graphics.PorterDuff;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2Course;
import com.commin.pro.lecture.model.Model2User;

import java.util.ArrayList;
import java.util.HashMap;



public class ApplicationProperty {

    /////////////////////////////////////////////////
//  CODE
// //////////////////////////////////////////////
    public static final int REQUEST_CODE_SIGN_UP = 0x11;
    public static final int RESULT_CODE_SIGN_UP_OK = 0x12;
    public static final int RESULT_CODE_SIGN_UP_FAIL = 0x13;

    public static int REQUEST_CODE_FOR_LECTURE_EDIT = 0x14;
    public static int RESULT_LOGOUT = 0x15;

    public static int REQUEST_CODE_FOR_LECTURE_SEARCH = 0x16;


    /////////////////////////////////////////////////
//  USER
// //////////////////////////////////////////////

    /***************
     * 유저정보가 static으로 잠시 저장되어집니다. 그러니까 앱을 완전히 종료하기전까지는 계속해서
     * 유저정보를 가지고있습니다.
     */
    public static boolean isLogined = false;
    public static Model2User model2User = new Model2User();


/////////////////////////////////////////////////
//  Lecture
// //////////////////////////////////////////////

    public static ArrayList<Model2Course> LECTURE_TEMP_LIST = null;
    private static ArrayList<Model2Course> LECTURE_REGISTERED_LIST = null;

    /**********************
     * ArrayList를 여러곳에서 호출하기때문에 매번 생성하는것이 자원의 낭비라 SingleTone 패턴을 사용했습니다.
     * @return
     */
    public static synchronized ArrayList<Model2Course> getRegisteredList() {// SingleTone
        if (LECTURE_REGISTERED_LIST == null) {
            LECTURE_REGISTERED_LIST = new ArrayList<Model2Course>();
        }
        return LECTURE_REGISTERED_LIST;
    }

    public static synchronized void setRegisteredList(ArrayList<Model2Course> model) {// SingleTone
        LECTURE_REGISTERED_LIST = model;
    }


    public static String[] time_title = null;


    private static HashMap<String, String> departMent_map = null;

    /***************************
     * 하드코딩 한 부분입니다.
     * 크롤링으로는 불러올 수 없는 영역이라 일일히 하드코딩했습니다.
     * 대학명에 따라서 새롭게 불러오는 영역이라 홈피만든 사람은 어떻게 조건주면 불러올수있는지 알수도있지만
     * 만든사람이아니고서야 알수가없습니다.
     * @return
     */
    public static synchronized HashMap<String, String> getDepartMentHashMap() {// SingleTone
        if (departMent_map == null) {
            departMent_map = new HashMap<String, String>();
            departMent_map.put("1101","국어국문학과");
            departMent_map.put("1102","영어영문학과");
            departMent_map.put("1103","불어불문학과");
            departMent_map.put("1104","독어독문학과");
            departMent_map.put("1105","중어중문학과");
            departMent_map.put("1106","사학과");
            departMent_map.put("1107","철학과");
            departMent_map.put("1108","고고인류학과");
            departMent_map.put("1109","일어일문학과");
            departMent_map.put("110A","한문학과");
            departMent_map.put("110B","노어노문학과");

            departMent_map.put("1201","정치외교학과");
            departMent_map.put("1202","사회학과");
            departMent_map.put("1203","지리학과");
            departMent_map.put("1204","문헌정보학과");
            departMent_map.put("1205","심리학과");
            departMent_map.put("1206","사회복지학과");
            departMent_map.put("1207","신문방송학과");
            departMent_map.put("1209","사회복지학부");
            departMent_map.put("120A","디지털정보관리융합전공");
            departMent_map.put("120B","IT정치융합전공 ");

            departMent_map.put("1301","수학과");
            departMent_map.put("1302","화학과");
            departMent_map.put("1303","지질학과");
            departMent_map.put("1304","통계학과");
            departMent_map.put("1305","천문대기과학과");
            departMent_map.put("130701","생명과학부 생물학전공");
            departMent_map.put("130705","생명과학부 생명공학전공");
            departMent_map.put("1309","생물학과");
            departMent_map.put("130A","물리학과");
            departMent_map.put("130Q","지구시스템과학부");
            departMent_map.put("130Q01","지구시스템과학부 지질학전공");
            departMent_map.put("130Q02","지구시스템과학부 천문대기과학전공");
            departMent_map.put("130Q03","지구시스템과학부 해양학전공");
            departMent_map.put("130R","물리.화학.생물 융합전공");


            departMent_map.put("1403","경영학부");
            departMent_map.put("1403001","경영학부 A");
            departMent_map.put("1403002","경영학부 B");
            departMent_map.put("1403003","경영학부 C");
            departMent_map.put("140301","경영학부 경영학전공");
            departMent_map.put("140302","경영학부 회계학전공");
            departMent_map.put("1404","경제통상학부");
            departMent_map.put("1407","비즈니스인텔리전스융합전공");


            departMent_map.put("1502","법학부");


            departMent_map.put("1601001","기계공학부 A");
            departMent_map.put("1601002","기계공학부 B");
            departMent_map.put("1601003","기계공학부 C");
            departMent_map.put("160101","기계공학부 기계공학전공");
            departMent_map.put("160102","기계공학부 기계설계학전공");
            departMent_map.put("1603","응용화학과 ");
            departMent_map.put("1604","화학공학과 ");
            departMent_map.put("1605","고분자공학과 ");
            departMent_map.put("1606","환경공학과 ");
            departMent_map.put("1607","섬유시스템공학과 ");
            departMent_map.put("1609001","신소재공학부 A");
            departMent_map.put("1609002","신소재공학부 B");
            departMent_map.put("160903","신소재공학부 금속신소재공학전공");
            departMent_map.put("160904","신소재공학부 전자재료공학전공");
            departMent_map.put("160E","토목공학과 ");
            departMent_map.put("160I01","건축학부 건축공학전공");
            departMent_map.put("160I02","건축학부 건축학전공");
            departMent_map.put("160K01","건축·토목공학부 건축학전공");
            departMent_map.put("160K02","건축·토목공학부 건축공학전공");
            departMent_map.put("160K03","건축·토목공학부 토목공학전공");
            departMent_map.put("1611","에너지공학부 ");
            departMent_map.put("161101","에너지공학부 신재생에너지전공");
            departMent_map.put("161102","에너지공학부 에너지변환전공");
            departMent_map.put("161101","응용화학공학부 A");
            departMent_map.put("1612002","응용화학공학부 B");
            departMent_map.put("161201","응용화학공학부 응용화학전공");
            departMent_map.put("161202","응용화학공학부 화학공학전공");


            departMent_map.put("1703","농업토목공학과");
            departMent_map.put("1708","조경학과");
            departMent_map.put("1709","생물산업기계공학과");
            departMent_map.put("170A","농업경제학과");
            departMent_map.put("170B","응용생명과학부");
            departMent_map.put("170B01","응용생명과학부 식물생명과학전공");
            departMent_map.put("170B04","응용생명과학부 환경생명화학전공");
            departMent_map.put("170B05","응용생명과학부 응용생물학전공");
            departMent_map.put("170C","임학과 ");
            departMent_map.put("170D","임산공학과 ");
            departMent_map.put("170O","원예과학과 ");
            departMent_map.put("170P","식품공학부 ");
            departMent_map.put("170P01","식품공학부 식품생물공학전공");
            departMent_map.put("170P02","식품공학부 식품소재공학전공");
            departMent_map.put("170P03","식품공학부 식품응용공학전공");
            departMent_map.put("170Q","바이오섬유소재학과 ");
            departMent_map.put("170R","농산업학과 ");
            departMent_map.put("170S","산림과학.조경학부 ");
            departMent_map.put("170T","농업토목.생물산업공학부 ");
            departMent_map.put("170U","스마트팜공학융합전공 ");

            departMent_map.put("1901","교육학과 ");
            departMent_map.put("1902","국어교육과 ");
            departMent_map.put("1903","영어교육과 ");
            departMent_map.put("190601","사회교육학부 역사교육전공");
            departMent_map.put("190602","사회교육학부 지리교육전공");
            departMent_map.put("190603","사회교육학부 일반사회교육전공");
            departMent_map.put("1907","윤리교육과 ");
            departMent_map.put("1908","수학교육과 ");
            departMent_map.put("190901","과학교육학부 물리교육전공");
            departMent_map.put("190902","과학교육학부 화학교육전공");
            departMent_map.put("190903","과학교육학부 생물교육전공");
            departMent_map.put("190904","과학교육학부 지구과학교육전공");
            departMent_map.put("190905","과학교육학부 공통과학교육전공");
            departMent_map.put("190A","가정교육과 ");
            departMent_map.put("190B","가정교육과(기술.가정) 기술·가정교육전공");
            departMent_map.put("190D01","체육교육과 ");
            departMent_map.put("190D02","유럽어교육학부 독어교육전공");
            departMent_map.put("190E","유럽어교육학부 불어교육전공");
            departMent_map.put("190F","역사교육과 ");
            departMent_map.put("190F","지리교육과 ");
            departMent_map.put("190G","일반사회교육과 ");
            departMent_map.put("190H","물리교육과 ");
            departMent_map.put("190I","화학교육과 ");
            departMent_map.put("190J","생물교육과 ");
            departMent_map.put("190K","지구과학교육과 ");
            departMent_map.put("191G","통합과학교육전공");

            departMent_map.put("1801","음악학과 ");
            departMent_map.put("1803","국악학과 ");
            departMent_map.put("1804","미술학과 ");
            departMent_map.put("1806","디자인학과 ");

            departMent_map.put("1F01","의학과");
            departMent_map.put("1F04","의예과");

            departMent_map.put("1G01","치의학과 ");
            departMent_map.put("1G02","치의예과 ");

            departMent_map.put("1A01","수의학과");
            departMent_map.put("1A02","수의예과");

            departMent_map.put("1B03","의류학과 ");
            departMent_map.put("1B04","식품영양학과 ");
            departMent_map.put("1B07","아동학부 ");
            departMent_map.put("1B0701","아동학부 아동가족학전공");
            departMent_map.put("1B0702","아동학부 아동학전공");

            departMent_map.put("1E01","인문사회자율전공 ");
            departMent_map.put("1E02","자연과학자율전공 ");

            departMent_map.put("1C01","간호학과");

            departMent_map.put("1O01","전자공학부 ");
            departMent_map.put("1O01001","전자공학부 A");
            departMent_map.put("1O01002","전자공학부 B");
            departMent_map.put("1O01003","전자공학부 C");
            departMent_map.put("1O01004","전자공학부 D");
            departMent_map.put("1O01005","전자공학부 E");
            departMent_map.put("1O01006","전자공학부 F");
            departMent_map.put("1O01007","전자공학부 H");
            departMent_map.put("1O0101","전자공학부 모바일공학전공");
            departMent_map.put("1O02","컴퓨터학부 ");
            departMent_map.put("1O0204","컴퓨터학부 글로벌소프트웨어융합전공");
            departMent_map.put("1O03","전기공학과");
            departMent_map.put("1O06","빅데이터전공");
            departMent_map.put("1O07","건설IT전공");
            departMent_map.put("1O08","핀테크전공");
            departMent_map.put("1O09","미디어아트전공");

            departMent_map.put("1P03","글로벌인재학부");

            departMent_map.put("1Q01","약학과");

            departMent_map.put("1S01","행정학부");
            departMent_map.put("1S0101","행정학부 공공관리전공");
            departMent_map.put("1S0102","행정학부 공공정책전공");

            departMent_map.put("1L02","생태환경시스템학부");
            departMent_map.put("1L0201","생태환경시스템학부 산림환경자원전공");
            departMent_map.put("1L0202","생태환경시스템학부 식물자원환경전공");
            departMent_map.put("1L04","축산학과");
            departMent_map.put("1L05","레저스포츠학과");
            departMent_map.put("1L0601","생태환경관광학부 생물응용전공");
            departMent_map.put("1L0603","생태환경관광학부 생태관광전공");
            departMent_map.put("1L08","해양학과");
            departMent_map.put("1L09","축산생명공학과");
            departMent_map.put("1L10","말/특수동물학과");

            departMent_map.put("1U01","건설방재공학부 ");
            departMent_map.put("1U0101","건설방재공학부 건설방재공학전공");
            departMent_map.put("1U0102","건설방재공학부 건설환경공학전공");
            departMent_map.put("1U02","정밀기계공학과 ");
            departMent_map.put("1U03","자동차공학부 ");
            departMent_map.put("1U0301","자동차공학부 친환경자동차전공");
            departMent_map.put("1U0302","자동차공학부 지능형자동차전공");
            departMent_map.put("1U05","산업전자공학과 ");
            departMent_map.put("1U0601","컴퓨터정보학부 컴퓨터시스템공학전공");
            departMent_map.put("1U0602","컴퓨터정보학부 컴퓨터소프트웨어전공");
            departMent_map.put("1U07","나노소재공학부 ");
            departMent_map.put("1U0701","나노소재공학부 신소재공학전공");
            departMent_map.put("1U0702","나노소재공학부 나노공학전공");
            departMent_map.put("1U0703","나노소재공학부 에너지화공전공");
            departMent_map.put("1U08","식품외식산업학과 ");
            departMent_map.put("1U0A01","섬유패션디자인학부 섬유공학전공");
            departMent_map.put("1U0A02","섬유패션디자인학부 패션디자인전공");
            departMent_map.put("1U0I","융복합시스템공학부 ");
            departMent_map.put("1U0I01","융복합시스템공학부 항공위성시스템전공");
            departMent_map.put("1U0I02","융복합시스템공학부 플랜트시스템전공");
            departMent_map.put("1U0J","치위생학과 ");
            departMent_map.put("1U0K","소프트웨어학과 ");
        }
        return departMent_map;
    }


    //////////////////////////////////////////////////
    /// Lecture Color
    /////////////////////////////////////////////////

    private static int[] Background_Colors = {
            R.color.backBackDDC, //0
            R.color.backBack84, //1
            R.color.backBackCB, //2
            R.color.backBack80, //3
            R.color.backBack83, //4
            R.color.backBack81, //5
            R.color.backBackD1, //6
            R.color.backBack85, //7
            R.color.backBackC3, //8
            R.color.backBackDD, //9
            R.color.backBackE8 //10
    };

    private static int COLOR_COUNT = -1; //min_value : 0 , max_value : 10

    public static int getBackgroundColor() {
        if (COLOR_COUNT >= 10) {
            COLOR_COUNT = 0;
        } else {
            COLOR_COUNT++; // 0~ 10 까지 순서대로 저장되게 됩니다.
        }
        return Background_Colors[COLOR_COUNT];
    }

}
