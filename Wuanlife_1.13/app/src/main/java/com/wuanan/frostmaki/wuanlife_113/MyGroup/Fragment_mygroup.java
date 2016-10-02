package com.wuanan.frostmaki.wuanlife_113.MyGroup;


import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class Fragment_mygroup extends Fragment implements View.OnClickListener{
    private View view;
    private ListView mlistView;
    private ArrayList<HashMap<String,String>>  arraylist;
    private Posts_listview_BaseAdapter posts_listview_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private int index=1;
    private int pageCount=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    posts_listview_baseAdapter = new Posts_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    mlistView.setAdapter(posts_listview_baseAdapter);
                    break;
                case 1:
            /*        android.app.Fragment nothingFragment=new Nothing_Fragment();
                    Bundle args=new Bundle();
                    args.putString("Name","我的星球");
                    nothingFragment.setArguments(args);
                    FragmentManager no_fm=getFragmentManager();
                    no_fm.beginTransaction().replace(R.id.content_frame,nothingFragment).commit();*/
                    break;
                case 2:

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mygroup,container,false);
        initView();
        getRes();
        return  view;
    }

    private void initView() {
        mlistView= (ListView) view.findViewById(R.id.listView_mygroup);
        arraylist=new ArrayList<HashMap<String, String>>();
        pre = (Button) view.findViewById(R.id.pre);
        next = (Button) view.findViewById(R.id.next);
        currentPage = (Button) view.findViewById(R.id.currentPage);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }

    private void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo()!=null) {
                    String ApiHost = MyApplication.getUrl();
                    String id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Post.GetMyGroupPost&id=" + id
                            + "&pn=" + index;
                    String JsonData = Http_Url.getUrlReponse(Pr_URL);
                    arraylist = MyGroupPosts_JSON.getJSONParse(JsonData);
                    if (arraylist!=null) {
                        //wo的星球帖子信息

                        MyApplication.setMyGroupPostsInfo(arraylist);
                        Message message = new Message();
                        message.what = 0;
                        message.obj = arraylist;
                        handler.sendMessage(message);
                    }
                }else {
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }

            }
        }).start();


    }
}
