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
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ArrayList<String> menusLists;
    private ArrayAdapter<String> adapter;

    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        menusLists = new ArrayList<String>();
        for (int i = 1; i <= 5; i++) {
            menusLists.add("drawer:  " + i);
        }
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
        Fragment myplanetFragment = new MyPlanetFragment();
        Bundle args = new Bundle();
        args.putString("text", menusLists.get(position));
        myplanetFragment.setArguments(args);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, myplanetFragment).commit();

        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void initToolbar() {
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        mtoolbar.setLogo(R.drawable.ic_drawer);
        mtoolbar.setTitle("");

        setSupportActionBar(mtoolbar);
    }
}
