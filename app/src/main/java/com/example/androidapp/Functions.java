package com.example.androidapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AlertDialog;

public class Functions {

    // function to pop up the alertDialog
    public static void createPopup(Context context, String msg){
        AlertDialog alertDialog= new AlertDialog.Builder(context).create();
        alertDialog.setTitle(msg);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.hide();
            }
        });
        alertDialog.show();
    }

    // function to navigate between Activities
    public static void moveToActivity(Context context , Class<Context> cls){
        Intent intent = new Intent(context,cls);
        context.startActivity(intent);
    }
}
