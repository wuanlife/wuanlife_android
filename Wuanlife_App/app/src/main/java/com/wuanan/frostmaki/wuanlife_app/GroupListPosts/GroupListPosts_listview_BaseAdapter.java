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
            Log.e("GroupListPosts.size()",arraylist.size()+"");
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
                viewHolder.posts_image = (ImageView) convertView.findViewById(R.id.posts_image);

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
            Log.e("image1",arraylist.get(position).get("image1"));
            Log.e("image2",arraylist.get(position).get("image2"));
            /*try {
                //URL url=new URL(arraylist.get(position).get("image"));
                Uri uri=Uri.parse(arraylist.get(position).get("image"));
                viewHolder.home_posts_image.setImageURI(uri);
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }*/
            //(arraylist.get(position).get("image"));
        }

        return convertView;
    }

    class ViewHolder{
        TextView posts_title;
        TextView posts_nickname;
        TextView posts_groupname;
        TextView posts_createTime;
        TextView posts_text;
        ImageView posts_image;
    }
}
