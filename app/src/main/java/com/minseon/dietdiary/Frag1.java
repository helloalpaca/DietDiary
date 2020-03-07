package com.minseon.dietdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.minseon.dietdiary.ModifyDatetime.nowdatetime;
import static com.minseon.dietdiary.ModifyDatetime.txt;

public class Frag1 extends Fragment {
    View view;
    CalendarView calendarView;
    Calendar cal;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag1,container,false);

        calendarView = (CalendarView)view.findViewById(R.id.calendarView);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        String str = nowdatetime;
        Date date = null;
        try {
            date = format.parse(nowdatetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        cal.setTime(date);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                System.out.println("year : "+year+", month : "+month+", day : "+dayOfMonth);
                cal.set(year, month, dayOfMonth);
                txt.setText(format.format(cal.getTime()).toString());
            }
        });

        return view;
    }
}
