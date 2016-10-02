package com.wuanan.frostmaki.wuanlife_113.NewView;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class Postcreating {
    private String title;
    private String text;
    private ArrayList<String> imageNetPath;

    public Postcreating(String title, String text, ArrayList<String> arrayList){
        this.title=title;
        this.text=text;
        imageNetPath=arrayList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImageNetPath(ArrayList<String> imageNetPath) {
        this.imageNetPath = imageNetPath;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public ArrayList<String> getImageNetPath() {
        return imageNetPath;
    }
}
