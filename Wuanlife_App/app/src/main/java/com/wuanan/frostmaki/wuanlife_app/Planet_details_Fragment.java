package com.wuanan.frostmaki.wuanlife_app;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/7/17.
 */
public class Planet_details_Fragment extends Fragment {
    private TextView textView;
    private Button button;
    private ListView planet_details_ListView;
    private ArrayList<String> planet_details_arrayList;
    private BaseAdapter planet_details_baseAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.planet_details,container,false);

        planet_details_ListView= (ListView) view.findViewById(R.id.planet_details_listview);
        planet_details_arrayList=new ArrayList<String>();

        for (int i=1;i<=20;++i){
            planet_details_arrayList.add("我的星球：  "+i);
        }
       planet_details_baseAdapter=new Planet_details_listview_BaseAdapter(getActivity().getApplicationContext(),
                planet_details_arrayList);

        planet_details_ListView.setAdapter(planet_details_baseAdapter);
        return view;
    }
}
