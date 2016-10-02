package com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatEditPostActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MyGroupPosts_JSON;
import com.wuanan.frostmaki.wuanlife_113.Posts.Activity_PostsDetail;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;
import com.wuanan.frostmaki.wuanlife_113.Utils.Posts_listview_BaseAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import static com.wuanan.frostmaki.wuanlife_113.R.id.cancel;
import static com.wuanan.frostmaki.wuanlife_113.R.id.groupName;
import static com.wuanan.frostmaki.wuanlife_113.R.id.next;

/**
 * Created by Frostmaki on 2016/9/30.
 */
public class GroupPostsActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{
    private Toolbar toolbar;
    private TextView toolbar_title;
    private ListView listView;
    private Button btn_pre;
    private Button btn_next;
    private Button btn_currentpage;


    private ArrayList<HashMap<String,String>> arraylist;
    private Posts_listview_BaseAdapter posts_listview_baseAdapter;
    private int index=1;
    private int pageCount=1;
    private String group_id=null;
    private String userID=null;
    private String group_name=null;

    public int TOASTMESSAGE=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=
                            (ArrayList<HashMap<String,String>>)msg.obj;
                    posts_listview_baseAdapter = new Posts_listview_BaseAdapter(
                            GroupPostsActivity.this,
                            arrayList,
                            btn_currentpage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    listView.setAdapter(posts_listview_baseAdapter);


                    break;
                case 1 :  //处理消息
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
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.groupposts_menu, menu);
        if (userID==null){
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
                        if (userID==null){
                            Toast.makeText(GroupPostsActivity.this,"未登录",Toast.LENGTH_SHORT).show();
                        }else
                         {

                            /*
                            跳转到发帖Activity
                             */
                            Intent intent=new Intent(GroupPostsActivity.this, CreatEditPostActivity.class);
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
                        joinGroup(group_id,userID);
                        break;
                }
                return true;
            }
        });
    }

    /*
               加入星球
                             */
    private void joinGroup(final String group_id, final String userID) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg=null;
                String ApiHost = MyApplication.getUrl();
                String Pr_URL="http://"+ApiHost+"/?service=Group.Join"
                        +"&group_id="+group_id+"&user_id="+userID;
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                try{
                    JSONObject jsonobject=new JSONObject(JsonData);
                    JSONObject data=jsonobject.getJSONObject("data");
                    int code=  data.getInt("code");
                    if (code==0){
                        msg=data.getString("msg");
                    }else {
                        msg="加入成功！";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.what=TOASTMESSAGE;
                message.obj=msg;
                handler.sendMessage(message);
            }
        });
    }

    private void initView() {
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar_title= (TextView) findViewById(R.id.toolbar_title);
        listView= (ListView) findViewById(R.id.listview_groupposts);
        btn_currentpage= (Button) findViewById(R.id.currentPage);
        btn_pre= (Button) findViewById(R.id.pre);
        btn_next= (Button) findViewById(R.id.next);

        arraylist=new ArrayList<HashMap<String, String>>();

        group_id=getIntent().getStringExtra("group_id");
        group_name=getIntent().getStringExtra("group_name");
        if (MyApplication.getUserInfo()!=null) {
            userID = MyApplication.getUserInfo().get(0).get("userID");
        }


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

    private void getRes() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String ApiHost = MyApplication.getUrl();
                String Pr_URL="http://"+ApiHost+"/?service=Post.GetGroupPost"
                        +"&group_id="+group_id+"&pn="+index;
                //Log.e("GroupPosts.url------->",Pr_URL);
                String JsonData = Http_Url.getUrlReponse(Pr_URL);
                arraylist = GroupPosts_JSON.getJSONParse(JsonData);
                MyApplication.setGroupPostsInfo(arraylist);
                if (arraylist != null) {
                    //星球帖子信息

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
        String data1 =null;

        data1= MyApplication.getGroupPostsInfo().get(position).get("postID");
        Intent intent=new Intent(GroupPostsActivity.this, Activity_PostsDetail.class);
        intent.putExtra("postID", data1);
        intent.putExtra("groupName",group_name);

        startActivity(intent);
    }
}
