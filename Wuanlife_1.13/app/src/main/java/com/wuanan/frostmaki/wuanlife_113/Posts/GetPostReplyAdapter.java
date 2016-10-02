package com.wuanan.frostmaki.wuanlife_113.Posts;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/31.
 */
public class GetPostReplyAdapter extends BaseAdapter {
    ArrayList<HashMap<String,String>> arrayList;
    Context mContext;
    TextView replyCount;
    TextView currentPage;

    public GetPostReplyAdapter(ArrayList<HashMap<String,String>> arrayList,
                              Context context,TextView textView1,TextView textView2){
        this.arrayList=arrayList;
        mContext=context;
replyCount=textView1;
        currentPage=textView2;
    }
    @Override
    public int getCount() {
        return arrayList.size();
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
        if (arrayList==null){
            Log.e("GetPostReplyadapter","   arraylist为空");
        }else {
            Log.e("GetPostReplyadapter","   arraylist"+arrayList.size());
            if (convertView==null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.postreply_listview_item, parent, false);
                viewHolder.nickname = (TextView) convertView.findViewById(R.id.reply_nickname);
                viewHolder.createTime = (TextView) convertView.findViewById(R.id.reply_createTime);
                viewHolder.text = (TextView) convertView.findViewById(R.id.replylistText);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.nickname.setText(arrayList.get(position).get("nickname"));
            viewHolder.createTime.setText(arrayList.get(position).get("createTime"));
            viewHolder.text.setText(arrayList.get(position).get("text"));
            replyCount.setText(arrayList.get(position).get("replyCount")+" 个回复");
            currentPage.setText(arrayList.get(position).get("currentPage")+" / "+
                    arrayList.get(position).get("pageCount"));
        }
        return convertView;
    }


    class ViewHolder{
        TextView nickname;
        TextView createTime;
        TextView text;
    }
}
