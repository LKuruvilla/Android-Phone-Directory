package com.example.lovin.stalphonsachurchdirectory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewScreen extends AppCompatActivity {
    Button add,edit,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_screen);
        init();
    }
    public void init(){
        add = findViewById(R.id.addButton);
        edit = findViewById(R.id.editButton);
        delete=findViewById(R.id.deleteButton);

    }
    public void addEntry(View view){
        Intent addScreen = new Intent();
        addScreen.setClass(this.getApplicationContext(),AddScreen.class);
        startActivity(addScreen);
        finish();
    }
}
