package com.example.androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
    String nameOfSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getSupportActionBar().setTitle(R.string.se_connecter);

        // go back to the previous activity
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //geting data form listview
        Intent intent = this.getIntent();
        if(intent != null){
            // gettign the sports name that was clicked from the user
            nameOfSport = intent.getStringExtra("name");
        }

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
                // send the sports chose by user to signeup view
                signupActivity.putExtra("nameOfSport",nameOfSport);
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
                    boolean isExists = db.checkUser(user,passwd);

                    // session Management
                    SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);

                    if(isExists){

                        // create the user object & Save The session
                        User userObj = new User(db.getUserId(),user);
                        sessionManagement.saveSession(userObj);

                        Toast.makeText(LoginActivity.this," vous êtes connecté ",Toast.LENGTH_LONG).show();

                        // back to the home page
                        Intent homepage = new Intent(LoginActivity.this, AccountActivity.class);
                        startActivity(homepage);

                    }else {
                        // show an pop up --> authentication failed ;
                        Functions.createPopup(LoginActivity.this,"email et/ou mot de passe est incorrect ");
                    }

                }catch(SQLiteException e){
                    System.out.println("[ERROR]: " +e.getMessage());
                }
            }
        });
    }
}