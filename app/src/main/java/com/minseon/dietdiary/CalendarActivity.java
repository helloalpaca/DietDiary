package com.minseon.dietdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.minseon.dietdiary.myadapter.CalendarCursorAdapter;

import java.util.Calendar;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.formatdate;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView2;
    ListView listView2;

    Calendar mycal;
    CalendarCursorAdapter adapter;

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
                mycal.set(year, month, dayOfMonth);
                queryDB();
            }
        });
    }

    public void queryDB(){
        String day = formatdate.format(mycal.getTime());
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59' order by date";
        final Cursor c = db.rawQuery(sql,null);
        String[] strs = new String[]{"eat","category"};
        int[] ints = new int[] {R.id.listview2_txt};
        adapter = new CalendarCursorAdapter(listView2.getContext(), R.layout.listview2, c, strs, ints);
        listView2.setAdapter(adapter);
    }
}
