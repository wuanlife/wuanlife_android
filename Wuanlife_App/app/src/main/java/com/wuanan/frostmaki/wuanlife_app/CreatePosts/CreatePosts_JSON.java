package com.wuanan.frostmaki.wuanlife_app.CreatePosts;

import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/29.
 */
public class CreatePosts_JSON {
    public static HashMap<String,String> getJSONParse(String s) {
        HashMap<String,String> maps;
        String title = "";     //标题
        String floor = "";   //楼层
        String group_base_id="";//所属星球ID
        String post_base_id = "";//帖子ID
        String createTime = "";//创建时间
        String text = "";//内容
        String user_base_id="";//用户ID

        String url = ""; //图片
        String msg="";

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");
            msg=jsonObject.getString("msg");

            JSONObject info=data.getJSONObject("info");

            int code=data.getInt("code");
            if (code==1){
                title=info.getString("title");
                text=info.getString("text");
                post_base_id=info.getString("post_base_id");
                user_base_id=info.getString("user_base_id");
                floor=info.getString("floor");
                createTime=info.getString("createTime");
            }

            maps=new HashMap<String, String>();
            maps.put("title",title);
            maps.put("text",text);
            maps.put("post_base_id",post_base_id);
            maps.put("user_base_id",user_base_id);
            maps.put("floor",floor);
            maps.put("createTime",createTime);

            return maps;

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CreatePost_JSOSN异常 ", e + "");
        }
        return null;
    }
}
