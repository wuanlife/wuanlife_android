package com.wuanan.frostmaki.wuanlife_app.Utils;

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
 * Created by Frostmaki on 2016/7/16.
 */
public class Posts_listview_BaseAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String,String>> arraylist;
    private Button currentPage;


    public Posts_listview_BaseAdapter(Context context,
                                      ArrayList<HashMap<String,String>> arrayList,
                                      Button currentPage){
        mContext=context;
        this.arraylist=arrayList;
        this.currentPage=currentPage;
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
                    convertView = LayoutInflater.from(mContext).inflate(
                            R.layout.posts_listview_item, parent, false);

                    viewHolder.home_posts_title =
                            (TextView) convertView.findViewById(R.id.posts_title);
                    viewHolder.home_posts_nickname =
                            (TextView) convertView.findViewById(R.id.posts_nickname);
                    viewHolder.home_posts_groupname = (TextView) convertView.findViewById(R.id.posts_groupname);
                    viewHolder.home_posts_createTime = (TextView) convertView.findViewById(R.id.posts_createTtime);
                    viewHolder.home_posts_text = (TextView) convertView.findViewById(R.id.posts_text);
                    viewHolder.home_posts_image01 = (ImageView) convertView.findViewById(R.id.posts_image01);
                    viewHolder.home_posts_image02 = (ImageView) convertView.findViewById(R.id.posts_image02);
                    viewHolder.home_posts_image03 = (ImageView) convertView.findViewById(R.id.posts_image03);

                    convertView.setTag(viewHolder);

                } else {
                    viewHolder = (ViewHolder) convertView.getTag();
                }
            viewHolder.home_posts_title.setText(arraylist.get(position).get("title"));
            viewHolder.home_posts_nickname.setText(arraylist.get(position).get("nickname"));
            viewHolder.home_posts_groupname.setText(arraylist.get(position).get("groupName"));
            viewHolder.home_posts_createTime.setText(arraylist.get(position).get("createTime"));
            viewHolder.home_posts_text.setText(arraylist.get(position).get("text"));
            currentPage.setText(arraylist.get(position).get("currentPage")+" / "+
                    arraylist.get(position).get("pageCount"));

            viewHolder.home_posts_image01.setVisibility(View.GONE);
            String urlTag01=arraylist.get(position).get("image01");
            if (urlTag01!=null) {
                Log.e("urlTag01",urlTag01);
                viewHolder.home_posts_image01.setVisibility(View.VISIBLE);
                viewHolder.home_posts_image01.setTag(urlTag01);
                new ImageLoader().showImageByThread(viewHolder.home_posts_image01, urlTag01);
            }

            viewHolder.home_posts_image02.setVisibility(View.GONE);
            String urlTag02=arraylist.get(position).get("image02");
            if (urlTag02!=null) {
                viewHolder.home_posts_image02.setVisibility(View.VISIBLE);
                viewHolder.home_posts_image02.setTag(urlTag02);
                new ImageLoader().showImageByThread(viewHolder.home_posts_image02, urlTag02);
            }

            viewHolder.home_posts_image03.setVisibility(View.GONE);
            String urlTag03=arraylist.get(position).get("image03");
            if (urlTag03!=null) {
                viewHolder.home_posts_image03.setVisibility(View.VISIBLE);
                viewHolder.home_posts_image03.setTag(urlTag03);
                new ImageLoader().showImageByThread(viewHolder.home_posts_image03, urlTag03);
            }


             //  String urlTag01=arraylist.get(position).get("image01");
             //  viewHolder.home_posts_image01.setTag(urlTag01);
              //  AsyncTaskImageUtils asyncTaskImageUtils = new AsyncTaskImageUtils(viewHolder.home_posts_image01);
                //asyncTaskImageUtils.execute("http://b.hiphotos.baidu.com/image/h%3D360/sign=8918c5efbe3eb1355bc7b1bd961ea8cb/7a899e510fb30f244bb50504ca95d143ad4b038d.jpg");

             //  asyncTaskImageUtils.execute(urlTag01);


        }



        return convertView;
    }



    class ViewHolder{
        TextView home_posts_title;
        TextView home_posts_nickname;
        TextView home_posts_groupname;
        TextView home_posts_createTime;
        TextView home_posts_text;
        ImageView home_posts_image01;
        ImageView home_posts_image02;
        ImageView home_posts_image03;



    }
}
