package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.minseon.dietdiary.listener.RealPathUtil;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.calendar;
import static com.minseon.dietdiary.MainActivity.format;

public class AddListActivity extends AppCompatActivity {

    static Button btnDate;
    Spinner spinner;
    EditText editTextPlace, editTextEat;
    ImageButton btnImg;

    static final int REQUEST_CODE = 1;
    boolean flag = false;   // flag to confirm list existence
    String extraDate, extraPlace, extraEat, extraUri;
    int extraCategory;
    Uri tempUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        /* get R.id */
        btnDate = (Button)findViewById(R.id.addlist_btn_date);
        spinner = (Spinner)findViewById(R.id.addlist_spinner);
        editTextPlace = (EditText)findViewById(R.id.addlist_edit_place);
        editTextEat = (EditText)findViewById(R.id.addlist_edit_eat);
        btnImg = (ImageButton)findViewById(R.id.addlist_imgbtn);

        /* get extra data from MainActivity */
        Intent intent = getIntent();
        extraDate = intent.getStringExtra("date");
        extraCategory = intent.getIntExtra("category",0);
        extraPlace = intent.getStringExtra("place");
        extraEat = intent.getStringExtra("eat");
        extraUri = intent.getStringExtra("uri");

        if(extraDate!=null|| extraPlace!=null || extraEat!=null || extraUri!=null ) { flag = true; }

        /* set data */
        if(extraDate==null) {
            btnDate.setText(format.format(calendar.getTime()).toString());
        } else {
            btnDate.setText(extraDate);
        }

        if(extraUri!=null){ btnImg.setImageURI(Uri.parse(extraUri)); }

        spinner.setSelection(extraCategory);
        editTextPlace.setText(extraPlace);
        editTextEat.setText(extraEat);
    }

    public void onClickButtonDate(View view){
        Intent intent = new Intent(AddListActivity.this, ModifyDatetime.class);
        intent.putExtra("Datetime",btnDate.getText());
        startActivity(intent);
    }

    public void onClickButtonAdd(View view){
        ContentValues values = new ContentValues();
        values.put("date", btnDate.getText().toString());
        values.put("place", editTextPlace.getText().toString());
        values.put("eat", editTextEat.getText().toString());
        values.put("category", spinner.getSelectedItemPosition());

        if(tempUri!=null) {
            values.put("uri",RealPathUtil.getRealPath(this, tempUri));
        }

        /* update or insert db */
        if(flag){
            db.update("diary",values,"date=? AND place=? AND eat=? AND category=?",
                    new String[]{extraDate,extraPlace,extraEat,Integer.toString(extraCategory)});
        } else {
            db.insert("diary",null,values);
        }

        Intent intent = new Intent(AddListActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void onClickButtonImg(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                tempUri = data.getData();
                btnImg.setImageURI(tempUri);
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
