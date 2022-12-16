package com.example.androidapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper  extends SQLiteOpenHelper {


    public DbHelper(Context context) {
        super(context, "stud.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE sport (sid text,name text,imageId int,prix text,empoloi int )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists sport");
        onCreate(sqLiteDatabase);
    }
}





