package com.minseon.dietdiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.minseon.dietdiary.AddListActivity.REQUEST_CODE;
import static com.minseon.dietdiary.SplashActivity.db;
import static java.sql.Types.NULL;

public class SettingActivity extends AppCompatActivity {

    Button btn_export, btn_import, btn_changesplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        btn_export = (Button)findViewById(R.id.setting_btn_export);
        btn_import = (Button)findViewById(R.id.setting_btn_import);
        btn_changesplash = (Button)findViewById(R.id.setting_btn_splash);

        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void onClickButtonExportJSON(View view) throws IOException {
        File emulated = Environment.getExternalStorageDirectory();
        File dir = new File(emulated.getAbsolutePath() + "/dietdiary");
        dir.mkdirs();
        File file = new File(dir,  "backup.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            fw.write("date"+","+"place"+","+"eat"+","+"category"+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM diary";
        Cursor c = db.rawQuery(sql,null);
        while(c.moveToNext()){
            String str1 = c.getString(c.getColumnIndex("date"));
            String str2 = c.getString(c.getColumnIndex("place"));
            String str3 = c.getString(c.getColumnIndex("eat"));
            int str4 = c.getInt(c.getColumnIndex("category"));
            try {
                fw.write(str1+","+str2+","+str3+","+str4+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("hello : "+str1 + ", "+str2 + ", "+str3+", "+str4);
        }

        fw.close();
    }

    public void onClickButtonImportJSON(View view){
        Intent intent = new Intent();
        intent.setType("text/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void onClickButtonChangeSplash(View view){
        Intent intent = new Intent(SettingActivity.this, ChangeSplashActivity.class);
        startActivity(intent);
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Uri temp = data.getData();
                InputStream in = null;
                try { in = getContentResolver().openInputStream(temp);
                } catch (FileNotFoundException e) { e.printStackTrace(); }
                InputStreamReader is = new InputStreamReader(in);

                BufferedReader reader = new BufferedReader(is);
                CSVReader read = new CSVReader(reader);
                String[] record = null;
                String nowType = null;
                try {
                    read.readNext();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                while (true){
                    try {
                        if ((record = read.readNext()) != null) {
                            ContentValues values = new ContentValues();
                            values.put("date",record[0]);
                            values.put("place",record[1]);
                            values.put("eat",record[2]);
                            values.put("category", Integer.parseInt(record[3]));
                            System.out.println("record[0] : "+ record[0]);
                            System.out.println("record[1] : "+ record[1]);
                            System.out.println("record[2] : "+ record[2]);
                            System.out.println("record[3] : "+ record[3]);
                            db.insert("diary",null,values);
                        } else { break; }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "파일 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
