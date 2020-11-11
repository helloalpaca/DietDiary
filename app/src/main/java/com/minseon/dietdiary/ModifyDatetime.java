package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ModifyDatetime extends AppCompatActivity implements View.OnClickListener{

    static TextView textViewDatetime;
    Button btnDate, btnTime;
    static String nowdatetime;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Frag1 frag1;
    Frag2 frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_datetime);

        /* get R.id */
        textViewDatetime = (TextView)findViewById(R.id.datetime_txt);
        btnDate = (Button) findViewById(R.id.datetime_btn1);
        btnTime = (Button) findViewById(R.id.datetime_btn2);

        /* Listener */
        btnDate.setOnClickListener(this);
        btnTime.setOnClickListener(this);

        frag1 = new Frag1();
        frag2 = new Frag2();

        setFrag(1); // default

        Intent intent = getIntent();
        nowdatetime = intent.getStringExtra("Datetime");
        textViewDatetime.setText(nowdatetime);
    }

    public void onClickButtonModifyDate(View view){
        btnDate.setText(textViewDatetime.getText());
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datetime_btn1:
                /* change Date */
                setFrag(1);
                break;
            case R.id.datetime_btn2:
                /* change Time */
                setFrag(2);
                break;
        }
    }

    public void setFrag(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        switch (n) {
            case 1:
                fragmentTransaction.replace(R.id.fragment, frag1);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.fragment, frag2);
                fragmentTransaction.commit();
                break;
        }
    }
}
