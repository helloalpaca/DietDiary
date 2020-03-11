package com.minseon.dietdiary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.minseon.dietdiary.MainActivity.format;
import static com.minseon.dietdiary.ModifyDatetime.nowdatetime;
import static com.minseon.dietdiary.ModifyDatetime.txt;

public class Frag2 extends Fragment {
    View view;
    TimePicker timePicker;
    Calendar cal;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.frag2,container,false);

        timePicker = (TimePicker)view.findViewById(R.id.datePicker);

        String str = txt.getText().toString();
        Date date = null;
        try { date = format.parse(str);
        } catch (ParseException e) { e.printStackTrace(); }
        cal = Calendar.getInstance();
        cal.setTime(date);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                System.out.println("hourOfDay : "+hourOfDay+", minute : "+minute);
                cal.set(Calendar.HOUR_OF_DAY,hourOfDay);
                cal.set(Calendar.MINUTE,minute);
                txt.setText(format.format(cal.getTime()).toString());
            }
        });
        return view;
    }
}