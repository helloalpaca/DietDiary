package com.minseon.dietdiary.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cursoradapter.widget.SimpleCursorAdapter;

import com.minseon.dietdiary.R;

public class CalendarCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private int layout;
    private Context context;
    private String[] from;
    private int[] to;

    public CalendarCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.layout = layout;
        this.context = context;
        this.from = from;
        this.to = to;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        this.c.moveToPosition(pos);

        String eat = c.getString(c.getColumnIndex(from[0]));
        int category = c.getInt(c.getColumnIndex(from[1]));

        TextView textView = (TextView) v.findViewById(to[0]);
        textView.setText(eat);
        if(category==3) textView.setTextColor(v.getResources().getColor(R.color.Palette2Color2));
        else if(category==4) textView.setTextColor(v.getResources().getColor(R.color.Palette2Color3));
        else textView.setTextColor(v.getResources().getColor(R.color.Palette2Color1));

        return (v);
    }
}
