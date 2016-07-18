package com.wuanan.frostmaki.wuanlife_app.Home;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;

public class Home_Fragment extends Fragment{

    private TextView mTextView;
    private ListView home_ListView;
    private ArrayList<String> home_arrayList;
    private BaseAdapter home_baseAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);
        mTextView= (TextView) view.findViewById(R.id.home_name);
        //String text=getArguments().getString("text");
        //mTextView.setText(text);

        home_ListView= (ListView) view.findViewById(R.id.home_listView);
        home_arrayList=new ArrayList<String>();

        for (int i=1;i<=20;++i){
            home_arrayList.add("首页：  "+i);
        }
        home_baseAdapter=new Home_listview_BaseAdapter(getActivity().getApplicationContext(),
                home_arrayList);

        home_ListView.setAdapter(home_baseAdapter);
        return view;
    }
}
