package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<String> menusLists;
    private ArrayAdapter<String> adapter;
    private LinearLayout mLinearLayout;

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout= (LinearLayout) findViewById(R.id.drawer_LinearLayout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
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
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //动态插入一个Fragment到FrameLayout
        Bundle args = new Bundle();
        args.putString("text", menusLists.get(position));
        switch (position){
            case 0:
                Fragment home_Fragment = new Home_Fragment();
                home_Fragment.setArguments(args);

                FragmentManager home_fm = getFragmentManager();
                home_fm.beginTransaction().replace(R.id.content_frame, home_Fragment).commit();

                break;
            case 1:
                Fragment my_planet_Fragment = new My_Planet_Fragment();
                my_planet_Fragment.setArguments(args);

                FragmentManager my_pianet_fm = getFragmentManager();
                my_pianet_fm.beginTransaction().replace(R.id.content_frame, my_planet_Fragment).commit();
                break;
            case 2:
                Fragment all_planet_Fragment = new all_planet_Fragment();
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
}
