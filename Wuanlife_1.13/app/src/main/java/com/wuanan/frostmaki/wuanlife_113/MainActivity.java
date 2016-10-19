package com.wuanan.frostmaki.wuanlife_113;


import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.wuanan.frostmaki.wuanlife_113.AllGroup.Fragment_allgroup;
import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatEditPostActivity;
import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.CreatePostFragment;
import com.wuanan.frostmaki.wuanlife_113.CreateEditPost.HomeCreatePostFragment;
import com.wuanan.frostmaki.wuanlife_113.Home.Fragment_home;
import com.wuanan.frostmaki.wuanlife_113.LoginRegisterCancel.BaseActivity;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.MygroupFragment;
import com.wuanan.frostmaki.wuanlife_113.Utils.Constant;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout personcenter;
    private LinearLayout noperson;
    private RelativeLayout person;

    private Boolean isman=true;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mfragmentPagerAdapter;
    private List<Fragment> mFragments;

    private Button login;
    private Button register;
    private Button cancel;


    private TextView l_name;
    private TextView l_sex;
    private TextView l_mail;
    private TextView l_date;

    private int tabIsselected=1;
    private static TabLayout tab;
    private FloatingActionButton fab;

    /**
     *
     */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    MyApplication.setisLogin(false);
                    MyApplication.setUserInfo(null);
                    MyApplication.setCreateGroupArrayList(null);
                    MyApplication.setJoinGroupArrayList(null);
                    MyApplication.setGroupListInfo(null);
                    MyApplication.setMyGroupPostsInfo(null);
                    MyApplication.setHomePostsInfo(null);
                    MyApplication.setGroupListInfo(null);
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    onStart();
            break;
                case 1:
                    Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();


                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.FEATURE_SUPPORT_ACTION_BAR);
        setContentView(R.layout.activity_main);

        initConstant();
        initToolbar();
        initView();

        initDrawerLayout();

        tab.getTabAt(1).select();


    }

    private void initConstant() {
        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        Constant.displayWidth = displayMetrics.widthPixels;

        Constant.displayHeight = displayMetrics.heightPixels;
    }


    private void initDrawerLayout() {
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {


            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerLayout.setDrawerLockMode(
                        DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText(R.string.wuan);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(AndroidToolbarExample.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();
                        drawerLayout.openDrawer(personcenter);
                    }
                }

        );
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_create :

                        int user_id=0;
                        if (MyApplication.getisLogin()==true){

                            if (tab.getTabAt(2).isSelected()){
                                Intent intent=new Intent(MainActivity.this, BaseActivity.class);
                                intent.putExtra("code",4);
                                startActivity(intent);
                            }else {
                                tab.getTabAt(0).select();
                                MygroupFragment.createPost();
                            }
                        }else {
                            Toast.makeText(MainActivity.this,R.string.requireLogin,Toast.LENGTH_SHORT).show();
                        }

                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mygroup_menu,menu);
        return true;
    }

    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Tab 1"));
        tab.addTab(tab.newTab().setText("Tab 2"));
        tab.addTab(tab.newTab().setText("Tab 3"));

        mViewPager = (ViewPager) findViewById(R.id.mViewpager);
        mFragments = new ArrayList<Fragment>();
        Fragment fragment_mygroup = new MygroupFragment();
        Fragment fragment_home = new Fragment_home();
        Fragment fragment_allgroup = new Fragment_allgroup();

        mFragments.add(fragment_mygroup);
        mFragments.add(fragment_home);
        mFragments.add(fragment_allgroup);

        mfragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public android.support.v4.app.Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        };
        mViewPager.setAdapter(mfragmentPagerAdapter);

        tab.setupWithViewPager(mViewPager);

        //设置TabLayout的模式
        tab.setTabMode(TabLayout.MODE_FIXED);
        //设置TabLayout中字体的颜色
        tab.setTabTextColors(Color.WHITE, Color.BLACK);
        //设置TabLayout指示器的颜色
        tab.setSelectedTabIndicatorColor(Color.WHITE);
        //设置TabLayout指示器的高度
        tab.setSelectedTabIndicatorHeight(5);
        //设置TabLayout的背景色
       // tab.setBackgroundColor(Color.GRAY);


        tab.getTabAt(0).setText(R.string.mGroup);
        tab.getTabAt(1).setText(R.string.home);
        tab.getTabAt(2).setText(R.string.aGroup);


        for (int i=0;i<tab.getTabCount();i++){
            TabLayout.Tab tabtab=tab.getTabAt(i);
            if (tabtab!=null){

            }
        }

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabIsselected=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        personcenter = (LinearLayout) findViewById(R.id.main_personCenter);
        person= (RelativeLayout) findViewById(R.id.person);
        noperson= (LinearLayout) findViewById(R.id.noperson);


        if (MyApplication.getisLogin()==false){
            person.setVisibility(View.GONE);
            noperson.setVisibility(View.VISIBLE);
        }else {
            noperson.setVisibility(View.GONE);
            person.setVisibility(View.VISIBLE);
        }

        login= (Button) findViewById(R.id.login);
        register= (Button) findViewById(R.id.register);
        cancel= (Button) findViewById(R.id.cancel);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        cancel.setOnClickListener(this);

        l_name= (TextView) findViewById(R.id.l_name);
        l_sex= (TextView) findViewById(R.id.l_sex);
        l_mail= (TextView) findViewById(R.id.l_mail);
        l_date= (TextView) findViewById(R.id.l_date);

        l_sex.setOnClickListener(this);

        fab= (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login ://登陆
                Intent intent=new Intent(MainActivity.this, BaseActivity.class);
                intent.putExtra("code",1);
                startActivity(intent);
                break;
            case R.id.register ://注册
                Intent intent_2=new Intent(MainActivity.this, BaseActivity.class);
                intent_2.putExtra("code",2);
                startActivity(intent_2);
                break;
            case R.id.cancel ://注销
                if (MyApplication.getisLogin()==true) {
                    cancelMethod();
                }
                break;
            case R.id.l_sex :
                if (isman==true) {
                    l_sex.setText(R.string.woman);
                    isman=false;
                }else{
                    l_sex.setText(R.string.man);
                    isman=true;
                }
                break;
            case R.id.fab :

                break;
        }

    }

    private void cancelMethod() {
        String resultData=null;
        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost = MyApplication.getApiHost();
                String UStatus = "http://" + ApiHost + "/?service=Group.UStatus&user_id=" + MyApplication.getUserInfo().get(0).get("userID");
                String resultData = Http_Url.getUrlReponse(UStatus);


                try {
                    JSONObject jsonObject = new JSONObject(resultData);
                    JSONObject data = jsonObject.getJSONObject("data");
                    int code = data.getInt("code");
                    String msg=null;
                    if (code == 1) {
                        MyApplication.setisLogin(false);
                        MyApplication.setUserInfo(null);
                        msg="注销成功";
                        Message message=new Message();
                        message.what=0;
                        message.obj=msg;
                        handler.sendMessage(message);
                    }else {
                        msg = data.getString("msg");

                        Message message = new Message();
                        message.what = 1;
                        message.obj = msg;
                        handler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                }

            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (MyApplication.getisLogin()==false){
            person.setVisibility(View.GONE);
            noperson.setVisibility(View.VISIBLE);
        }else {
            noperson.setVisibility(View.GONE);
            person.setVisibility(View.VISIBLE);

            l_name.setText(MyApplication.getUserInfo().get(0).get("nickname"));
            l_sex.setText("男");
            l_mail.setText(MyApplication.getUserInfo().get(0).get("Email"));

            SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日");
            Date    curDate    =   new Date(System.currentTimeMillis());//获取当前时间
            String    str    =    formatter.format(curDate);

            l_date.setText(str);
        }


        initToolbar();
        initView();

        initDrawerLayout();

        tab.getTabAt(tabIsselected).select();
    }


}
