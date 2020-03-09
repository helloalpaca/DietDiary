package com.minseon.dietdiary;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.InputStream;

public class ImageCursorAdapter extends SimpleCursorAdapter {

    private Cursor c;
    private Context context;

    public ImageCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.c = c;
        this.context = context;
    }

    public View getView(int pos, View inView, ViewGroup parent) {
        View v = inView;
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.listview, null);
        }
        this.c.moveToPosition(pos);
        String date = this.c.getString(this.c.getColumnIndex("date"));
        String place = this.c.getString(this.c.getColumnIndex("place"));
        String eat = this.c.getString(this.c.getColumnIndex("eat"));
        String struri = this.c.getString(this.c.getColumnIndex("uri"));
        ImageView iv = (ImageView) v.findViewById(R.id.listview_img);
        if (struri != null) { iv.setImageURI(Uri.parse(struri)); }
        else { iv.setVisibility(inView.GONE); }
        TextView txt = (TextView) v.findViewById(R.id.listview_txt);
        txt.setText(eat);
        return (v);
    }
}