package com.wuanan.frostmaki.wuanlife_app.Home;


import android.app.Fragment;
import android.os.AsyncTask;
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
import java.util.HashMap;
import java.util.Map;

public class Home_Fragment extends Fragment{

    private TextView mTextView;
    private ListView home_ListView;
    public ArrayList<Map<String,String>> home_arrayList;
    public Map<String,String> map;
    private BaseAdapter home_baseAdapter;
    private Posts_http posts_AsyncTask;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);
        mTextView= (TextView) view.findViewById(R.id.home_name);
        //String text=getArguments().getString("text");
        //mTextView.setText(text);

        home_ListView= (ListView) view.findViewById(R.id.home_listView);

        map=new HashMap<String,String>();
        home_arrayList=new ArrayList<Map<String, String>>();

        //getRes();
        AdapterAsyncTask adapterAsyncTask=new AdapterAsyncTask();
        adapterAsyncTask.execute(home_arrayList);

        return view;
    }
    public void getRes(){
        posts_AsyncTask=new Posts_http(home_arrayList,map);
        posts_AsyncTask.execute();

    }

    private class AdapterAsyncTask extends AsyncTask<ArrayList<Map<String,String>>,Void,ArrayList<Map<String,String>>>{


        @Override
        protected ArrayList<Map<String, String>> doInBackground(ArrayList<Map<String, String>>... params) {
            ArrayList<Map<String,String>> list=params[0];
            return list;
        }

        @Override
        protected void onPreExecute() {
            getRes();
        }

        @Override
        protected void onPostExecute(ArrayList<Map<String, String>> maps) {
            home_baseAdapter=new Home_listview_BaseAdapter(getActivity().getApplicationContext(),
                    home_arrayList,map);

            home_ListView.setAdapter(home_baseAdapter);
        }
    }
}
