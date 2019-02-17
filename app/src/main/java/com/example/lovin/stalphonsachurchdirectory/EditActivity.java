package com.example.lovin.stalphonsachurchdirectory;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {
    int index;
    ArrayList<Info> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }
    public void init(){
        String temp = getIntent().getStringExtra("index");
        index = Integer.valueOf(temp);
        DBMS db = new DBMS();
        items = db.view(getApplicationContext());


    }
}
