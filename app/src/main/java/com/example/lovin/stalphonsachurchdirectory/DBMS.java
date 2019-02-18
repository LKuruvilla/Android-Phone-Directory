package com.example.lovin.stalphonsachurchdirectory;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;



public class DBMS  {
    public static final String  DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString();
    private static final String DATABASE_NAME = "St.Augustine.db";

    private static final String path = DATABASE_FILE_PATH
            + File.separator + DATABASE_NAME;

    private static final int VERSION = 1;
    private static final String TAG = "DBMSTag";
    public final String TABLE_NAME = "Contacts";
    public final String Col_ID = "id";
    public final String Couple_Name = "couple_name";
    public final String Child_Name ="child_name";
    public final String Phone_no = "phone";
    public final String Street = "street";
    public final String City = "city";
    public final String Zip = "zip";
    public final String State = "state";
    public final String Note = "note";
    public final String ByteArray = "byte_array";
    public static byte[] ref;
    private SQLiteDatabase db;

    public DBMS() {
        try
        {

            db = SQLiteDatabase.openOrCreateDatabase(path,null);
            Log.d(TAG,"path:"+path);
            onCreate();
            Log.d(TAG,"Created tables");


        }
        catch (SQLiteException ex)
        {
            Log.e(TAG, "error -- " + ex.getMessage(), ex);
            // error means tables does not exits

        }
        finally
        {
            db.close();
        }

    }


    public void onCreate() {
        try {
            String createDB = "Create TABLE " + TABLE_NAME + " ("
                    + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + Couple_Name + " VARCHAR(80),"
                    + Child_Name + " VARCHAR(80),"
                    + Phone_no + " VARCHAR(10),"
                    + Street + " VARCHAR(50),"
                    + City + " VARCHAR(25),"
                    + Zip + " VARCHAR(5),"
                    + State + " VARCHAR(15),"
                    + Note + " VARCHAR(250),"
                    + ByteArray + " BLOB" + " );";
            db.execSQL(createDB);
        }catch (Exception e)
        {
            Log.d(TAG ,"Exception caught: "+e.getLocalizedMessage());
        }
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

    }

