package com.wuanan.frostmaki.wuanlife_113.NewView;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class PostDetailsClass {
    private int postID;
    private String title;
    private String text;
    private int lock;
    private String createTime;
    private String nickname;
    private int groupID;
    private String groupName;
    private ArrayList<String> image;
    private int id;
    private int stickyRight;
    private int editRight;
    private int deleteRight;
    private int lockRight;


    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDeleteRight(int deleteRight) {
        this.deleteRight = deleteRight;
    }
    public void setEditRight(int editRight) {
        this.editRight = editRight;
    }

    public void setLockRight(int lockRight) {
        this.lockRight = lockRight;
    }

    public void setStickyRight(int stickyRight) {
        this.stickyRight = stickyRight;
    }

    //////////

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getGroupID() {
        return groupID;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getLock() {
        return lock;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPostID() {
        return postID;
    }


    public int getDeleteRight() {
        return deleteRight;
    }

    public int getEditRight() {
        return editRight;
    }

    public int getLockRight() {
        return lockRight;
    }

    public int getStickyRight() {
        return stickyRight;
    }

    public int getId() {
        return id;
    }
}
