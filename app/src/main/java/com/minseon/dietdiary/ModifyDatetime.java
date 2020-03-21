package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static com.minseon.dietdiary.AddListActivity.datebtn;

public class ModifyDatetime extends AppCompatActivity implements View.OnClickListener{

    static TextView txt;
    Button btn1, btn2;
    static String nowdatetime;

    FragmentManager fm;
    FragmentTransaction tran;
    Frag1 frag1;
    Frag2 frag2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_datetime);

        txt = (TextView)findViewById(R.id.datetime_txt);
        btn1 = (Button) findViewById(R.id.datetime_btn1);
        btn2 = (Button) findViewById(R.id.datetime_btn2);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        frag1 = new Frag1();
        frag2 = new Frag2();

        setFrag(0);

        Intent intent = getIntent();
        nowdatetime = intent.getStringExtra("Datetime");
        txt.setText(nowdatetime);

    }

    public void onClickButtonModifyDate(View view){
        datebtn.setText(txt.getText());
        onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.datetime_btn1:
                setFrag(0);
                break;
            case R.id.datetime_btn2:
                setFrag(1);
                break;
        }
    }

    public void setFrag(int n) {
        fm = getSupportFragmentManager();
        tran = fm.beginTransaction();
        switch (n) {
            case 0:
                tran.replace(R.id.fragment, frag1);
                tran.commit();
                break;
            case 1:
                tran.replace(R.id.fragment, frag2);
                tran.commit();
                break;
        }
    }
}
