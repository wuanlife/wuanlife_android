package com.wuanan.frostmaki.wuanlife_app.My_planet;

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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.Nothing.Nothing_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_app.Utils.Posts_JSON;
import com.wuanan.frostmaki.wuanlife_app.Utils.Posts_listview_BaseAdapter;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class My_Planet_Fragment extends Fragment implements View.OnClickListener{
    private TextView name;
    private Button button;
    private ListView my_planet_ListView;
    private ArrayList<HashMap<String,String>> my_planet_arrayList;
    private Map<String,String> map;
    private BaseAdapter my_planet_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private int index=1;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    my_planet_baseAdapter = new Posts_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);

                    my_planet_ListView.setAdapter(my_planet_baseAdapter);
                    break;
                case 1:
                    Fragment nothingFragment=new Nothing_Fragment();
                    Bundle args=new Bundle();
                    args.putString("Name","我的星球");
                    nothingFragment.setArguments(args);
                    FragmentManager no_fm=getFragmentManager();
                    no_fm.beginTransaction().replace(R.id.content_frame,nothingFragment).commit();
                    break;

            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        if (MyApplication.getUserInfo()!=null) {
            View view = inflater.inflate(R.layout.my_planet, container, false);
            name = (TextView) view.findViewById(R.id.my_planet_name);
            String text = getArguments().getString("text");
            //textView.setText("你好，这里是"+text);

            pre = (Button) view.findViewById(R.id.pre);
            next = (Button) view.findViewById(R.id.next);
            currentPage = (Button) view.findViewById(R.id.currentPage);

            pre.setOnClickListener(this);
            next.setOnClickListener(this);

            my_planet_ListView = (ListView) view.findViewById(R.id.my_planet_listView);
            my_planet_arrayList = new ArrayList<HashMap<String, String>>();
            map = new HashMap<String, String>();

            mContext = getActivity().getApplicationContext();

            getRes();
            return view;
        }else {
            View view=inflater.inflate(R.layout.nothing,container,false);
            name= (TextView) view.findViewById(R.id.name);
            name.setText("我的星球");
            button=(Button)view.findViewById(R.id.nothingButton);
            button.setVisibility(View.INVISIBLE);
            return view;
        }
    }


    public void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo()!=null) {
                    String ApiHost = MyApplication.getUrl();
                    String id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost + "/?service=Post.GetMyGroupPost&id=" + id + "&pn=" + index;
                    String JsonData = Http_Url.getUrlReponse(Pr_URL);
                    my_planet_arrayList = Posts_JSON.getJSONParse(JsonData);
                    if (my_planet_arrayList!=null) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = my_planet_arrayList;
                        handler.sendMessage(message);
                    }
                }else {
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
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
                    my_planet_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:

                if (index<my_planet_arrayList.size()) {
                    ++index;
                    getRes();
                    my_planet_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            default:
                break;
        }
    }
}
