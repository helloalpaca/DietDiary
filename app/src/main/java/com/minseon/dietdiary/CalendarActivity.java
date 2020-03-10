package com.minseon.dietdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.SimpleCursorAdapter;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.formatdate;
import static com.minseon.dietdiary.MainActivity.formatmain;

public class CalendarActivity extends AppCompatActivity {

    TextView textView;
    CalendarView calendarView2;
    ListView listView2;
    Calendar mycal;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendarView2 = (CalendarView)findViewById(R.id.calendarView2);
        listView2 = (ListView)findViewById(R.id.calendar_listview);

        mycal = Calendar.getInstance();
        queryDB();

        calendarView2.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                System.out.println("year : "+year+", month : "+month+", day : "+dayOfMonth);
                mycal.set(year, month, dayOfMonth);
                queryDB();
            }
        });
    }

    public void queryDB(){
        String day = formatdate.format(mycal.getTime());
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59'";
        final Cursor c = db.rawQuery(sql,null);
        String[] strs = new String[]{"eat"};
        int[] ints = new int[] {R.id.listview2_txt};
        adapter = new SimpleCursorAdapter(listView2.getContext(), R.layout.listview2, c, strs, ints,0);
        listView2.setAdapter(adapter);
    }
}
