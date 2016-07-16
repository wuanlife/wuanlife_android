package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class My_Planet_Fragment extends Fragment{
    private TextView textView;
    private ListView my_planet_ListView;
    private ArrayList<String> my_planet_arrayList;
    private BaseAdapter my_planet_baseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_planet,container,false);
        textView= (TextView) view.findViewById(R.id.my_planet_name);
        String text=getArguments().getString("text");
        //textView.setText("你好，这里是"+text);

        my_planet_ListView= (ListView) view.findViewById(R.id.my_planet_listView);
        my_planet_arrayList=new ArrayList<String>();

        for (int i=1;i<=20;++i){
            my_planet_arrayList.add("我的星球：  "+i);
        }
        my_planet_baseAdapter=new My_planet_listview_BaseAdapter(getActivity().getApplicationContext(),
                my_planet_arrayList);

        my_planet_ListView.setAdapter(my_planet_baseAdapter);
        return view;
    }
}
