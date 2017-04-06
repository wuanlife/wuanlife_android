package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetIndex {

    /**
     * ret : 200
     * data : {"pageCount":6,"currentPage":6,"posts":[{"post_id":"3","p_title":"sdfasd","p_text":"fasdfsd","lock":"0","create_time":"2017","user_name":"123123","group_id":"355","groupName":"一二三四五六七八九十一二三四五六七八九十","approved":"0","approvednum":"0","image":[]},{"post_id":"11","p_title":"1","p_text":"1","lock":"0","create_time":"2017","user_name":"xjkui","group_id":"166","groupName":"叶氏春秋","approved":"0","approvednum":"0","image":[]}]}
     * msg :
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * pageCount : 6
         * currentPage : 6
         * posts : [{"post_id":"3","p_title":"sdfasd","p_text":"fasdfsd","lock":"0","create_time":"2017","user_name":"123123","group_id":"355","groupName":"一二三四五六七八九十一二三四五六七八九十","approved":"0","approvednum":"0","image":[]},{"post_id":"11","p_title":"1","p_text":"1","lock":"0","create_time":"2017","user_name":"xjkui","group_id":"166","groupName":"叶氏春秋","approved":"0","approvednum":"0","image":[]}]
         */

        public int pageCount;
        public int             currentPage;
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
             * groupName : 一二三四五六七八九十一二三四五六七八九十
             * approved : 0
             * approvednum : 0
             * image : []
             */

            public String post_id;
            public String  p_title;
            public String  p_text;
            public String  lock;
            public String  create_time;
            public String  user_name;
            public String  group_id;
            public String  groupName;
            public String  approved;
            public String  approvednum;
            public List<?> image;
        }
    }
}
