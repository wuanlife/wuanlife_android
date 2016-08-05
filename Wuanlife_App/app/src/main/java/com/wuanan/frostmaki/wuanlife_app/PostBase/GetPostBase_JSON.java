package com.wuanan.frostmaki.wuanlife_app.PostBase;

import android.util.Log;

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
        String p_image="";
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
                p_image = data.getString("p_image");
                sticky = data.getString("sticky");

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
                maps.put("p_image", p_image);
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
