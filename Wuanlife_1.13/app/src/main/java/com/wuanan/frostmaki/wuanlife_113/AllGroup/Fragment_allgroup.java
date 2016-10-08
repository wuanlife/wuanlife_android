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
import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.MySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class Fragment_allgroup extends Fragment implements AdapterView.OnItemClickListener,View.OnClickListener {
    private View view;
    private TextView name;
    private Button createGroup;
    private Button pre;
    private Button next;
    private Button currentPage;
    private ListView mlistView;
    private ArrayList<HashMap<String,String>> all_planet_arrayList;
    private BaseAdapter all_planet_baseAdapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;

    private int lastVisibleItemPosition=0;
    private MySwipeRefreshLayout mRefreshLayout;
    private ImageButton arrow_update;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    all_planet_baseAdapter = new All_planet_listview_BaseAdapter(
                            mContext,
                            arrayList);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    mlistView.setAdapter(all_planet_baseAdapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_allgroup,container,false);

        mlistView= (ListView) view.findViewById(R.id.listview);
        mRefreshLayout= (MySwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mContext=getActivity().getApplicationContext();

        mlistView.setOnItemClickListener(this);

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
        mRefreshLayout.setOnLoadListener(new MySwipeRefreshLayout.OnLoadListener() {
            @Override
            public void onLoad() {
                loadMore();

            }
        });

        arrow_update= (ImageButton) view.findViewById(R.id.arrow_update);
        arrow_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.setTabVisibilty();
            }
        });

        mlistView.setOnItemClickListener(this);

        getRes();
        return  view;
    }

    private void loadMore() {
        if (index<pageCount) {
            mRefreshLayout.setRefreshing(true);

            ++index;
            getRes();
            all_planet_baseAdapter.notifyDataSetChanged();

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
            all_planet_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        }else {
            mRefreshLayout.setRefreshing(true);

            getRes();
            all_planet_baseAdapter.notifyDataSetChanged();
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
                    all_planet_arrayList = AllGroupLists_JSON.getJSONParse(JsonData);

                    MyApplication.setGroupListInfo(all_planet_arrayList);

                    Message message = new Message();
                    message.what = 0;
                    message.obj = all_planet_arrayList;
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
