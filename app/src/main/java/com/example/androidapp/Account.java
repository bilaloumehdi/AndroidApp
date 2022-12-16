package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends AppCompatActivity {

    DataBaseHelper db ;
    SessionManagement sessionManagement ;
    int id ;
    User user ;

    CardView natationCard ;
    CardView tennisCard ;
    CardView footballCard ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tennisCard = (CardView) findViewById(R.id.card1);
        natationCard = (CardView) findViewById(R.id.card2);
        footballCard = (CardView) findViewById(R.id.card3);

        sessionManagement = new SessionManagement(Account.this);
        db = new DataBaseHelper(Account.this);

        id = sessionManagement.getSession();
        user = db.getUserDetails(id) ;

        String[] sports = user.getSports().split("/");

        displaySports(sports);
    }

    // traitment of the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater  inflater= getMenuInflater();
        inflater.inflate(R.menu.user_account_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
               Intent intent = new Intent(Account.this,SettingsActivity.class);
                startActivity(intent);
                return true ;
            case R.id.logout_option:
                //if click on logout --> destroy session
                // remove the session and open the main activity
                sessionManagement.removeSession();
                Toast.makeText(Account.this,"logout clicked",Toast.LENGTH_SHORT).show();
                Intent it = new Intent(Account.this, MainActivity.class);
                startActivity(it);
                return true ;
        }
        return super.onOptionsItemSelected(item);
    }


    // function to check the visibility and displays the user sports
    public void displaySports(String[] sports){
        for(String sport: sports)
        {
            if (sport.equals("Tennis")) {
                tennisCard.setVisibility(View.VISIBLE);
            }
            if (sport.equals("Foot-ball")) {
                footballCard.setVisibility(View.VISIBLE);
            }
            if (sport.equals("Natation")) {
                natationCard.setVisibility(View.VISIBLE);
            }
        }
    }

}