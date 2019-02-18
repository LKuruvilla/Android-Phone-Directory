package com.example.lovin.stalphonsachurchdirectory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
    Button add,edit,delete, prev, nex, search;
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

    public void init(){
        add = findViewById(R.id.addButton);
        edit = findViewById(R.id.editButton);
        delete=findViewById(R.id.deleteButton);
        prev = findViewById(R.id.prevButton);
        nex = findViewById(R.id.nextButton);
        couple = findViewById(R.id.coupleText);
        children = findViewById(R.id.childrenText);
        ph = findViewById(R.id.phoneText);
        address = findViewById(R.id.addressText);
        notes = findViewById(R.id.notesText);
        img = findViewById(R.id.imgView);
        search = findViewById(R.id.searchButton);


        Drawable d =getResources().getDrawable(R.drawable.no_image); // the drawable (Captain Obvious, to the rescue!!!)
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();


        try
        {

            String key = getIntent().getStringExtra("key");
            key.matches("1");

        }catch (Exception e)
        {
            DBMS db = new DBMS();
            DBMS.ref = bitmapdata;
            TransferData.array_item = db.view(getApplicationContext());
        }




        previewData();
    }

    public void search(View view){
        Intent search = new Intent();
        search.setClass(getApplicationContext(), SearchActivity.class);
        startActivity(search);
        finish();


    }

    public void previewData(){
        if(TransferData.array_item.isEmpty()) {
            Toast.makeText(this, "No data yet, please add people", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {       edit.setVisibility(View.VISIBLE);
                delete.setVisibility(View.VISIBLE);
                count = TransferData.array_item.size();
                current =index =0;
                Info i = TransferData.array_item.get(index);
                id=i.getId();
                couple.setText(i.getCouplename());
                children.setText(i.getChildname());
                ph.setText(i.getPh());
                address.setText(i.getStreet()+" "+i.getCity()+" "+i.getState()+ " "+i.getZipcode());
                notes.setText(i.getNote());
                img.setImageBitmap(getImage(i.getPic()));
                if(TransferData.array_item.size()>1) {
                    nex.setVisibility(View.VISIBLE);
                    prev.setVisibility(View.VISIBLE);
                }
                index++;





        }

    }

    public void next(View view){
        Toast.makeText(this, "Value of index:"+index, Toast.LENGTH_SHORT).show();
        Info i = TransferData.array_item.get(index);
        current = index;
        id=i.getId();
        index++;
        couple.setText(i.getCouplename());
        children.setText(i.getChildname());
        ph.setText(i.getPh());
        address.setText(i.getStreet()+" "+i.getCity()+" "+i.getState()+ " "+i.getZipcode());
        notes.setText(i.getNote());
        img.setImageBitmap(getImage(i.getPic()));
        if(index==count)
            index=0;
    }

    public void previous(View view){
        Toast.makeText(this, "Value of index:"+index, Toast.LENGTH_SHORT).show();
        if(index==0)
            index=count-1;
        else
            index--;

        Info i = TransferData.array_item.get(index);
        id = i.getId();
        current = index;

        couple.setText(i.getCouplename());
        children.setText(i.getChildname());
        ph.setText(i.getPh());
        address.setText(i.getStreet()+" "+i.getCity()+" "+i.getState()+ " "+i.getZipcode());
        notes.setText(i.getNote());
        img.setImageBitmap(getImage(i.getPic()));

    }

    public Bitmap getImage(byte[] image){
        Bitmap bitmap;
        try {
          bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length);
        }catch (Exception e)
        {
            return null;
        }
        return bitmap;
    }

    public void deleteEntry(View view){
        DBMS db = new DBMS();
        db.deleteEntry(id);
    }

    public void addEntry(View view){
        Intent addScreen = new Intent();
        addScreen.setClass(this.getApplicationContext(),AddActivity.class);
        startActivity(addScreen);
        finish();
    }

    public void editEntry(View view){
        Intent editScreen = new Intent();
        editScreen.setClass(this.getApplicationContext(),AddActivity.class);
        editScreen.putExtra("index",String.valueOf(current));
        startActivity(editScreen);

    }
}
