package com.wuanan.frostmaki.wuanlife_113.MyGroup;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts.GroupPostsActivity;
import com.wuanan.frostmaki.wuanlife_113.R;
import com.wuanan.frostmaki.wuanlife_113.Utils.Http_Url;
import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/4.
 */
public class CreateFragment extends Fragment implements View.OnClickListener {
    private View view;
    private LinearLayout linearLayout;
    private TextView textView;
    private ImageButton imageButton;
    private Button button_1;
    private Button button_2;

    private ArrayList<JoinCreateGroup> arraylist=null;
    private Handler hanlder =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    linearLayout.setVisibility(View.GONE);
                    break;
                case 1 :

                    if (arraylist.size()>0){
                        button_1.setVisibility(View.VISIBLE);
                        button_1.setText(arraylist.get(0).getName());
                        if (arraylist.size()>1){
                            button_2.setVisibility(View.VISIBLE);
                            button_2.setText(arraylist.get(1).getName());

                        }
                    }
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_mygroup_frame_content,container,false);
        initView();
        initData();
        return view;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (MyApplication.getUserInfo() != null) {
                    String ApiHost = MyApplication.getApiHost();
                    String user_id = MyApplication.getUserInfo().get(0).get("userID");
                    String Pr_URL = "http://" + ApiHost
                            + "/?service=Group.GetCreate&user_id=" + user_id;
                    String resultdata = Http_Url.getUrlReponse(Pr_URL);
                    arraylist = new ArrayList<JoinCreateGroup>();
                    try {
                        JSONObject jsonobject = new JSONObject(resultdata);
                        JSONObject data = jsonobject.getJSONObject("data");
                        JSONArray groups = data.getJSONArray("groups");
                        for (int i = 0; i < groups.length(); i++) {
                            JoinCreateGroup mlist = new JoinCreateGroup();

                            mlist.setNum(data.getInt("num"));
                            mlist.setPageCount(data.getInt("pageCount"));
                            mlist.setCurrentPage(data.getInt("currentPage"));
                            mlist.setUser_name(data.getString("user_name"));

                            mlist.setName(groups.getJSONObject(i).getString("name"));
                            mlist.setId(groups.getJSONObject(i).getInt("id"));//星球ID
                            mlist.setG_image(groups.getJSONObject(i).getString("g_image"));
                            mlist.setG_introduction(groups.getJSONObject(i).getString("g_introduction"));

                            arraylist.add(mlist);
                        }
                        MyApplication.setCreateGroupArrayList(arraylist);
                        Message message=new Message();
                        if (arraylist==null){
                            message.what=0;
                        }else {
                            message.what=1;
                            message.obj=arraylist;
                        }
                        hanlder.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

        }).start();
    }

    private void initView() {
        textView= (TextView) view.findViewById(R.id.name);
        imageButton= (ImageButton) view.findViewById(R.id.more);
        button_1= (Button) view.findViewById(R.id.group_1);
        button_2= (Button) view.findViewById(R.id.group_2);
        linearLayout= (LinearLayout) view.findViewById(R.id.content_fragment);

        textView.setText("已创建的星球");
        button_1.setVisibility(View.GONE);
        button_2.setVisibility(View.GONE);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.more :
                break;
            case R.id.group_1 :
                int id=arraylist.get(0).getId();
                String name=arraylist.get(0).getName();

                openThisGroup(id,name);
                break;
            case R.id.group_2 :
                int id_2=arraylist.get(1).getId();
                String name_2=arraylist.get(1).getName();

                openThisGroup(id_2,name_2);
                break;
        }
    }

    private void openThisGroup(int id,String name) {
        Intent intent=new Intent(getActivity(), GroupPostsActivity.class);
        intent.putExtra("group_id",id);
        intent.putExtra("group_name",name);

        startActivity(intent);
    }
}
