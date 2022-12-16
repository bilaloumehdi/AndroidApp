package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account extends AppCompatActivity {

    DataBaseHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        SessionManagement sessionManagement = new SessionManagement(Account.this);
        db = new DataBaseHelper(Account.this);

        int id = sessionManagement.getSession();
        User user = db.getUserDetails(id) ;



    }
}