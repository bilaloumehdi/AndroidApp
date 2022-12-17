package com.example.androidapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SettingsActivity extends AppCompatActivity {
    TextInputEditText username;
    TextInputEditText oldPassword;
    TextInputEditText newPassword;
    TextInputEditText confirmPassword;
    CheckBox natationChbx;
    CheckBox tennisChbx;
    CheckBox footballChbx;
    Button saveBtn ;
    Button cancelBtn ;
    String sports ;
    // DB
    DataBaseHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // go back to the previous activity
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        username = (TextInputEditText) findViewById(R.id.textInputName);
        oldPassword = (TextInputEditText) findViewById(R.id.textInputOldPsswd);
        newPassword = (TextInputEditText) findViewById(R.id.textInputNewPsswd);
        confirmPassword = (TextInputEditText) findViewById(R.id.textInputConfirmPsswd);
        saveBtn = (Button)findViewById(R.id.Btn_save);
        cancelBtn =(Button)findViewById(R.id.Btn_cancel);
        natationChbx = (CheckBox) findViewById(R.id.NatationCheckbox);
        tennisChbx = (CheckBox) findViewById(R.id.TennisCheckbox);
        footballChbx = (CheckBox) findViewById(R.id.FootCheckbox);

        db = new DataBaseHelper(SettingsActivity.this);
        SessionManagement sessionManagement = new SessionManagement(SettingsActivity.this) ;
        int id = sessionManagement.getSession();

        User user = db.getUserDetails(id);

        username.setText(user.getUsername());
        sports = user.getSports();
        String[] sportsArray = sports.split("/");
        for(String sport : sportsArray){
            if(sport.equals("Tennis")) tennisChbx.setChecked(true);
            if(sport.equals("Foot-ball")) footballChbx.setChecked(true);
            if(sport.equals("Natation")) natationChbx.setChecked(true);
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usernameStr = username.getText().toString();
                String oldPasswordStr = oldPassword.getText().toString();
                String newPasswordStr = newPassword.getText().toString();
                String confirmPasswordStr = confirmPassword.getText().toString();
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

                if(usernameStr.equals("") || (newPasswordStr.equals("") && oldPasswordStr.equals(user.getPassword()))){
                    Functions.createPopup(SettingsActivity.this, "il faut remplir les champs ");
                } else if(!oldPasswordStr.equals(user.getPassword()) && !oldPasswordStr.equals("") ){
                    Functions.createPopup(SettingsActivity.this, "l'ancien mot de passe est incorrect");
                }else if(!newPasswordStr.equals(confirmPasswordStr)){
                    Functions.createPopup(SettingsActivity.this,"le nouveau mot de passe et le confirmé sont differents ");
                }else{
                    if(db.UpdateUser(usernameStr,confirmPasswordStr,sports)){
                        Toast.makeText(SettingsActivity.this,"vos donnez sont modifiés",Toast.LENGTH_SHORT);
                        Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                        startActivity(intent);
                    }else{
                        Functions.createPopup(SettingsActivity.this,"une erreur si produit, merci d'essayer ultirièrement ");
                    }
                }

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingsActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }

}