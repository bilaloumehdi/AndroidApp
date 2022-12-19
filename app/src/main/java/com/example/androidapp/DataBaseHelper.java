package com.example.androidapp;

import android.annotation.SuppressLint;
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
    public static final String COL_SPORTS= "sports";
    private int _id ;
    private String username ;

    // get the user id to use it in sessions
    public int getUserId(){
        return _id ;
    }

    // to get the username & use it in the User Object creation ;
    public String getUsername() {
        return username;
    }

// TODO: verify the DB table



    public DataBaseHelper(Context context){
        super(context,DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TABLE_NAME+"(_id INTEGER PRIMARY KEY AUTOINCREMENT,username TEXT,email TEXT,password TEXT,phone TEXT ,sports TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //function to check if the user already exists or not --> register
    @SuppressLint("Range")
    public boolean checkUser(String email, String password){
        try{

            SQLiteDatabase db = this.getReadableDatabase();
            String query = "SELECT * FROM "+TABLE_NAME+ " WHERE "
                    +COL_EMAIL +"=?  AND "+COL_PASSWORD+"= ?" ;
            String[] selectionArgs = {email,password};

            Cursor cursor = db.rawQuery(query,selectionArgs);
            if(cursor.getCount() > 0){
                if(cursor.moveToFirst()){

                    _id = cursor.getInt(cursor.getColumnIndex(COL_ID));
                    username = cursor.getString(cursor.getColumnIndex(COL_USERNAME));

                    // closing cursor & db connection before returning
                    cursor.close();
                    db.close();

                    return true ;
                }
            }

        }catch(SQLiteException e ){
            System.out.println("[ERROR]: "+e.getMessage());
            return false ;
        }
        return false ;
    }

    // function to add a new user --> register
    public void addUser(String username,String email, String password,String phone,String sports){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_EMAIL,email);
        contentValues.put(COL_PASSWORD,password);
        contentValues.put(COL_PHONE,phone);
        contentValues.put(COL_SPORTS,sports);

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1) System.out.println("does not inserted ");
        db.close();
    }
    // Update the user information
    // could modify username,password,and sport choices

    public boolean UpdateUser(String username,String password,String sports){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues() ;
        contentValues.put(COL_USERNAME,username);
        contentValues.put(COL_PASSWORD,password);
        contentValues.put(COL_SPORTS,sports);
        int result= db.update(TABLE_NAME,contentValues,"",null);

        if(result == -1) return false ;

        return true ;
    }

    // get the user account information
    @SuppressLint("Range")
    public  User getUserDetails(int _id){
        SQLiteDatabase db = this.getReadableDatabase();

        String query ="SELECT * FROM "+TABLE_NAME +
                " WHERE "+COL_ID +" = ?";

        String[] selectionArgs = {String.valueOf(_id)};
        Cursor cursor = db.rawQuery(query,selectionArgs) ;

        if(cursor.getCount() >0){
            if(cursor.moveToFirst()){
             String username= cursor.getString(cursor.getColumnIndex(COL_USERNAME));
             String email = cursor.getString(cursor.getColumnIndex(COL_EMAIL));
             String password = cursor.getString(cursor.getColumnIndex(COL_PASSWORD));
             String phone = cursor.getString(cursor.getColumnIndex(COL_PHONE));
             String sports = cursor.getString(cursor.getColumnIndex(COL_SPORTS));
             return new User(_id,username,email,password,phone,sports);
            }
        }
        return null ;
    }
}
