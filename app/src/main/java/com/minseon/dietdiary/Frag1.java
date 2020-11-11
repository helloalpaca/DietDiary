package com.minseon.dietdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.minseon.dietdiary.MainActivity.format;
import static com.minseon.dietdiary.ModifyDatetime.textViewDatetime;

/* Fragment to Change Date */
public class Frag1 extends Fragment {
    View view;
    CalendarView calendarView;
    Calendar calendar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag1,container,false);

        /* get R.id */
        calendarView = (CalendarView)view.findViewById(R.id.calendarView); // frag1.xml

        /* variables */
        String datetime = textViewDatetime.getText().toString();

        Date date = null;
        try { date = format.parse(datetime);
        } catch (ParseException e) { e.printStackTrace(); }

        calendar = Calendar.getInstance();
        calendar.setTime(date);

        /* Listener */
        calendarView.setOnDateChangeListener(new CustomCalendarView());

        return view;
    }

    public class CustomCalendarView implements CalendarView.OnDateChangeListener{

        @Override
        public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            calendar.set(year, month, dayOfMonth);
            textViewDatetime.setText(format.format(calendar.getTime()).toString());
        }
    }
}
