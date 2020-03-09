package com.minseon.dietdiary;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static java.sql.Types.NULL;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    static SQLiteDatabase db;
    BaseAdapter adapter;
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    static final SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
    static final SimpleDateFormat formatmain = new SimpleDateFormat("MM월 dd일",Locale.KOREA);
    static Calendar calendar;
    ListView listView;
    ImageButton daybefore, dayafter;
    Button daybtn;
    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(MainActivity.this,
                permission_list,  1);

        daybtn = (Button)findViewById(R.id.main_btn);
        listView = (ListView)findViewById(R.id.main_listview);

        helper = new DBHelper(MainActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);

        calendar = Calendar.getInstance();
        queryDB();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                String msg1 = cursor.getString(cursor.getColumnIndex("date"));
                String msg2 = cursor.getString(cursor.getColumnIndex("place"));
                String msg3 = cursor.getString(cursor.getColumnIndex("eat"));
                String msg4 = cursor.getString(cursor.getColumnIndex("uri"));

                Intent intent = new Intent(MainActivity.this, Display.class);
                intent.putExtra("date",msg1);
                intent.putExtra("place",msg2);
                intent.putExtra("eat",msg3);
                intent.putExtra("uri",msg4);
                startActivity(intent);
            }
        });

        ImageButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddList.class);
                startActivity(intent);
            }
        });
    }

    public void onClickButtonDayBefore(View view){
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        queryDB();
    }

    public void onClickButtonDayAfter(View view){
        calendar.add(Calendar.DAY_OF_MONTH,1);
        queryDB();
    }

    public void onClickButtonCalendar(View view){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void onClickButtonSetting(View view){
        Intent intent = new Intent(MainActivity.this,Setting.class);
        startActivity(intent);
    }

    public void queryDB(){
        String day = formatdate.format(calendar.getTime());
        daybtn.setText(formatmain.format(calendar.getTime()));
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59'";
        final Cursor c = db.rawQuery(sql,null);
        String[] strs = new String[]{"eat","uri"};
        int[] ints = new int[] {R.id.listview_txt,R.id.listview_img};
        adapter = new ImageCursorAdapter(listView.getContext(), R.layout.listview, c, strs, ints);
        listView.setAdapter(adapter);
    }

}
