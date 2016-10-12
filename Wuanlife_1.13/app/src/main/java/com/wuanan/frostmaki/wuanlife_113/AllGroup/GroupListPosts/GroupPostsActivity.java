package com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatEditPostActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyGroupJson;
import com.wuanan.frostmaki.wuanlife_113.NewView.GroupPostClass;
import com.wuanan.frostmaki.wuanlife_113.Posts.PostsDetailActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/9/30.
 */
public class GroupPostsActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private TextView toolbar_title;
    private ListView listView;
    private TextView nothing;
    private Button btn_pre;
    private Button btn_next;
    private Button btn_currentpage;
    private SwipeRefreshLayout mRefreshLayout;

    private ArrayList<GroupPostClass> arraylist;
    private GroupPosts_listview_BaseAdapter posts_listview_baseAdapter;
    private int index=1;
    private int pageCount=1;
    private int group_id=0;
    private int userID=0;
    private String group_name=null;

    public int TOASTMESSAGE=2;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    nothing.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ArrayList<GroupPostClass> arrayList=
                            (ArrayList<GroupPostClass>) msg.obj;
                    posts_listview_baseAdapter = new GroupPosts_listview_BaseAdapter(
                            GroupPostsActivity.this,
                            arrayList,
                            btn_currentpage);
                    pageCount= arrayList.get(0).getPageCount();
                    listView.setAdapter(posts_listview_baseAdapter);


                    break;
                case 1 :  //处理消息
                    //String message= (String) msg.obj;
                    listView.setVisibility(View.GONE);
                    nothing.setVisibility(View.VISIBLE);
                    //Toast.makeText(GroupPostsActivity.this,message,Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    String message= (String) msg.obj;
                    Toast.makeText(GroupPostsActivity.this,message,Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupposts);
        initView();
        initToolbar();
        getRes();
      //  setOverflowShowingAlways();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        getRes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.groupposts_menu, menu);
        if (userID==0){
            menu.findItem(R.id.action_join).setVisible(false);
        }
        return true;

    }

    private void initToolbar() {

        toolbar_title.setText(group_name);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_18dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                xiao销毁当前活动，返回上一级活动
                 */
                finish();
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_create:
                        if (userID==0){
                            Toast.makeText(GroupPostsActivity.this,"未登录",Toast.LENGTH_SHORT).show();
                        }else {

                            /*
                            跳转到发帖Activity
                             */
                            Intent intent=new Intent(GroupPostsActivity.this, CreatEditPostActivity.class);
                             intent.putExtra("code",2);
                            intent.putExtra("groupName",group_name);
                            intent.putExtra("group_id",group_id);
                            intent.putExtra("user_id",userID);
                            startActivity(intent);
                        }
                        break;
                    case R.id.action_join:
                            /*
                            加入星球
                             */
                        joinGroup();
                        break;
                }
                return true;
            }
        });
    }

    /*
               加入星球
                             */
    private void joinGroup() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg=null;
                String ApiHost = MyApplication.getApiHost();
                String Pr_URL="http://"+ApiHost+"/?service=Group.Join"
                        +"&group_base_id="+group_id+"&user_id="+userID;
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                try{
                    JSONObject jsonobject=new JSONObject(JsonData);
                    if (jsonobject.getInt("ret")==200) {
                        JSONObject data = jsonobject.getJSONObject("data");
                        int code = data.getInt("code");

                        if (code == 0) {
                            msg = data.getString("msg");
                        } else {
                            msg="加入成功";
                        }
                    }else {
                        msg=jsonobject.getString("msg");
                    }
                    Message message=new Message();
                    message.what=TOASTMESSAGE;
                    message.obj=msg;

                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        listView= (ListView) findViewById(R.id.listview_groupposts);
        btn_currentpage= (Button) findViewById(R.id.currentPage);
        btn_pre= (Button) findViewById(R.id.pre);
        btn_next= (Button) findViewById(R.id.next);

        nothing= (TextView) findViewById(R.id.nothing);

        arraylist= new ArrayList<GroupPostClass>();

        group_id=getIntent().getIntExtra("group_id",0);
        group_name=getIntent().getStringExtra("group_name");
        if (MyApplication.getisLogin()==true) {
            userID = Integer.parseInt(MyApplication.getUserInfo().get(0).get("userID"));
        }
        mRefreshLayout= (android.support.v4.widget.SwipeRefreshLayout) findViewById(R.id.swipe_container);

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

        btn_next.setOnClickListener(this);
        btn_currentpage.setOnClickListener(this);
        btn_pre.setOnClickListener(this);

        listView.setOnItemClickListener(this);
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
            case R.id.currentPage:
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

    private void longTimeOperation() {
        if (index > 1) {
            mRefreshLayout.setRefreshing(true);
            --index;
            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();
            mRefreshLayout.setRefreshing(false);
        } else {
            mRefreshLayout.setRefreshing(true);

            getRes();
            if (listView.getVisibility()==View.VISIBLE) {
                posts_listview_baseAdapter.notifyDataSetChanged();
            }
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void loadMore() {
        if (index<pageCount) {
            mRefreshLayout.setRefreshing(true);

            ++index;
            getRes();
            posts_listview_baseAdapter.notifyDataSetChanged();

            mRefreshLayout.setRefreshing(false);
        }else {
            mRefreshLayout.setRefreshing(true);
            Toast.makeText(GroupPostsActivity.this,"已经是最后一页",Toast.LENGTH_SHORT).show();
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void getRes() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String ApiHost = MyApplication.getApiHost();
                String Pr_URL="http://"+ApiHost+"/?service=Post.GetGroupPost"
                        +"&group_id="+group_id+"&pn="+index;
                //Log.e("GroupPosts.url------->",Pr_URL);
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                arraylist = GroupJson.getJSONParse(JsonData);
                MyApplication.setGroupPostsInfo(arraylist);
                if (arraylist != null) {
                    //星球帖子信息

                    MyApplication.setGroupPostsInfo(arraylist);
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
        int data1 =0;

        data1= MyApplication.getGroupPostsInfo().get(position).getPostID();
        Intent intent=new Intent(GroupPostsActivity.this, PostsDetailActivity.class);
        intent.putExtra("postID", data1);
        intent.putExtra("groupName",group_name);

        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getRes();
    }
}
