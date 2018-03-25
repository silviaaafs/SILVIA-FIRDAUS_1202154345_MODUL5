package com.example.android.silviafirdaus_1202154345_modul5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by ASUS on 3/24/2018.
 */

public class Database extends SQLiteOpenHelper {
    //deklarasi variabel
    Context cntx;
    SQLiteDatabase db;

    public static final String nama_db = "listtodo.db";
    public static final String namatabel = "daftartodo";
    public static final String kolom1 = "todo";
    public static final String kolom2 = "description";
    public static final String kolom3 = "priority";

    //konstruktor
    public Database(Context context) {
        super(context, nama_db, null, 1);
        this.cntx = context;
        db = this.getWritableDatabase();
        db.execSQL("create table if not exists "+namatabel+" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    @Override //ketika database dibuat
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+namatabel+" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+namatabel);
        onCreate(sqLiteDatabase);
    }

    public boolean inputdata(AddData list) {
        //mencocokkan kolom beserta nilainya
        ContentValues val = new ContentValues();
        val.put(kolom1, list.getTodo());
        val.put(kolom2, list.getDesc());
        val.put(kolom3, list.getPrior());
        long hasil = db.insert(namatabel, null, val);
        if (hasil==-1) {
            return false;
        }else {
            return true;
        }
    }

    //method untuk menghapus data pada database
    public boolean removedata(String ToDo) {
        return db.delete(namatabel, kolom1+"=\""+ToDo+"\"", null)>0;
    }

    //method untuk mengakses dan membaca database
    public void readdata(ArrayList<AddData> daftar){
        Cursor cursor = this.getReadableDatabase().rawQuery("select todo, description, priority from "+namatabel, null);
        while (cursor.moveToNext()){
            daftar.add(new AddData(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
    }
}
