package commin.pro.lectureschedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import commin.pro.lectureschedule.dao.DBHelper;
import commin.pro.lectureschedule.dao.Dao2Lecture;
import commin.pro.lectureschedule.page.lecture.Page2Lecture;


public class Application extends AppCompatActivity {

    private final DBHelper db = new DBHelper(Application.this, "lecture.db", null, 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_application);

        Dao2Lecture.setDatabase(db);
        startActivity(new Intent(Application.this, Page2Lecture.class));
        finish();
    }
}
