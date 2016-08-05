package com.wuanan.frostmaki.wuanlife_app.GroupListPosts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/17.
 */
public class GroupListPosts_listview_BaseAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String,String>> arraylist;
    private Button currentPage;

    public GroupListPosts_listview_BaseAdapter(
            Context context, ArrayList<HashMap<String,String>> arrayList, Button button){
        mContext=context;
        this.arraylist=arrayList;
        currentPage=button;
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
            Log.e("GroupListPosts.size()>",arraylist.size()+"");
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.posts_listview_item, parent, false);

                viewHolder.posts_title =
                        (TextView) convertView.findViewById(R.id.posts_title);
                viewHolder.posts_nickname =
                        (TextView) convertView.findViewById(R.id.posts_nickname);
                viewHolder.posts_groupname = (TextView) convertView.findViewById(R.id.posts_groupname);
                viewHolder.posts_createTime = (TextView) convertView.findViewById(R.id.posts_createTtime);
                viewHolder.posts_text = (TextView) convertView.findViewById(R.id.posts_text);
                viewHolder.posts_image01 = (ImageView) convertView.findViewById(R.id.posts_image01);
                viewHolder.posts_image02 = (ImageView) convertView.findViewById(R.id.posts_image02);
                viewHolder.posts_image03 = (ImageView) convertView.findViewById(R.id.posts_image03);
                //viewHolder.home_listview_image.setImageResource(R.drawable.ic_launcher);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.posts_title.setText(arraylist.get(position).get("title"));
            viewHolder.posts_nickname.setText(arraylist.get(position).get("nickname"));
            viewHolder.posts_groupname.setText(arraylist.get(position).get("groupName"));
            viewHolder.posts_createTime.setText(arraylist.get(position).get("createTime"));
            viewHolder.posts_text.setText(arraylist.get(position).get("text"));
            currentPage.setText(arraylist.get(position).get("currentPage")+" / "+
                    arraylist.get(position).get("pageCount"));

            viewHolder.posts_image01.setVisibility(View.GONE);
            String urlTag01=arraylist.get(position).get("image01");
            if (urlTag01!=null) {
                viewHolder.posts_image01.setVisibility(View.VISIBLE);
                viewHolder.posts_image01.setTag(urlTag01);
                new ImageLoader().showImageByThread(viewHolder.posts_image01, urlTag01);
            }

            viewHolder.posts_image02.setVisibility(View.GONE);
            String urlTag02=arraylist.get(position).get("image02");
            if (urlTag02!=null) {
                viewHolder.posts_image02.setVisibility(View.VISIBLE);
                viewHolder.posts_image02.setTag(urlTag02);
                new ImageLoader().showImageByThread(viewHolder.posts_image02, urlTag02);
            }

            viewHolder.posts_image03.setVisibility(View.GONE);
            String urlTag03=arraylist.get(position).get("image03");
            if (urlTag03!=null) {
                viewHolder.posts_image03.setVisibility(View.VISIBLE);
                viewHolder.posts_image03.setTag(urlTag03);
                new ImageLoader().showImageByThread(viewHolder.posts_image03, urlTag03);
            }
        }

        return convertView;
    }

    class ViewHolder{
        TextView posts_title;
        TextView posts_nickname;
        TextView posts_groupname;
        TextView posts_createTime;
        TextView posts_text;
        ImageView posts_image01;
        ImageView posts_image02;
        ImageView posts_image03;
    }
}
