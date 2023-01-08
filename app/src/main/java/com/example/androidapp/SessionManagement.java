package com.example.androidapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    public static final String SHARED_PREF_NAME = "session" ;
    public static final String SESSION_KEY = "session_user" ;

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public  void saveSession(User user ){
        int id = user.getId();
        editor.putInt(SESSION_KEY,id).commit();
    }


    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY,-1) ;
    }



    public void removeSession(){
        editor.putInt(SESSION_KEY,-1).commit();
    }

}
