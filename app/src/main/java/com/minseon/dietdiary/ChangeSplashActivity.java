package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.SplashActivity.str;

public class ChangeSplashActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_splash);

        editText = (EditText)findViewById(R.id.chnagesplash_edittext);
        editText.setText(str);
    }

    public void onClickButtonModifySplash(View view){

        ContentValues values = new ContentValues();
        values.put("txt",str);
        db.update("splash",values,"txt=?",new String[]{str});

    }
}
