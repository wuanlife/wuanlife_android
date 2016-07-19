package com.wuanan.frostmaki.wuanlife_app.Home;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.wuanan.frostmaki.wuanlife_app.MyApplication;

import org.json.JSONArray;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Created by Frostmaki on 2016/7/19.
 */
public class Posts_http extends AsyncTask<String,String,String> {
    private String ApiHost;
    public ArrayList<Map<String,String>> arraylist;
    public Map<String,String> map;

    public Posts_http(ArrayList<Map<String,String>> arraylist,Map<String,String > map){
        this.arraylist=arraylist;
        //this.map=map;
        ApiHost= MyApplication.getUrl();
    }
    @Override
    protected String doInBackground(String... params) {
        String Pr_URL="http://"+ApiHost+"/?service=Post.GetIndexPost&pn=1";
        BufferedReader reader;

        try{
            URL url=new URL(Pr_URL);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(8000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type", "plain/text; charset=UTF-8");
            connection.setRequestMethod("POST");


            InputStream in=connection.getInputStream();

            reader=new BufferedReader(new InputStreamReader(in));


            int responseCode=connection.getResponseCode();
            if(responseCode==200) {
                String inputline = null;
                String resultData = null;

                while ((inputline = reader.readLine()) != null) {
                    resultData = inputline + "\n";
                }

                return resultData;
            }else{
                Log.e("responseCode","ww"+responseCode);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String resultData) {

        Log.e("s",resultData);
        String title="";
        String nickname="";
        String groupName="";
        String createTime="";
        String text="";
        String image="";
        try {
            JSONObject jsonObject =new JSONObject(resultData);
            JSONObject data=jsonObject.getJSONObject("data");
            JSONArray posts=data.getJSONArray("posts");
            //arraylist=new ArrayList<Map<String, String>>();
            Log.e("posts.length",posts.length()+"");
            for (int i=0;i<posts.length();++i){

                map=new HashMap<String, String>();
                JSONObject posts_details=posts.getJSONObject(i);
                title=posts_details.getString("title");
                nickname=posts_details.getString("nickname");
                groupName=posts_details.getString("groupName");
                createTime=posts_details.getString("createTime");
                text=posts_details.getString("text");
                image=posts_details.getString("image");
                Log.e("anuanl","title:       "+title+
                                "nickname:   "+nickname+
                                "groupName:  "+groupName+
                                "createTime: "+createTime+
                                "text:       "+text+
                                "image:      "+image);
                map.put("title",title);
                map.put("nickname",nickname);
                map.put("groupName",groupName);
                map.put("createTime",createTime);
                map.put("text",text);
                map.put("image",image);
                arraylist.add(map);

            }
            Log.e( "onPostExecute: ",arraylist.get(0).get("text")+"" );
            Log.e( "onPostExecute: ",arraylist.get(3).get("title")+"" );
        }catch (Exception e){
            e.printStackTrace();
            Log.e( "onPostExecute: ",e+"" );
        }
    }
}
