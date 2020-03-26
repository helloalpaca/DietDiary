package com.minseon.dietdiary.myadapter;

import android.media.Image;

public class ListViewItem {

    private Image img ;
    private String str;

    public void setImg(Image _img) { img = _img; }
    public void setStr(String _str){ str = _str; }
    public Image getImg() { return this.img; }
    public String getStr() { return this.str; }
}
