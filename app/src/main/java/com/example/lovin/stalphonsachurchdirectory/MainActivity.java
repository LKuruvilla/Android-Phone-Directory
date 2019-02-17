package com.example.lovin.stalphonsachurchdirectory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    //global variables
    Button view ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

    }
    public void init(){
       view = findViewById(R.id.button1);


    }
    public void viewList(View view){
        Intent viewScreen = new Intent();
        viewScreen.setClass(this.getApplicationContext(), ViewScreen.class);
        startActivity(viewScreen);
        finish();

    }
}
