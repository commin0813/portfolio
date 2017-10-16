package com.commin.pro.lecture.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.Toast;

public class UtilDialog {
    public static void openError(Activity activity, String message, AlertDialog.OnClickListener ok_listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ok_listener)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    public static void openDialog(Activity activity, String message, AlertDialog.OnClickListener ok_listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ok_listener)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    public static void openConfirm(Activity activity, String message, AlertDialog.OnClickListener ok_listener, AlertDialog.OnClickListener cancel_listener) {
        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ok_listener)
                .setNegativeButton(android.R.string.cancel, cancel_listener)
                .setCancelable(false)
                .create();
        alertDialog.show();
    }

    public static void openCustomDialogConfirm(Activity activity, String title, String message, String mLeft, String mRight, UtilCustomDialog.OnClickListener ok_listener, UtilCustomDialog.OnClickListener cancel_listener) {
        new UtilCustomDialog(activity, title, message, mLeft, mRight, ok_listener, cancel_listener).show();
    }

    public static void openInputDialog(Activity activity, EditText input, String message, AlertDialog.OnClickListener ok_listener, AlertDialog.OnClickListener cancel_listener) {


        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ok_listener)
                .setNegativeButton(android.R.string.cancel, cancel_listener)
                .setCancelable(false)
                .setView(input)
                .create();
        alertDialog.show();

    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

}
