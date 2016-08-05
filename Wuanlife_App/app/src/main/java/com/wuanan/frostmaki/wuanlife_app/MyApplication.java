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






    // /登陆的用户信息，通过它得到用户ID，展示我的星球内容
    private static ArrayList<HashMap<String,String>> userArraylists=null;

    //MyApplication.getUserInfo().get(0).get("userID");得到登录用户ID
    public static ArrayList<HashMap<String,String>> getUserInfo(){
        return userArraylists;
    }
    public static void setUserInfo(ArrayList<HashMap<String,String>> info){
        userArraylists=info;
    }







//全部星球ArrayLists，通过它获得星球ID，得到该星球中发表的帖子
    private static ArrayList<HashMap<String,String>> GroupListArraylists=null;

    //MyApplication.getGroupInfo().get(position).get("id");得到星球ID
    public static ArrayList<HashMap<String,String>> getGroupInfo(){
        return GroupListArraylists;
    }
    public static void setGroupListInfo(ArrayList<HashMap<String,String>> info){
        GroupListArraylists=info;
    }



    //全部星球-->星球帖子列表-->帖子的信息ArrayLists，通过它获得帖子的ID，进而开始帖子详情。
    private static ArrayList<HashMap<String,String>> GroupPostsArraylists=null;

    //MyApplication.getGroupPostsInfo().get(position).get("postsID");
    //MyApplication.getGroupPostsInfo().get(position).get("groupID");
    public static ArrayList<HashMap<String,String>> getGroupPostsInfo(){
        return GroupPostsArraylists;
    }
    public static void setGroupPostsInfo(ArrayList<HashMap<String,String>> info){
        GroupPostsArraylists=info;
    }

    //
    //
    //
    //
    //
    //
    //
    //
    //
    //Home帖子的信息ArrayLists，通过它获得帖子的ID，进而开始帖子详情。
    private static ArrayList<HashMap<String,String>> HomePostsArraylists=null;

    //MyApplication.getHomePostsInfo().get(position).get("postID");
    //MyApplication.getHomePostsInfo().get(position).get("groupID");
    public static ArrayList<HashMap<String,String>> getHomePostsInfo(){
        return HomePostsArraylists;
    }
    public static void setHomePostsInfo(ArrayList<HashMap<String,String>> info){
        HomePostsArraylists=info;
    }






    //MyGroups帖子的信息ArrayLists，通过它获得帖子的ID，进而开始帖子详情。
    private static ArrayList<HashMap<String,String>> MyGroupPostsArraylists=null;

    //MyApplication.getMyGroupPostsInfo().get(position).get("postID");
    //MyApplication.getMyGroupPostsInfo().get(position).get("groupID");
    public static ArrayList<HashMap<String,String>> getMyGroupPostsInfo(){
        return MyGroupPostsArraylists;
    }
    public static void setMyGroupPostsInfo(ArrayList<HashMap<String,String>> info){
        MyGroupPostsArraylists=info;
    }

}
