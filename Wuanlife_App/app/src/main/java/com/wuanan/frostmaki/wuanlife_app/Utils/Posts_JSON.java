package com.wuanan.frostmaki.wuanlife_app.Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Frostmaki on 2016/7/21.
 */
public class Posts_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {

        ArrayList<HashMap<String,String>> arraylist;
        HashMap<String,String> maps;
        String title = "";
        String nickname = "";
        String groupName = "";
        String createTime = "";
        String text = "";
        String image = "";
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
                    title = posts_details.getString("title").trim();
                    nickname = posts_details.getString("nickname").trim();
                    groupName = posts_details.getString("groupName").trim();
                    createTime = posts_details.getString("createTime").trim();
                    text = posts_details.getString("text").trim();
                    image = posts_details.getString("image");
                /*Log.e("anuanl","title:       "+title+
                                "nickname:   "+nickname+
                                "groupName:  "+groupName+
                                "createTime: "+createTime+
                                "text:       "+text+
                                "image:      "+image);*/
                    maps.put("title", title);
                    maps.put("nickname", nickname);
                    maps.put("groupName", groupName);
                    maps.put("createTime", createTime);
                    maps.put("text", text);
                    maps.put("image", image);
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
