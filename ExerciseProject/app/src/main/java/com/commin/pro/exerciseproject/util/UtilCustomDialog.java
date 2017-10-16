package com.commin.pro.exerciseproject.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.commin.pro.exerciseproject.R;


/**
 * Created by user on 2016-04-19.
 */
public class UtilCustomDialog extends Dialog {

    private static UtilCustomDialog utilCustomDialog;

    private TextView tv_custom_dialog_title;

    private TextView tv_custom_dialog_content;

    private Button btn_custom_dialog_ok;

    private Button btn_custom_dialog_cancel;

    private OnClickListener onClickListener_ok;

    private OnClickListener onClickListener_cancel;

    private String mTitle;

    private String mContent;

    private boolean isConfirmDialog = false;

    private String mLeft = "";

    private String mRight = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        if (isConfirmDialog) {
            setContentView(R.layout.util_custom_dialog_confirm);
        }
        setLayout();
        setTitle(mTitle);
        setContent(mContent);
        setClickListener(onClickListener_ok, onClickListener_cancel);

    }

    public UtilCustomDialog(Context context) {
        // Dialog 배경을 투명 처리 해준다.
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    public UtilCustomDialog(Context context, String mTitle, String mContent, OnClickListener onClickListenerOk, OnClickListener onClickListener_cancel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        utilCustomDialog = this;
        isConfirmDialog = true;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.onClickListener_ok = onClickListenerOk;
        this.onClickListener_cancel = onClickListener_cancel;

    }


    public UtilCustomDialog(Context context, String mTitle, String mContent, String mLeft, String mRight, OnClickListener onClickListenerOk, OnClickListener onClickListener_cancel) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        utilCustomDialog = this;
        isConfirmDialog = true;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mLeft = mLeft;
        this.mRight = mRight;
        this.onClickListener_ok = onClickListenerOk;
        this.onClickListener_cancel = onClickListener_cancel;

    }

    public UtilCustomDialog(Context context, String title, String content, OnClickListener onClickListenerOk) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        utilCustomDialog = this;
        isConfirmDialog = false;
        this.mTitle = title;
        this.mContent = content;
        this.onClickListener_ok = onClickListenerOk;
    }


    private void setTitle(String title) {
        tv_custom_dialog_title.setText(title);
    }

    private void setContent(String content) {
        tv_custom_dialog_content.setText(content);
    }

    private void setClickListener(OnClickListener onClickListenerOk, OnClickListener onClickListenerCancel) {
        if (onClickListenerOk != null && onClickListenerCancel != null) {
            btn_custom_dialog_ok.setOnClickListener(onClickListenerOk);
            btn_custom_dialog_cancel.setOnClickListener(onClickListenerCancel);
        } else if (onClickListenerOk != null && onClickListenerCancel == null) {
            btn_custom_dialog_ok.setOnClickListener(onClickListenerOk);
        } else {

        }
    }

    private void setLayout() {
        tv_custom_dialog_title = (TextView) findViewById(R.id.tv_custom_dialog_title);

        tv_custom_dialog_content = (TextView) findViewById(R.id.tv_custom_dialog_content);

        btn_custom_dialog_ok = (Button) findViewById(R.id.btn_custom_dialog_confirm);

        btn_custom_dialog_cancel = (Button) findViewById(R.id.btn_custom_dialog_cancel);

        if (!(mRight.equals("") && mLeft.equals(""))) {
            btn_custom_dialog_ok.setText(mLeft);
            btn_custom_dialog_cancel.setText(mRight);
        }
    }


    public static abstract class OnClickListener implements View.OnClickListener {
        Handler handler = new Handler();

        @Override
        public void onClick(View v) {
            onClick();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    utilCustomDialog.dismiss();
                }
            });
        }

        public abstract void onClick();
    }


}
