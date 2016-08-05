package com.wuanan.frostmaki.wuanlife_app.Home;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/21.
 */
public class HomePosts_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {

        ArrayList<HashMap<String,String>> arraylist;
        HashMap<String,String> maps;
        String title = "";           //标题
        String nickname = "";        //昵称
        String groupName = "";       //星球名称
        String createTime = "";      //创建时间
        String text = "";            //内容
        //String image01 = null;           //图片
        //String image02=null;
        //String image03 = null;
        String postID="";           //贴子ID
        String groupID="";           //xingqiuID
        int pageCount=0;
        int currentPage=0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");

            JSONArray posts = data.getJSONArray("posts");
            if (posts.length()>0) {
                arraylist = new ArrayList<HashMap<String, String>>();
                //Log.e("posts.length", posts.length() + "");
                for (int i = 0; i < posts.length(); ++i) {
                    String image01 = null;           //图片
                    String image02=null;
                    String image03 = null;

                    maps = new HashMap<String, String>();
                    pageCount = data.getInt("pageCount");
                    currentPage = data.getInt("currentPage");

                    JSONObject posts_details = posts.getJSONObject(i);
                    title = posts_details.getString("title");
                    nickname = posts_details.getString("nickname");
                    groupName = posts_details.getString("groupName");
                    createTime = posts_details.getString("createTime");
                    text = posts_details.getString("text");
                    postID=posts_details.getString("postID");
                    groupID=posts_details.getString("groupID");

                    //image = posts_details.getString("image");

                    JSONArray imageDetails=posts_details.getJSONArray("image");
                    if (imageDetails.length()==1) {
                        image01 = imageDetails.getString(0);
                    }else if (imageDetails.length()==2){
                        image01 = imageDetails.getString(0);
                        image02 = imageDetails.getString(1);
                    }else if (imageDetails.length()>=3){
                        image01 = imageDetails.getString(0);
                        image02 = imageDetails.getString(1);
                        image03 = imageDetails.getString(2);

                    }
                    //Log.e("HomePosts_JSON   image",image01+"     "+image02+"     "+image03);

                    maps.put("title", title);
                    maps.put("nickname", nickname);
                    maps.put("groupName", groupName);
                    maps.put("createTime", createTime);
                    maps.put("text", text);
                    maps.put("image01", image01);
                    //if (image01==null){
                    //    Log.e("image01","    位空"+"   title"+title);
                    //}else Log.e("image01","    no位空"+image01+"   title"+title);
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

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onPostExecute: ", e + "");
        }
        return null;
    }
}
