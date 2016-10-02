package com.wuanan.frostmaki.wuanlife_113.AllGroup;


import android.app.FragmentManager;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

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
    private ListView all_planet_ListView;
    private ArrayList<HashMap<String,String>> all_planet_arrayList;
    private BaseAdapter all_planet_baseAdapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    all_planet_baseAdapter = new All_planet_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    all_planet_ListView.setAdapter(all_planet_baseAdapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_allgroup,container,false);


        pre= (Button) view.findViewById(R.id.pre);
        next= (Button) view.findViewById(R.id.next);
        currentPage= (Button) view.findViewById(R.id.currentPage);
        //createGroup.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);

        all_planet_ListView= (ListView) view.findViewById(R.id.listview);

        mContext=getActivity().getApplicationContext();


        all_planet_ListView.setOnItemClickListener(this);

        getRes();
        return  view;
    }

    private void getRes() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getUrl();
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
        String group_id=MyApplication.getGroupInfo().get(position).get("id");
        String group_name=MyApplication.getGroupInfo().get(position).get("title");
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);
        intent.putExtra("group_id",group_id);
        intent.putExtra("group_name",group_name);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pre:
                if (index > 1) {
                    --index;
                    getRes();
                    all_planet_baseAdapter.notifyDataSetChanged();
                } else {
                    Log.e("pre", "cuowu");
                }
                break;
            case R.id.next:

                if (index < pageCount) {
                    ++index;
                    getRes();
                    all_planet_baseAdapter.notifyDataSetChanged();

                } else {
                    Log.e("next", "cuowu");
                }
                break;
            default:
                break;
        }
    }
}
