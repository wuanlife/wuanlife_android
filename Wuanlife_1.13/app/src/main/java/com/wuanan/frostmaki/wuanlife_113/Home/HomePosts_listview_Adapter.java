package com.wuanan.frostmaki.wuanlife_113.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/7/16.
 */
public class HomePosts_listview_Adapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Postlist> arraylist;



    public HomePosts_listview_Adapter(Context context,
                                      ArrayList<Postlist> arrayList){
        this.mContext=context;
        this.arraylist=arrayList;

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
            //Log.e("arraylist.size()",arraylist.size()+"");
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView =
                        LayoutInflater.from(mContext).inflate(
                                R.layout.postsitem, parent, false);

                viewHolder.home_posts_title =
                        (TextView) convertView.findViewById(R.id.title);
                viewHolder.home_posts_nickname =
                        (TextView) convertView.findViewById(R.id.nickname);
                viewHolder.home_posts_groupname = (TextView) convertView.findViewById(R.id.groupName);
                viewHolder.home_posts_createTime = (TextView) convertView.findViewById(R.id.createTime);
                viewHolder.home_posts_text = (TextView) convertView.findViewById(R.id.text);
                viewHolder.image_linear = (LinearLayout) convertView.findViewById(R.id.postitemImage_linear);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.home_posts_title.setText(arraylist.get(position).getTitle());
            viewHolder.home_posts_nickname.setText(arraylist.get(position).getNickname());
            viewHolder.home_posts_groupname.setText(arraylist.get(position).getGroupName());
            viewHolder.home_posts_createTime.setText(arraylist.get(position).getCreateTime());
            viewHolder.home_posts_text.setText(arraylist.get(position).getText());

            viewHolder.image_linear.setVisibility(View.GONE);
            if (arraylist.get(position).getImage() != null) {
                viewHolder.image_linear.setVisibility(View.VISIBLE);
                if (arraylist.get(position).getImage().size()!=viewHolder.image_linear.getChildCount()) {
                    for (int i = 0; i < arraylist.get(position).getImage().size(); i++) {
                        ImageView imageView = new ImageView(mContext);
                        String urlTag = arraylist.get(position).getImage().get(i);
                        imageView.setTag(urlTag);
                        new ImageLoader().showImageByThread(imageView, urlTag);

                        viewHolder.image_linear.addView(imageView);
                    }
                }
            }


        }

        return convertView;
    }



    class ViewHolder{
        TextView home_posts_title;
        TextView home_posts_nickname;
        TextView home_posts_groupname;
        TextView home_posts_createTime;
        TextView home_posts_text;


        LinearLayout image_linear;

    }
}
