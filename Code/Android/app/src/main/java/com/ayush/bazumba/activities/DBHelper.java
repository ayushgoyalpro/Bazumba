package com.ayush.bazumba.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Bazumba";


    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table open_tracker (opened INT not null)");
        db.execSQL("create table user (id int(11),name varchar(50),email varchar(50),password varchar(100))");
        ContentValues data = new ContentValues();
        data.put("opened",0);
        db.insert("open_tracker",null,data);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVer, int newVer) {
        db.execSQL("drop table if exists open_tracker");
        onCreate(db);
    }

    public boolean setOpened(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues data = new ContentValues();
        data.put("opened",1);
        int i = db.update("open_tracker",data,"opened=0",null);
        if(i==1){
            return true;
        }
        else{
            return false;
        }

    }

    public boolean inputUser(String[] params){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from user");
        ContentValues data = new ContentValues();
        data.put("id",params[0]);
        data.put("name",params[1]);
        data.put("email",params[3]);
        data.put("password",params[4]);
        long rowid = db.insert("user",null,data);
        if(rowid == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from open_tracker",null);
        return res;
    }

    public Cursor getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from user",null);
        return res;
    }
}