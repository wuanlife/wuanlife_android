package com.wuanla.www.wuanlife_120.http.Post;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetGroup {

    /**
     * ret : 200
     * data : {"creator_id":"46","creator_name":"叶寻","group_id":"166","g_name":"叶氏春秋","private":"0","identity":"03","posts":[{"digest":"0","post_id":"16","title":"1","p_text":"1","create_time":"2017","id":"127","user_name":"xjkui","sticky":"0","lock":"0","image":[]},{"digest":"0","post_id":"24","title":"1","p_text":"1","create_time":"2017","id":"127","user_name":"xjkui","sticky":"0","lock":"0","image":[]}],"group_name":"叶氏春秋","pageCount":1,"currentPage":1}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * creator_id : 46
         * creator_name : 叶寻
         * group_id : 166
         * g_name : 叶氏春秋
         * private : 0
         * identity : 03
         * posts : [{"digest":"0","post_id":"16","title":"1","p_text":"1","create_time":"2017","id":"127","user_name":"xjkui","sticky":"0","lock":"0","image":[]},{"digest":"0","post_id":"24","title":"1","p_text":"1","create_time":"2017","id":"127","user_name":"xjkui","sticky":"0","lock":"0","image":[]}]
         * group_name : 叶氏春秋
         * pageCount : 1
         * currentPage : 1
         */

        public String creator_id;
        public String          creator_name;
        public String          group_id;
        public String          g_name;
        @SerializedName("private")
        public String          privateX;
        public String          identity;
        public String          group_name;
        public int             pageCount;
        public int             currentPage;
        public List<PostsBean> posts;

        public static class PostsBean {
            /**
             * digest : 0
             * post_id : 16
             * title : 1
             * p_text : 1
             * create_time : 2017
             * id : 127
             * user_name : xjkui
             * sticky : 0
             * lock : 0
             * image : []
             */

            public String digest;
            public String  post_id;
            public String  title;
            public String  p_text;
            public String  create_time;
            public String  id;
            public String  user_name;
            public String  sticky;
            public String  lock;
            public List<?> image;
        }
    }
}
