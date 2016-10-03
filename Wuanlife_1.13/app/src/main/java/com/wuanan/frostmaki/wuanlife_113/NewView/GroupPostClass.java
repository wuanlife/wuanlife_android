package com.wuanan.frostmaki.wuanlife_113.NewView;

import java.util.ArrayList;

/**
 * Created by Frostmaki on 2016/10/3.
 */
public class GroupPostClass {
    private int privateNum;
    private int identity;

    private String digest;
    private String sticky;


    private int groupID;
    private String groupName;
    private int creatorID;
    private String creatorName;

    private int id;
    private int postID;
    private String title;
    private String text;
    private int lock;
    private String createTime;
    private String nickname;
    private ArrayList<String> image;
    private int pageCount;
    private int currentPage;



    public void setPostID(int postID) {
        this.postID = postID;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }


    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreatorID(int creatorID) {
        this.creatorID = creatorID;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public void setImage(ArrayList<String> image) {
        this.image = image;
    }

    public void setLock(int lock) {
        this.lock = lock;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public void setIdentity(int identity) {
        this.identity = identity;
    }

    public void setPrivateNum(int privateNum) {
        this.privateNum = privateNum;
    }

    public void setSticky(String sticky) {
        this.sticky = sticky;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getNickname() {
        return nickname;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getImage() {
        return image;
    }

    public String getSticky() {
        return sticky;
    }

    public String getDigest() {
        return digest;
    }

    public int getPrivateNum() {
        return privateNum;
    }

    public int getIdentity() {
        return identity;
    }

    public int getGroupID() {
        return groupID;
    }

    public int getId() {
        return id;
    }



    public int getLock() {
        return lock;
    }



    public int getPostID() {
        return postID;
    }



    public String getCreateTime() {
        return createTime;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public int getCreatorID() {
        return creatorID;
    }
}
