package commin.pro.lectureschedule.page.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;



import java.util.Random;

import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.dao.Dao2Lecture;
import commin.pro.lectureschedule.model.Model2Lecture;
import commin.pro.lectureschedule.util.UtilDialog;

public class Page2Note extends AppCompatActivity {
    EditText ed_memo_title, ed_memo;
    Button btn_memo_complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_page_note);
        createGUI();

    }

    private void createGUI() {
        ed_memo_title = (EditText) findViewById(R.id.ed_memo_title);
        ed_memo = (EditText) findViewById(R.id.ed_memo);
        btn_memo_complete = (Button) findViewById(R.id.btn_memo_complete);
        btn_memo_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    private boolean checkNull() {
        if (ed_memo_title.getText().toString().equals("") || ed_memo_title.getText().toString().equals(null)) {
            return true;
        }
        if (ed_memo.getText().toString().equals("") || ed_memo.getText().toString().equals(null)) {
            return true;
        }
        return false;
    }

    private void saveData() {
        if (checkNull()) {
            UtilDialog.showToast(Page2Note.this, "값을 모두 입력하세요.");
            return;
        }
        Model2Lecture model = (Model2Lecture) getIntent().getSerializableExtra("model");
        model.setData(true);
        model.setMemo(true);
        model.setMemo_title(ed_memo_title.getText().toString());
        model.setMemo(ed_memo.getText().toString());
        int groupid = new Random().nextInt();
        model.setGroupid(groupid);
        Dao2Lecture.insertDatabaseForMemo(model);
        setResult(RESULT_OK, null);
        finish();


    }
}
