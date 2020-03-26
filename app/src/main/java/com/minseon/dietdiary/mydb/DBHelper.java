package com.minseon.dietdiary.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE if not exists diary ("
                + "_id integer primary key autoincrement,"
                + "date datetime, place text, eat text, category int, uri text);";

        db.execSQL(sql);

        String sql2 = "CREATE TABLE if not exists splash ("
                + "_id integer primary key autoincrement,"
                + "txt text);";

        db.execSQL(sql2);

        String sql3 = "INSERT INTO splash (txt) values('Diet Diary');";

        db.execSQL(sql3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE if exists diary";
        db.execSQL(sql);
        String sql2 = "DROP TABLE if exists splash";
        db.execSQL(sql2);
        onCreate(db);
    }
}
