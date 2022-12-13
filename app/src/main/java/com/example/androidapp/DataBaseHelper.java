package com.example.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "registerUser.db";
    public static final String TABLE_NAME = "user";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "email";
    public static final String COL_4 = "password";
    public static final String COL_5 = "phone";

    // TODO: verify the DB table



    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"( ID INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    // function to add a new user --> register
    public void addUser(String username,String email, String password,String phone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put("username",username);
        contentValues.put("email",email);
        contentValues.put("password",password);
        contentValues.put("phone",phone);


        db.insert(TABLE_NAME,null,contentValues);
        db.close();
    }

    //function to check if the user already exists or not --> register

    public boolean checkUser(String username, String password){
        try{
            String[] columns = {COL_1};
            SQLiteDatabase db = this.getReadableDatabase();
            String selection = COL_3+ "=? and " +COL_4 + " =?";
            String[] selectionArgs = {username, password} ;

            Cursor cursor = db.query(TABLE_NAME,columns,selection,selectionArgs,null,null,null);
            int count = cursor.getCount();

            // closing cursor & db connection before returning
            cursor.close();
            db.close();

            if(count>0) return true ;
        }catch(SQLiteException e ){
            return false ;
        }
        return false ;
    }


}
