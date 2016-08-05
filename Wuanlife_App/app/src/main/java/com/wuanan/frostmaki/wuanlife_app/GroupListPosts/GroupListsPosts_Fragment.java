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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.CreatePosts.CreatePosts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.Post.Posts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/17.
 */
public class GroupListsPosts_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView GroupName;
    private Button joinOrcreate;
    private ListView GroupListPosts_ListView;
    private ArrayList<HashMap<String,String>> GroupListPosts_arrayList;
    private BaseAdapter GroupListPosts_baseAdapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;
    private Button currentpage;
    private Button pre;
    private Button next;
    private String groupName="";

    private LinearLayout mLinearLayout;
    private TextView nothingText;

    private static boolean isJoinGroup=false;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:

                    ArrayList<HashMap<String,String>> arrayList=null;
                    arrayList= (ArrayList<HashMap<String, String>>) msg.obj;
                    GroupListPosts_baseAdapter=new GroupListPosts_listview_BaseAdapter(
                            mContext,arrayList,currentpage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    GroupListPosts_ListView.setAdapter(GroupListPosts_baseAdapter);
                    break;
                case 1:
                    //Fragment nothingFragment =new Nothing_Fragment();
                    //Bundle args=new Bundle();
                    //args.putString("Name",groupName);
                    //nothingFragment.setArguments(args);
                    //FragmentManager no_fm=getFragmentManager();
                    //no_fm.beginTransaction().replace(R.id.content_frame,nothingFragment).commit();

                    mLinearLayout.setVisibility(View.GONE);
                    nothingText.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    joinOrcreate.setText("发帖");
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.planet_details,container,false);

        mLinearLayout= (LinearLayout) view.findViewById(R.id.main);
        nothingText= (TextView) view.findViewById(R.id.post_nothing);

        nothingText.setVisibility(View.GONE);

        GroupName= (TextView) view.findViewById(R.id.groupName);
        int position=getArguments().getInt("position");
        groupName=MyApplication.getGroupInfo().get(position).get("title");
        GroupName.setText(groupName);

        joinOrcreate= (Button) view.findViewById(R.id.GroupPosts_JoinOrCreate);
        isJoinGroup();
        joinOrcreate.setOnClickListener(this);

        GroupListPosts_ListView= (ListView) view.findViewById(R.id.planet_details_listview);
        GroupListPosts_ListView.setOnItemClickListener(this);
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
                if (GroupListPosts_arrayList!=null) {
                    Message message = new Message();
                    message.what = 0;
                    message.obj=GroupListPosts_arrayList;
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
                if (index<pageCount) {
                    ++index;
                    getRes();
                    GroupListPosts_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            case R.id.GroupPosts_JoinOrCreate:
                if (MyApplication.getUserInfo()!=null) {
                    if (isJoinGroup == true) {
                        int position = getArguments().getInt("position");
                        String group_base_id = MyApplication.getGroupInfo().get(position).get("id");
                        Bundle bundle = new Bundle();
                        bundle.putString("groupId", group_base_id);

                        CreatePosts_Fragment createPosts_fragment = new CreatePosts_Fragment();
                        createPosts_fragment.setArguments(bundle);
                        FragmentManager fm = getFragmentManager();
                        fm.beginTransaction().replace(R.id.content_frame, createPosts_fragment).addToBackStack(null).commit();
                    } else if (isJoinGroup == false) {
                        joinGroup();
                    }
                }else Toast.makeText(mContext,"未登录",Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (MyApplication.getUserInfo()==null){
            Toast.makeText(mContext,"未登录",Toast.LENGTH_SHORT).show();
        }else {
            if (MyApplication.getGroupPostsInfo()!=null) {
                String data1 = MyApplication.getGroupPostsInfo().get(position).get("postID");
                Bundle bundle = new Bundle();
                bundle.putString("postID", data1);

                Posts_Fragment posts_fragment = new Posts_Fragment();
                posts_fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.content_frame, posts_fragment).addToBackStack(null).commit();
            }
        }
    }


    private void isJoinGroup(){
        new Thread(new Runnable() {
            @Override
                public void run() {
                if (MyApplication.getUserInfo()!=null) {
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    int position=getArguments().getInt("position");
                    String group_base_id=MyApplication.getGroupInfo().get(position).get("id");
                    String ApiHost = MyApplication.getUrl();
                    String Pre_URL = "http://" + ApiHost + "/?service=Group.GStatus"
                            + "&user_id=" + user_id
                            +"&group_base_id="+group_base_id;
                    String resultData = Http_Url.getUrlReponse(Pre_URL);
                    try {
                        JSONObject jsonObject = new JSONObject(resultData);
                        JSONObject data=jsonObject.getJSONObject("data");
                        int code=data.getInt("code");
                        if (code==1){
                            isJoinGroup=true;
                            Message message =new Message();
                            message.what=2;
                            handler.sendMessage(message);
                        }else {
                            isJoinGroup=false;
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        Log.e("shi是否加入星球异常JSOn",e+"");
                    }
                }
                }
        }).start();
    }

    private void joinGroup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() == null) {
                    Toast.makeText(mContext, "未登录", Toast.LENGTH_SHORT).show();
                } else {
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    int position = getArguments().getInt("position");
                    String group_base_id = MyApplication.getGroupInfo().get(position).get("id");
                    String ApiHost = MyApplication.getUrl();
                    String Pre_URL = "http://" + ApiHost + "/?service=Group.Join"
                            + "&user_id=" + user_id
                            + "&group_base_id=" + group_base_id;
                    String resultData = Http_Url.getUrlReponse(Pre_URL);
                    try {
                        JSONObject jsonObject = new JSONObject(resultData);
                        JSONObject data = jsonObject.getJSONObject("data");
                        int code = data.getInt("code");
                        if (code == 1) {
                            isJoinGroup = true;
                            Message message = new Message();
                            message.what = 2;
                            handler.sendMessage(message);
                        } else {
                            isJoinGroup = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("加入星球shi是否成功异常JSOn", e + "");
                    }
                }
            }
        }).start();
    }
}
