package com.wuanan.frostmaki.wuanlife_113.MyGroup;

/**
 * Created by Frostmaki on 2016/10/4.
 */
public class JoinCreateGroup {
    String name;
    int id;//xing星球ID
    String g_image;
    String g_introduction;

    int num;//星球数量
    int pageCount;
    int currentPage;
    String user_name;

    public void setId(int id) {
        this.id = id;
    }

    public void setG_image(String g_image) {
        this.g_image = g_image;
    }

    public void setG_introduction(String g_introduction) {
        this.g_introduction = g_introduction;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


    //////


    public int getId() {
        return id;
    }

    public String getG_image() {
        return g_image;
    }

    public String getG_introduction() {
        return g_introduction;
    }

    public String getName() {
        return name;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public int getNum() {
        return num;
    }

    public int getPageCount() {
        return pageCount;
    }

    public String getUser_name() {
        return user_name;
    }
}
