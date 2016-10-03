package com.wuanan.frostmaki.wuanlife_113.AllGroup.GroupListPosts;

import android.util.Log;

import com.wuanan.frostmaki.wuanlife_113.NewView.GroupPostClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class GroupJson {
    public static ArrayList<GroupPostClass> getJSONParse(String s) {
        ArrayList<GroupPostClass> arraylist = null;
        int creatorID;
        String creatorName;
        int groupID;
        String groupName;
        int privateNum;
        int identity;

        String digest;
        String sticky;
        int id;
        int postID;
        String title;
        String text;
        int lock;
        String createTime;
        String nickname;
        ArrayList<String> imagelist;
        int pageCount = 0;
        int currentPage = 0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");

            creatorID = data.getInt("creatorID");
            creatorName = data.getString("creatorName");
            groupID = data.getInt("groupID");
            groupName = data.getString("groupName");
            privateNum = data.getInt("private");
            identity = data.getInt("identity");

            pageCount = data.getInt("pageCount");
            currentPage = data.getInt("currentPage");

            JSONArray posts = data.getJSONArray("posts");
            arraylist = new ArrayList<GroupPostClass>();

            if (posts.length() > 0) {

                for (int i = 0; i < posts.length(); ++i) {
                    GroupPostClass groupPostClass = new GroupPostClass();

                    JSONObject posts_details = posts.getJSONObject(i);

                    digest = posts_details.getString("digest");
                    postID = posts_details.getInt("postID");
                    title = posts_details.getString("title");
                    text = posts_details.getString("text");
                    createTime = posts_details.getString("createTime");
                    id = posts_details.getInt("id");
                    nickname = posts_details.getString("nickname");
                    sticky = posts_details.getString("sticky");
                    lock=posts_details.getInt("lock");



                    JSONArray image = posts_details.getJSONArray("image");

                    imagelist=null;
                    if (image.length() > 0) {
                        imagelist = new ArrayList<String>();
                        for (int j = 0; j<image.length(); j++) {

                            imagelist.add(image.getString(j));
                        }
                    }

                    groupPostClass.setTitle(title);
                    groupPostClass.setNickname(nickname);
                    groupPostClass.setGroupName(groupName);
                    groupPostClass.setGroupID(groupID);
                    groupPostClass.setCreateTime(createTime);
                    groupPostClass.setText(text);
                    groupPostClass.setImage(imagelist);
                    groupPostClass.setPostID(postID);
                    groupPostClass.setDigest(digest);
                    groupPostClass.setSticky(sticky);
                    groupPostClass.setId(id);
                    groupPostClass.setCurrentPage(currentPage);
                    groupPostClass.setPageCount(pageCount);
                    groupPostClass.setLock(lock);
                    groupPostClass.setCreatorID(creatorID);
                    groupPostClass.setCreatorName(creatorName);
                    groupPostClass.setPrivateNum(privateNum);
                    groupPostClass.setIdentity(identity);


                    arraylist.add(groupPostClass);

                }
                //MyApplication.setGroupPostsInfo(arraylist);
                return arraylist;

            } else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("GroupListPost异常 ", e + "");
        }
        return null;
    }
}
