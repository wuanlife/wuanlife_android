package com.wuanan.frostmaki.wuanlife_113.Home;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyGroupJson;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.CircleImageView;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.MySwipeRefreshLayout;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class Fragment_home extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private View view;
    private ListView mlistview;
    private ArrayList<Postlist> arraylist;
    private HomePosts_listview_Adapter posts_listview_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private int index=1;
    private int pageCount=1;
    private int lastVisibleItemPosition=0;

    private SwipeRefreshLayout mRefreshLayout;
    private ImageButton arrow_update;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    //ArrayList<Postlist> arrayList=(ArrayList<Postlist>)msg.obj;
                    posts_listview_baseAdapter = new HomePosts_listview_Adapter(
                            mContext,
                            arraylist);
                    pageCount= arraylist.get(0).getPageCount();
                    mlistview.setAdapter(posts_listview_baseAdapter);
                    Log.e("为什么不显示000000",arraylist.toString());

                    break;
                case 1:
                    Log.e("为什么不显示111111",arraylist.toString());
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_home,container,false);

        initView();
        getRes();
        return  view;
    }

    private void initView() {
        mContext=getActivity().getApplicationContext();
        mlistview= (ListView) view.findViewById(R.id.listView_home);
        arraylist= new ArrayList<Postlist>();

        mRefreshLayout= (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);


        mlistview.setOnItemClickListener(this);

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


        arrow_update= (ImageButton) view.findViewById(R.id.arrow_update);
        arrow_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setTabVisibilty();
            }
        });
    }

    private void loadMore() {
        if (index<pageCount) {
            mRefreshLayout.setRefreshing(true);

            ++index;
            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();

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
            posts_listview_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }else {
            mRefreshLayout.setRefreshing(true);

            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            default:
                break;
        }
    }

    private void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {

                String ApiHost = MyApplication.getApiHost();
                String Pr_URL = "http://" + ApiHost + "/?service=Post.GetIndexPost&pn=" + index;
                //Log.e("Home.url------->",Pr_URL);
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                arraylist = MyGroupJson.getJSONParse(JsonData);
                MyApplication.setHomePostsInfo(arraylist);
                if (arraylist != null) {
                    //wo的星球帖子信息

                    MyApplication.setHomePostsInfo(arraylist);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = arraylist;
                    handler.sendMessage(message);
                } else {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }

        }).start();


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int data1 = MyApplication.getHomePostsInfo().get(position).getPostID();
        Intent intent=new Intent(getActivity(), PostsDetailActivity.class);
        intent.putExtra("postID", data1);
        getActivity().startActivity(intent);

    }

}
