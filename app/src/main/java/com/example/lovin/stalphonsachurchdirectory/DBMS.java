package com.example.lovin.stalphonsachurchdirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBMS extends SQLiteOpenHelper {
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
