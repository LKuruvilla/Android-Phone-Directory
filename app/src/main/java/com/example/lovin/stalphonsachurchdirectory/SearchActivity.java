package com.example.lovin.stalphonsachurchdirectory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    Button searchButton;
    EditText searchText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        init();
    }

    private void init(){
        searchButton = findViewById(R.id.searchButton);
        searchText = findViewById(R.id.searchText);

    }
    public void search(View view){
        String s = searchText.getText().toString().trim();
        new searchThread().execute(s);

//        DBMS db = new DBMS();
//        TransferData.array_item =db.searchEntry(s);
//        if(TransferData.array_item.size()==0)
//        {
//            Toast.makeText(this, "No search results!", Toast.LENGTH_SHORT).show();
//        }
//        else
//        {
//            Intent toView = new Intent();
//            toView.setClass(getApplicationContext(),ViewActivity.class);
//            toView.putExtra("key","1");
//            startActivity(toView);
//            finish();
//        }


    }
    private class searchThread extends AsyncTask<String,Integer,Void>
    {
        ProgressDialog progress;
        DBMS db ;

        @Override
        protected void onPreExecute() {
            progress = new ProgressDialog(getApplicationContext());
            progress.setMessage("Searching Contacts..Please Wait...");
            progress.setProgress(20);
            progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progress.setIndeterminate(true);
            progress.show();

        }

        @Override
        protected Void doInBackground(String... voids) {

            db = new DBMS();
            TransferData.array_item =db.searchEntry(voids[0]);
            publishProgress(100);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if(TransferData.array_item.size()==0)
            {
                Toast.makeText(getApplicationContext(), "No search results!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Intent toView = new Intent();
                toView.setClass(getApplicationContext(),ViewActivity.class);
                toView.putExtra("key","1");
                startActivity(toView);
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0]);
            progress.show();
        }
    }
}
