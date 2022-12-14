package com.example.androidapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Club.db";
    public static final String TABLE_NAME = "user";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";
    public static final String COL_PHONE = "phone";

    // TODO: verify the DB table



    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,email TEXT,password TEXT,phone TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //function to check if the user already exists or not --> register
    public boolean checkUser(String email, String password){
        try{

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM "+TABLE_NAME+ " WHERE "
                    +COL_EMAIL +"=?  AND "+COL_PASSWORD+"= ?" ;
            String[] selectionArgs = {email,password};
            System.out.println(email);
            System.out.println(password);

            Cursor cursor = db.rawQuery(query,selectionArgs);
            int count = cursor.getCount();

            // closing cursor & db connection before returning
            cursor.close();
            db.close();

            if(count> 0) return true ;
        }catch(SQLiteException e ){
            System.out.println("[ERROR]: "+e.getMessage());
            return false ;
        }
        return false ;
    }

    // function to add a new user --> register
    public void addUser(String username,String email, String password,String phone){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PASSWORD,password);
        contentValues.put(COL_PHONE,phone);


        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1) System.out.println("does not inserted ");
        db.close();
    }

    // TODO: get the user account information
    public void getUser(int _id){
        String query = "SELECT "+COL_USERNAME +","+COL_PASSWORD +" FROM " ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null ;
    }



}
