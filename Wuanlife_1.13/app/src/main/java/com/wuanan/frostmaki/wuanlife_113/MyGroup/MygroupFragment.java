package com.wuanan.frostmaki.wuanlife_113.MyGroup;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class MygroupFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private View view;
    private ListView mlistView;
    private ArrayList<Postlist> arraylist;
    private Posts_listview_BaseAdapter posts_listview_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private int index=1;
    private int pageCount=1;

    private LinearLayout myGroup;
    private LinearLayout nomyGroup;
    private String user_id=null;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<Postlist> arrayList= (ArrayList<Postlist>) msg.obj;
                    Log.e("postlist",arrayList.toString());
                    posts_listview_baseAdapter = new Posts_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= arrayList.get(0).getPageCount();
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
        if (MyApplication.getisLogin()==true){
            user_id=MyApplication.getUserInfo().get(0).get("userID");
            nomyGroup.setVisibility(View.GONE);
            myGroup.setVisibility(View.VISIBLE);
            getRes();
        }else {
            myGroup.setVisibility(View.GONE);
            nomyGroup.setVisibility(View.VISIBLE);
        }

        return  view;
    }

    private void initView() {
        mContext=getActivity().getApplicationContext();
        mlistView= (ListView) view.findViewById(R.id.listView_mygroup);
        arraylist= new ArrayList<>();
        pre = (Button) view.findViewById(R.id.pre);
        next = (Button) view.findViewById(R.id.next);
        currentPage = (Button) view.findViewById(R.id.currentPage);

        myGroup= (LinearLayout) view.findViewById(R.id.myGroup);
        nomyGroup= (LinearLayout) view.findViewById(R.id.nomyGroup);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        mlistView.setOnItemClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre:
                if (index>1){
                    --index;
                    getRes();
                    posts_listview_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:

                if (index<pageCount) {
                    ++index;
                    getRes();
                    posts_listview_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            default:
                break;
        }
    }

    private void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo()!=null) {
                    String ApiHost = MyApplication.getApiHost();
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Post.GetMyGroupPost&id=" + user_id
                            + "&pn=" + index;
                    String JsonData = Http_Url.getUrlReponse(Pr_URL);
                    arraylist = MyGroupJson.getJSONParse(JsonData);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int data1 = 0;
        if (MyApplication.getMyGroupPostsInfo() != null) {
            data1 = MyApplication.getMyGroupPostsInfo().get(position).getPostID();
            Intent intent = new Intent(getActivity().getApplicationContext(), PostsDetailActivity.class);
            intent.putExtra("postID", data1);
            intent.putExtra("groupName", MyApplication.getMyGroupPostsInfo().get(position).getGroupName());

            startActivity(intent);
        }
    }
}
