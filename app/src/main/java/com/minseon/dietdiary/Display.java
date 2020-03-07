package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.minseon.dietdiary.MainActivity.db;

public class Display extends AppCompatActivity {

    TextView date, place, eat;
    Button modify, delete;
    String str1, str2, str3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        date = (TextView)findViewById(R.id.display_date);
        place = (TextView)findViewById(R.id.display_place);
        eat = (TextView)findViewById(R.id.displace_eat);
        modify = (Button)findViewById(R.id.display_modify);
        delete = (Button)findViewById(R.id.display_delete);

        Intent intent = getIntent();
        str1 = intent.getStringExtra("date");
        str2 = intent.getStringExtra("place");
        str3 = intent.getStringExtra("eat");

        eat.setText(str3);
    }

    public void onClickButtonModify(View view){
        Intent intent = new Intent(Display.this, AddList.class);
        intent.putExtra("date",str1);
        intent.putExtra("place",str2);
        intent.putExtra("eat",str3);
        startActivity(intent);
    }

    public void onClickButtonDelete(View view){

        db.delete("diary","date=? AND place=? AND eat=?",new String[]{str1, str2, str3});
        Intent intent = new Intent(Display.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
