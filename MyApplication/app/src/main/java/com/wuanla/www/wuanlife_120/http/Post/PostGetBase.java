package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetBase {

    /**
     * ret : 200
     * data : {"post_id":"1","group_id":"2","g_name":"星球2号","p_title":"通过接口编辑","p_text":"成功","user_id":"58","user_name":"xiaochao_php","create_time":"2017-02-12 20:26:06","sticky":0,"lock":1,"approved":"0","approvednum":"3","p_image":[[]],"collect":0,"edit_right":0,"delete_right":1,"sticky_right":1,"lock_right":1,"code":1}
     * msg : 查看帖子成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * post_id : 1
         * group_id : 2
         * g_name : 星球2号
         * p_title : 通过接口编辑
         * p_text : 成功
         * user_id : 58
         * user_name : xiaochao_php
         * create_time : 2017-02-12 20:26:06
         * sticky : 0
         * lock : 1
         * approved : 0
         * approvednum : 3
         * p_image : [[]]
         * collect : 0
         * edit_right : 0
         * delete_right : 1
         * sticky_right : 1
         * lock_right : 1
         * code : 1
         */

        public String post_id;
        public String        group_id;
        public String        g_name;
        public String        p_title;
        public String        p_text;
        public String        user_id;
        public String        user_name;
        public String        create_time;
        public int           sticky;
        public int           lock;
        public String        approved;
        public String        approvednum;
        public int           collect;
        public int           edit_right;
        public int           delete_right;
        public int           sticky_right;
        public int           lock_right;
        public int           code;
        public List<List<?>> p_image;
    }
}
