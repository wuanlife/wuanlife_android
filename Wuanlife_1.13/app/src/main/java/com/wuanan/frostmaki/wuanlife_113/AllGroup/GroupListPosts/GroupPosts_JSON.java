package com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts;

import android.util.Log;

import com.wuanan.frostmaki.wuanlife_113.Utils.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/24.
 */
public class GroupPosts_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {
        ArrayList<HashMap<String,String>> arraylist;
        HashMap<String,String> maps;
        String title = "";
        String nickname = "";
        String groupID="";
        String groupName = "";
        String createTime = "";
        String text = "";

        String image0 = "";
        String image1 = "";
        String image2 = "";
        String postID="";
        String digest="";
        String sticky="";
        String id="";
        int pageCount=0;
        int currentPage=0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");

            groupID=data.getString("groupID");
            groupName=data.getString("groupName");
            pageCount=data.getInt("pageCount");
            currentPage=data.getInt("currentPage");

            JSONArray posts = data.getJSONArray("posts");
            arraylist=new ArrayList<HashMap<String, String>>();
            Log.e("Groupposts_JSON.length", posts.length() + "");
            if (posts.length()>0) {
                for (int i = 0; i < posts.length(); ++i) {

                    maps = new HashMap<String, String>();

                    JSONObject posts_details = posts.getJSONObject(i);

                    digest = posts_details.getString("digest");
                    postID = posts_details.getString("postID");
                    title = posts_details.getString("title");
                    text = posts_details.getString("text");
                    createTime = posts_details.getString("createTime");
                    id = posts_details.getString("id");
                    nickname = posts_details.getString("nickname");
                    sticky = posts_details.getString("sticky");

                    JSONArray imageRes = posts_details.getJSONArray("image");
                    if (imageRes.length() == 1) {
                        image0 = imageRes.getString(0);
                    } else if (imageRes.length() == 2) {
                        image0 = imageRes.getString(0);
                        image1 = imageRes.getString(1);
                    } else if (imageRes.length() >= 3) {
                        image0 = imageRes.getString(0);
                        image1 = imageRes.getString(1);
                        image2 = imageRes.getString(2);
                    }

                    maps.put("title", title);
                    maps.put("nickname", nickname);
                    maps.put("groupName", groupName);
                    maps.put("groupID", groupID);
                    maps.put("createTime", createTime);
                    maps.put("text", text);
                    maps.put("image0", image0);
                    maps.put("image1", image1);
                    maps.put("image2", image2);
                    maps.put("postID", postID);
                    maps.put("digest", digest);
                    maps.put("sticky", sticky);
                    maps.put("id", id);
                    maps.put("currentPage", currentPage + "");
                    maps.put("pageCount", pageCount + "");

                    arraylist.add(maps);

                }
                //MyApplication.setGroupPostsInfo(arraylist);
                return arraylist;

            }else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GroupListPost异常 ", e + "");
        }
        return null;
    }
}
