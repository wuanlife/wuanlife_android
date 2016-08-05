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
import android.widget.ListView;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_app.GroupListPosts.GroupListsPosts_Fragment;
import com.wuanan.frostmaki.wuanlife_app.GroupLists.All_planet_listview_BaseAdapter;
import com.wuanan.frostmaki.wuanlife_app.Groupcreate.Create_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyGroupCreate_Fragment extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private TextView name;
    private Button createGroup;
    private Button pre;
    private Button next;
    private Button currentPage;
    private ListView myGroupJoin_ListView;
    private ArrayList<HashMap<String,String>> myGroupJoin_arrayList;
    private BaseAdapter myGroupJoin_baseAdapter;
    private Context mContext;
    private int index=1;
    private int pageCount=1;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    ArrayList<HashMap<String,String>> arrayList=(ArrayList<HashMap<String,String>>)msg.obj;
                    myGroupJoin_baseAdapter = new All_planet_listview_BaseAdapter(
                            mContext,
                            arrayList,
                            currentPage);
                    pageCount= Integer.parseInt(arrayList.get(0).get("pageCount"));
                    myGroupJoin_ListView.setAdapter(myGroupJoin_baseAdapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.all_planet, container, false);
        //String text=getArguments().getString("text");
        //mButton.setText(text);
        name= (TextView) view.findViewById(R.id.all_planet_name);
        name.setText("我加入的星球");
        createGroup= (Button) view.findViewById(R.id.create_planet);
        pre= (Button) view.findViewById(R.id.pre);
        next= (Button) view.findViewById(R.id.next);
        currentPage= (Button) view.findViewById(R.id.currentPage);
        createGroup.setOnClickListener(this);
        pre.setOnClickListener(this);
        next.setOnClickListener(this);

        myGroupJoin_ListView= (ListView) view.findViewById(R.id.all_planet_listView);

        mContext=getActivity().getApplicationContext();


        myGroupJoin_ListView.setOnItemClickListener(this);

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
                    myGroupJoin_baseAdapter.notifyDataSetChanged();
                }else {
                    Log.e("pre","cuowu");
                }
                break;
            case R.id.next:

                if (index<pageCount) {
                    ++index;
                    getRes();
                    myGroupJoin_baseAdapter.notifyDataSetChanged();

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

    /*public void getRes(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                String ApiHost = MyApplication.getUrl();
                String userid = MyApplication.getUserInfo().get(0).get("userID");
                String Pr_URL = "http://" + ApiHost
                        + "?service=Group.GetJoined" +"&user_id="+ userid;
                //String ApiHost= MyApplication.getUrl();
                //String Pr_URL="http://"+ApiHost+"/?service=Group.Lists&page="+index;
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


    }*/
    private void getRes(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                myGroupJoin_arrayList=new ArrayList<HashMap<String, String>>();
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
                        + "?service=Group.GetCreate" +"&user_id="+ userid;
                String resultData = Http_Url.getUrlReponse(Pr_URL);
                try {
                    JSONObject jsonObject=new JSONObject(resultData);
                    if (jsonObject.getInt("ret")==200) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        m_num = data.getString("num");
                        m_currentPage = data.getString("currentPage");
                        m_pageCount = data.getString("pageCount");
                        m_user_name = data.getString("user_name");

                        JSONArray groups = data.getJSONArray("groups");
                        if (groups.length() != 0) {
                            for (int i = 0; i < groups.length(); ++i) {
                                JSONObject groups_details = groups.getJSONObject(i);
                                map = new HashMap<String, String>();

                                name = groups_details.getString("name");
                                id = groups_details.getString("id");
                                g_introduction = groups_details.getString("g_introduction");
                                g_image = groups_details.getString("g_image");

                                map.put("title", name);
                                map.put("id", id);
                                map.put("text", g_introduction);
                                map.put("image", g_image);

                                map.put("num", m_num);
                                map.put("currentPage", m_currentPage);
                                map.put("pageCount", m_pageCount);
                                map.put("user_name", m_user_name);

                                myGroupJoin_arrayList.add(map);
                            }
                            if (myGroupJoin_arrayList != null) {
                                Log.e("myGroupJoin_arrayList","jiazai");
                                MyApplication.setGroupListInfo(myGroupJoin_arrayList);
                                Message message = new Message();
                                message.what = 0;
                                message.obj = myGroupJoin_arrayList;
                                handler.sendMessage(message);
                            }
                        }
                    }else {

                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Log.e("wo加入的星球异常",e+"");
                }
            }
        }).start();
    }

}
