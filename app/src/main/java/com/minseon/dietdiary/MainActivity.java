package com.minseon.dietdiary;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.minseon.dietdiary.myadapter.ImageCursorAdapter;
import com.minseon.dietdiary.mylistener.CustomOnItemClickListener;
import com.minseon.dietdiary.mylistener.CustomTouchListener;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.minseon.dietdiary.AddListActivity.REQUEST_CODE;
import static com.minseon.dietdiary.SplashActivity.db;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    /* date format */
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
    static final SimpleDateFormat formatdate = new SimpleDateFormat("yyyy-MM-dd",Locale.KOREA);
    static final SimpleDateFormat formatmain = new SimpleDateFormat("MM월 dd일",Locale.KOREA);

    static ListView listView;
    static Button daybtn;
    DrawerLayout drawLayout;
    NavigationView navigationView;

    static ImageCursorAdapter adapter;
    static Calendar calendar;
    static Context applicationContext;
    static ContentResolver cr;

    String[] permission_list = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* set toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /* get R.id */
        daybtn = (Button)findViewById(R.id.main_btn);
        listView = (ListView)findViewById(R.id.main_listview);
        drawLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        /* get permission */
        ActivityCompat.requestPermissions(MainActivity.this, permission_list,  1);

        /* set Variables */
        calendar = Calendar.getInstance();
        applicationContext = getApplication();
        cr = getContentResolver();

        /* navigationdrawer click listener */
        navigationView.setNavigationItemSelectedListener(this);

        queryDB();

        /* listView click listener */
        listView.setOnItemClickListener(this);
        listView.setOnTouchListener(new CustomTouchListener());

        /* Admob */
        AdView adView = (AdView)findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }

    public void onClickButtonDayBefore(View view){
        DayBefore();
    }

    public void onClickButtonDayAfter(View view){
        DayAfter();
    }

    public void onClickButtonMainAdd(View view){
        Intent intent = new Intent(MainActivity.this, AddListActivity.class);
        startActivity(intent);
    }

    public void onClickButtonCalendar(View view){
        Intent intent = new Intent(MainActivity.this, CalendarActivity.class);
        startActivity(intent);
    }

    /* query DB by date */
    static public void queryDB(){
        String day = formatdate.format(calendar.getTime());
        daybtn.setText(formatmain.format(calendar.getTime()));

        /* query */
        String sql = "SELECT * FROM diary WHERE date BETWEEN '"+day+" 00:00:00' AND '"+day+" 23:59:59' order by date";
        final Cursor c = db.rawQuery(sql,null);

        String[] strs = new String[]{"eat", "category", "uri"};
        int[] ints = new int[] {R.id.listview_txt, R.id.listview_txt2, R.id.listview_img};
        adapter = new ImageCursorAdapter(listView.getContext(), R.layout.listview, c, strs, ints);

        listView.setAdapter(adapter);
    }

    static public void DayBefore(){
        calendar.add(Calendar.DAY_OF_MONTH,-1);
        queryDB();
    }

    static public void DayAfter(){
        calendar.add(Calendar.DAY_OF_MONTH,1);
        queryDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_drawer){
            drawLayout.openDrawer(GravityCompat.END);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_export) {
            exportJson();
            Toast.makeText(this,"데이터를 내보냈습니다.",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_import) {
            importJson();
            Toast.makeText(this,"데이터를 가져왔습니다.",Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_splash) {
            changeSplash();
        } else if (id == R.id.nav_rating) {
            playStore();
        } else if (id == R.id.nav_email) {
            bugReport();
        }

        /*DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    /* start DisplayActivity */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Cursor cursor = (Cursor) adapter.getItem(position);
        String clicked_date = cursor.getString(cursor.getColumnIndex("date"));
        String clicked_place = cursor.getString(cursor.getColumnIndex("place"));
        String clicked_eat = cursor.getString(cursor.getColumnIndex("eat"));
        String clicked_uri = cursor.getString(cursor.getColumnIndex("uri"));
        int clicked_category = cursor.getInt(cursor.getColumnIndex("category"));

        Intent intent = new Intent(MainActivity.this, DisplayActivity.class);
        intent.putExtra("date", clicked_date);
        intent.putExtra("place", clicked_place);
        intent.putExtra("eat", clicked_eat);
        intent.putExtra("uri", clicked_uri);
        intent.putExtra("category", clicked_category);

        startActivity(intent);
    }

    /* export db in .csv format */
    public void exportJson()  {
        /* get directory */
        File emulated = Environment.getExternalStorageDirectory();
        File dir = new File(emulated.getAbsolutePath() + "/dietdiary");
        dir.mkdirs();

        /* make file */
        File file = new File(dir,  "backup.csv");
        FileWriter fw = null;
        try {
            fw = new FileWriter(file, false);
            fw.write("date"+","+"place"+","+"eat"+","+"category"+"\n"); // write categories

            /* query */
            String sql = "SELECT * FROM diary";
            Cursor c = db.rawQuery(sql,null);
            while(c.moveToNext()){
                String strDate = c.getString(c.getColumnIndex("date"));
                String strPlace = c.getString(c.getColumnIndex("place"));
                String strEat = c.getString(c.getColumnIndex("eat"));
                int strCategory = c.getInt(c.getColumnIndex("category"));

                /* write file */
                try {
                    fw.write(strDate+","+strPlace+","+strEat+","+strCategory+"\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /* import db */
    public void importJson(){
        Intent intent = new Intent();
        intent.setType("text/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_CODE);
    }

    /* start changeSplashActivity to change splash text*/
    public void changeSplash(){
        Intent intent = new Intent(this, ChangeSplashActivity.class);
        startActivity(intent);
    }

    /* start playstore */
    public void playStore() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(
                "https://play.google.com/store/apps/details?id=com.minseon.dietdiary"));
        intent.setPackage("com.android.vending");
        startActivity(intent);
    }

    /* bug report to developer by mail */
    public void bugReport(){
        /* get app version */
        String appVersion = "";
        try {
            PackageInfo pInfo = applicationContext.getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }

        /* set report text */
        String[] address = {"mskwon16@address.com"};
        String reportSubject = "<다다일기 문의>";
        String reportText = "앱 버전 : " + appVersion + "\n기기명 : \n안드로이드 OS 버전 : \n문의내용 : \n";

        /* start mail activity */
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");

        intent.putExtra(Intent.EXTRA_EMAIL, address);
        intent.putExtra(Intent.EXTRA_SUBJECT,reportSubject);
        intent.putExtra(Intent.EXTRA_TEXT, reportText);

        startActivity(intent);
    }

    /* importJson startActivityForResult */
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
                            values.put("date", record[0]);
                            values.put("place", record[1]);
                            values.put("eat", record[2]);
                            values.put("category", Integer.parseInt(record[3]));

                            db.insert("diary",null,values);
                        } else { break; }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
            else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "파일 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }
}
