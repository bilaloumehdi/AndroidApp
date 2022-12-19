package com.example.androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;


public class SignUpActivity extends AppCompatActivity {
    TextInputLayout name;
    TextInputLayout email;
    TextInputLayout password;
    TextInputLayout phone;
    CheckBox natationChbx;
    CheckBox tennisChbx;
    CheckBox footballChbx;
    Button btnSignup ;
    TextView textViewLogin ;
    // DB
    DataBaseHelper db ;
    String nameOfSport;
    SessionManagement sessionManagement;
    @Override
    protected void onStart(){
        super.onStart();

        // check if the user is logged in
        // if the user is logged in move to Account_Activity


        sessionManagement = new SessionManagement(SignUpActivity.this);


        int userID = sessionManagement.getSession();

        if(userID != -1){
            Intent it = new Intent(SignUpActivity.this, AccountActivity.class);
            startActivity(it);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // go back to the previous activity option
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // getting the View elements
        name= (TextInputLayout) findViewById(R.id.TextViewLayoutName);
        email = (TextInputLayout) findViewById(R.id.TextViewLayoutEmail);
        password = (TextInputLayout) findViewById(R.id.TextViewLayoutPassword);
        phone= (TextInputLayout) findViewById(R.id.TextViewLayoutPhone);
        natationChbx= (CheckBox) findViewById(R.id.NatationCheckbox);
        tennisChbx= (CheckBox) findViewById(R.id.TennisCheckbox);
        footballChbx= (CheckBox) findViewById(R.id.FootCheckbox);
        btnSignup= (Button) findViewById(R.id.BtnSignup);
        textViewLogin = (TextView) findViewById(R.id.TextViewLogin);
        // DB
        db = new DataBaseHelper(SignUpActivity.this);

        //geting data form listview
        Intent intent = this.getIntent();
        if(intent != null){
            nameOfSport = intent.getStringExtra("nameOfSport");

            switch(nameOfSport){
                case "Natation":
                    natationChbx.setChecked(true);
                    break;
                case "Tennis":
                    tennisChbx.setChecked(true);
                    break;
                case "Foot-ball":
                    footballChbx.setChecked(true);
                    break;
            }
        }
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user =  name.getEditText().getText().toString().trim();
                    String mail =  email.getEditText().getText().toString().trim();
                    String psswd =  password.getEditText().getText().toString().trim();
                    String mobile =  phone.getEditText().getText().toString().trim();
                    // getting the sports used
                    String sports= "";
                    if(tennisChbx.isChecked()){
                        sports = sports + "Tennis/";
                    }
                    if(footballChbx.isChecked()){
                        sports = sports + "Foot-ball/";
                    }
                    if(natationChbx.isChecked()){
                        sports = sports + "Natation/";
                    }
                        SessionManagement sessionManagement = new SessionManagement(SignUpActivity.this);
                    //check if the user exists already or not
                    if(!db.checkUser(mail,psswd)){

                         db.addUser(user,mail,psswd,mobile,sports);

                        // verify if the user is added to db && for assign the user id to the id attribut (in checkUser())
                        if(db.checkUser(mail,psswd)){
                            User userObjet = new User(db.getUserId(),mail);
                            sessionManagement.saveSession(userObjet);

                            Toast.makeText(SignUpActivity.this,"vous avez bien s'identifier",Toast.LENGTH_SHORT).show();

                            // go to account activity
                            Intent accountIntent = new Intent(SignUpActivity.this, AccountActivity.class);
                            startActivity(accountIntent);
                        }else{
                            Functions.createPopup(SignUpActivity.this,"une erreur si produit , ressayez plus tard.");
                        }

                    }else{
                        // show an pop up --> account already exists ;
                        Functions.createPopup(SignUpActivity.this,"Le compte existe déja");
                    }

                }catch(SQLiteException e){
                    // show an pop up --> there is an exception  ;
                    Functions.createPopup(SignUpActivity.this,"une erreur si produit , merci de ressayer plus tard ");
                }

            }
        });

        // if the user clicks the "se connecter" text --> return to the login LoginActivity
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
}