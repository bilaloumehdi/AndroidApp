package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Account extends AppCompatActivity {
    TextView welcomeText ;
    Button logout ;
    DataBaseHelper db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        logout = (Button) findViewById(R.id.logout_btn);
        welcomeText = (TextView) findViewById(R.id.welcomeText);
        SessionManagement sessionManagement = new SessionManagement(Account.this);
        db = new DataBaseHelper(Account.this);

        int id = sessionManagement.getSession();
        User user = db.getUserDetails(id) ;
        welcomeText.setText(" welcome "+user.getUsername()+" ");

        // if click on logout --> destroy session
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // remove the session and open the main activity
                 sessionManagement.removeSession();
                Intent it = new Intent(Account.this, MainActivity0.class);
                startActivity(it);
            }
        });


    }
}