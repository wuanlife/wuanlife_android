package com.wuanan.frostmaki.wuanlife_app;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/7/18.
 */
public class MyApplication {
    //服务器地址
    private static String ApiHost="dev.wuanlife.com:800";

    public static String getUrl(){
        return ApiHost;
    }

//登陆的用户信息，通过它得到用户ID，展示我的星球内容
    private static ArrayList<HashMap<String,String>> userArraylists=null;

    public static ArrayList<HashMap<String,String>> getUserInfo(){
        return userArraylists;
    }
    public static void setUserInfo(ArrayList<HashMap<String,String>> info){
        userArraylists=info;
    }


//全部星球ArrayLists，通过它获得星球ID，得到该星球中发表的帖子
    private static ArrayList<HashMap<String,String>> GroupListArraylists=null;

    public static ArrayList<HashMap<String,String>> getGroupInfo(){
        return GroupListArraylists;
    }
    public static void setGroupListInfo(ArrayList<HashMap<String,String>> info){
        GroupListArraylists=info;
    }


    //首页帖子的信息ArrayLists，通过它获得帖子的ID，进而开始帖子详情。
    private static ArrayList<HashMap<String,String>> HomePostsArraylists=null;

    public static ArrayList<HashMap<String,String>> getHomePostsInfo(){
        return GroupListArraylists;
    }
    public static void setHomePostsInfo(ArrayList<HashMap<String,String>> info){
        GroupListArraylists=info;
    }
}
