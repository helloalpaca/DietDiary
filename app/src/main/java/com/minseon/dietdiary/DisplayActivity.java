package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import static com.minseon.dietdiary.SplashActivity.db;
import static java.sql.Types.NULL;

public class DisplayActivity extends AppCompatActivity {

    TextView date, category, place, eat;
    ImageView img;

    String extraDate, extraPlace, extraEat, extraUri;
    int extraCategory;
    String TAG = "DisplayAcitivy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        /* get R.id */
        date = (TextView)findViewById(R.id.display_date);
        place = (TextView)findViewById(R.id.display_place);
        eat = (TextView)findViewById(R.id.displace_eat);
        img = (ImageView)findViewById(R.id.display_img);
        category = (TextView)findViewById(R.id.display_category);

        /* Variables */
        Resources res = getResources();
        String[] strs = res.getStringArray(R.array.spinner_array);

        Intent intent = getIntent();
        extraDate = intent.getStringExtra("date");
        extraPlace = intent.getStringExtra("place");
        extraEat = intent.getStringExtra("eat");
        extraUri = intent.getStringExtra("uri");
        extraCategory = intent.getIntExtra("category",0);

        category.setText(strs[extraCategory]);
        date.setText(extraDate);
        place.setText(extraPlace);
        eat.setText(extraEat);
        if(extraUri!=null) img.setImageURI(Uri.parse(extraUri));
    }

    /* start AddListActivity to change Data */
    public void onClickButtonModify(View view){
        Intent intent = new Intent(DisplayActivity.this, AddListActivity.class);
        intent.putExtra("date", extraDate);
        intent.putExtra("category", extraCategory);
        intent.putExtra("place", extraPlace);
        intent.putExtra("eat", extraEat);
        intent.putExtra("uri", extraUri);

        startActivity(intent);
    }

    public void onClickButtonDelete(View view){
        Log.i(TAG, "Delete Button Clicked");
        String strCategory = Integer.toString(extraCategory);

        /* delete */
        if(extraUri==null) {
            Log.i(TAG, "extraUri is null");
            db.delete("diary","date=? AND place=? AND eat=?", new String[]{extraDate, extraPlace, extraEat}); }
        else { db.delete("diary","date=? AND place=? AND eat=? AND uri=? AND category=?",
                new String[]{extraDate, extraPlace, extraEat, extraUri, strCategory}); }

        /* clear Activity Stack and start MainActivity */
        Intent intent = new Intent(DisplayActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
