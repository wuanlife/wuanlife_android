package com.wuanan.frostmaki.wuanlife_app.Groupcreate;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.wuanan.frostmaki.wuanlife_app.GroupLists.All_planet_Fragment;
import com.wuanan.frostmaki.wuanlife_app.MyApplication;
import com.wuanan.frostmaki.wuanlife_app.R;
import com.wuanan.frostmaki.wuanlife_app.Utils.Http_Url;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by Frostmaki on 2016/7/16.
 */
public class Create_planet_Fragment extends Fragment{
    private Button button;
    private EditText name;
    private EditText introduction;
    private ImageView image;
    private Context mContext;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    Toast.makeText(mContext, "星球创建成功", Toast.LENGTH_SHORT).show();
                    Fragment all_planet_Fragment = new All_planet_Fragment();
                    FragmentManager all_planet_fm = getFragmentManager();
                    all_planet_fm.beginTransaction().replace(R.id.content_frame, all_planet_Fragment).commit();
                    break;
                case 1:
                    String failure=msg.obj.toString();
                    Toast.makeText(mContext, failure, Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(mContext, "未登录", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            Log.e("创建星球","转到所有星球界面");

        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.create_planet,container,false);
        name= (EditText) view.findViewById(R.id.create_planet_edit);
        introduction= (EditText) view.findViewById(R.id.editText);
        image= (ImageView) view.findViewById(R.id.imageView);

        button= (Button) view.findViewById(R.id.create_planet_btn);
        mContext=getActivity().getApplicationContext();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GroupName=name.getText().toString();
                if (GroupName.length()>0){
                    Log.e("创建星球","开始创建");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e("创建星球","开始线程");
                            if (MyApplication.getUserInfo() != null) {

                                String userID = MyApplication.getUserInfo().get(0).get("userID");
                                String ApiHost = MyApplication.getUrl();
                                String Pre_URL = "http://" + ApiHost + "/?service=Group.Create&user_id=" + userID +
                                        "&name=" + name.getText().toString()
                                        +"&g_introduction="+introduction.getText().toString()
                                        +"&g_image="+"null";
                                String resultData = Http_Url.getUrlReponse(Pre_URL);
                                try {
                                    JSONObject jsonObject = new JSONObject(resultData);
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    int code = data.getInt("code");
                                    if (code == 1) {
                                        JSONObject info = data.getJSONObject("info");
                                        ArrayList<HashMap<String, String>> createGroupInfo = new ArrayList<HashMap<String, String>>();
                                        HashMap<String, String> map = new HashMap<String, String>();
                                        String group_base_id = "";//星球ID
                                        int user_base_id = 0;//用户ID
                                        String authorization = "";//用户权限 01表示创建者，02表示管理员，03表示会员
                                        String name = "";//星球名字
                                        String g_introduction = "";//星球简介
                                        String URL = "";//星球图片资源

                                        group_base_id = info.getString("group_base_id");
                                        user_base_id = info.getInt("user_base_id");
                                        authorization = info.getString("authorization");
                                        name = info.getString("name");
                                        g_introduction = info.getString("g_introduction");
                                        URL = info.getString("URL");

                                        map.put("group_base_id", group_base_id);
                                        map.put("user_base_id", user_base_id + "");
                                        map.put("authorization", authorization);
                                        map.put("name", name);
                                        map.put("g_introduction", g_introduction);
                                        map.put("URL", URL);
                                        createGroupInfo.add(map);

                                        Log.e("创建星球","创建成功");
                                        Message message=new Message();
                                        message.what=0;
                                        handler.sendMessage(message);

                                    } else if (code==0){
                                        Log.e("创建星球","创建失败"+resultData);
                                        String msg=data.getString("msg");
                                        Message message=new Message();
                                        message.what=1;
                                        message.obj=msg;
                                        handler.sendMessage(message);

                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Log.e("创建星球","异常"+e);
                                }

                            }else {
                                Log.e("创建星球","未登录");
                                Message message=new Message();
                                message.what=2;
                                handler.sendMessage(message);

                            }
                        }
                    }).start();
                }else Toast.makeText(mContext, "未输入星球名称", Toast.LENGTH_SHORT).show();





            }
        });
        return view;
    }
}
