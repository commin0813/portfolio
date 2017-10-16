package com.commin.pro.exerciseproject.page.menu_list;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.dao.Dao2Excercise;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.model.Model2Menu;
import com.commin.pro.exerciseproject.page.calendar.Page2Calendar;
import com.commin.pro.exerciseproject.page.do_excercise_beginner.Page2DoExcerciseBegin;
import com.commin.pro.exerciseproject.page.do_excercise_expert.Page2DoExcerciseExpert;
import com.commin.pro.exerciseproject.page.game.Page2Game;
import com.commin.pro.exerciseproject.page.photo.Page2Photo;
import com.commin.pro.exerciseproject.util.UtilCustomDialog;
import com.commin.pro.exerciseproject.util.UtilDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/*
메뉴를 구성하는 Activity 입니다.
Adapter에서 그려주는 List에따라 변경되어 화면에 출력됩니다.
 */
public class Page2MenuList extends AppCompatActivity {
    private static final String LOG_TAG = "Page2MenuList";
    private final int ITEM_SELECT_EXCERCIZE = 0;
    private final int ITEM_SELECT_CALENDAR = 1;
    private final int ITEM_SELECT_GAME = 2;
    private final int ITEM_SELECT_PHOTO = 3;

    private ListView listView;
    private ArrayList<Model2Menu> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_list_layout);
        createGUI();
    }

    private void createGUI() {
        createList();
    }

    private void createList() {
        listView = (ListView) findViewById(R.id.lst_menu);
        listView.setDivider(null);
        items = new ArrayList<Model2Menu>();


        final String[] menus = getResources().getStringArray(R.array.menu);
        LinkedList<Drawable> icons = new LinkedList<Drawable>();
        icons.add(ContextCompat.getDrawable(this, R.drawable.url));
        icons.add(ContextCompat.getDrawable(this, R.drawable.documents));
        icons.add(ContextCompat.getDrawable(this, R.drawable.earings));
        icons.add(ContextCompat.getDrawable(this, R.drawable.camera));


        for (String menu : menus) {
            Model2Menu model = new Model2Menu();
            model.setIcon(icons.pop());
            model.setMenu_text(menu);
            items.add(model);
        }

        Adapter2MenuList adapter = new Adapter2MenuList(Page2MenuList.this, R.layout.page_list_item, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case ITEM_SELECT_EXCERCIZE: {
                        //Util 클래스들은 제가 사용하려고 만들어놓은 클래스들입니다. 선물로드릴게요.
                        UtilDialog.openCustomDialogConfirm(Page2MenuList.this, "난이도선택", "난이도를선택하세요", "고급", "초급", new UtilCustomDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                startActivityForResult(new Intent(Page2MenuList.this, Page2DoExcerciseExpert.class), ApplicationProperty.REQUEST_CODE_FOR_EXCERCIZE_EXPERT);
                            }
                        }, new UtilCustomDialog.OnClickListener() {
                            @Override
                            public void onClick() {
                                startActivityForResult(new Intent(Page2MenuList.this, Page2DoExcerciseBegin.class), ApplicationProperty.REQUEST_CODE_FOR_EXCERCIZE_BEGINNER);
                            }
                        });

                        break;
                    }
                    case ITEM_SELECT_CALENDAR: {
                        startActivity(new Intent(Page2MenuList.this, Page2Calendar.class));
                        break;
                    }
                    case ITEM_SELECT_GAME: {
                        startActivity(new Intent(Page2MenuList.this, Page2Game.class));
                        break;
                    }
                    case ITEM_SELECT_PHOTO: {
                        startActivity(new Intent(Page2MenuList.this, Page2Photo.class));
                        break;
                    }

                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ApplicationProperty.RESULT_CODE_FOR_EXCERCIZE_BEGINNER: {
                Model2Excercise model = (Model2Excercise) data.getSerializableExtra("Model2Excercise");
                checkExcercise(model);

                break;
            }
            case ApplicationProperty.RESULT_CODE_FOR_EXCERCIZE_EXPERT: {
                Model2Excercise model = (Model2Excercise) data.getSerializableExtra("Model2Excercise");
                checkExcercise(model);
                break;
            }
        }
    }

    private void checkExcercise(Model2Excercise model) {
        if (Dao2Excercise.getHashMap().isEmpty()) {
            Dao2Excercise.insertModel(model);
            Log.d(LOG_TAG, "complete insertModel in first if \n" + model.getCheck() + "---" + model.getDate() + "----" + model.isBeginner());
            return;
        }


        for (Date date : Dao2Excercise.getHashMap().keySet()) {
            if (date.getYear() == model.getDate().getYear()//
                    && date.getMonth() == model.getDate().getMonth()//
                    && date.getDay() == model.getDate().getDay()) //
            {

                Dao2Excercise.updateModel(date, model);
                Log.d(LOG_TAG, "complete updateModel in second if \n" + model.getCheck() + "---" + model.getDate() + "----" + model.isBeginner());

//                if (Dao2Excercise.getHashMap().get(date).isBeginner() == model.isBeginner()) {//need update
//                    Dao2Excercise.updateModel(date, model);
//                    Log.d(LOG_TAG, "complete updateModel in second if \n" + model.getCheck() + "---" + model.getDate() + "----" + model.isBeginner());
//                } else {//need insert
//                    Dao2Excercise.insertModel(model);
//                    Log.d(LOG_TAG, "complete insertModel in third if \n" + model.getCheck() + "---" + model.getDate() + "----" + model.isBeginner());
//                }

                return;
            }
        }

        Dao2Excercise.insertModel(model);
        Log.d(LOG_TAG, "complete insertModel in last line \n" + model.getCheck() + "---" + model.getDate() + "----" + model.isBeginner());
        return;
    }

}
