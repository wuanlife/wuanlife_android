package com.wuanan.frostmaki.wuanlife_app.All_planet;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.Create_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Planet_details.Planet_details_Fragment;
import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;

public class All_planet_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView textView;
    private Button button;
    private ListView all_planet_ListView;
    private ArrayList<String> all_planet_arrayList;
    private BaseAdapter all_planet_baseAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_planet, container, false);
        //String text=getArguments().getString("text");
        //mButton.setText(text);
        textView= (TextView) view.findViewById(R.id.create_planet_name);
        button= (Button) view.findViewById(R.id.create_planet);
        button.setOnClickListener(this);

        all_planet_ListView= (ListView) view.findViewById(R.id.all_planet_listView);
        all_planet_arrayList=new ArrayList<String>();

        for (int i=1;i<=20;++i){
            all_planet_arrayList.add("全部星球：  "+i);
        }
        all_planet_baseAdapter=new All_planet_listview_BaseAdapter(getActivity().getApplicationContext(),
                all_planet_arrayList);

        all_planet_ListView.setAdapter(all_planet_baseAdapter);
        all_planet_ListView.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        Fragment create_planet_Fragment = new Create_planet_Fragment();
        FragmentManager create_planet_fm = getFragmentManager();
        create_planet_fm.beginTransaction().replace(R.id.content_frame, create_planet_Fragment).commit();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment planet_details_Fragment = new Planet_details_Fragment();
        FragmentManager planet_details_fm = getFragmentManager();
        planet_details_fm.beginTransaction().replace(R.id.content_frame, planet_details_Fragment).commit();
    }
}
