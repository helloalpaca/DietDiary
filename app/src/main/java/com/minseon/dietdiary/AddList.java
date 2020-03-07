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

    EditText date, place, eat;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
        Calendar cal = Calendar.getInstance();

        date = (EditText)findViewById(R.id.addlist_edit_date);
        place = (EditText)findViewById(R.id.addlist_edit_place);
        eat = (EditText)findViewById(R.id.addlist_edit_eat);
        btn = (Button)findViewById(R.id.addlist_btn);

        Intent intent = getIntent();
        String str1 = intent.getStringExtra("date");
        String str2 = intent.getStringExtra("place");
        String str3 = intent.getStringExtra("eat");

        date.setText(str1);
        place.setText(str2);
        eat.setText(str3);
    }

    public void onClickButton1(View view){
        ContentValues values = new ContentValues();
        values.put("date","20200307");
        values.put("place","Guseo");
        values.put("eat",eat.getText().toString());
        db.insert("diary",null,values);

        Intent intent = new Intent(AddList.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
