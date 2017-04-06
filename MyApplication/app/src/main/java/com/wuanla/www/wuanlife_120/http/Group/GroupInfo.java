package com.wuanla.www.wuanlife_120.http.Group;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupInfo {

    /**
     * ret : 200
     * data : {"group_id":"1","g_name":"装备2014中队","g_introduction":null,"g_image":null,"creator":1}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * group_id : 1
         * g_name : 装备2014中队
         * g_introduction : null
         * g_image : null
         * creator : 1
         */

        public String group_id;
        public String g_name;
        public Object g_introduction;
        public Object g_image;
        public int    creator;
    }
}
