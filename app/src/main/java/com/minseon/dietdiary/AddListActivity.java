package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.minseon.dietdiary.mylistener.RealPathUtil;

import java.io.InputStream;

import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.calendar;
import static com.minseon.dietdiary.MainActivity.format;

public class AddListActivity extends AppCompatActivity {

    static Button datebtn;
    Spinner spinner;
    EditText place, eat;
    ImageButton imgbtn;

    static final int REQUEST_CODE = 1;
    boolean flag = false;
    String _date, _place, _eat, _uri;
    int _category;
    Uri temp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        datebtn = (Button)findViewById(R.id.addlist_btn_date);
        spinner = (Spinner)findViewById(R.id.addlist_spinner);
        place = (EditText)findViewById(R.id.addlist_edit_place);
        eat = (EditText)findViewById(R.id.addlist_edit_eat);
        imgbtn = (ImageButton)findViewById(R.id.addlist_imgbtn);

        Intent intent = getIntent();
        _date = intent.getStringExtra("date");
        _category = intent.getIntExtra("category",0);
        _place = intent.getStringExtra("place");
        _eat = intent.getStringExtra("eat");
        _uri = intent.getStringExtra("uri");

        if(_date!=null|| _place!=null || _eat!=null || _uri!=null ) flag = true;

        /* set data */
        if(_date==null) { datebtn.setText(format.format(calendar.getTime()).toString());
        } else { datebtn.setText(_date); }

        if(_uri!=null){ imgbtn.setImageURI(Uri.parse(_uri)); }

        spinner.setSelection(_category);
        place.setText(_place);
        eat.setText(_eat);
    }

    public void onClickButtonDate(View view){
        Intent intent = new Intent(AddListActivity.this, ModifyDatetime.class);
        intent.putExtra("Datetime",datebtn.getText());
        startActivity(intent);
    }

    public void onClickButtonAdd(View view){
        ContentValues values = new ContentValues();
        values.put("date",datebtn.getText().toString());
        values.put("place",place.getText().toString());
        values.put("eat",eat.getText().toString());
        values.put("category",spinner.getSelectedItemPosition());

        if(temp!=null) {
            //System.out.println("TEMP:"+ RealPathUtil.getRealPath(this,temp));
            values.put("uri",RealPathUtil.getRealPath(this, temp));
        }

        if(flag){
            db.update("diary",values,"date=? AND place=? AND eat=? AND category=?",
                    new String[]{_date,_place,_eat,Integer.toString(_category)});
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
                temp = data.getData();
                imgbtn.setImageURI(temp);
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