    public void addEntry(Info info){

        db = SQLiteDatabase.openOrCreateDatabase(path,null);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Couple_Name, info.getCouplename().trim());
        contentValues.put(Child_Name,info.getChildname().trim());
        contentValues.put(Phone_no,info.getPh().trim());
        contentValues.put(Street,info.getStreet().trim());
        contentValues.put(City,info.getCity().trim());
        contentValues.put(Zip,info.getZipcode());
        contentValues.put(State, info.getState());
        contentValues.put(Note,info.getNote());
        contentValues.put(ByteArray,info.getPic());
        db.insert(TABLE_NAME,null,contentValues);
        db.close();

    }


    public void updateEntry(Info i)
    {   Info info = i;

        db = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        String updateQuery = "UPDATE "+TABLE_NAME +" SET "+Couple_Name+" ="+i.getCouplename()
                +" ,"+Child_Name+" ="+i.getChildname()
                +" ,"+Phone_no+" ="+i.getPh()
                +" ,"+Street+" ="+i.getStreet()
                +" ,"+City+" ="+i.getCity()
                +" ,"+State+" ="+i.getState()
                +" ,"+Zip+" ="+i.getZipcode()
                +" ,"+Note+" ="+i.getNote()
                +" ,"+ByteArray+" ="+i.getPic()
                +" WHERE "+Col_ID+" = "+i.getId()+ " ;";

        ContentValues contentValues = new ContentValues();
        contentValues.put(Couple_Name, info.getCouplename().trim());
        contentValues.put(Child_Name,info.getChildname().trim());
        contentValues.put(Phone_no,info.getPh().trim());
        contentValues.put(Street,info.getStreet().trim());
        contentValues.put(City,info.getCity().trim());
        contentValues.put(Zip,info.getZipcode());
        contentValues.put(State, info.getState());
        contentValues.put(Note,info.getNote());
        contentValues.put(ByteArray,info.getPic());
        int count = db.update(TABLE_NAME,contentValues,Col_ID+"="+info.getId(),null);
      //  db.execSQL(updateQuery);
        db.close();
    }

    public void deleteEntry(int i)
    {
        db = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);
        String query = "DELETE FROM "+TABLE_NAME+" WHERE "+Col_ID+" = "+i+";";
        db.execSQL(query);
    }

    public ArrayList<Info> searchEntry(String s){
        ArrayList<Info> items= null;
        items = new ArrayList<Info>();
        String query_allItems = "SELECT * FROM "+TABLE_NAME+ " WHERE "+Couple_Name +" LIKE '"+s+"%' ;";

        db = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READONLY);


        // Toast.makeText(context, "Please make a database first", Toast.LENGTH_SHORT).show();


        String count = "SELECT count(*) FROM "+TABLE_NAME+ " ;";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount==0)
        {
            //Toast.makeText(context, "Please add items first", Toast.LENGTH_SHORT).show();
            return items;
        }

        //create cursor to move from each rows
        Cursor cs = null;
        try {
            cs = db.rawQuery(query_allItems, null);
        }catch (Exception e)
        {
            int x = 2+2;
        }

        cs.moveToFirst();

        while(!cs.isAfterLast())
        {
            // if (cs.getString(cs.getColumnIndex(Couple_Name)) != null) {
            Info i = new Info();
            i.setId(cs.getInt(cs.getColumnIndex(Col_ID)));
            i.setCouplename(cs.getString(cs.getColumnIndex(Couple_Name)));
            i.setChildname(cs.getString(cs.getColumnIndex(Child_Name)));
            i.setPh(cs.getString(cs.getColumnIndex(Phone_no)));
            i.setStreet(cs.getString(cs.getColumnIndex(Street)));
            i.setCity(cs.getString(cs.getColumnIndex(City)));
            i.setZipcode(cs.getString(cs.getColumnIndex(Zip)));
            i.setState(cs.getString(cs.getColumnIndex(State)));
            i.setNote(cs.getString(cs.getColumnIndex(Note)));
            i.setPic(cs.getBlob(cs.getColumnIndex(ByteArray)));
            if(i.getPic()==null)
            {
                i.setPic(ref);

            }

            items.add(i);
            i=null;

            cs.moveToNext();

        }
        db.close();
        cs.close();
        mcursor.close();
        return items;




    }

    public ArrayList<Info> view(Context context){
        ArrayList<Info> items = null;
        items = new ArrayList<Info>();
        String query_allItems = "SELECT * FROM "+TABLE_NAME+ " ;";

            db = SQLiteDatabase.openDatabase(path, null,
                    SQLiteDatabase.OPEN_READONLY);


           // Toast.makeText(context, "Please make a database first", Toast.LENGTH_SHORT).show();


        String count = "SELECT count(*) FROM "+TABLE_NAME+ " ;";
        Cursor mcursor = db.rawQuery(count, null);
        mcursor.moveToFirst();
        int icount = mcursor.getInt(0);
        if(icount==0)
        {
            //Toast.makeText(context, "Please add items first", Toast.LENGTH_SHORT).show();
            return items;
        }

        //create cursor to move from each rows
        Cursor cs = db.rawQuery(query_allItems,null);

        cs.moveToFirst();

        while(!cs.isAfterLast())
        {
           // if (cs.getString(cs.getColumnIndex(Couple_Name)) != null) {
                Info i = new Info();
                i.setId(cs.getInt(cs.getColumnIndex(Col_ID)));
                i.setCouplename(cs.getString(cs.getColumnIndex(Couple_Name)));
                i.setChildname(cs.getString(cs.getColumnIndex(Child_Name)));
                i.setPh(cs.getString(cs.getColumnIndex(Phone_no)));
                i.setStreet(cs.getString(cs.getColumnIndex(Street)));
                i.setCity(cs.getString(cs.getColumnIndex(City)));
                i.setZipcode(cs.getString(cs.getColumnIndex(Zip)));
                i.setState(cs.getString(cs.getColumnIndex(State)));
                i.setNote(cs.getString(cs.getColumnIndex(Note)));
                i.setPic(cs.getBlob(cs.getColumnIndex(ByteArray)));
//                if(i.getPic()==null)
//               {
//                   i.setPic(ref);
//
//               }

                items.add(i);
                i=null;

            cs.moveToNext();

        }
        mcursor.close();
        cs.close();
        db.close();

        return items;

    }





}

/*
public class DBMS extends SQLiteOpenHelper {
    public static final String  DATABASE_FILE_PATH = Environment.getExternalStorageDirectory().toString();
    private static final String DATABASE_NAME = "St.Augustine.db";
    private static final int VERSION = 1;
    public final String TABLE_NAME = "Contacts";
    public final String Col_ID = "id";
    public final String Couple_Name = "couple_name";
    public final String Child_Name ="child_name";
    public final String Phone_no = "phone";
    public final String Street = "street";
    public final String City = "city";
    public final String Zip = "zip";
    public final String State = "state";
    public final String Note = "note";
    public final String ByteArray = "byte_array";

    public DBMS(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context,DATABASE_NAME , factory, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDB = "Create TABLE " +TABLE_NAME +" ("
                +Col_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"
                +Couple_Name +" VARCHAR(80),"
                +Child_Name +" VARCHAR(80),"
                +Phone_no +" VARCHAR(10),"
                +Street +" VARCHAR(50),"
                +City +" VARCHAR(25),"
                +Zip +" VARCHAR(5),"
                +State +" VARCHAR(15),"
                +Note +" VARCHAR(250),"
                +ByteArray+ " BLOB" +" );";
        db.execSQL(createDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addEntry(Info info){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Couple_Name, info.getCouplename().trim());
        contentValues.put(Child_Name,info.getChildname().trim());
        contentValues.put(Phone_no,info.getPh().trim());
        contentValues.put(Street,info.getStreet().trim());
        contentValues.put(City,info.getCity().trim());
        contentValues.put(Zip,info.getZipcode());
        contentValues.put(State, info.getState());
        contentValues.put(Note,info.getNote());
        contentValues.put(ByteArray,info.getPic());
        db.insert(TABLE_NAME,null,contentValues);

    }



}

 */