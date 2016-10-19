package com.wuanan.frostmaki.wuanlife_113.Home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Constant;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frostmaki on 2016/10/13.
 */
public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<Postlist> arraylist;
    private static final int TYPE_NORMAL_ITEM = 0;  //普通Item
    private static final int TYPE_FOOTER_ITEM = 1;  //底部FooterView


    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 1;
    //正在加载中
    public static final int LOADING_MORE = 2;
    //默认为0
    private int load_more_status = 0;

    public HomeRecyclerViewAdapter(Context context,
                                   ArrayList<Postlist> arrayList) {
        this.mContext = context;
        this.arraylist = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.postsitem, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout
                    .listview_footer, parent, false);
            FooterViewHolder FviewHolder = new FooterViewHolder(view);

            return FviewHolder;
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder hviewHolder = (ViewHolder) holder;
            hviewHolder.home_posts_title.setText(arraylist.get(position).getTitle());
            hviewHolder.home_posts_nickname.setText(arraylist.get(position).getNickname());
            hviewHolder.home_posts_groupname.setText(arraylist.get(position).getGroupName());
            hviewHolder.home_posts_createTime.setText(arraylist.get(position).getCreateTime());
            hviewHolder.home_posts_text.setText(arraylist.get(position).getText());

            hviewHolder.image_linear.setVisibility(View.GONE);
            if (arraylist.get(position).getImage() != null) {
                hviewHolder.image_linear.setVisibility(View.VISIBLE);
                if (arraylist.get(position).getImage().size() != hviewHolder.image_linear.getChildCount()) {
                    for (int i = 0; i < arraylist.get(position).getImage().size(); i++) {
                        ImageView imageView = new ImageView(mContext);
                        int width = (int) (Constant.displayWidth * 0.003f);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                (int) (Constant.displayWidth * 0.3f + 0.5f),
                                (int) (Constant.displayWidth * 0.3f + 0.5f));
                        params.setMargins(width, width, width, width);
                        imageView.setLayoutParams(params);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        String urlTag = arraylist.get(position).getImage().get(i);
                        imageView.setTag(urlTag);
                        new ImageLoader().showImageByThread(imageView, urlTag);

                        hviewHolder.image_linear.addView(imageView);
                    }
                }
            }
            //设置点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnItemClickListener != null) {
                        //Returns the position of the ViewHolder in terms of the
                        //latest layout pass.
                        int pos = holder.getLayoutPosition();
                        mOnItemClickListener.OnItemClick(pos);
                    }
                }
            });
//设置长按事件
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemClickListener != null) {
                        int pos = holder.getLayoutPosition();
                        return mOnItemClickListener.OnItemLongClick(pos);
                    }
                    return false;
                }
            });
        } else {
            FooterViewHolder fviewHolder = (FooterViewHolder) holder;
            switch (load_more_status) {
                case PULLUP_LOAD_MORE:
                    fviewHolder.foot_view_item_tv.setVisibility(View.VISIBLE);
                    fviewHolder.foot_view_item_tv.setText("上拉加载更多");
                    fviewHolder.pb.setVisibility(View.GONE);
                    break;
                case LOADING_MORE:
                    fviewHolder.foot_view_item_tv.setVisibility(View.GONE);
                    fviewHolder.pb.setVisibility(View.VISIBLE);
                    break;
            }
        }


    }

    @Override
    public int getItemCount() {
        return arraylist.size() + 1;
    }

    public int getItemViewType(int position) {
        // 如果position+1等于整个布局所有数总和就是底部布局
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER_ITEM;
        } else {
            return TYPE_NORMAL_ITEM;
        }
    }

    public void setMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView home_posts_title;
        TextView home_posts_nickname;
        TextView home_posts_groupname;
        TextView home_posts_createTime;
        TextView home_posts_text;
        LinearLayout image_linear;

        public ViewHolder(View itemView) {
            super(itemView);
            home_posts_title =
                    (TextView) itemView.findViewById(R.id.title);
            home_posts_nickname =
                    (TextView) itemView.findViewById(R.id.nickname);
            home_posts_groupname = (TextView) itemView.findViewById(R.id.groupName);
            home_posts_createTime = (TextView) itemView.findViewById(R.id.createTime);
            home_posts_text = (TextView) itemView.findViewById(R.id.text);
            image_linear = (LinearLayout) itemView.findViewById(R.id.postitemImage_linear);
        }


    }


    /**
     * 底部FooterView布局
     */
    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView foot_view_item_tv;
        public ProgressBar pb;

        public FooterViewHolder(View view) {
            super(view);
            pb = (ProgressBar) view.findViewById(R.id.pull_to_refresh_load_progress);
            foot_view_item_tv = (TextView) view.findViewById(R.id.pull_to_refresh_loadmore_text);
        }
    }

    //回调
    public interface OnItemClickListener {
        //点击事件
        void OnItemClick(int position);

        //长按事件
        boolean OnItemLongClick(int position);
    }

    private OnItemClickListener mOnItemClickListener;

    //设置回调
    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

