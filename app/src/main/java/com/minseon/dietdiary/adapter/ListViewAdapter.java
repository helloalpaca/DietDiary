package com.minseon.dietdiary.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.minseon.dietdiary.R;
import com.minseon.dietdiary.domain.ListViewItem;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListViewItem> list = new ArrayList<ListViewItem>() ;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public Image getImg(int position) { return list.get(position).getImg(); }
    public String getStr(int position) { return list.get(position).getStr(); }

    public void addItem(Image img,String str){
        ListViewItem item = new ListViewItem();

        item.setImg(img);
        item.setStr(str);

        list.add(item);
    }

    public void addItem(String str){
        ListViewItem item = new ListViewItem();

        item.setImg(null);
        item.setStr(str);

        list.add(item);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos=position;
        final Context context=parent.getContext();

        if(convertView==null){
            final LayoutInflater systemService = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=systemService.inflate(R.layout.listview, parent, false);

        }

        ImageView img = (ImageView)convertView.findViewById(R.id.listview_img);
        TextView txt=(TextView)convertView.findViewById(R.id.listview_txt);

        ListViewItem listViewItem = list.get(position);

        txt.setText(listViewItem.getStr());


        return convertView;

    }
}
