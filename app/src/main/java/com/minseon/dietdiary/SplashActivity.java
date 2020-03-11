package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    static DBHelper helper;
    static String str;
    static SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        TextView textView = (TextView)findViewById(R.id.splash_txt);
        helper = new DBHelper(SplashActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        String sql = "SELECT * FROM splash WHERE _id=1";
        final Cursor c = db.rawQuery(sql,null);
        while(c.moveToNext()){
            str = c.getString(c.getColumnIndex("txt"));
            textView.setText(str);
        }

        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1500);
    }

    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }
}
