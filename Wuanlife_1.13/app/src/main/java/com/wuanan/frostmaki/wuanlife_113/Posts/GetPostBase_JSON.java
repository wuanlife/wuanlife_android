package com.wuanan.frostmaki.wuanlife_113.Posts;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/31.
 */
public class GetPostBase_JSON {
    public static HashMap<String,String> getJSONParse(String s) {
        HashMap<String,String> maps;
        String postID="";
        String groupID="";
        String groupName="";
        String title="";
        String text="";
        String id="";//y发帖用户ID
        String nickname="";//发帖人
        String createTime="";//发帖时间
        String editRight="";//编辑权限
        String deleteRight="";//删除权限
        String stickyRight="";//置顶权限


        String sticky="";
        maps=new HashMap<String, String>();
        try {
            JSONObject jsonObject=new JSONObject(s);
            if (jsonObject.getInt("ret")==200){
                JSONObject data = jsonObject.getJSONObject("data");
                postID = data.getString("postID");
                groupID = data.getString("groupID");
                groupName = data.getString("groupName");
                title = data.getString("title");
                text = data.getString("text");
                id = data.getString("id");
                nickname = data.getString("nickname");
                createTime = data.getString("createTime");
                editRight = data.getString("editRight");
                deleteRight = data.getString("deleteRight");
                stickyRight = data.getString("stickyRight");
                sticky = data.getString("sticky");
                String image01=null;
                String image02=null;
                String image03=null;

        if ((data.getString("p_image"))=="null") {


        }else if((data.getString("p_image"))!="null"){

                JSONArray p_image = data.getJSONArray("p_image");
                if (p_image.length() == 1) {
                    image01 = p_image.getJSONObject(0).getString("URL");
                } else if (p_image.length() == 2) {
                    image01 = p_image.getJSONObject(0).getString("URL");
                    image02 = p_image.getJSONObject(1).getString("URL");
                } else if (p_image.length() >= 3) {
                    image01 = p_image.getJSONObject(0).getString("URL");
                    image02 = p_image.getJSONObject(1).getString("URL");
                    image03 = p_image.getJSONObject(2).getString("URL");

                }
        }
                maps.put("postID", postID);
                maps.put("groupID", groupID);
                maps.put("groupName", groupName);
                maps.put("title", title);
                maps.put("text", text);
                maps.put("id", id);
                maps.put("nickName", nickname);
                maps.put("createTime", createTime);
                maps.put("editRight", editRight);
                maps.put("deleteRight", deleteRight);
                maps.put("stickyRight", stickyRight);
                maps.put("image01",image01);
                maps.put("image02",image02);
                maps.put("image03",image03);
                maps.put("sticky", sticky);
                return maps;
            }else{
                String msg="";
                msg=jsonObject.getString("msg");
                maps.put("msg",msg);
                return maps;
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("PostBase_JSON异常",e+"");
        }

        return null;
    }
}
