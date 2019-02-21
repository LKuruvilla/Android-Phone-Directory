package com.example.lovin.stalphonsachurchdirectory;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ImageView add, edit, delete, prev, nex, search;
    TextView couple, children, ph, address, notes;
    ImageView img;
    ArrayList<Info> items;
    int count, index, current, id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        init();
    }

    public void init() {
        add = findViewById(R.id.addButton);
        edit = findViewById(R.id.editButton);
        delete = findViewById(R.id.deleteButton);
        prev = findViewById(R.id.prevButton);
        nex = findViewById(R.id.nextButton);
        couple = findViewById(R.id.coupleText);
        children = findViewById(R.id.childrenText);
        ph = findViewById(R.id.phoneText);
        address = findViewById(R.id.addressText);
        notes = findViewById(R.id.notesText);
        img = findViewById(R.id.imgView);
        search = findViewById(R.id.searchButton);


        Drawable d = getResources().getDrawable(R.drawable.no_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();


        try {

            String key = getIntent().getStringExtra("key");
            key.matches("1");
            new previewThread().execute();

        } catch (Exception e) {
            new viewThread().execute();
        }



    }

    private class previewThread extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            previewData();
        }
    }

    private class viewThread extends AsyncTask<Void,Integer,Void>{
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... voids) {

            DBMS db = new DBMS();
            //DBMS.ref = bitmapdata;
            publishProgress(50);
            TransferData.array_item = db.view(getApplicationContext());
            publishProgress(100);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            previewData();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exiting App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    public void search(View view) {
        Intent search = new Intent();
        search.setClass(getApplicationContext(), SearchActivity.class);
        startActivity(search);
        finish();


    }

    public void previewData() {
        if (TransferData.array_item.isEmpty()) {
            Toast.makeText(this, "No data yet, please add people", Toast.LENGTH_SHORT).show();
            return;
        } else {
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            count = TransferData.array_item.size();
            current = index = 0;
            Info i = TransferData.array_item.get(index);
            id = i.getId();
            couple.setText(i.getCouplename());
            children.setText(i.getChildname());
            ph.setText(i.getPh());
            address.setText(i.getStreet() + " " + i.getCity() + " " + i.getState() + " " + i.getZipcode());
            notes.setText(i.getNote());
            //
            if(i.getPic()==null)
            {
                img.setImageResource(R.drawable.no_image);
            }
            else
                img.setImageBitmap(getImage(i.getPic()));
            if (TransferData.array_item.size() > 1) {
                nex.setVisibility(View.VISIBLE);
                prev.setVisibility(View.VISIBLE);
            }
            index++;


        }

    }

    public void next(View view) {

        Info i = TransferData.array_item.get(index);
        current = index;
        id = i.getId();
        index++;
        couple.setText(i.getCouplename());
        children.setText(i.getChildname());
        ph.setText(i.getPh());
        address.setText(i.getStreet() + " " + i.getCity() + " " + i.getState() + " " + i.getZipcode());
        notes.setText(i.getNote());
        if(i.getPic()==null)
        {
            img.setImageResource(R.drawable.no_image);

        }
        else
            img.setImageBitmap(getImage(i.getPic()));

        if (index == count)
            index = 0;
    }

    public void previous(View view) {

        if (index == 0)
            index = count - 1;
        else
            index--;

        Info i = TransferData.array_item.get(index);
        id = i.getId();
        current = index;

        couple.setText(i.getCouplename());
        children.setText(i.getChildname());
        ph.setText(i.getPh());
        address.setText(i.getStreet() + " " + i.getCity() + " " + i.getState() + " " + i.getZipcode());
        notes.setText(i.getNote());
        if(i.getPic()==null)
        {
            img.setImageResource(R.drawable.no_image);
        }
        else
            img.setImageBitmap(getImage(i.getPic()));

    }

    public Bitmap getImage(byte[] image) {
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        } catch (Exception e) {
            return null;
        }
        return bitmap;
    }

    public void deleteEntry(View view) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deleting Contact")
                .setMessage("Are you sure you want to delete this contact")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBMS db = new DBMS();
                        db.deleteEntry(id);
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        Toast.makeText(ViewActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                    }

                })
                .setNegativeButton("No", null)
                .show();



    }

    public void addEntry(View view) {
        Intent addScreen = new Intent();
        addScreen.setClass(this.getApplicationContext(), AddActivity.class);
        startActivity(addScreen);
        finish();
    }

    public void editEntry(View view) {
        Intent editScreen = new Intent();
        editScreen.setClass(this.getApplicationContext(), AddActivity.class);
        editScreen.putExtra("index", String.valueOf(current));
        startActivity(editScreen);

    }

    public void maps(View view) {
        Uri location = Uri.parse("geo:0,0?q=" + Uri.encode(address.getText().toString().trim()));
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(location);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void call(View view) {


        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + ph.getText().toString().trim()));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);

    }

}
