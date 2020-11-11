package com.minseon.dietdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static com.minseon.dietdiary.MainActivity.format;
import static com.minseon.dietdiary.ModifyDatetime.textViewDatetime;

/* Fragment to Change Time */
public class Frag2 extends Fragment {
    View view;
    TimePicker timePicker;
    Calendar calendar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag2,container,false);

        /* get R.id */
        timePicker = (TimePicker)view.findViewById(R.id.datePicker); // frag2.xml

        /* variables */
        String str = textViewDatetime.getText().toString();

        Date date = null;
        try { date = format.parse(str);
        } catch (ParseException e) { e.printStackTrace(); }

        calendar = Calendar.getInstance();
        calendar.setTime(date);

        /* Listener */
        timePicker.setOnTimeChangedListener(new CustomTimePicker());

        return view;
    }

    public class CustomTimePicker implements TimePicker.OnTimeChangedListener{

        @Override
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            textViewDatetime.setText(format.format(calendar.getTime()));
        }
    }
}