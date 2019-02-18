package com.example.lovin.stalphonsachurchdirectory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    private static final String TAG = "AddScreenActivity";
    int index;Info i;
    EditText couple, children, street, city, state, zip, ph, notes;
    Button capturePhoto, save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
        try{
            Intent edit = getIntent();
            String temp = edit.getStringExtra("index");
            index = Integer.valueOf(temp);

            populateField();

        }catch (Exception e)
        {
            Log.d(TAG,"Failed to get intent: "+e.getLocalizedMessage());

        }
        if(savedInstanceState!=null)
        {
            couple.setText(savedInstanceState.getString("couple"));
            children.setText(savedInstanceState.getString("children"));
            ph.setText(savedInstanceState.getString("ph"));
            street.setText(savedInstanceState.getString("street"));
            city.setText(savedInstanceState.getString("city"));
            state.setText(savedInstanceState.getString("state"));
            zip.setText(savedInstanceState.getString("zip"));
            notes.setText(savedInstanceState.getString("notes"));
        }

    }
    public void init(){
        couple = findViewById(R.id.coupleText);
        children = findViewById(R.id.childrenText);
        street = findViewById(R.id.streetText);
        city = findViewById(R.id.cityText);
        state = findViewById(R.id.stateText);
        zip = findViewById(R.id.zipText3);
        ph = findViewById(R.id.phoneText);
        notes = findViewById(R.id.noteText);
        capturePhoto = findViewById(R.id.takePic);
        save = findViewById(R.id.saveButton);

    }

    public void populateField(){
        DBMS db = new DBMS();
        ArrayList<Info> items = db.view(getApplicationContext());
        i= items.get(index);

        couple.setText(i.getCouplename());
        children.setText(i.getChildname());
        street.setText(i.getStreet()+" oooooo");
        city.setText(i.getCity());
        state.setText(i.getState());
        zip.setText(i.getZipcode());
        ph.setText(i.getPh());
        notes.setText(i.getNote());
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDB();
            }
        });
    }

    public void takePic(View view)
    {
        Intent takePicture = new Intent();
        takePicture.setClass(this.getApplicationContext(),CameraActivity.class);
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
        if(TransferData.bitmap!=null)
        {

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            TransferData.bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
            TransferData.bitmap = null;
            i.setPic(stream.toByteArray());
        }
        DBMS db = new DBMS();
        db.addEntry(i);
       //TODO request permission to write to external storage


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString(couple.getText().toString(), "couple");
        outState.putString(children.getText().toString(),"children");
        outState.putString(ph.getText().toString(),"ph");
        outState.putString(street.getText().toString(),"street");
        outState.putString(city.getText().toString(),"city");
        outState.putString(zip.getText().toString(),"zip");
        outState.putString(state.getText().toString(),"state");
        outState.putString(notes.getText().toString(),"notes");
        super.onSaveInstanceState(outState);

    }



    public void updateDB(){
        Info in = new Info();
        in.setId(i.getId());
        in.setCouplename(couple.getText().toString().trim());
        in.setChildname(children.getText().toString().trim());
        in.setStreet(street.getText().toString().trim());
        in.setCity(city.getText().toString().trim());
        in.setState(state.getText().toString().trim());
        in.setZipcode(zip.getText().toString().trim());
        in.setPh(ph.getText().toString().trim());
        in.setNote(notes.getText().toString().trim());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        TransferData.bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        in.setPic(stream.toByteArray());

        DBMS db = new DBMS();
        db.updateEntry(in);



    }
}


        /*

        try {

            final String inFileName = "/data/data/com.example.lovin.stalphonsachurchdirectory/databases/St.Augustine.db";
             String external = Environment.getExternalStorageDirectory().getAbsolutePath();
             String data = Environment.getRootDirectory().toURI().getPath();
             Log.d(TAG,"External:"+external);
             Log.d(TAG,"data:"+data);

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

        }

*/