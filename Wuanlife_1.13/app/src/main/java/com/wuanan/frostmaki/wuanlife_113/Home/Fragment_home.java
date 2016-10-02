package com.wuanan.frostmaki.wuanlife_113.Home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyGroupPosts_JSON;
import com.wuanan.frostmaki.wuanlife_113.Posts.Activity_PostsDetail;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class Fragment_home extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private View view;
    private ListView mlistView;
    private ArrayList<HashMap<String,String>> arraylist;
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
        mContext=getActivity().getApplicationContext();
        mlistView= (ListView) view.findViewById(R.id.listView_mygroup);
        arraylist=new ArrayList<HashMap<String, String>>();
        pre = (Button) view.findViewById(R.id.pre);
        next = (Button) view.findViewById(R.id.next);
        currentPage = (Button) view.findViewById(R.id.currentPage);

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

                String ApiHost = MyApplication.getUrl();
                String Pr_URL = "http://" + ApiHost + "/?service=Post.GetIndexPost&pn=" + index;
                Log.e("Home.url------->",Pr_URL);
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                arraylist = MyGroupPosts_JSON.getJSONParse(JsonData);
                MyApplication.setHomePostsInfo(arraylist);
                if (arraylist != null) {
                    //wo的星球帖子信息

                    MyApplication.setMyGroupPostsInfo(arraylist);
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
        String data1 = MyApplication.getHomePostsInfo().get(position).get("postID");
      /*  Bundle bundle = new Bundle();
        bundle.putString("postID", data1);

        Posts_Fragment posts_fragment = new Posts_Fragment();
        posts_fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, posts_fragment).addToBackStack(null).commit();
*/
        Intent intent=new Intent(getActivity(), Activity_PostsDetail.class);
        intent.putExtra("postID", data1);
        getActivity().startActivity(intent);

    }
}
