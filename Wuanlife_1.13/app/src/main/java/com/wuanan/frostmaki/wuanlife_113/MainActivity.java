package com.wuanan.frostmaki.wuanlife_113;


import android.app.FragmentManager;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.Fragment_allgroup;
import com.wuanan.frostmaki.wuanlife_113.Home.Fragment_home;
import com.wuanan.frostmaki.wuanlife_113.MyGroup.Fragment_mygroup;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private LinearLayout personcenter;

    private ViewPager mViewPager;
    private FragmentPagerAdapter mfragmentPagerAdapter;
    private List<Fragment> mFragments;

    private Button btn_mygroup;
    private Button btn_home;
    private Button btn_allgroup;
    private ImageView underline;

    private TabLayout tab;

    /**
     * 下划线图片宽度
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.FEATURE_SUPPORT_ACTION_BAR);
        setContentView(R.layout.activity_main);
        initToolbar();
        initView();


        //Fragment baseFragment=new Fragment();
        //FragmentManager fm=getFragmentManager();
        //fm.beginTransaction().replace(R.id.main_content,baseFragment).commit();
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView textView = (TextView) findViewById(R.id.toolbar_title);
        textView.setText("午安网");
        toolbar.setTitle("");

        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(AndroidToolbarExample.this, "clicking the toolbar!", Toast.LENGTH_SHORT).show();
                        drawerLayout.openDrawer(personcenter);
                    }
                }

        );
    }


    private void initView() {
        tab = (TabLayout) findViewById(R.id.tab);
        tab.addTab(tab.newTab().setText("Tab 1"));
        tab.addTab(tab.newTab().setText("Tab 2"));
        tab.addTab(tab.newTab().setText("Tab 3"));


        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        personcenter = (LinearLayout) findViewById(R.id.main_personCenter);

        mViewPager = (ViewPager) findViewById(R.id.mViewpager);
        mFragments = new ArrayList<Fragment>();
        Fragment fragment_mygroup = new Fragment_mygroup();
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


        tab.getTabAt(0).setText("我的星球");
        tab.getTabAt(1).setText("首页");
        tab.getTabAt(2).setText("全部星球");


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

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
