package com.wuanan.frostmaki.wuanlife_113.Posts;

import android.util.Log;

import com.wuanan.frostmaki.wuanlife_113.NewView.PostDetailsClass;
import com.wuanan.frostmaki.wuanlife_113.Utils.Postlist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class GetBaseJson {
    public static PostDetailsClass getJSONParse(String s) {

        PostDetailsClass newpostdatails = null;
        int groupID;
        String groupName;

        int stickyRight;
        int editRight;
        int deleteRight;
        int lockRight;
        int id;
        int postID;
        String title;
        String text;
        int lock;
        String createTime;
        String nickname;
        ArrayList<String> imagelist;

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONObject data = jsonObject.getJSONObject("data");


            newpostdatails = new PostDetailsClass();


            title = data.getString("title");
            nickname = data.getString("nickname");
            groupName = data.getString("groupName");
            createTime = data.getString("createTime");
            text = data.getString("text");
            lock = data.getInt("lock");
            postID = data.getInt("postID");
            groupID = data.getInt("groupID");
            id=data.getInt("id");
            stickyRight = data.getInt("stickyRight");
            editRight = data.getInt("editRight");
            lockRight = data.getInt("lockRight");
            deleteRight = data.getInt("deleteRight");

            String image=data.getString("p_image");
            imagelist = null;



            newpostdatails.setTitle(title);
            newpostdatails.setNickname(nickname);
            newpostdatails.setGroupName(groupName);
            newpostdatails.setCreateTime(createTime);
            newpostdatails.setText(text);
            newpostdatails.setLock(lock);
            newpostdatails.setGroupID(groupID);
            newpostdatails.setPostID(postID);
            newpostdatails.setImage(imagelist);
            newpostdatails.setId(id);
            newpostdatails.setStickyRight(stickyRight);
            newpostdatails.setEditRight(editRight);
            newpostdatails.setDeleteRight(deleteRight);
            newpostdatails.setLockRight(lockRight);


            return newpostdatails;


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onPostExecute: ", e + "");
        }
        return null;
    }
}
