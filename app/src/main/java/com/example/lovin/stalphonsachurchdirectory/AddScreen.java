package com.example.lovin.stalphonsachurchdirectory;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class AddScreen extends AppCompatActivity {

    EditText couple, children, street, city, state, zip, ph, notes;
    Button capturePhoto, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_screen);
        init();
    }
    public void init(){
        couple = findViewById(R.id.coupleText);
        children = findViewById(R.id.childrenText);
        street = findViewById(R.id.stateText);
        city = findViewById(R.id.cityText);
        state = findViewById(R.id.stateText);
        zip = findViewById(R.id.zipText3);
        ph = findViewById(R.id.phoneText);
        notes = findViewById(R.id.noteText);
        capturePhoto = findViewById(R.id.takePic);
        save = findViewById(R.id.saveButton);

    }
    public void takePic(View view)
    {
        Intent takePicture = new Intent();
        takePicture.setClass(this.getApplicationContext(), PreviewPhotoScreen.class);
        startActivity(takePicture);
    }
    public void save(View view)
    {
        Info i = new Info();
        i.setCouplename(couple.getText().toString().trim());
        i.setChildname(children.getText().toString().trim());
        i.setStreet(street.getText().toString().trim());
        i.setCity(city.getText().toString().trim());
        i.setState(state.getText().toString().trim());
        i.setZipcode(zip.getText().toString().trim());
        i.setPh(ph.getText().toString().trim());
        i.setNote(notes.getText().toString().trim());

        //convert image to byte array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TransferData.bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        i.setPic(stream.toByteArray());
        DBMS db = new DBMS(this,null,null,1);
        db.addEntry(i);
       //TODO request permission to write to external storage



        try {

            final String inFileName = "/data/data/com.example.lovin.stalphonsachurchdirectory/databases/St.Augustine.db";

            File dbFile = new File(inFileName);
            FileInputStream fis = new FileInputStream(dbFile);

            String outFileName = Environment.getExternalStorageDirectory() + "/St.Augustinecopy.db";

            // Open the empty db as the output stream
            OutputStream output = new FileOutputStream(outFileName);

            // Transfer bytes from the inputfile to the outputfile
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }

            // Close the streams
            output.flush();
            output.close();
            fis.close();
        }catch (Exception e)
        {
            Log.d("AddScreen", e.getLocalizedMessage());
        }


    }


    public static void saveDB(){

    }
}
