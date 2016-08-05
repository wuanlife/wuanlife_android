package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.GroupLists.All_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Home.Home_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Login_Register.Login_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Login_Register.Registered_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyGroup.MyGroupPosts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<String> menusLists;
    private ArrayAdapter<String> adapter;
    public LinearLayout mLinearLayout;
    public static Button login_btn;
    public boolean isLogin=false;
    public static Button registered_btn;

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mtoolbar;
    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    login_btn.setText("登陆");
                    registered_btn.setText("注册");
                    Toast.makeText(MainActivity.this,"注销成功！！！",Toast.LENGTH_SHORT).show();
                    isLogin=false;
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout= (LinearLayout) findViewById(R.id.drawer_LinearLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        login_btn= (Button) findViewById(R.id.login);
        registered_btn= (Button) findViewById(R.id.registered);

        menusLists = new ArrayList<String>();

        menusLists.add("首页");
        menusLists.add("我的星球");
        menusLists.add("全部星球");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menusLists);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);

        initToolbar();
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mtoolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        initContentView();

        login_btn.setOnClickListener(this);
        registered_btn.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //动态插入一个Fragment到FrameLayout
        Bundle args = new Bundle();
        args.putString("name", menusLists.get(position));
        switch (position){
            case 0:
                //首页
                initContentView();

                break;
            case 1:
                //我的星球
                //if (MyApplication.getUserInfo()!=null) {
                    Log.e("我的星球---->","q");
                   Fragment my_planet_Fragment = new MyGroupPosts_Fragment();
                    //my_planet_Fragment.setArguments(args);

                   FragmentManager my_pianet_fm = getFragmentManager();
                    my_pianet_fm.beginTransaction().replace(R.id.content_frame, my_planet_Fragment).commit();
                    break;

            case 2:
                //所有星球
                Fragment all_planet_Fragment = new All_planet_Fragment();
                all_planet_Fragment.setArguments(args);

                FragmentManager all_planet_fm = getFragmentManager();
                all_planet_fm.beginTransaction().replace(R.id.content_frame, all_planet_Fragment).commit();
            default:
                break;
        }

        mDrawerLayout.closeDrawer(mLinearLayout);
    }

    private void initToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setTitle("");

        setSupportActionBar(mtoolbar);
        mtoolbar.setNavigationIcon(R.drawable.ic_drawer);
    }

    private void initContentView(){
        Fragment home_Fragment = new Home_Fragment();

        FragmentManager home_fm = getFragmentManager();
        home_fm.beginTransaction().replace(R.id.content_frame, home_Fragment).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                if (isLogin==false) {
                    Fragment login_Fragment = new Login_Fragment();
                    FragmentManager login_fm = getFragmentManager();
                    login_fm.beginTransaction().replace(R.id.content_frame, login_Fragment).commit();
                    isLogin=true;
                }
                    break;
            case R.id.registered:
                if (isLogin==false) {
                    Fragment regis_Fragment = new Registered_Fragment();
                    FragmentManager regis_fm = getFragmentManager();
                    regis_fm.beginTransaction().replace(R.id.content_frame, regis_Fragment).commit();
                    isLogin=true;
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String ApiHost=MyApplication.getUrl();
                            String UStatus="http://"+ApiHost+"/?service=Group.UStatus&user_id="+login_btn.getText().toString();
                            String resultData= Http_Url.getUrlReponse(UStatus);
                            try {
                                JSONObject jsonObject = new JSONObject(resultData);
                                JSONObject data=jsonObject.getJSONObject("data");
                                int code =data.getInt("code");
                                if (code==1){
                                    Message message=new Message();
                                    message.what=0;
                                    handler.sendMessage(message);
                                    MyApplication.setUserInfo(null);

                                }else if (code==0){
                                    Toast.makeText(MainActivity.this,"注销失败！请重新尝试....",Toast.LENGTH_SHORT).show();
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                Log.e("zhuxiao",e+"");
                            }
                        }
                    }).start();

                }
                    break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(mLinearLayout);
    }


}
