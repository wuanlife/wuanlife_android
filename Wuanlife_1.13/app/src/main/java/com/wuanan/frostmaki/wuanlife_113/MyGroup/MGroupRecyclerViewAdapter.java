package com.wuanan.frostmaki.wuanlife_113.MyGroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel.BaseActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Constant;
import com.wuanan.frostmaki.wuanlife_113.Utils.ImageLoader;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/13.
 */
public class MGroupRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements View.OnClickListener {
    private Context mContext;
    private Activity activity;
    private ArrayList<Postlist> arraylist;
    private ArrayList<JoinCreateGroup> jlist;
    private ArrayList<JoinCreateGroup> clist;
    private static final int TYPE_HEADER_ITEM_1 = 3;
    private static final int TYPE_HEADER_ITEM_0 = 2;
    private static final int TYPE_NORMAL_ITEM = 0;  //普通Item
    private static final int TYPE_FOOTER_ITEM = 1;  //底部FooterView


    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 1;
    //正在加载中
    public static final int LOADING_MORE = 2;
    //默认为0
    private int load_more_status = 0;

    public MGroupRecyclerViewAdapter(Context context,Activity activity,
                                     ArrayList<Postlist> arrayList) {
        this.activity=activity;
        this.mContext = context;
        this.arraylist = arrayList;
        jlist= MyApplication.getJoinGroupArrayList();
        clist=MyApplication.getCreateGroupArrayList();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.postsitem, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        } else if (viewType == TYPE_FOOTER_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout
                    .listview_footer, parent, false);
            FooterViewHolder FviewHolder = new FooterViewHolder(view);

            return FviewHolder;
        } else if (viewType == TYPE_HEADER_ITEM_0) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content, parent, false);
            HeaderViewHolder HviewHolder = new HeaderViewHolder(view);

            return HviewHolder;
        }else if (viewType == TYPE_HEADER_ITEM_1) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content_create, parent, false);
            CHeaderViewHolder CHviewHolder = new CHeaderViewHolder(view);

            return CHviewHolder;
        }
        return null;
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
                    if (mOnItemClickListener != null) {
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


        } else if (holder instanceof FooterViewHolder) {
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


        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder hviewHolder = (HeaderViewHolder) holder;
            if (jlist != null) {
                hviewHolder.join_linearLayout.setVisibility(View.VISIBLE);
                if (jlist.size() > 0) {

                    hviewHolder.join_imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, BaseActivity.class);
                            intent.putExtra("code", 3);
                            activity.startActivity(intent);
                        }
                    });

                    hviewHolder.join_button_1.setVisibility(View.VISIBLE);
                    hviewHolder.join_button_1.setText(jlist.get(0).getName());
                    hviewHolder.join_button_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = jlist.get(0).getId();
                            String name = jlist.get(0).getName();
                            openThisGroup(id, name);
                        }
                    });
                    if (jlist.size() > 1) {
                        hviewHolder.join_button_2.setVisibility(View.VISIBLE);
                        hviewHolder.join_button_2.setText(jlist.get(1).getName());
                        hviewHolder.join_button_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = jlist.get(1).getId();
                                String name = jlist.get(1).getName();
                                openThisGroup(id, name);
                            }
                        });
                    }
                }
            } else {
                hviewHolder.join_linearLayout.setVisibility(View.GONE);
            }
        } else if (holder instanceof CHeaderViewHolder) {
            CHeaderViewHolder chviewHolder = (CHeaderViewHolder) holder;
            if (jlist != null) {
                chviewHolder.create_linearLayout.setVisibility(View.VISIBLE);
                if (jlist.size() > 0) {

                    chviewHolder.create_imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(activity, BaseActivity.class);
                            intent.putExtra("code", 3);
                            activity.startActivity(intent);
                        }
                    });

                    chviewHolder.create_button_1.setVisibility(View.VISIBLE);
                    chviewHolder.create_button_1.setText(clist.get(0).getName());
                    chviewHolder.create_button_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int id = clist.get(0).getId();
                            String name = clist.get(0).getName();
                            openThisGroup(id, name);
                        }
                    });
                    if (jlist.size() > 1) {
                        chviewHolder.create_button_2.setVisibility(View.VISIBLE);
                        chviewHolder.create_button_2.setText(clist.get(1).getName());
                        chviewHolder.create_button_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                int id = clist.get(1).getId();
                                String name = clist.get(1).getName();
                                openThisGroup(id, name);
                            }
                        });
                    }
                }
            } else {
                chviewHolder.create_linearLayout.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public int getItemCount() {
        return arraylist.size() + 3;
    }

    public int getItemViewType(int position) {
        // 如果position+1等于整个布局所有数总和就是底部布局
        if (position + 1 == arraylist.size()+1) {
            return TYPE_FOOTER_ITEM;
        } else if (position==0){
            return TYPE_HEADER_ITEM_0;
        } else if (position==1){
            return TYPE_HEADER_ITEM_1;
        }else {
            return TYPE_NORMAL_ITEM;
        }
    }

    public void setMoreStatus(int status){
        load_more_status=status;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
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
 * 顶部View——1布局
 */
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout join_linearLayout;
        public TextView join_textView;
        public ImageButton join_imageButton;
        public Button join_button_1;
        public Button join_button_2;

        public HeaderViewHolder(View view) {
        super(view);
            join_textView= (TextView) view.findViewById(R.id.name);
            join_imageButton= (ImageButton) view.findViewById(R.id.more);
            join_button_1= (Button) view.findViewById(R.id.group_1);
            join_button_2= (Button) view.findViewById(R.id.group_2);
            join_linearLayout= (LinearLayout) view.findViewById(R.id.content_fragment);

            join_textView.setText("已加入的星球");
            join_button_1.setVisibility(View.GONE);
            join_button_2.setVisibility(View.GONE);

            join_button_1.setTextColor(Color.BLACK);
            join_button_2.setTextColor(Color.BLACK);
    }
}

    public static class CHeaderViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout create_linearLayout;
        public TextView create_textView;
        public ImageButton create_imageButton;
        public Button create_button_1;
        public Button create_button_2;

        public CHeaderViewHolder(View view) {
            super(view);
            create_textView= (TextView) view.findViewById(R.id.name);
            create_imageButton= (ImageButton) view.findViewById(R.id.more_create);
            create_button_1= (Button) view.findViewById(R.id.create_group_1);
            create_button_2= (Button) view.findViewById(R.id.create_group_2);
            create_linearLayout= (LinearLayout) view.findViewById(R.id.content_fragment);

            create_textView.setText("已创建的星球");
            create_button_1.setVisibility(View.GONE);
            create_button_2.setVisibility(View.GONE);

            create_button_1.setTextColor(Color.BLACK);
            create_button_2.setTextColor(Color.BLACK);
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

    private void openThisGroup(int id,String name) {
        Intent intent=new Intent(activity, GroupPostsActivity.class);
        intent.putExtra("group_id",id);
        intent.putExtra("group_name",name);

        activity.startActivity(intent);
    }
}

