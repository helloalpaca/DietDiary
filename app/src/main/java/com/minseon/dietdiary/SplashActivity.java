package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.minseon.dietdiary.db.DBHelper;

public class SplashActivity extends AppCompatActivity {

    static DBHelper helper;
    static String splashText;
    static SQLiteDatabase db;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* get R.id */
        textView = (TextView)findViewById(R.id.splash_txt);

        /* set Variables */
        helper = new DBHelper(SplashActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        /* get splash text from person.db */
        String sql = "SELECT * FROM splash WHERE _id=1";
        final Cursor c = db.rawQuery(sql,null);

        while(c.moveToNext()){
            splashText = c.getString(c.getColumnIndex("txt"));
            textView.setText(splashText);
        }

        /* splashhandler runs after 1.5 seconds */
        Handler hd = new Handler();
        hd.postDelayed(new splashhandler(), 1500);
    }

    /* start MainActivity */
    private class splashhandler implements Runnable{
        public void run(){
            startActivity(new Intent(getApplication(), MainActivity.class));
            SplashActivity.this.finish();
        }
    }

}
