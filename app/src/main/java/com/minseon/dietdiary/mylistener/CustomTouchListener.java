package com.minseon.dietdiary.mylistener;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static com.minseon.dietdiary.MainActivity.DayAfter;
import static com.minseon.dietdiary.MainActivity.DayBefore;

public class CustomTouchListener implements View.OnTouchListener {

    private float x1, x2;
    static final int MIN_DISTANCE = 150;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                float deltaX = x2 - x1;
                if (Math.abs(deltaX) > MIN_DISTANCE) {
                    if(x1<x2){
                        DayBefore();
                        Log.i("onTouch","Left to Right");

                    } else {
                        DayAfter();
                        Log.i("onTouch","Right to Left");
                    }

                }
                break;
        }
        return false;
    }
}
