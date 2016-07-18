package com.wuanan.frostmaki.wuanlife_app.All_planet;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;

public class All_planet_listview_BaseAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> all_planet_arraylist;


    public All_planet_listview_BaseAdapter(Context context,ArrayList<String> arrayList){
        mContext=context;
        all_planet_arraylist=arrayList;
    }
    @Override
    public int getCount() {
        return 10;
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
            viewHolder.all_planet_listview_text=
                    (TextView) convertView.findViewById(R.id.all_planet_listView_item_text);
            viewHolder.all_planet_listview_image.setImageResource(R.drawable.ic_null);
            viewHolder.all_planet_listview_text.setText(all_planet_arraylist.get(position));

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView all_planet_listview_image;
        TextView all_planet_listview_text;
    }
}
