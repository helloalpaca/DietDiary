package com.minseon.dietdiary.myadapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.storage.StorageManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.minseon.dietdiary.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImageCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private int layout;
    private static Context context;
    private String[] from;
    private int[] to;

    public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.layout = layout;
        this.context = context;
        this.from = from;
        this.to = to;
    }

    public Cursor getC(){
        return c;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(layout, null);
        }
        this.c.moveToPosition(pos);

        String eat = c.getString(c.getColumnIndex(from[0]));
        int category = c.getInt(c.getColumnIndex(from[1]));
        String uri = c.getString(c.getColumnIndex(from[2]));

        ImageView imageView = (ImageView) v.findViewById(to[2]);
        TextView textView1 = (TextView) v.findViewById(to[0]);
        TextView textView2 = (TextView) v.findViewById(to[1]);

        Resources res = v.getResources();
        String[] strs = res.getStringArray(R.array.spinner_array);

        if(uri!=null){
            System.out.println("[IMAGECURSORADAPTER] uri : "+uri);
            imageView.setImageURI(Uri.parse(uri));
        }

        textView1.setText(eat);
        textView2.setText(strs[category]);
        if(category==3) textView2.setTextColor(v.getResources().getColor(R.color.Palette2Color2));
        else if(category==4) textView2.setTextColor(v.getResources().getColor(R.color.Palette2Color3));
        else textView2.setTextColor(v.getResources().getColor(R.color.Palette2Color1));

        return (v);
    }

}