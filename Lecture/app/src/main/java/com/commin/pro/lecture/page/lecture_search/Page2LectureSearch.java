package com.commin.pro.lecture.page.lecture_search;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2Course;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.util.UtilDialog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Page2LectureSearch extends AppCompatActivity {
    public static String LOG_TAG = "Page2LectureSearch";


    private RadioGroup rdg_search, rdg_campus;
    private EditText ed_search_word;
    private Button btn_query_data;
    private ArrayList<Model2Course> courses = null;
    private ListView lst_search_result = null;
    private ArrayAdapter2LectureSearch adapter = null;


    private Handler mHandler;
    private ProgressDialog mProgressDialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_search_layout);

        init();
    }

    private void init() {
        btn_query_data = (Button) findViewById(R.id.btn_query_data);
        btn_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startGetData(); // 크롤링 시작부분
                } catch (Exception e) {
                    UtilDialog.openError(Page2LectureSearch.this, e.getMessage(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    });
                }
            }
        });

        ed_search_word = (EditText) findViewById(R.id.ed_search_word);
        rdg_search = (RadioGroup) findViewById(R.id.rdg_search);
        rdg_campus = (RadioGroup) findViewById(R.id.rdg_campus);


        lst_search_result = (ListView) findViewById(R.id.lst_search_result);
        lst_search_result.setDivider(null);


        mHandler = new Handler();


    }

    private void startGetData() throws Exception {

        /*******
         * 다이얼로그 프로그래스바를 조회를 하기전에 화면에 띄워줍니다.
         * 조회 부분을 Handler를 이용해 약간의 딜레이를 준 이유는 다이얼로그가 뜨기전에
         * 조회가 시작되어 화면이 멈추는 현상을 방지하기위해서입니다.
         * 조회가 끝나는 시점과 데이터 셋팅이 끝나는 시점에 다이얼로그를 dismiss 시켜줍니다.
         * ********/


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mProgressDialog = ProgressDialog.show(Page2LectureSearch.this, "",
                        "잠시만 기다려 주세요.", true);
            }
        });

        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        if(ApplicationProperty.LECTURE_TEMP_LIST != null){
                            courses = ApplicationProperty.LECTURE_TEMP_LIST;
                        }

                        if (courses == null) { // courses 에 데이터가 없으면 크롤링을 시작합니다. 만약 데이터가있으면 있는 데이터로 바로 셋팅을 합니다.
                            courses = new GetSiteInfo(Page2LectureSearch.this).execute().get();
                        }
                        if (courses != null) {
                            createData(courses); // 데이터를 리스트뷰에 뿌리기전에 검색조건을 확인하여 데이터를 가공하는 메서드를 호출하는 부분입니다.
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            Handler handler = new Handler();
            handler.postDelayed(runnable, 100);

        } catch (Exception e) {
            throw e;
        }
    }

    /**********************
     * param의 이름도 courses 이기때문에 헷갈리지 마세요.
     * 전역변수로 되어있는 courses는 항상 최초의 크롤링한 데이터를 가지고있습니다.
     * 인자로 받은 courses는 검색조건에따라 계속해서 값이 변하고 그걸 setData()메서드로 보내서
     * ListView에 넣는것입니다.
     *
     * @param courses
     * @throws Exception
     */
    private void createData(ArrayList<Model2Course> courses) throws Exception {
        int setting_campus_id = rdg_campus.getCheckedRadioButtonId();
        ArrayList<Model2Course> imsi_list = new ArrayList<Model2Course>();

        String search_text = "";
        search_text = ed_search_word.getText().toString();
        switch (setting_campus_id) {
            case R.id.rd_campus_total: { //전체
                if (search_text.equals("")) {
                    imsi_list = courses;
                } else {
                    imsi_list = getContainItem(courses, search_text);
                }

                break;
            }
            case R.id.rd_campus_D: { // 대구캠퍼스
                for (Model2Course model : courses) {
                    String name = model.getCourseCampus();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals("대구캠퍼스") || name.contains("대구")) {
                        imsi_list.add(model);
                    }
                }

                if (search_text.equals("")) {

                } else {
                    imsi_list = getContainItem(imsi_list, search_text);
                }

                break;
            }
            case R.id.rd_campus_S: { // 상주캠퍼스
                for (Model2Course model : courses) {
                    String name = model.getCourseCampus();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals("상주캠퍼스") || name.contains("상주")) {
                        imsi_list.add(model);
                    }
                }

                if (search_text.equals("")) {

                } else {
                    imsi_list = getContainItem(imsi_list, search_text);
                }
                break;
            }
        }
        courses = imsi_list;
        setData(courses);
    }


    /*********************************
     * 만약 검색조건에 사용자가 입력한 검색키워드가 있다면
     * 검색 키워드가 포함된 데이터를 찾아서 리스트형식으로 리턴해주는 메서드입니다.
     *
     * @param courses
     * @param search_text
     * @return
     */
    private ArrayList<Model2Course> getContainItem(ArrayList<Model2Course> courses, String search_text) {
        int setting_search_id = rdg_search.getCheckedRadioButtonId();
        ArrayList<Model2Course> imsi_list = new ArrayList<Model2Course>();

        switch (setting_search_id) {
            case R.id.rd_search_pro: { // 교수이름
                for (Model2Course model : courses) {
                    String name = model.getCourseProfessor();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals(search_text) || name.contains(search_text)) {
                        imsi_list.add(model);
                    }
                }
                break;
            }
            case R.id.rd_search_department: { //학과이름
                for (Model2Course model : courses) {
                    String name = model.getCourseMajor();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals(search_text) || name.contains(search_text)) {
                        imsi_list.add(model);
                    }
                }
                break;
            }
            case R.id.rd_search_subject: { // 과목이름
                for (Model2Course model : courses) {
                    String name = model.getCourseName();
                    if (name == null) {
                        continue;
                    }
                    if (name.equals(search_text) || name.contains(search_text)) {
                        imsi_list.add(model);
                    }
                }
                break;
            }

        }

        return imsi_list;
    }


    /*************************
     * 검색조건등으로 만들어진 데이터를
     * ListView에 뿌리는 메서드입니다.
     *
     * @param dataList
     * @throws Exception
     */
    private void setData(ArrayList<Model2Course> dataList) throws Exception {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2000);

        UtilDialog.showToast(Page2LectureSearch.this, dataList.size() + "개의 데이터를 가져왔습니다.");
        adapter = new ArrayAdapter2LectureSearch(Page2LectureSearch.this, R.layout.item_list_search_result, dataList);
        lst_search_result.setAdapter(adapter);
        setListViewHeightBasedOnChildren(lst_search_result);
        adapter.notifyDataSetChanged();
    }

    /**************************
     * 리스트뷰가 화면을 그릴때, 리스트뷰 자식 뷰들의 길이를 구해서 리스트뷰에게알려주는 메서드입니다.
     * 이렇게하면 리스트뷰가 더 깔끔하게 잘 나옵니다.
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public void back(View view) {
        onBackPressed();
    }


    private static String url2 = "http://my.knu.ac.kr/stpo/stpo/cour/listLectPln/list.action?search_open_yr_trm=20172&sub=1F&search_open_crse_cde=1F01";
    private String section_id = "search_open_yr_trm=20172";


    /*****************************
     * 안드로이드에서는 메인 쓰레드안에서 네트워크를 통한 서비스를 할 수 없도록 해놨습니다.
     * 때문에 다른 쓰레드안에서 Network 작업을 해야하는데요
     * 대표적으로 사용되는 AsyncTask를 이용해 크롤링하였습니다.
     */
    class GetSiteInfo extends AsyncTask<Integer, Integer, ArrayList<Model2Course>> {
        private Context context;

        public GetSiteInfo(Context context) {
            this.context = context;
        }

        @Override
        protected ArrayList<Model2Course> doInBackground(Integer... integers) {
            Elements tableRows = null;
            Elements options = null;

            ArrayList<Model2Course> course_list = new ArrayList<Model2Course>();// 테이블 Row데이터를 Model에담아 리스트에 넣는다.
            try {
                Document document = Jsoup.connect(url2).get(); // Jsoup이라는 라이브러리를 이용해서 Doc 형식의 html 파싱데이터를 받아옵니다.
                options = document.select("div > .search > #sub02 > option");
                ArrayList<String> options_value = new ArrayList<>();
                for (Element element : options) {
                    String value = element.attr("value");
                    if (value.equals("")) {
                        continue;
                    }
                    options_value.add(value);
                }

                /////////////// 추가 수정된 부분, 각 검색조건으로 크롤링하여 courses에 추가합니다.
                for (String sub : options_value) {
                    if (sub.equals("")) {
                        continue;
                    }
                    HashMap<String, String> map = ApplicationProperty.getDepartMentHashMap();

                    for (String key : map.keySet()) {
                        if (!key.startsWith(sub)) {
                            continue;
                        }
                        ///////////// sub : 대학명 key : 학과명
                        String url = "http://my.knu.ac.kr/stpo/stpo/cour/listLectPln/list.action?search_open_yr_trm=20172&sub=" + sub + "&search_open_crse_cde=" + key;
                        Document document2 = Jsoup.connect(url).get();
                        tableRows = document2.select("#viewPlans > table > tbody > tr"); // html 태그의 아이디가 viewPlans인 태그 안에 table row 데이터를 선택해서 Elements 로 받아옵니다.
                        int i = 0;// 테이블의 첫번째 Row는 받지않기위해서 쓰는 변수

                        for (Element element : tableRows) {
                            Elements tableColumn = element.children();
                            int column_count = 1;//테이블 각 항목에따라 모델에 set해야하는 부분이 다르므로 구분하기위해 쓰는 변수
                            Model2Course model = new Model2Course();
                            for (Element element1 : tableColumn) {
                                if (i == 0) {
                                    i = 1; //첫번째 행은 건너띄고자 만들었습니다. (제목등이 들어있습니다.)
                                    break;
                                }
                                String text = element1.text();
                                if (text.equals("")) {
                                    Log.d(LOG_TAG, "No Data ::: " + column_count);
                                    column_count++;
                                    continue;
                                }
                                switch (column_count) {//컬럼을 분기하여 model에다가 데이터를 넣습니다.
                                    case 1: {//학년
                                        model.setCourseGrade(text);
                                        break;
                                    }
                                    case 2: {//구분 **
                                        model.setCourseDivide(text);
                                        break;
                                    }
                                    case 3: {//과목아이디 **
                                        model.setCourseID(text);
                                        break;
                                    }
                                    case 4: {//과목이름 **
                                        model.setCourseName(text);
                                        break;
                                    }
                                    case 5: {//학점
                                        model.setCourseCredit((text));
                                        break;
                                    }
                                    case 6: {//이론
                                        model.setCourseTheory((text));
                                        break;
                                    }
                                    case 7: {//실습
                                        model.setCoursePractice(text);
                                        break;
                                    }
                                    case 8: {//교수이름 **
                                        model.setCourseProfessor(text);
                                        break;
                                    }
                                    case 9: {//강의시간 **
                                        model.setCourseTime(text);
                                        break;
                                    }
                                    case 10: {//강의실 **
                                        model.setCourseRoom(text);
                                        break;
                                    }
                                    case 11: {//정원
                                        model.setCourseLimit((text));
                                        break;
                                    }
                                    case 12: {//수강신청한인원
                                        model.setCoursePeople((text));
                                        break;
                                    }
                                    case 13: {//패케지 인원
                                        model.setCoursePackage((text));
                                        break;
                                    }
                                    case 14: {//패케지 가능여부
                                        model.setCoursePackageEnabled(text);
                                        break;
                                    }
                                    case 15: {//비고 **
                                        model.setCourseNote(text);
                                        break;
                                    }
                                }
                                column_count++;
                            }
                            if (i == 1) {
                                i = 2;
                            } else {
                                String str = model.getCourseNote();
                                if (str != null && str.contains("상주")) {
                                    model.setCourseCampus("상주캠퍼스");
                                } else {
                                    model.setCourseCampus("대구캠퍼스");
                                }
                                //////////// 학과이름을 고통적으로 추가해놓습니다.
                                model.setCourseMajor(map.get(key));
                                i++;
                                course_list.add(model);
                            }

                        }

                    }
                }


                Log.d(LOG_TAG, "Complete Data List ::: " + course_list.size());
                ApplicationProperty.LECTURE_TEMP_LIST = course_list;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return course_list;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
        }

        @Override
        protected void onPostExecute(ArrayList<Model2Course> result) {
            if (mProgressDialog != null && mProgressDialog.isShowing()) { // 맨 마지막에 호출되는 메서드이므로 이곳에 다이얼로그 프로그래스바를 dissmiss시키는 코딩을 했습니다.
                mProgressDialog.dismiss();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
