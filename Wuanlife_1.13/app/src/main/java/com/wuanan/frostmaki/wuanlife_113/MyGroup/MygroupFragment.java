package com.wuanan.frostmaki.wuanlife_113.MyGroup;




import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ScrollingView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/9/27.
 */
public class MygroupFragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private View view;
    private View headView;
    private View headViewCreate;

    private ListView mlistView;
    private ArrayList<Postlist> arraylist;
    private Posts_listview_BaseAdapter posts_listview_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private ScrollView scrollView;
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
                    //Log.e("postlist",arrayList.toString());
                    posts_listview_baseAdapter = new Posts_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= arrayList.get(0).getPageCount();
                    mlistView.setAdapter(posts_listview_baseAdapter);
                    setListViewHeightBasedOnChildren(mlistView);
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
                    join_linearLayout.setVisibility(View.GONE);
                    break;
                case 3 :

                    if (joinlist.size()>0){
                        join_button_1.setVisibility(View.VISIBLE);
                        join_button_1.setText(joinlist.get(0).getName());
                        if (joinlist.size()>1){
                            join_button_2.setVisibility(View.VISIBLE);
                            join_button_2.setText(joinlist.get(1).getName());

                        }
                    }
                    break;
                case 4 :
                    create_linearLayout.setVisibility(View.GONE);
                    break;
                case 5 :

                    if (arraylist.size()>0){
                        create_button_1.setVisibility(View.VISIBLE);
                        create_button_1.setText(createlist.get(0).getName());
                        if (createlist.size()>1){
                            create_button_2.setVisibility(View.VISIBLE);
                            create_button_2.setText(createlist.get(1).getName());

                        }
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mygroup_content,container,false);
        initView();
        if (MyApplication.getisLogin()==true){
            user_id=MyApplication.getUserInfo().get(0).get("userID");
            nomyGroup.setVisibility(View.GONE);
            myGroup.setVisibility(View.VISIBLE);
            getRes();

            initjoinView();
            initjoinData();

            initcreateView();
            initcreateData();
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

        //scrollView= (ScrollView) view.findViewById(R.id.scrollView_mygroup);

        headView=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content,null);
        mlistView.addHeaderView(headView);

        headViewCreate=LayoutInflater.from(mContext).inflate(R.layout.fragment_mygroup_frame_content_create,null);
        mlistView.addHeaderView(headViewCreate);

     /*   JoinFragment joinFragment=new JoinFragment();
        FragmentManager fm_join=getFragmentManager();
        fm_join.beginTransaction().replace(R.id.frame_content_join,joinFragment).commit();

        CreateFragment createFragment=new CreateFragment();
        FragmentManager fm_create=getFragmentManager();
        fm_create.beginTransaction().replace(R.id.frame_content_create,createFragment).commit();

*/
        pre.setOnClickListener(this);
        next.setOnClickListener(this);
        mlistView.setOnItemClickListener(this);
        mlistView.setFocusable(false);
        mlistView.setFocusableInTouchMode(false);
        mlistView.requestFocus();
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

            case R.id.more :
                break;
            case R.id.group_1 :
                int id=joinlist.get(0).getId();
                String name=joinlist.get(0).getName();

                openThisGroup(id,name);
                break;
            case R.id.group_2 :
                int id_2=joinlist.get(1).getId();
                String name_2=joinlist.get(1).getName();

                openThisGroup(id_2,name_2);
                break;
            case R.id.create_group_1 :
                int id_create=createlist.get(0).getId();
                String name_create=createlist.get(0).getName();

                openThisGroup(id_create,name_create);
                break;
            case R.id.create_group_2 :
                int id_2_create=createlist.get(1).getId();
                String name_2_create=createlist.get(1).getName();

                openThisGroup(id_2_create,name_2_create);
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
            data1 = MyApplication.getMyGroupPostsInfo().get(position-2).getPostID();
            Intent intent = new Intent(getActivity().getApplicationContext(), PostsDetailActivity.class);
            intent.putExtra("postID", data1);
            intent.putExtra("groupName", MyApplication.getMyGroupPostsInfo().get(position-2).getGroupName());

            startActivity(intent);
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


    /*
    join
     */
    //private View view;
    private LinearLayout join_linearLayout;
    private TextView join_textView;
    private ImageButton join_imageButton;
    private Button join_button_1;
    private Button join_button_2;

    private ArrayList<JoinCreateGroup> joinlist=null;

    private void initjoinView() {
        join_textView= (TextView) headView.findViewById(R.id.name);
        join_imageButton= (ImageButton) headView.findViewById(R.id.more);
        join_button_1= (Button) headView.findViewById(R.id.group_1);
        join_button_2= (Button) headView.findViewById(R.id.group_2);
        join_linearLayout= (LinearLayout) headView.findViewById(R.id.content_fragment);

        join_textView.setText("已加入的星球");
        join_button_1.setVisibility(View.GONE);
        join_button_2.setVisibility(View.GONE);
        join_button_1.setOnClickListener(this);
        join_button_2.setOnClickListener(this);

        join_button_1.setTextColor(Color.BLACK);
        join_button_2.setTextColor(Color.BLACK);
    }

    private void initjoinData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() != null) {
                    String ApiHost = MyApplication.getApiHost();
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Group.GetJoined&user_id=" + user_id;
                    String resultdata = Http_Url.getUrlReponse(Pr_URL);
                    joinlist = new ArrayList<JoinCreateGroup>();
                    try {
                        JSONObject jsonobject = new JSONObject(resultdata);
                        JSONObject data = jsonobject.getJSONObject("data");
                        JSONArray groups = data.getJSONArray("groups");
                        for (int i = 0; i < groups.length(); i++) {
                            JoinCreateGroup mlist = new JoinCreateGroup();

                            mlist.setNum(data.getInt("num"));
                            mlist.setPageCount(data.getInt("pageCount"));
                            mlist.setCurrentPage(data.getInt("currentPage"));
                            mlist.setUser_name(data.getString("user_name"));

                            mlist.setName(groups.getJSONObject(i).getString("name"));
                            mlist.setId(groups.getJSONObject(i).getInt("id"));//星球ID
                            mlist.setG_image(groups.getJSONObject(i).getString("g_image"));
                            mlist.setG_introduction(groups.getJSONObject(i).getString("g_introduction"));

                            joinlist.add(mlist);
                        }
                        MyApplication.setJoinGroupArrayList(joinlist);
                        Message message=new Message();
                        if (arraylist==null){
                            message.what=2;
                        }else {
                            message.what=3;
                            message.obj=arraylist;
                        }
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }

    private void openThisGroup(int id,String name) {
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);
        intent.putExtra("group_id",id);
        intent.putExtra("group_name",name);

        startActivity(intent);
    }



    /*
    create
     */

    private LinearLayout create_linearLayout;
    private TextView create_textView;
    private ImageButton create_imageButton;
    private Button create_button_1;
    private Button create_button_2;

    private ArrayList<JoinCreateGroup> createlist=null;

    private void initcreateView() {
        create_textView= (TextView) headViewCreate.findViewById(R.id.name);
        create_imageButton= (ImageButton) headViewCreate.findViewById(R.id.more);
        create_button_1= (Button) headViewCreate.findViewById(R.id.create_group_1);
        create_button_2= (Button) headViewCreate.findViewById(R.id.create_group_2);
        create_linearLayout= (LinearLayout) headViewCreate.findViewById(R.id.content_fragment);

        create_textView.setText("已创建的星球");
        create_button_1.setVisibility(View.GONE);
        create_button_2.setVisibility(View.GONE);
        create_button_1.setOnClickListener(this);
        create_button_2.setOnClickListener(this);

        create_button_1.setTextColor(Color.BLACK);
        create_button_2.setTextColor(Color.BLACK);
    }

    private void initcreateData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() != null) {
                    String ApiHost = MyApplication.getApiHost();
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Group.GetCreate&user_id=" + user_id;
                    String resultdata = Http_Url.getUrlReponse(Pr_URL);
                    createlist = new ArrayList<JoinCreateGroup>();
                    try {
                        JSONObject jsonobject = new JSONObject(resultdata);
                        JSONObject data = jsonobject.getJSONObject("data");
                        JSONArray groups = data.getJSONArray("groups");
                        for (int i = 0; i < groups.length(); i++) {
                            JoinCreateGroup mlist = new JoinCreateGroup();

                            mlist.setNum(data.getInt("num"));
                            mlist.setPageCount(data.getInt("pageCount"));
                            mlist.setCurrentPage(data.getInt("currentPage"));
                            mlist.setUser_name(data.getString("user_name"));

                            mlist.setName(groups.getJSONObject(i).getString("name"));
                            mlist.setId(groups.getJSONObject(i).getInt("id"));//星球ID
                            mlist.setG_image(groups.getJSONObject(i).getString("g_image"));
                            mlist.setG_introduction(groups.getJSONObject(i).getString("g_introduction"));

                            createlist.add(mlist);
                        }

                        MyApplication.setCreateGroupArrayList(createlist);
                        Message message=new Message();
                        if (createlist==null){
                            message.what=4;
                        }else {
                            message.what=5;
                            message.obj=arraylist;
                        }
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }
}
