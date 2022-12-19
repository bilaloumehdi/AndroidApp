package com.example.androidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class AccountActivity extends AppCompatActivity {
    private static int REQUEST_CODE = 100;
    DataBaseHelper db ;
    SessionManagement sessionManagement ;
    int id ;
    ImageView img;
    CardView natationCard ;
    CardView tennisCard ;
    CardView footballCard ;
    Button btn;

    OutputStream outputStream;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        tennisCard = (CardView) findViewById(R.id.card1);
        natationCard = (CardView) findViewById(R.id.card2);
        footballCard = (CardView) findViewById(R.id.card3);
        img = findViewById(R.id.imageView1);


        TextView tvDateTime = findViewById(R.id.tvDateTime);
        String currentDateTimeString = DateFormat.getDateInstance().format(new Date());
        tvDateTime.setText(currentDateTimeString);

        Button btnToUploadTennis = (Button) findViewById(R.id.btndownload);
        Button btnToUploadSwimming = (Button) findViewById(R.id.btndownload2);
        Button btnToUploadFootBall = (Button) findViewById(R.id.btndownload3);


        sessionManagement = new SessionManagement(AccountActivity.this);
        db = new DataBaseHelper(AccountActivity.this);
        id = sessionManagement.getSession();
        User user = db.getUserDetails(id);

        // display the name of user in top of our interface
        TextView userConnectedUserName;
        userConnectedUserName = findViewById(R.id.UserName);
       userConnectedUserName.setText( userConnectedUserName.getText().toString() +" "+ user.getUsername().toUpperCase(Locale.ROOT).toString());

        String[] sports = user.getSports().split("/");
        displaySports(sports);



       // upload time table for Tennis
        btnToUploadTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = (ImageView)findViewById(R.id.imageView1);
                if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    askPermission();

                }

            }
        });
        // upload time table for Swimming
        btnToUploadSwimming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = (ImageView)findViewById(R.id.imageView2);
                if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    askPermission();

                }

            }
        });

      // upload time table for football
        btnToUploadFootBall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                img = (ImageView)findViewById(R.id.imageView3);
                if (ContextCompat.checkSelfPermission(AccountActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveImage();
                } else {
                    askPermission();

                }

            }
        });

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
               Intent intent = new Intent(AccountActivity.this,SettingsActivity.class);
                startActivity(intent);
                return true ;
            case R.id.logout_option:
                //if click on logout --> destroy session
                // remove the session and open the main activity
                sessionManagement.removeSession();

                Intent it = new Intent(AccountActivity.this, MainActivity.class);
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



  // function to ask  permission for uploading images in user device
  // if the user give us permission the manifest file is changed automatically by adding WRITE_EXTERNAL_STORAGE permission
    private void askPermission() {

        ActivityCompat.requestPermissions(AccountActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

    }

    // if the request  Permission is 100 so we save the image
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                saveImage();

            } else {
                Toast.makeText(AccountActivity.this, "Please provide the required permissions", Toast.LENGTH_SHORT).show();
            }

        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

// function to upload time table image in "/uploadImage/" directory
// after upload  this image is stored in /uploadImage/
// to see the image uploaded  go to   :  setting -> Storage -> other -> uploadImage


    private void saveImage() {

        BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File filepath = Environment.getExternalStorageDirectory();
        File dir = new File(filepath.getAbsolutePath() + "/uploadImage/");

        if (!dir.exists()) {
            dir.mkdir();
        }


        File file = new File(dir, System.currentTimeMillis() + ".jpg");

        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        try {
            outputStream.flush();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }try{
            Toast.makeText(getApplicationContext(), "Downloaded into tableSports.", Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Image is not loaded yet...", Toast.LENGTH_SHORT).show();
        }
    }

}





