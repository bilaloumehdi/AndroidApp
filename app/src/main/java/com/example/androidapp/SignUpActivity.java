package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String user =  name.getEditText().getText().toString().trim();
                    String mail =  email.getEditText().getText().toString().trim();
                    String psswd =  password.getEditText().getText().toString().trim();
                    String mobile =  phone.getEditText().getText().toString().trim();

                    //check if the user exists already or not
                    if(!db.checkUser(user,psswd)){
                        db.addUser(user,mail,psswd,mobile);
                        Toast.makeText(SignUpActivity.this,"vous avez bien s'identifier",Toast.LENGTH_LONG);
                    }else{
                        Toast.makeText(SignUpActivity.this,"le compte s'existe déja  ",Toast.LENGTH_LONG);
                    }

                }catch(SQLiteException e){
                    Toast.makeText(SignUpActivity.this,"une erreur si produit , merci de ressayer plus tard",Toast.LENGTH_LONG);
                }

            }
        });

        // if the user clicks the "se connecter" text --> return to the login activitytextViewLogin
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }

}