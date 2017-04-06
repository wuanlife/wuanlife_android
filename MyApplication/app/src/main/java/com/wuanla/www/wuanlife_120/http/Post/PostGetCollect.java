package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/4/1.
 */

public class PostGetCollect {

    /**
     * ret : 200
     * data : {"pageCount":1,"currentPage":1,"posts":[{"post_id":"1","create_time":"2017-03-21 13:46:57","post_name":"通过接口编辑","gb_id":"2","group_name":"午安网","user_name":"xiaochao_php","delete":"1"},{"post_id":"11","create_time":"2017-03-18 07:13:35","post_name":"1","gb_id":"166","group_name":"叶氏春秋","user_name":"xjkui","delete":"0"}]}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * pageCount : 1
         * currentPage : 1
         * posts : [{"post_id":"1","create_time":"2017-03-21 13:46:57","post_name":"通过接口编辑","gb_id":"2","group_name":"午安网","user_name":"xiaochao_php","delete":"1"},{"post_id":"11","create_time":"2017-03-18 07:13:35","post_name":"1","gb_id":"166","group_name":"叶氏春秋","user_name":"xjkui","delete":"0"}]
         */

        public int pageCount;
        public int             currentPage;
        public List<PostsBean> posts;

        public static class PostsBean {
            /**
             * post_id : 1
             * create_time : 2017-03-21 13:46:57
             * post_name : 通过接口编辑
             * gb_id : 2
             * group_name : 午安网
             * user_name : xiaochao_php
             * delete : 1
             */

            public String post_id;
            public String create_time;
            public String post_name;
            public String gb_id;
            public String group_name;
            public String user_name;
            public String delete;
        }
    }
}
