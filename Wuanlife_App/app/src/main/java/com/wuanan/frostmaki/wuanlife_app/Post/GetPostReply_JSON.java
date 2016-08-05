package com.wuanan.frostmaki.wuanlife_app.Post;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/31.
 */
public class GetPostReply_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {
        ArrayList<HashMap<String,String>> arrayList=null;
        HashMap<String,String> map=null;
        String postID="";//贴子ID
        String replyCount="";//
        String pageCount="";
        String currentPage="";

        String text="";
        String nickname="" ;
        String createTime="";
        try {

            JSONObject jsonObject = new JSONObject(s);
            if (jsonObject.getInt("ret")==200){
                arrayList=new ArrayList<HashMap<String, String>>();

                JSONObject data=jsonObject.getJSONObject("data");
                postID=data.getString("postID");
                replyCount=data.getString("replyCount");
                pageCount=data.getString("pageCount");
                currentPage=data.getString("currentPage");

                JSONArray reply=data.getJSONArray("reply");
                for (int i=0;i<reply.length();++i){
                    JSONObject reply_details=reply.getJSONObject(i);
                    nickname=reply_details.getString("nickname");
                    createTime=reply_details.getString("createTime");
                    text=reply_details.getString("text");

                    map=new HashMap<String, String>();
                    map.put("nickname",nickname);
                    map.put("createTime",createTime);
                    map.put("text",text);

                    map.put("postID",postID);
                    map.put("replyCount",replyCount);
                    map.put("pageCount",pageCount);
                    map.put("currentPage",currentPage);

                    arrayList.add(map);
                }
                return arrayList;

            }else {
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("GetPostReply_JSON异常",e+"");
        }
        return null;
    }
}
