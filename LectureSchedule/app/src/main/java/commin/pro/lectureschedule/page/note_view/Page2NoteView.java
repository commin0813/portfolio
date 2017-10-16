package commin.pro.lectureschedule.page.note_view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import commin.pro.lectureschedule.R;
import commin.pro.lectureschedule.model.Model2Lecture;


public class Page2NoteView extends AppCompatActivity {
    EditText ed_memo_title, ed_memo;
    Button btn_back_memo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_page_note_view);
        createGUI();
        init_data();
    }

    private void createGUI() {
        ed_memo_title = (EditText) findViewById(R.id.ed_memo_title);
        ed_memo = (EditText) findViewById(R.id.ed_memo);
        btn_back_memo = (Button) findViewById(R.id.btn_back_memo);
        btn_back_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init_data() {
        Model2Lecture model = (Model2Lecture) getIntent().getSerializableExtra("model");
        ed_memo_title.setText(model.getMemo_title().toString());
        ed_memo.setText(model.getMemo().toString());
    }
}
