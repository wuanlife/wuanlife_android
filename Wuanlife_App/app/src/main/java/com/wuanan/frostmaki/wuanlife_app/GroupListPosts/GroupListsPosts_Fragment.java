package com.wuanan.frostmaki.wuanlife_app.GroupListPosts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.Nothing.Nothing_Fragment;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/17.
 */
public class GroupListsPosts_Fragment extends Fragment implements View.OnClickListener{
    private TextView GroupName;
    private Button button;
    private ListView GroupListPosts_ListView;
    private ArrayList<HashMap<String,String>> GroupListPosts_arrayList;
    private BaseAdapter GroupListPosts_baseAdapter;
    private Context mContext;
    private int index=1;
    private Button currentpage;
    private Button pre;
    private Button next;
    private String groupName="";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    GroupListPosts_baseAdapter=new GroupListPosts_listview_BaseAdapter(
                            mContext,GroupListPosts_arrayList,currentpage);
                    GroupListPosts_ListView.setAdapter(GroupListPosts_baseAdapter);
                    break;
                case 1:
                    Fragment nothingFragment =new Nothing_Fragment();
                    Bundle args=new Bundle();
                    args.putString("Name",groupName);
                    nothingFragment.setArguments(args);
                    FragmentManager no_fm=getFragmentManager();
                    no_fm.beginTransaction().replace(R.id.content_frame,nothingFragment).commit();
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.planet_details,container,false);

        GroupName= (TextView) view.findViewById(R.id.all_planet_name);
        int position=getArguments().getInt("position");
        groupName=MyApplication.getGroupInfo().get(position).get("title");
        GroupName.setText(groupName);

        GroupListPosts_ListView= (ListView) view.findViewById(R.id.planet_details_listview);
        GroupListPosts_arrayList=new ArrayList<HashMap<String, String>>();

        mContext=getActivity().getApplicationContext();

currentpage= (Button) view.findViewById(R.id.currentPage);
        pre= (Button) view.findViewById(R.id.pre);
        next= (Button) view.findViewById(R.id.next);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        getRes();

        return view;
    }

    private void getRes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int position=getArguments().getInt("position");
                String group_id=MyApplication.getGroupInfo().get(position).get("id");

                String ApiHost=MyApplication.getUrl();
                String Pre_URL="http://"+ApiHost+"/?service=Post.GetGroupPost"
                        +"&group_id="+group_id+"&pn="+index;

                String resultData= Http_Url.getUrlReponse(Pre_URL);
                GroupListPosts_arrayList=GroupPosts_JSON.getJSONParse(resultData);
                if (GroupListPosts_arrayList.size()!=0) {
                    Message message = new Message();
                    message.what = 0;
                    handler.sendMessage(message);
                }else {
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);

                }

            }
        }).start();
    }

    @Override
    public void onClick(View v) {
        //Log.e("GroupListPosts.v.click","");
        switch (v.getId()){
            case R.id.pre:
                if (index>1){
                    --index;
                    getRes();
                    GroupListPosts_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:
                if (index<GroupListPosts_arrayList.size()) {
                    ++index;
                    getRes();
                    GroupListPosts_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            default:
                break;
        }
    }
}
