package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetMyGroup {

    /**
     * ret : 200
     * data : {"pageCount":1,"currentPage":1,"posts":[{"post_id":"3","p_title":"sdfasd","p_text":"fasdfsd","lock":"0","create_time":"2017","user_name":"123123","group_id":"355","g_name":"一二三四五六七八九十一二三四五六七八九十","image":[]},{"post_id":"6","p_title":"cs","p_text":"cs","lock":"0","create_time":"2017","user_name":"xjkui","group_id":"29","g_name":"测试29","image":[]}],"user_name":"123123"}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * pageCount : 1
         * currentPage : 1
         * posts : [{"post_id":"3","p_title":"sdfasd","p_text":"fasdfsd","lock":"0","create_time":"2017","user_name":"123123","group_id":"355","g_name":"一二三四五六七八九十一二三四五六七八九十","image":[]},{"post_id":"6","p_title":"cs","p_text":"cs","lock":"0","create_time":"2017","user_name":"xjkui","group_id":"29","g_name":"测试29","image":[]}]
         * user_name : 123123
         */

        public int pageCount;
        public int             currentPage;
        public String          user_name;
        public List<PostsBean> posts;

        public static class PostsBean {
            /**
             * post_id : 3
             * p_title : sdfasd
             * p_text : fasdfsd
             * lock : 0
             * create_time : 2017
             * user_name : 123123
             * group_id : 355
             * g_name : 一二三四五六七八九十一二三四五六七八九十
             * image : []
             */

            public String post_id;
            public String  p_title;
            public String  p_text;
            public String  lock;
            public String  create_time;
            public String  user_name;
            public String  group_id;
            public String  g_name;
            public List<?> image;
        }
    }
}
