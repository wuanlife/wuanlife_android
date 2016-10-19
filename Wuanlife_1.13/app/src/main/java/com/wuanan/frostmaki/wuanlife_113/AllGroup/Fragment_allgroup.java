package com.wuanan.frostmaki.wuanlife_113.AllGroup;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.Home.HomeRecyclerViewAdapter;
import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.MySwipeRefreshLayout;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class Fragment_allgroup extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private View view;
    private TextView name;

    private ListView mlistView;
    private ArrayList<HashMap<String,String>> all_planet_arrayList;
    private AGroupRecyclerViewAdapter adapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;

    private RecyclerView mRecyclerView;
    private ArrayList<HashMap<String,String>> arraylist;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout mRefreshLayout;


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    adapter = new AGroupRecyclerViewAdapter(
                            mContext,
                            arraylist);
                    pageCount= Integer.parseInt(arraylist.get(0).get("pageCount"));
                    adapter.setOnItemClickListener(new AGroupRecyclerViewAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(int position) {
                            //Toast.makeText(mContext, "你点击了第" + position + "个图片",
                            //        Toast.LENGTH_SHORT).show();
                            int group_id=Integer.parseInt(MyApplication.getGroupInfo().get(position).get("id"));
                            String group_name=MyApplication.getGroupInfo().get(position).get("title");
                            Intent intent=new Intent(getActivity(), GroupPostsActivity.class);

                            intent.putExtra("group_id",group_id);
                            intent.putExtra("group_name",group_name);
                            startActivity(intent);
                        }

                        @Override
                        public boolean OnItemLongClick(int position) {
                            return true;
                            //return false;
                        }
                    });
                    mRecyclerView.setAdapter(adapter);
                    mRefreshLayout.setRefreshing(false);
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_allgroup,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        linearLayoutManager
                = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(linearLayoutManager);

        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mContext=getActivity().getApplicationContext();


        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        //mRefreshLayout.setEnabled(false);

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                longTimeOperation();
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter
                        .getItemCount()) {
                    adapter.setMoreStatus(adapter.LOADING_MORE);
                    loadMore();
                }
            }
        });





        getRes();
        return  view;
    }

    private void loadMore() {
        if (index<pageCount) {
            mRefreshLayout.setRefreshing(true);

            ++index;
            getRes();
            //all_planet_baseAdapter.notifyDataSetChanged();

            mRefreshLayout.setRefreshing(false);
        }else {
            Toast.makeText(mContext,"已经是最后一页",Toast.LENGTH_SHORT).show();
        }
    }

    private void longTimeOperation() {
        if (index>1){
            mRefreshLayout.setRefreshing(true);
            --index;
            getRes();
            //all_planet_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }else {
            mRefreshLayout.setRefreshing(true);

            getRes();
            //all_planet_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }


    }


    private void getRes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getApiHost();
                String Pr_URL="http://"+ApiHost+"/?service=Group.Lists&page="+index;
                //Log.e("PostsDetail.URL——————>",Pr_URL);
                String JsonData= Http_Url.getUrlReponse(Pr_URL);
                if (JsonData!=null) {
                    arraylist = AllGroupLists_JSON.getJSONParse(JsonData);

                    MyApplication.setGroupListInfo(arraylist);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = arraylist;
                    handler.sendMessage(message);
                }
            }
        }).start();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int group_id=Integer.parseInt(MyApplication.getGroupInfo().get(position).get("id"));
        String group_name=MyApplication.getGroupInfo().get(position).get("title");
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);

        intent.putExtra("group_id",group_id);
        intent.putExtra("group_name",group_name);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            default:
                break;
        }
    }
}
