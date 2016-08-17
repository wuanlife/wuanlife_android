package com.wuanan.frostmaki.wuanlife_app;

import android.app.Application;

import com.qiniu.util.Auth;

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


    private static String uptoken ="drhxTyPuxNKJJ4SuDUhxGb-Osh_q52icfG8xak06:Xdp34ylO7AN1KbJvaUqfcYcMQRk=:eyJzY29wZSI6ImV4YW1wbGUwMyIsImRlYWRsaW5lIjoxNDcwNjg0MDk5fQ==";

    //设置好账号的ACCESS_KEY和SECRET_KEY
    static String ACCESS_KEY = "fOCmqJDZvBUZCL9lGSbN1sl1_cVNuV7f7ns0bcfs";
    static String SECRET_KEY = "GWhI_igD2NcqaomXi0fv8R_j8fnGVvH6tJPLwLFk";
    //要上传的空间
    static String bucketname = "wuanlife";
    //域名
    static String yuMing="7xlx4u.com1.z0.glb.clouddn.com";
    public static  String getUptoken(){
        Auth auth=Auth.create(ACCESS_KEY,SECRET_KEY);
        return auth.uploadToken(bucketname);
    }
    public static String getAccessKey(){
        return ACCESS_KEY;
    }
    public static String getSecretKey(){
        return SECRET_KEY;
    }
    public static String getBucketname(){
        return bucketname;
    }
    public static String getYuMing(){
        return yuMing;
    }

}
