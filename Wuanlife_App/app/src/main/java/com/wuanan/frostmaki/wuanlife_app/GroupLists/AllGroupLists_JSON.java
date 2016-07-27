package com.wuanan.frostmaki.wuanlife_app.GroupLists;

import android.util.Log;

import com.wuanan.frostmaki.wuanlife_app.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/22.
 */
public class AllGroupLists_JSON {
    public static ArrayList<HashMap<String,String>> getJSONParse(String s) {

        ArrayList<HashMap<String, String>> arraylist;
        HashMap<String, String> map;
        String title="";
        String text="";
        String image="";
        int num=0;//星球成员数
        int id=0;//星球ID
        int currentPage=0;
        int pageCount=0;
        try {
            JSONObject jsonObject =new JSONObject(s);
            JSONObject data=jsonObject.getJSONObject("data");
            JSONArray lists=data.getJSONArray("lists");
            arraylist=new ArrayList<HashMap<String, String>>();
            Log.e("posts.length",lists.length()+"");
            for (int i=0;i<lists.length();++i){
                currentPage=data.getInt("currentPage");
                pageCount=data.getInt("pageCount");

                map=new HashMap<String, String>();
                JSONObject lists_details=lists.getJSONObject(i);
                title=lists_details.getString("name");
                text=lists_details.getString("g_introduction");
                image=lists_details.getString("g_image");
                num=lists_details.getInt("num");
                id=lists_details.getInt("id");

                map.put("num",num+"");
                map.put("id",id+"");
                map.put("currentPage",currentPage+"");
                map.put("pageCount",pageCount+"");
                map.put("title",title);
                map.put("text",text);
                map.put("image",image);
                arraylist.add(map);

            }
            MyApplication.setGroupListInfo(arraylist);
            return arraylist;
            //Log.e( "onPostExecute: ",arraylist.get(0).get("text")+"" );
            //Log.e( "onPostExecute: ",arraylist.get(3).get("title")+"" );
        }catch (Exception e){
            e.printStackTrace();
            Log.e( "onPostExecute: ",e+"" );
        }
        return null;
    }
}
