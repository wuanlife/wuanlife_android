package com.wuanan.frostmaki.wuanlife_app.MyGroup;

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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.GroupLists.All_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Nothing.Nothing_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Post.Posts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_app.Utils.Posts_listview_BaseAdapter;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MyGroupPosts_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView name;
    private Button button;

    private Button myGroupCreate;
    private Button myGroupJoin;

    private ImageButton more;

    private ListView my_planet_ListView;
    private ArrayList<HashMap<String,String>> my_planet_arrayList;
    private ArrayList<HashMap<String,String>> arrayList=null;
    private Map<String,String> map;
    private BaseAdapter my_planet_baseAdapter;
    private Context mContext;
    private Button currentPage;
    private Button pre;
    private Button next;
    private int index=1;
    private int pageCount=1;
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
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
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
                case 2:

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
            //String text = getArguments().getString("text");
            //textView.setText("你好，这里是"+text);

            pre = (Button) view.findViewById(R.id.pre);
            next = (Button) view.findViewById(R.id.next);
            currentPage = (Button) view.findViewById(R.id.currentPage);

            pre.setOnClickListener(this);
            next.setOnClickListener(this);

            my_planet_ListView = (ListView) view.findViewById(R.id.my_planet_listView);
            my_planet_ListView.setOnItemClickListener(this);
            my_planet_arrayList = new ArrayList<HashMap<String, String>>();
            map = new HashMap<String, String>();

            mContext = getActivity().getApplicationContext();




            myGroupCreate= (Button) view.findViewById(R.id.group_fir);
            myGroupJoin= (Button) view.findViewById(R.id.group_Sec);
            //myGroup03= (Button) view.findViewById(R.id.group_Third);
            //myGroup04=(Button)view.findViewById(R.id.four);
            //more= (ImageButton) view.findViewById(R.id.more);
            myGroupCreate.setOnClickListener(this);
            myGroupJoin.setOnClickListener(this);
            //myGroup03.setOnClickListener(this);
            //myGroup04.setOnClickListener(this);
            //more.setOnClickListener(this);

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
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Post.GetMyGroupPost&id=" + id
                            + "&pn=" + index;
                    String JsonData = Http_Url.getUrlReponse(Pr_URL);
                    my_planet_arrayList = MyGroupPosts_JSON.getJSONParse(JsonData);
                    if (my_planet_arrayList!=null) {
                        //wo的星球帖子信息

                        MyApplication.setMyGroupPostsInfo(my_planet_arrayList);
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
    private void getRes02(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                arrayList=new ArrayList<HashMap<String, String>>();
                HashMap<String,String> map=null;
                String name="";
                String id="";
                String g_image="";
                String g_introduction="";

                String m_num="";
                String m_pageCount="";
                String m_currentPage="";
                String m_user_name="";

                String ApiHost = MyApplication.getUrl();
                String userid = MyApplication.getUserInfo().get(0).get("userID");
                String Pr_URL = "http://" + ApiHost
                        + "?service=Group.GetJoined" +"&user_id="+ userid;
                String resultData = Http_Url.getUrlReponse(Pr_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    if (jsonObject.getInt("ret")==200){
                        JSONObject data=jsonObject.getJSONObject("data");
                        m_num=data.getString("num");
                        m_currentPage=data.getString("currentPage");
                        m_pageCount=data.getString("pageCount");
                        m_user_name=data.getString("ser_name");

                        JSONArray groups=data.getJSONArray("groups");
                        for (int i=0;i<groups.length();++i){
                            JSONObject groups_details=groups.getJSONObject(i);
                            map=new HashMap<String, String>();

                            name=groups_details.getString("name");
                            id=groups_details.getString("id");
                            g_introduction=groups_details.getString("g_introduction");
                            g_image=groups_details.getString("g_image");

                            map.put("name",name);
                            map.put("id",id);
                            map.put("g_introduction",g_introduction);
                            map.put("g_image",g_image);

                            map.put("num",m_num);
                            map.put("currentPage",m_currentPage);
                            map.put("pageCount",m_pageCount);
                            map.put("user_name",m_user_name);

                            arrayList.add(map);
                        }
                        if (arrayList!=null) {
                            Message message=new Message();
                            message.what=2;
                            message.obj=arrayList;
                            handler.sendMessage(message);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("wo加入的星球异常",e+"");
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

                if (index<pageCount) {
                    ++index;
                    getRes();
                    my_planet_baseAdapter.notifyDataSetChanged();

                }else {
                    Log.e("next","cuowu");
                }
                break;
            case R.id.group_fir:
                MyGroupCreate_Fragment myGroupCreate_fragment = new MyGroupCreate_Fragment();
                FragmentManager myGroupCreate_fm = getFragmentManager();
                myGroupCreate_fm.beginTransaction().replace(R.id.content_frame, myGroupCreate_fragment).addToBackStack(null).commit();
                break;
            case R.id.group_Sec:
                MyGroupJoin_Fragment myGroupJoin_fragment = new MyGroupJoin_Fragment();
                FragmentManager myGroupJoin_fm = getFragmentManager();
                myGroupJoin_fm.beginTransaction().replace(R.id.content_frame, myGroupJoin_fragment).addToBackStack(null).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String data1=MyApplication.getMyGroupPostsInfo().get(position).get("postID");
        Bundle bundle = new Bundle();
        bundle.putString("postID", data1);

        Posts_Fragment posts_fragment = new Posts_Fragment();
        posts_fragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.content_frame, posts_fragment).addToBackStack(null).commit();
    }
}
