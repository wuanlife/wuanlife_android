package com.wuanan.frostmaki.wuanlife_113.MyGroup;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import com.wuanan.frostmaki.wuanlife_113.R;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/7.
 */
public class MyJoinCreateGroupAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<JoinCreateGroup> arraylist;
    public MyJoinCreateGroupAdapter(Context c,ArrayList<JoinCreateGroup> s){
        mContext=c;
        arraylist=s;
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
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_myjoinandcreate_buttonitem, parent, false);
            viewHolder.button= (Button) convertView.findViewById(R.id.button);
            viewHolder.button.setText(arraylist.get(position).getName());

            viewHolder.button.setTextColor(Color.BLACK);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }





    private class ViewHolder{
        GridView gridView;
        Button button;
    }
}
