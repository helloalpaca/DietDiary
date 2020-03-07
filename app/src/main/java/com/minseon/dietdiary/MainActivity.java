package com.minseon.dietdiary;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DBHelper helper;
    static SQLiteDatabase db;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        ListView listView =(ListView)findViewById(R.id.main_listview);

        helper = new DBHelper(MainActivity.this, "person.db", null, 1);
        db = helper.getWritableDatabase();
        helper.onCreate(db);


        Cursor c = db.query("diary",null,null,null,null,null,null,null);
        String[] strs = new String[]{"eat"};
        int[] ints = new int[] {R.id.listview_txt};

        adapter = new SimpleCursorAdapter(listView.getContext(), R.layout.listview, c, strs, ints,0);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) adapter.getItem(position);
                String msg1 = cursor.getString(cursor.getColumnIndex("date"));
                String msg2 = cursor.getString(cursor.getColumnIndex("place"));
                String msg3 = cursor.getString(cursor.getColumnIndex("eat"));
                //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, Display.class);
                intent.putExtra("date",msg1);
                intent.putExtra("place",msg2);
                intent.putExtra("eat",msg3);
                startActivity(intent);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddList.class);
                startActivity(intent);
            }
        });
    }
}
