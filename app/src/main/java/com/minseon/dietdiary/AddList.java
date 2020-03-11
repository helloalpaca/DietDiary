package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.InputStream;

import static com.minseon.dietdiary.MainActivity.calendar;
import static com.minseon.dietdiary.SplashActivity.db;
import static com.minseon.dietdiary.MainActivity.format;

public class AddList extends AppCompatActivity {

    static final int REQUEST_CODE = 0;
    Spinner spinner;
    EditText place, eat;
    Button btn;
    ImageButton imgbtn;
    static Button datebtn;
    boolean flag = false;
    String ex_date, ex_place, ex_eat, ex_uri;
    int ex_category;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        datebtn = (Button)findViewById(R.id.addlist_btn_date);
        spinner = (Spinner)findViewById(R.id.addlist_spinner);
        place = (EditText)findViewById(R.id.addlist_edit_place);
        eat = (EditText)findViewById(R.id.addlist_edit_eat);
        btn = (Button)findViewById(R.id.addlist_btn);
        imgbtn = (ImageButton)findViewById(R.id.addlist_imgbtn);

        Intent intent = getIntent();
        ex_date = intent.getStringExtra("date");
        ex_place = intent.getStringExtra("place");
        ex_eat = intent.getStringExtra("eat");
        ex_uri = intent.getStringExtra("uri");
        ex_category = intent.getIntExtra("category",0);

        System.out.println("date!! : "+ex_date);
        System.out.println("place!! : "+ex_place);
        System.out.println("eat!! : "+ex_eat);
        System.out.println("uri!! : "+ex_uri);
        System.out.println("category!! : "+ex_category);

        if(ex_date!=null|| ex_place!=null || ex_eat!=null || ex_uri!=null ) flag = true;

        if(ex_date==null) { datebtn.setText(format.format(calendar.getTime()).toString()); }
        else { datebtn.setText(ex_date); }

        if(ex_uri!=null){
            Uri tempuri = Uri.parse(ex_uri);
            imgbtn.setImageURI(tempuri);
        }

        spinner.setSelection(ex_category);
        place.setText(ex_place);
        eat.setText(ex_eat);

        System.out.println("flag!! : "+flag);
        System.out.println("spinner!! : "+spinner.getSelectedItemPosition());

    }

    public void onClickButtonDate(View view){
        Intent intent = new Intent(AddList.this, ModifyDatetime.class);
        intent.putExtra("Datetime",datebtn.getText());
        startActivity(intent);
    }

    public void onClickButtonAdd(View view){
        ContentValues values = new ContentValues();
        values.put("date",datebtn.getText().toString());
        values.put("place",place.getText().toString());
        values.put("eat",eat.getText().toString());
        values.put("category",spinner.getSelectedItemPosition());
        System.out.println("spinner!! : "+spinner.getSelectedItemPosition());

        if(uri!=null) values.put("uri",getRealPathFromURI(uri).toString());

        if(flag){
            System.out.println("ㄷㅡㄹ어와땅!");
            db.update("diary",values,"date=? AND place=? AND eat=? AND category=?", new String[]{ex_date,ex_place,ex_eat,Integer.toString(ex_category)});
        } else {
            db.insert("diary",null,values);
        }

        Intent intent = new Intent(AddList.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void onClickButtonImg(View view){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Uri temp = data.getData();
                //uri = getRealPathFromURI(temp);
                uri = data.getData();
                System.out.println("URI!:"+uri);
                try{
                    InputStream in = getContentResolver().openInputStream(temp);
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    imgbtn.setImageBitmap(img);
                }catch(Exception e) { }
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

    private Uri getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return Uri.parse(result);
    }
}
