package com.minseon.dietdiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.ListView;

import com.minseon.dietdiary.adapter.CalendarCursorAdapter;

import java.util.Calendar;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.formatdate;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    ListView listView;

    Calendar calendar;
    CalendarCursorAdapter calendarCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        /* get R.id */
        calendarView = (CalendarView)findViewById(R.id.calendarView2);
        listView = (ListView)findViewById(R.id.calendar_listview);

        /* variables */
        calendar = Calendar.getInstance();

        /* Listeners */
        calendarView.setOnDateChangeListener(new CustomCalendarView());

        queryDB();
    }

    /* query DB by date */
    public void queryDB(){
        String day = formatdate.format(calendar.getTime());
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59' order by date";
        final Cursor c = db.rawQuery(sql,null);

        String[] strs = new String[]{"eat","category"};
        int[] ints = new int[] {R.id.listview2_txt};
        calendarCursorAdapter = new CalendarCursorAdapter(listView.getContext(), R.layout.listview2, c, strs, ints);

        listView.setAdapter(calendarCursorAdapter);
    }

    public class CustomCalendarView implements CalendarView.OnDateChangeListener{

        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            calendar.set(year, month, dayOfMonth);
            queryDB();
        }
    }
}
