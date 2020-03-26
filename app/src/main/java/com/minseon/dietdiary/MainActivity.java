package com.minseon.dietdiary;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.minseon.dietdiary.myadapter.ImageCursorAdapter;
import com.minseon.dietdiary.mylistener.CustomOnItemClickListener;
import com.minseon.dietdiary.mylistener.CustomTouchListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.minseon.dietdiary.SplashActivity.db;

public class MainActivity extends AppCompatActivity {

    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    static final SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
    static final SimpleDateFormat formatmain = new SimpleDateFormat("MM월 dd일",Locale.KOREA);

    static ImageCursorAdapter adapter;
    static Calendar calendar;
    static ListView listView;
    static Button daybtn;
    static Context applicationContext;
    static ContentResolver cr;

    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* get R.id */
        daybtn = (Button)findViewById(R.id.main_btn);
        listView = (ListView)findViewById(R.id.main_listview);

        /* get permission */
        ActivityCompat.requestPermissions(MainActivity.this, permission_list,  1);

        /* set Variables */
        calendar = Calendar.getInstance();
        applicationContext = getApplication();
        cr = getContentResolver();

        queryDB();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                String msg1 = cursor.getString(cursor.getColumnIndex("date"));
                String msg2 = cursor.getString(cursor.getColumnIndex("place"));
                String msg3 = cursor.getString(cursor.getColumnIndex("eat"));
                String msg4 = cursor.getString(cursor.getColumnIndex("uri"));
                int msg5 = cursor.getInt(cursor.getColumnIndex("category"));

                Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
                intent.putExtra("date",msg1);
                intent.putExtra("place",msg2);
                intent.putExtra("eat",msg3);
                intent.putExtra("uri",msg4);
                intent.putExtra("category",msg5);
                startActivity(intent);
            }
        });
        listView.setOnTouchListener(new CustomTouchListener());
    }

    public void onClickButtonDayBefore(View view){
        DayBefore();
    }

    public void onClickButtonDayAfter(View view){
        DayAfter();
    }

    public void onClickButtonMainAdd(View view){
        Intent intent = new Intent(MainActivity.this, AddListActivity.class);
        startActivity(intent);
    }

    public void onClickButtonCalendar(View view){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    public void onClickButtonSetting(View view){
        Intent intent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    static public void queryDB(){
        String day = formatdate.format(calendar.getTime());
        daybtn.setText(formatmain.format(calendar.getTime()));

        /* query */
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59' order by date";
        final Cursor c = db.rawQuery(sql,null);

        String[] strs = new String[]{"eat","category","uri"};
        int[] ints = new int[] {R.id.listview_txt,R.id.listview_txt2,R.id.listview_img};
        adapter = new ImageCursorAdapter(listView.getContext(), R.layout.listview, c, strs, ints);

        listView.setAdapter(adapter);
    }

    static public void DayBefore(){
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        queryDB();
    }

    static public void DayAfter(){
        calendar.add(Calendar.DAY_OF_MONTH,1);
        queryDB();
    }

}
