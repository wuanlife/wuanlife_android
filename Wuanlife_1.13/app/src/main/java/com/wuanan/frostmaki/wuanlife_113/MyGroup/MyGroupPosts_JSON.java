package com.wuanan.frostmaki.wuanlife_113.MyGroup;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/21.
 */
public class MyGroupPosts_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {

        ArrayList<HashMap<String,String>> arraylist;
        HashMap<String,String> maps;
        String title = "";
        String nickname = "";
        String groupName = "";
        String createTime = "";
        String text = "";

        String postID="";//贴子ID
        String groupID="";//星球ID
        int pageCount=0;
        int currentPage=0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");

            JSONArray posts = data.getJSONArray("posts");
            if (posts.length()>0) {
                arraylist = new ArrayList<HashMap<String, String>>();
                Log.e("posts.length", posts.length() + "");
                for (int i = 0; i < posts.length(); ++i) {

                    maps = new HashMap<String, String>();
                    pageCount = data.getInt("pageCount");
                    currentPage = data.getInt("currentPage");

                    JSONObject posts_details = posts.getJSONObject(i);
                    title = posts_details.getString("title");
                    nickname = posts_details.getString("nickname");
                    groupName = posts_details.getString("groupName");
                    createTime = posts_details.getString("createTime");
                    text = posts_details.getString("text");
                    //image = posts_details.getString("image");
                    postID=posts_details.getString("postID");
                    groupID=posts_details.getString("groupID");
                    String image01=null;
                    String image02=null;
                    String image03=null;
                    JSONArray image=posts_details.getJSONArray("image");
                    if (image.length()==1){
                        image01=image.getString(0);
                    }else if (image.length()==2){
                        image01=image.getString(0);
                        image02=image.getString(1);
                    }else if (image.length()==3){
                        image01=image.getString(0);
                        image02=image.getString(1);
                        image03=image.getString(2);
                    }

                    maps.put("title", title);
                    maps.put("nickname", nickname);
                    maps.put("groupName", groupName);
                    maps.put("createTime", createTime);
                    maps.put("text", text);

                    maps.put("image01", image01);
                    maps.put("image02", image02);
                    maps.put("image03", image03);

                    maps.put("postID",postID);
                    maps.put("groupID",groupID);
                    maps.put("currentPage", currentPage + "");
                    maps.put("pageCount", pageCount + "");

                    arraylist.add(maps);

                }
                return arraylist;
            }else if (posts.length()==0){
                return null;
            }
            //Log.e( "onPostExecute: ",arraylist.get(0).get("text")+"" );
            //Log.e( "onPostExecute: ",arraylist.get(3).get("title")+"" );
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onPostExecute: ", e + "");
        }
        return null;
    }
}
