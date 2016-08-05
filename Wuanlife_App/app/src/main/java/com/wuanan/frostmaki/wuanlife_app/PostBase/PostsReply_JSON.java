package com.wuanan.frostmaki.wuanlife_app.PostBase;

import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/31.
 */
public class PostsReply_JSON {
    public static HashMap<String,String> getJSONParse(String s) {


        HashMap<String,String> maps;
        String post_base_id = "";//贴子ID
        String user_base_id = "";//回帖人ID
        String replyid = "";  //null
        String createTime = "";
        String text = "";
        String floor = "";//楼层


        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");


            maps = new HashMap<String, String>();

            maps.put("createTime", createTime);
            maps.put("text", text);
            maps.put("post_base_id",post_base_id);
            maps.put("user_base_id",user_base_id);
            maps.put("replyid",replyid);
            maps.put("floor",floor);





            return maps;


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("POstsReply-JSON异常", e + "");
        }
        return null;
    }
}
