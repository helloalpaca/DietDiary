package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.SplashActivity.splashText;

public class ChangeSplashActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_splash);

        /* get R.id */
        editText = (EditText)findViewById(R.id.chnagesplash_edittext);

        editText.setText(splashText);
    }

    /* change splash text */
    public void onClickButtonModifySplash(View view){
        ContentValues values = new ContentValues();
        values.put("txt",editText.getText().toString());

        /* update db */
        db.update("splash",values,"txt=?",new String[]{splashText});
        onBackPressed();
    }
}
