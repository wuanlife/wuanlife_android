package com.wuanan.frostmaki.wuanlife_app.My_planet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/7/14.
 */
public class My_planet_listview_BaseAdapter extends BaseAdapter{

    private Context mContext;
    private ArrayList<String> my_planet_arraylist;


    public My_planet_listview_BaseAdapter(Context context,ArrayList<String> arrayList){
        mContext=context;
        my_planet_arraylist=arrayList;
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
                    R.layout.my_planet_listview_item,parent,false);
            viewHolder.my_planet_listview_image=
                    (ImageView) convertView.findViewById(R.id.my_planet_listView_item_image);
            viewHolder.my_planet_listview_text=
                    (TextView) convertView.findViewById(R.id.my_planet_listView_item_text);
            viewHolder.my_planet_listview_title=
                    (TextView) convertView.findViewById(R.id.my_planet_listView_item_title);
            viewHolder.my_planet_listview_image.setImageResource(R.drawable.ic_launcher);
            viewHolder.my_planet_listview_title.setText("我的星球的帖子de标题"+my_planet_arraylist.get(position));
            viewHolder.my_planet_listview_text.setText("我的星球的帖子的内容"+my_planet_arraylist.get(position));

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView my_planet_listview_image;
        TextView my_planet_listview_text;
        TextView my_planet_listview_title;
    }
}
