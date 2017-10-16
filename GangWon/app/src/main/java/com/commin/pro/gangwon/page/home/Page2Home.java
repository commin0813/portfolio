package com.commin.pro.gangwon.page.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.commin.pro.gangwon.R;
import com.commin.pro.gangwon.model.Model2News;
import com.commin.pro.gangwon.model.Model2SMP;
import com.commin.pro.gangwon.page.ApplicationProperty;
import com.commin.pro.gangwon.page.company.Page2Company;
import com.commin.pro.gangwon.page.development.Page2Development;
import com.commin.pro.gangwon.page.energy.Page2Energy;
import com.commin.pro.gangwon.page.map.Page2Map;
import com.commin.pro.gangwon.page.menu.CustomMenu;
import com.commin.pro.gangwon.page.util.Util2Menu;
import com.commin.pro.gangwon.page.webview.Page2WebView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Page2Home extends AppCompatActivity {

    private Model2SMP smps = null;
    private ArrayList<Model2News> news_items = null;
    private TextView tv_home_smp_date, tv_home_smp_max, tv_home_smp_avg;

    private ListView lst_home_news = null;
    private ArrayAdapter2News adapter = null;

    private Handler mHandler;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_home_layout);
        init();
        init_menu();
        createGUI();
    }

    /**************
     * 자원의 초기화를 담당하는 메서드
     */
    private void init() {

        ApplicationProperty.HOME_CONTEXT = Page2Home.this;//메뉴로 이동시 HOME화면을 finish 시키는것을 막기위해서 처음 호출된 Page2Home Acitivity의 Context를 static 변수에 저장

        tv_home_smp_date = (TextView) findViewById(R.id.tv_home_smp_date);
        tv_home_smp_max = (TextView) findViewById(R.id.tv_home_smp_max);
        tv_home_smp_avg = (TextView) findViewById(R.id.tv_home_smp_avg);

        lst_home_news = (ListView) findViewById(R.id.lst_home_news);
        lst_home_news.setDivider(null);

        lst_home_news.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String url = news_items.get(position).getTarget_url();
                Intent intent = new Intent(Page2Home.this, Page2WebView.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

        mHandler = new Handler();

    }


    /************************
     * 화면을 그리기위해 호출
     */
    private void createGUI() {

        //다이얼로그 프로그래스바로 크롤링이 완료되기전까지 유저에게 로딩중임을 알림
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(Page2Home.this, "",
                        "LOADING..", true);
            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    smps = new GetSMPInfo(Page2Home.this).execute().get(); // smp 모델에 smp 크롤링 정보 저장
                    news_items = new GetNewsInfo(Page2Home.this).execute().get(); //  news 모델에 웹 기사 크롤링 데이터 ArrayList형태로 저장
                } catch (Exception e) {
                    Log.w("LOG_TAG", e);
                }
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable, 1000);
    }

    /*********************************
     * web에서 기사를 크롤링하기위한 AsyncTask를 상속받은 클래스,
     */
    class GetNewsInfo extends AsyncTask<Integer, Integer, ArrayList<Model2News>> {
        private Context context;

        public GetNewsInfo(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Model2News> doInBackground(Integer... integers) {
            Elements options = null;
            ArrayList<Model2News> items = new ArrayList<Model2News>();
            try {
                //Jsoup 라이브러리 사용
                Document document = Jsoup.connect(ApplicationProperty.ADDR_ARTICLE).get();
                options = document.select("div > .container > #content > .syw_result_box > .syw_result > .sywr_summary > .box > ul");
                for (Element element : options) {
                    Element title_ele = element.child(0).child(0);
                    if (title_ele == null) {
                        continue;
                    }
                    Model2News model = new Model2News();
                    model.setTitle(title_ele.text());
                    model.setTarget_url(title_ele.attr("href"));
                    items.add(model);
                    if (items.size() >= ApplicationProperty.SIZE_NEWS) {
                        break;
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(ArrayList<Model2News> result) {
            adapter = new ArrayAdapter2News(Page2Home.this, R.layout.item_list_news, result);
            lst_home_news.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    /**************
     * smp 크롤링하기위한 클래스
     */
    class GetSMPInfo extends AsyncTask<Integer, Integer, Model2SMP> {
        private Context context;

        public GetSMPInfo(Context context) {
            this.context = context;
        }

        @Override
        protected Model2SMP doInBackground(Integer... integers) {
            Elements options = null;
            Model2SMP model = new Model2SMP();
            try {
                Document document = Jsoup.connect(ApplicationProperty.ADDR_SMP).get(); // Jsoup이라는 라이브러리를 이용해서 Doc 형식의 html 파싱데이터를 받아옵니다.
                options = document.select("div > #smp_01 > table > tbody > tr > td");
                ArrayList<String> options_value = new ArrayList<>();
                for (Element element : options) {
                    String value = element.text();
                    if (value.equals("")) {
                        continue;
                    }
                    options_value.add(value);
                }
                model.setDate(options_value.get(0));
                model.setMax_value(options_value.get(1));
                model.setAvg_value(options_value.get(2));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return model;
        }

        @Override
        protected void onPreExecute() {
        }

        /***************************
         * 해당메서드는 AsyncTask가 모든 작업을 수행하고 마지막으로 호출하는 함수이므로
         * 이곳에 결과 데이터를 가지고 화면에 setting하면됩니다.
         * @param result
         */
        @Override
        protected void onPostExecute(Model2SMP result) {
            tv_home_smp_date.setText(result.getDate());
            tv_home_smp_max.setText(result.getMax_value());
            tv_home_smp_avg.setText(result.getAvg_value());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ll_nav_menu != null)
            ll_nav_menu.setVisibility(View.INVISIBLE);
    }

    /*********************
     * 맵 Activity를 호출합니다.
     * @param view
     */
    public void startMap(View view) {
        startActivity(new Intent(Page2Home.this, Page2Map.class));
    }

    /**********************
     * Url데이터만 알면 Intent에 url을 넣어 보내면됩니다.
     * 그러면 webview에서 url을 실행합니다.
     * onClick 속성을 이용하면 클릭당한 view를 함께 보내게되는데
     * 이 view의 id를 비교하여 클릭된 view가 어떤것인지 알수있습니다.
     * 여기에서는 두개의 view에 onClick:startWebView 속성을 주었는데
     * 하나는 동계올림픽 홈페이지버튼, 하나는 강원사이버관광페이지 버튼입니다.
     * 비교하여서 URL을 다르게하여 Page2WebView 로 보내면됩니다.
     * @param view
     */
    public void startWebView(View view) {
        Intent intent = new Intent(Page2Home.this, Page2WebView.class);
        String url = "";
        switch (view.getId()) {
            case R.id.ll_py:
                url = ApplicationProperty.ADDR_PYEONGCHANG;
                break;
            case R.id.ll_siber:
                url = ApplicationProperty.ADDR_SIBER;
                break;
        }
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /***************
     * 클릭시 호출되며 에너지역사 Activity로 이동합니다.
     * @param view
     */
    public void startEnergyHistory(View view) {
        startActivity(new Intent(Page2Home.this, Page2Energy.class));
    }

    /***************************
     * Intent에 tab이라는 key값으로 CODE값을 같이전달하면 해당하는 tab으로
     * Development Activity를 엽니다.
     * @param view
     */
    public void startDevelopment(View view) {
        Intent intent = new Intent(Page2Home.this, Page2Development.class);

        switch (view.getId()) {
            case R.id.ll_sun_light: {
                intent.putExtra("tab", ApplicationProperty.CODE_SUN_LIGHT);
            }
            break;

            case R.id.ll_sun_fire: {
                intent.putExtra("tab", ApplicationProperty.CODE_SUN_FIRE);

            }
            break;

            case R.id.ll_wind_force: {
                intent.putExtra("tab", ApplicationProperty.CODE_WIND_FORCE);
            }
            break;

            case R.id.ll_water_fire: {
                intent.putExtra("tab", ApplicationProperty.CODE_WATER_FIRE);
            }
            break;

        }
        startActivity(intent);
    }


    /***************************************
     * Footer
     * 앱 하단의 세가지 버튼을 컨트롤하기위한 메서드입니다.
     * 필요한 Acitivity 클래스에 복사 붙여넣기하여 약간 수정하면
     * 재활용 가능합니다.
     */
    private LinearLayout ll_nav_menu = null;
    private CustomMenu customMenu = null;

    /******************
     * 해당 메서드는 onCreate - init 함수다음에 반드시 호출해줘야합니다.
     */
    private void init_menu() {
        customMenu = new CustomMenu(Page2Home.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        customMenu.setLayoutParams(lp);

        ImageView imageView = (ImageView) customMenu.findViewById(R.id.iv_item_cancel_menu);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util2Menu.isNavMenuShowing(Page2Home.this, ll_nav_menu);
            }
        });
    }

    /***************************
     * 메뉴 화면을 부릅니다.'
     * isNavMenuShowing메서드를 통해서 열고닫을때 애니메이션효과를 같이줍니다.
     * @param view
     */
    public void call_menu(View view) {
        if (ll_nav_menu == null)
            ll_nav_menu = (LinearLayout) findViewById(R.id.ll_nav_menu);
        else
            ll_nav_menu.removeAllViews();

        ll_nav_menu.addView(customMenu);
        Util2Menu.isNavMenuShowing(Page2Home.this, ll_nav_menu);
    }

    /*******************
     * 하단버튼 세개중 가장 오른쪽에있는 버튼을 클릭하면
     * Page2Company로 이동합니다.
     * @param view
     */
    public void startCompany(View view){
        startActivity(new Intent(Page2Home.this, Page2Company.class));
    }

    /***************************
     * 가운데 홈버튼을 누를때 실행되는 메서드입니다.
     * 홈에서는 필요없는 기능이지만, 홈에서 홈버튼을누르면 새로고침 효과가 날수있도록 다시 크롤링하여 화면을 그리도록 했습니다.
     * 다른곳에서는 finish 함수를 실행시키기만해도 홈화면으로 이동하는것같은 효과를 볼 수 있습니다.
     * Task 관리의 일환으로 여러개의 Acitivity가 호출되지않도록 신경써서 개발하였기때문에
     * 홈화면은 계속해서 켜져있고 나머지 Acitivity는 다른 Activity가 호출될때 다 finish됩니다.
     * @param view
     */
    public void call_home(View view) {
        createGUI();
    }

    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    /*****************
     * Back 버튼을 눌렀을때, 바로 앱이 종료되지않기위해 넣은 메서드이며,
     * 메뉴가 열려있다면 Back버튼을 눌러 메뉴만 종료될수있도록 하였습니다.
     */
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (ll_nav_menu != null && ll_nav_menu.isShown()) {
            Util2Menu.isNavMenuShowing(Page2Home.this, ll_nav_menu);
            return;
        }
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로 버튼을 한번더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }

    }
}
