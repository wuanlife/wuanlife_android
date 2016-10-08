package com.wuanan.frostmaki.wuanlife_113.MyGroup;

import android.util.Log;

import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class MyGroupJson {
    public static ArrayList<Postlist> getJSONParse(String s) {

        ArrayList<Postlist> arraylist=null;

        String title = null;
        String nickname = null;
        String groupName = null;
        String createTime = null;
        String text =null;
        int lock=0;
        int postID=0;//贴子ID
        int groupID=0;//星球ID
        int pageCount=0;
        int currentPage=0;
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");

            pageCount = data.getInt("pageCount");
            currentPage = data.getInt("currentPage");

            JSONArray posts = data.getJSONArray("posts");
            if (posts.length()>0) {
                arraylist = new ArrayList<Postlist>();

                for (int i = 0; i < posts.length(); ++i) {

                    JSONObject posts_details = posts.getJSONObject(i);
                    title = posts_details.getString("title");
                    nickname = posts_details.getString("nickname");
                    groupName = posts_details.getString("groupName");
                    createTime = posts_details.getString("createTime");
                    text = posts_details.getString("text");
                    lock = posts_details.getInt("lock");
                    postID = posts_details.getInt("postID");
                    groupID = posts_details.getInt("groupID");

                    JSONArray image = posts_details.getJSONArray("image");

                    ArrayList<String> imagelist=null;
                    if (image.length() > 0) {
                        imagelist = new ArrayList<String>();
                        for (int j = 0; j<image.length(); j++) {

                            imagelist.add(image.getString(j));
                        }
                    }

                    Postlist newpostlist=new Postlist();
                    newpostlist.setTitle(title);
                    newpostlist.setNickname(nickname);
                    newpostlist.setGroupName(groupName);
                    newpostlist.setCreateTime(createTime);
                    newpostlist.setText(text);

                    newpostlist.setCurrentPage(currentPage);
                    newpostlist.setPageCount(pageCount);
                    newpostlist.setLock(lock);
                    newpostlist.setGroupID(groupID);
                    newpostlist.setPostID(postID);
                    newpostlist.setImage(imagelist);

                    arraylist.add(newpostlist);
                }

                return arraylist;
            }else {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("myGroupJson: ", e + "");
        }
        return null;
    }
}
