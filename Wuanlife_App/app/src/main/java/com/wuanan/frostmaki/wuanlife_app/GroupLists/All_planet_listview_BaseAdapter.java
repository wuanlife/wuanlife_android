package com.wuanan.frostmaki.wuanlife_app.GroupLists;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;
import java.util.HashMap;

public class All_planet_listview_BaseAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<HashMap<String,String>> arraylist;
    private Button currentPage;

    public All_planet_listview_BaseAdapter(Context context,ArrayList<HashMap<String,String>> arrayList,
                                           Button currenPage){
        mContext=context;
        this.arraylist=arrayList;
        this.currentPage=currenPage;
    }
    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(mContext).inflate(
                    R.layout.all_planet_listview_item,parent,false);
            viewHolder.all_planet_listview_image=
                    (ImageView) convertView.findViewById(R.id.all_planet_listView_item_image);
            viewHolder.all_planet_listview_title=
                    (TextView) convertView.findViewById(R.id.all_planet_listView_item_title);
            viewHolder.all_planet_listview_text=
                    (TextView) convertView.findViewById(R.id.all_planet_listView_item_text);


            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.all_planet_listview_image.setImageResource(R.drawable.ic_null);
        viewHolder.all_planet_listview_title.setText(arraylist.get(position).get("title"));
        viewHolder.all_planet_listview_text.setText(arraylist.get(position).get("text"));
        currentPage.setText(arraylist.get(position).get("currentPage")+" / "+arraylist.get(position).get("pageCount"));
        return convertView;
    }

    class ViewHolder{
        ImageView all_planet_listview_image;
        TextView all_planet_listview_title;
        TextView all_planet_listview_text;
    }
}
