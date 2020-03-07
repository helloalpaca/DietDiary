package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.minseon.dietdiary.MainActivity.db;

public class AddList extends AppCompatActivity {

    EditText place, eat;
    Button date, btn;
    boolean flag = false;
    String ex_date, ex_place, ex_eat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Calendar cal = Calendar.getInstance();
        Date today = null;

        try {
            today = format.parse(format.format(cal.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        date = (Button)findViewById(R.id.addlist_btn_date);
        place = (EditText)findViewById(R.id.addlist_edit_place);
        eat = (EditText)findViewById(R.id.addlist_edit_eat);
        btn = (Button)findViewById(R.id.addlist_btn);

        Intent intent = getIntent();
        ex_date = intent.getStringExtra("date");
        ex_place = intent.getStringExtra("place");
        ex_eat = intent.getStringExtra("eat");
        if(ex_date!=null|| ex_place!=null || ex_eat!=null) flag = true;

        date.setText(ex_date);
        place.setText(ex_place);
        eat.setText(ex_eat);

        if(ex_date==null) date.setText(format.format(today).toString());
    }

    public void onClickButtonDate(View view){
        Intent intent = new Intent(AddList.this, ModifyDatetime.class);
        intent.putExtra("Datetime",date.getText());
        startActivity(intent);
    }

    public void onClickButtonAdd(View view){
        ContentValues values = new ContentValues();
        values.put("date",date.getText().toString());
        values.put("place",place.getText().toString());
        values.put("eat",eat.getText().toString());

        if(flag){ db.update("diary",values,"date=? AND place=? AND eat=?", new String[]{ex_date,ex_place,ex_eat}); }
        else { db.insert("diary",null,values); }

        Intent intent = new Intent(AddList.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
