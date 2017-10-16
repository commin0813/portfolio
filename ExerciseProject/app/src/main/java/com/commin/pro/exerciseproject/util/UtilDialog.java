package com.commin.pro.exerciseproject.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by user on 2016-03-21.
 */
public class UtilDialog {
    public static void openError(Activity activity, String message, AlertDialog.OnClickListener ok_listener) {
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

    public static void openInputDialog(Activity activity,EditText input, String message, AlertDialog.OnClickListener ok_listener, AlertDialog.OnClickListener cancel_listener){


        AlertDialog alertDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, ok_listener)
                .setNegativeButton(android.R.string.cancel, cancel_listener)
                .setCancelable(false)
                .setView(input)
                .create();
        alertDialog.show();

//        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
//
//        alert.setTitle(title);
//        alert.setMessage(message)
//                .setPositiveButton(android.R.string.ok,)
//
//        // Set an EditText view to get user input
//        final EditText input = new EditText(activity);
//        alert.setView(input);
//
//        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int whichButton) {
//                String value = input.getText().toString();
//                value.toString();
//                // Do something with value!
//            }
//        });
//
//
//        alert.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int whichButton) {
//                        // Canceled.
//                    }
//                });
//
//        alert.show();
    }

    public static void showToast(Activity activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

}
