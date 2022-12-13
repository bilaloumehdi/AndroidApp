package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    TextView signUpText ;
    TextInputLayout email;
    TextInputLayout password ;
    Button loginBtn ;
    //DB
    DataBaseHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // getting the View elements
        email = (TextInputLayout) findViewById(R.id.TextViewLayoutEmail);
        password= (TextInputLayout) findViewById(R.id.TextViewLayoutPassword);
        signUpText = (TextView) findViewById(R.id.TextViewLogin);
        loginBtn = (Button)findViewById(R.id.LoginBtn);
        db = new DataBaseHelper(LoginActivity.this);

        // if the user clicks ---> sign up  activity
        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupActivity = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(signupActivity);
            }
        });


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // getting the username & password values ;
                try{
                    String user = email.getEditText().getText().toString().trim();
                    String passwd = password.getEditText().getText().toString();
                    Boolean isExists = db.checkUser(user,passwd);


                    if(isExists){
                        Toast.makeText(LoginActivity.this,"you r logged in "+email,Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(LoginActivity.this,"the account does not exists",Toast.LENGTH_LONG).show();

                    }
                }catch(SQLiteException e){
                    System.out.println(e.getMessage());
                }


            }
        });
    }
}