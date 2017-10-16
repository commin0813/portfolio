package com.commin.pro.lecture.page.sign_up;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.commin.pro.lecture.R;
import com.commin.pro.lecture.page.ApplicationProperty;
import com.commin.pro.lecture.util.UtilDialog;

/*************
 * 해당 액티비티는 회원가입을 위한 액티비티입니다.
 */
public class Page2SignUp extends AppCompatActivity {

    Page2SignUpAdvisor advisor = null;
    EditText ed_user_id, ed_user_password, ed_user_password_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_layout);
        init();
    }

    /*******
     * 자원을 초기화해주는 메서드입니다.
     */
    private void init() {
        advisor = new Page2SignUpAdvisor();
        ed_user_id = (EditText) findViewById(R.id.ed_user_id);
        ed_user_password = (EditText) findViewById(R.id.ed_user_password);
        ed_user_password_confirm = (EditText) findViewById(R.id.ed_user_password_confirm);

    }

    /***************************
     * 버튼을 누르면 실행되는 메서드로
     * advisor를 통해 유저를 생성하고 로그인화면으로 넘어가게됩니다.
     * 실패시에는 다이얼로그를 띄우고 그대로 있게됩니다.
     * @param view
     */
    public void sign_up(View view) {
        String err_msg = checkInvalid();
        if (err_msg.equals("OK")) {
            try {
                advisor.cretaeUser(ed_user_id.getText().toString(), ed_user_password.getText().toString());
                setResult(ApplicationProperty.RESULT_CODE_SIGN_UP_OK);
                finish();

            } catch (Exception e) {
                UtilDialog.openError(Page2SignUp.this, e.getMessage(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        return;
                    }
                });
            }

        } else {
            UtilDialog.openError(Page2SignUp.this, err_msg, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    return;
                }
            });
        }

    }

    /**********************
     * 아이디와 비밀번호의 무결성을 검사합니다.
     * @return
     */
    private String checkInvalid() {
        if (ed_user_id.getText().toString().equals("")) {
            return "User ID를 입력해주세요";
        }

        if (ed_user_password.getText().toString().equals("")) {
            return "Password를 입력해주세요";
        }

        if (ed_user_password_confirm.getText().toString().equals("")) {
            return "Password Confirm을 입력해주세요";
        }

        if (!ed_user_password.getText().toString().equals(ed_user_password_confirm.getText().toString())) {
            return "Password가 서로 다릅니다.";
        }

        return "OK";
    }

    public void back(View view) {
        onBackPressed();
    }
}
