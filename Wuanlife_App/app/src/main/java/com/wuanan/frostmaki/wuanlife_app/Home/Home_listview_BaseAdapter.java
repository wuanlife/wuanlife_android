package com.wuanan.frostmaki.wuanlife_app.Home;

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
 * Created by Frostmaki on 2016/7/16.
 */
public class Home_listview_BaseAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<String> my_planet_arraylist;


    public Home_listview_BaseAdapter(Context context,ArrayList<String> arrayList){
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
            viewHolder.home_listview_image=
                    (ImageView) convertView.findViewById(R.id.my_planet_listView_item_image);
            viewHolder.home_listview_text=
                    (TextView) convertView.findViewById(R.id.my_planet_listView_item_text);
            viewHolder.home_listview_title=
                    (TextView) convertView.findViewById(R.id.my_planet_listView_item_title);
            viewHolder.home_listview_image.setImageResource(R.drawable.ic_launcher);
            viewHolder.home_listview_title.setText("首页的帖子de标题"+my_planet_arraylist.get(position));
            viewHolder.home_listview_text.setText("首页的帖子的内容"+my_planet_arraylist.get(position));

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView home_listview_image;
        TextView home_listview_text;
        TextView home_listview_title;
    }
}
