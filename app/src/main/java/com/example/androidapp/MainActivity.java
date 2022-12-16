package com.example.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    ListView lv;



    @Override
    protected void onStart(){
        super.onStart();
        // check if the user is logged in
        // if the user is logged in move to Account_Activity
        //
        // TODO : uncomment this bloc

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);


        int userID = sessionManagement.getSession();

        if(userID != -1){
            Intent it = new Intent(MainActivity.this,Account.class);
            startActivity(it);
        }
    }




    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        btnLogin = (Button)findViewById(R.id.buttonLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            Intent in = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(in);*/


        // insert sports
   /*     createSports("2","foot-ball","400 mad",R.drawable.listfootback, R.drawable.emploitennis);
        createSports("4","Natation","550 mad",R.drawable.listswiningback2,R.drawable.emploitennis);
        createSports("1","tennis","450 mad",R.drawable.listtennisback,R.drawable.emploitennis);*/



        //System.out.println("row number is " + row);
        //te.setText("row number is " + row);





        // resources
        // from string file and drawable directory

        int[] imageId = {R.drawable.listfootback, R.drawable.listswiningback2, R.drawable.listtennisback};
        String[] name = {"Foot-ball","Natation","Tennis"};
        String[] sid = {String.valueOf(R.string.id1), String.valueOf(R.string.id2), String.valueOf(R.string.id3)};
        String[] prix = {String.valueOf(R.string.prix1), String.valueOf(R.string.prix2), String.valueOf(R.string.prix3)};
        int[] emploi ={R.drawable.footballtble,R.drawable.swimmingtable,R.drawable.tennistable} ;

        // generique list of sports objects
        ArrayList<Sports> userArrayList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Sports sp = new Sports(sid[i],name[i],imageId[i],prix[i],emploi[i]);
            // Sports sp = new Sports(id[i],name[i],imageId[i],prix[i]);
            userArrayList.add(sp);
        }


        LandingAdaptateur listAdapter = new LandingAdaptateur(this, userArrayList);

        lv = (ListView) findViewById(R.id.listView1);
        lv.setAdapter(listAdapter);

        // lv.setClickable(true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                in.putExtra("id", sid[position] );
                in.putExtra("name", name[position]);
                in.putExtra("prix", prix[position]);
                in.putExtra("imageId", imageId[position]);
                in.putExtra("emploi", emploi[position]);
                //System.out.println(name[position] +" clicked");
                startActivity(in);


                //  Toast.makeText(MainActivity.this,"---"+ listAdapter.getItem(position),Toast.LENGTH_SHORT).show();


            }
        });

    }

  /*  public void onlog(View view) {
        Intent in = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(in);
    }*/

/*
    public void createSports (String sid,String name , String prix,int imageId,int emploi){
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("sid",sid);
        values.put("name",name);
        values.put("imageId",imageId);
        values.put("prix",prix);
        values.put("empoloi",emploi);
        long row = db.insert("sport",null,values);

    }


    public ArrayList<Sports>  readAllSports(){
        ArrayList<Sports> userArrayList = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db2 = dbHelper.getReadableDatabase();
        String projection[] ={"sid","name","imageId","prix","empoloi"};
        Cursor c =db2.query("sport",projection,null,null,null,null,null);

        for (int i = 0; i < 3; i++) {
            Sports sp = new Sports(c.getString(i),c.getString(i),c.getInt(i),c.getString(i),c.getInt(i));
           // Sports sp = new Sports(id[i],name[i],imageId[i],prix[i]);
            userArrayList.add(sp);
            c.moveToNext();
        }
        return   userArrayList;

    }*/


}

