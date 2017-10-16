package com.commin.pro.exerciseproject.page.photo_edit;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.commin.pro.exerciseproject.ApplicationProperty;
import com.commin.pro.exerciseproject.R;
import com.commin.pro.exerciseproject.model.Model2Excercise;
import com.commin.pro.exerciseproject.util.UtilDialog;
import com.commin.pro.exerciseproject.util.UtilImage;

import java.util.Date;

public class Page2PhotoEdit extends AppCompatActivity {
    private static final String LOG_TAG = "Page2PhotoEdit";

    private Button btn_save_edit, btn_add_line, btn_add_text;

    private LinearLayout ll_edit_container;
    private String user_photo_path;
    public static Bitmap user_photo;
    private Date selected_date;
    private EditView editView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_photo_edit_layout);

        createGUI();
    }


    public interface EditHandler {
        void onDrawText(String text);

        void onDrawLine();

        void onSaveBitmap();
    }

    private void createGUI() {

        Model2Excercise model = (Model2Excercise) getIntent().getSerializableExtra("Model2Excercise");
        user_photo_path = model.getUser_photo_path();
        selected_date = model.getDate();

        user_photo = UtilImage.getBitmap(user_photo_path);

        init_elements();
        init_click_handler();
    }

    private void init_elements() {
        editView = new EditView(Page2PhotoEdit.this);

        ll_edit_container = (LinearLayout) findViewById(R.id.ll_edit_container);
        ll_edit_container.addView(editView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        btn_save_edit = (Button) findViewById(R.id.btn_save_edit);
        btn_add_line = (Button) findViewById(R.id.btn_add_line);
        btn_add_text = (Button) findViewById(R.id.btn_add_text);

    }


    private void init_click_handler() {

        btn_add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(Page2PhotoEdit.this);
                UtilDialog.openInputDialog(Page2PhotoEdit.this, input, "텍스트를 입력하세요.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = input.getText().toString();
                        editView.onDrawText(text);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });
        btn_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UtilDialog.showToast(Page2PhotoEdit.this,"사진영역에 그림을 그리세요.");
                editView.onDrawLine();
            }
        });
        btn_save_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editView.onSaveBitmap();
                Bitmap bitmap = user_photo;
                Model2Excercise model = new Model2Excercise();
                model.setDate(selected_date);
                model.setUser_photo_path(UtilImage.getImageCode(Page2PhotoEdit.this, bitmap));
                Intent intent = new Intent();
                intent.putExtra("Model2Excercise", model);
                setResult(ApplicationProperty.RESULT_CODE_FOR_PHOTO_EDIT, intent);
                finish();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        user_photo.recycle();

    }
}
