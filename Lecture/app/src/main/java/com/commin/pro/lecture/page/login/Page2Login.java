package com.commin.pro.lecture.page.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.model.Model2User;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.page.lecture.Page2Lecture;
import com.commin.pro.lecture.page.sign_up.Page2SignUp;
import com.commin.pro.lecture.util.DBException;
import com.commin.pro.lecture.util.UtilDialog;
import com.commin.pro.lecture.util.UtilShare;


public class Page2Login extends AppCompatActivity {
    EditText ed_login_user_password, ed_login_user_id;
    Page2LoginAdvisor advisor = null;
    CheckBox cb_user_login_check = null;

    public SharedPreferences.Editor editor;
    public SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        loadPreferences();
        init();
    }

    private void init() {
        ed_login_user_password = (EditText) findViewById(R.id.ed_login_user_password);
        ed_login_user_id = (EditText) findViewById(R.id.ed_login_user_id);
        if(sharedPreferences.getBoolean(UtilShare.KEY_VALUE_ISCHECKED,false)){
            ed_login_user_id.setText(sharedPreferences.getString(UtilShare.KEY_VALUE_LOGINED_USER_ID,""));
            ed_login_user_password.setText(sharedPreferences.getString(UtilShare.KEY_VALUE_LOGINED_USER_PASS,""));
        }

        cb_user_login_check = (CheckBox)findViewById(R.id.cb_user_login_check);
        cb_user_login_check.setChecked(sharedPreferences.getBoolean(UtilShare.KEY_VALUE_ISCHECKED,false));
        advisor = new Page2LoginAdvisor();
    }

    /**********************
     * 회원가입 버튼을 누르면 실행되는 메서드
     * @param view
     */
    public void sign_up(View view) {
        startActivityForResult(new Intent(Page2Login.this, Page2SignUp.class), ApplicationProperty.REQUEST_CODE_SIGN_UP);
    }

    private String checkInvalid() {
        if (ed_login_user_id.getText().toString().equals("")) {
            return "User ID를 입력해주세요";
        }

        if (ed_login_user_password.getText().toString().equals("")) {
            return "Password를 입력해주세요";
        }

        return "OK";
    }

    /**********************
     * Shared Preference를 사용해서 설정정보들을 파일로 저장시켜둡니다. 그래야
     * 껐다켜도 로그인을 그대로 유지시킬 수 있고 설정정보들도 그대로 유지시켜둘 수 있습니다.
     * @param view
     */
    public void login(View view) {
        try {
            String message = checkInvalid();
            if (!message.equals("OK")) {
                throw new DBException(message);
            }
            boolean islogin = advisor.login(ed_login_user_id.getText().toString(), ed_login_user_password.getText().toString());
            if (islogin) {
                UtilDialog.showToast(Page2Login.this, "로그인 성공");

                ApplicationProperty.isLogined = true;
                Model2User model = new Model2User();
                model.setUser_id(ed_login_user_id.getText().toString());
                model.setUser_password(ed_login_user_password.getText().toString());
                boolean isChecked = cb_user_login_check.isChecked();
                if(isChecked){
                    loadPreferences();
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_ISLOGINED, String.valueOf(true), UtilShare.BOOL_TYPE);
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_ISCHECKED, String.valueOf(isChecked), UtilShare.BOOL_TYPE);
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_LOGINED_USER_ID, String.valueOf(model.getUser_id()), UtilShare.STRING_TYPE);
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_LOGINED_USER_PASS, String.valueOf(model.getUser_password()), UtilShare.STRING_TYPE);
                }else{
                    UtilShare.savePreferences(editor, UtilShare.KEY_VALUE_ISCHECKED, String.valueOf(isChecked), UtilShare.BOOL_TYPE);
                    UtilShare.removePreferences(editor,UtilShare.KEY_VALUE_ISLOGINED);
                    UtilShare.removePreferences(editor,UtilShare.KEY_VALUE_LOGINED_USER_ID);
                    UtilShare.removePreferences(editor,UtilShare.KEY_VALUE_LOGINED_USER_PASS);
                    editor.clear();
                }

                ed_login_user_id.setText("");
                ed_login_user_password.setText("");

                ApplicationProperty.model2User = model; // static으로 계속해서 저장해둘려고 로그인이완료되는 시점에 model을 만들어 넣어둡니다.

                startActivity(new Intent(Page2Login.this, Page2Lecture.class));
                finish();

            }
        } catch (Exception e) {
            UtilDialog.openError(Page2Login.this, e.getMessage(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
        }
    }

    public void back(View view) {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ApplicationProperty.RESULT_CODE_SIGN_UP_OK: {
                UtilDialog.openDialog(Page2Login.this, "로그인해주세요.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ed_login_user_id.requestFocus();
                        return;
                    }
                });

                break;
            }
        }
    }

    private void loadPreferences() {
        sharedPreferences = UtilShare.getSharedPreferences(UtilShare.SAHRE_USER, Page2Login.this);
        editor = UtilShare.getEditor(sharedPreferences);
    }
}
