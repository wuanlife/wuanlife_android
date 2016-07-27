package com.wuanan.frostmaki.wuanlife_app.Home;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_app.Utils.Posts_JSON;
import com.wuanan.frostmaki.wuanlife_app.Utils.Posts_listview_BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Home_Fragment extends Fragment implements View.OnClickListener{

    private TextView name;
    private ListView home_ListView;
    public ArrayList<HashMap<String,String>> home_arrayList;
    public Map<String,String> map;
    private BaseAdapter home_baseAdapter;

    public Context mContext;
    private int index=1;
    private Button pre;
    private Button next;
    private Button currentPage;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    //将首页帖子信息传到MyApplication中，作为全局变量。
                    MyApplication.setHomePostsInfo(arrayList);

                    home_baseAdapter = new Posts_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);

                            home_ListView.setAdapter(home_baseAdapter);
            }
        }
    };




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home,container,false);
        name= (TextView) view.findViewById(R.id.home_name);
        //String text=getArguments().getString("text");
        //mTextView.setText(text);

        mContext=getActivity().getApplicationContext();
        home_ListView= (ListView) view.findViewById(R.id.home_listView);
        home_arrayList=new ArrayList<HashMap<String,String>>();

        pre= (Button) view.findViewById(R.id.pre);
        next= (Button) view.findViewById(R.id.next);
        currentPage= (Button) view.findViewById(R.id.currentPage);

        pre.setOnClickListener(this);
        next.setOnClickListener(this);


        getRes();


        return view;
    }
    public void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost= MyApplication.getUrl();
                String Pr_URL="http://"+ApiHost+"/?service=Post.GetIndexPost&pn="+index;
                String JsonData= Http_Url.getUrlReponse(Pr_URL);

                if (JsonData!=null) {
                    home_arrayList = Posts_JSON.getJSONParse(JsonData);
                    Message message = new Message();
                    message.what = 0;
                    message.obj = home_arrayList;
                    handler.sendMessage(message);
                }else {
                    //Log.e("Home---->JsonData",JsonData);
                }
            }
        }).start();


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pre:
                if (index>1){
                    --index;
                   getRes();
                    home_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:

                    if (index<home_arrayList.size()) {
                        ++index;
                        getRes();
                        home_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            default:
                break;
        }
    }


}
