package com.wuanan.frostmaki.wuanlife_app.GroupLists;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.Groupcreate.Create_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;

import com.wuanan.frostmaki.wuanlife_app.GroupListPosts.GroupListsPosts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import java.util.ArrayList;
import java.util.HashMap;

public class All_planet_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView name;
    private Button createGroup;
    private Button pre;
    private Button next;
    private Button currentPage;
    private ListView all_planet_ListView;
    private ArrayList<HashMap<String,String>> all_planet_arrayList;
    private BaseAdapter all_planet_baseAdapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    all_planet_baseAdapter = new All_planet_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    all_planet_ListView.setAdapter(all_planet_baseAdapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_planet, container, false);
        //String text=getArguments().getString("text");
        //mButton.setText(text);
        name= (TextView) view.findViewById(R.id.create_planet_name);
        createGroup= (Button) view.findViewById(R.id.create_planet);
        pre= (Button) view.findViewById(R.id.pre);
        next= (Button) view.findViewById(R.id.next);
        currentPage= (Button) view.findViewById(R.id.currentPage);
        createGroup.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);

        all_planet_ListView= (ListView) view.findViewById(R.id.all_planet_listView);

        mContext=getActivity().getApplicationContext();


        all_planet_ListView.setOnItemClickListener(this);

        getRes();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_planet:
                Fragment create_planet_Fragment = new Create_planet_Fragment();
                FragmentManager create_planet_fm = getFragmentManager();
                create_planet_fm.beginTransaction().replace(R.id.content_frame, create_planet_Fragment).addToBackStack(null).commit();
                break;
            case R.id.pre:
                if (index>1){
                    --index;
                    getRes();
                    all_planet_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:

                if (index<pageCount) {
                    ++index;
                    getRes();
                    all_planet_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //jiang星球在ListView中的位置传递，
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        Fragment planet_details_Fragment = new GroupListsPosts_Fragment();
        planet_details_Fragment.setArguments(bundle);
        FragmentManager planet_details_fm = getFragmentManager();
        planet_details_fm.beginTransaction().replace(R.id.content_frame, planet_details_Fragment).addToBackStack(null).commit();
    }

    public void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getUrl();
                String Pr_URL="http://"+ApiHost+"/?service=Group.Lists&page="+index;
                String JsonData= Http_Url.getUrlReponse(Pr_URL);
                if (JsonData!=null) {
                    all_planet_arrayList = AllGroupLists_JSON.getJSONParse(JsonData);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = all_planet_arrayList;
                    handler.sendMessage(message);
                }
            }
        }).start();


    }

}
