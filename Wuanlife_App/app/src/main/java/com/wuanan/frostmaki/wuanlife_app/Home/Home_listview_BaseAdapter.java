package com.wuanan.frostmaki.wuanlife_app.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Home_listview_BaseAdapter extends BaseAdapter{
    private Context mContext;
    private ArrayList<Map<String,String>> arraylist;
    private Map<String,String> map;


    public Home_listview_BaseAdapter(Context context,ArrayList<Map<String,String>> arrayList,Map<String,String> map){
        mContext=context;
        this.arraylist=arrayList;
        this.map=map;
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
        if (arraylist.size()==0){
            Log.e( "arrayList: ","0" );
        }else {
            Log.e("arrayList.size()",arraylist.size()+"");

                if (convertView == null) {
                    viewHolder = new ViewHolder();
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.posts_listview_item, parent, false);

                    viewHolder.home_posts_title =
                            (TextView) convertView.findViewById(R.id.posts_title);
                    viewHolder.home_posts_nickname =
                            (TextView) convertView.findViewById(R.id.posts_nickname);
                    viewHolder.home_posts_groupname = (TextView) convertView.findViewById(R.id.posts_groupname);
                    viewHolder.home_posts_createTime = (TextView) convertView.findViewById(R.id.posts_createTtime);
                    viewHolder.home_posts_text = (TextView) convertView.findViewById(R.id.posts_text);
                    viewHolder.home_posts_image = (ImageView) convertView.findViewById(R.id.posts_image);

                    //viewHolder.home_listview_image.setImageResource(R.drawable.ic_launcher);

                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
            viewHolder.home_posts_title.setText(arraylist.get(position).get("title"));
            viewHolder.home_posts_nickname.setText(arraylist.get(position).get("nickname"));
            viewHolder.home_posts_groupname.setText(arraylist.get(position).get("groupName"));
            viewHolder.home_posts_createTime.setText(arraylist.get(position).get("createTime"));
            viewHolder.home_posts_text.setText(arraylist.get(position).get("text"));
            //viewHolder.home_posts_image.setImageResource(arraylist.get(i).get("image"));
            }

        return convertView;
    }

    class ViewHolder{
        TextView home_posts_title;
        TextView home_posts_nickname;
        TextView home_posts_groupname;
        TextView home_posts_createTime;
        TextView home_posts_text;
        ImageView home_posts_image;
    }
}
