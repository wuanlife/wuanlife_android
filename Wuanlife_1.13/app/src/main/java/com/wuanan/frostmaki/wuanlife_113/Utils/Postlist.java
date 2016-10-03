package com.wuanan.frostmaki.wuanlife_113.Utils;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class Postlist {
    private int postID;
    private String title;
    private String text;
    private int lock;
    private String createTime;
    private String nickname;
    private int groupID;
    private String groupName;
    private ArrayList<String> image;

    private int pageCount;
    private int currentPage;

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

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
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

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

}
