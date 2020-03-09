package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.minseon.dietdiary.MainActivity.db;
import static java.sql.Types.NULL;

public class Display extends AppCompatActivity {

    TextView date, place, eat;
    ImageView img;
    String str1, str2, str3, str4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        date = (TextView)findViewById(R.id.display_date);
        place = (TextView)findViewById(R.id.display_place);
        eat = (TextView)findViewById(R.id.displace_eat);
        img = (ImageView)findViewById(R.id.display_img);

        Intent intent = getIntent();
        str1 = intent.getStringExtra("date");
        str2 = intent.getStringExtra("place");
        str3 = intent.getStringExtra("eat");
        str4 = intent.getStringExtra("uri");

        date.setText(str1);
        place.setText(str2);
        eat.setText(str3);
        if(str4!=null) img.setImageURI(Uri.parse(str4));
    }

    public void onClickButtonModify(View view){
        Intent intent = new Intent(Display.this, AddList.class);
        intent.putExtra("date",str1);
        intent.putExtra("place",str2);
        intent.putExtra("eat",str3);
        intent.putExtra("uri",str4);
        startActivity(intent);
    }

    public void onClickButtonDelete(View view){

        System.out.println(str1+" "+str2+" "+str3+" "+str4);
        if(str4==null) { db.delete("diary","date=? AND place=? AND eat=?",new String[]{str1, str2, str3}); }
        else db.delete("diary","date=? AND place=? AND eat=? AND uri=?",new String[]{str1, str2, str3, str4});
        Intent intent = new Intent(Display.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }
}
