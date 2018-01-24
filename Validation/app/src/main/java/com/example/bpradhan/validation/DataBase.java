package com.example.bpradhan.validation;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bpradhan on 24/1/18.
 */

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Login.db";
    public static final String TABLE_NAME="Register";
    public static final String C0L_1="EMAIL";
    public static final String COL_2="PASSWORD";
    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+" ("+C0L_1+" varchar2(20),"+COL_2+" varchar2(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String email,String passsword){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(C0L_1,email);
        contentValues.put(COL_2,passsword);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }
    public Cursor getData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    public boolean updateData(String email,String passsword){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(C0L_1,email);
        contentValues.put(COL_2,passsword);
        db.update(TABLE_NAME,contentValues,C0L_1+"=?",new String[]{email});
        return true;
    }
    public Integer deleteData(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,C0L_1+"=?",new String[]{email});
    }
}
