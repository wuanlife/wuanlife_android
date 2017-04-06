package com.wuanla.www.wuanlife_120.http.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupSearch {

    /**
     * ret : 200
     * data : {"group":[{"g_name":"装备2014中队","group_id":"1","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":"1$g_image=1","num":"11"},{"g_name":"星球11号","group_id":"11","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":null,"num":"8"}],"group_page":39,"g_current_page":1,"posts":[{"post_id":"16","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:34:57","user_name":"xjkui","group_id":"166","g_name":"叶氏春秋"},{"post_id":"9","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:34:20","user_name":"xjkui","group_id":"166","g_name":"叶氏春秋"}],"posts_page":10,"p_current_page":1}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * group : [{"g_name":"装备2014中队","group_id":"1","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":"1$g_image=1","num":"11"},{"g_name":"星球11号","group_id":"11","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":null,"num":"8"}]
         * group_page : 39
         * g_current_page : 1
         * posts : [{"post_id":"16","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:34:57","user_name":"xjkui","group_id":"166","g_name":"叶氏春秋"},{"post_id":"9","p_title":"1","p_text":"1","lock":"0","create_time":"2017-03-03 13:34:20","user_name":"xjkui","group_id":"166","g_name":"叶氏春秋"}]
         * posts_page : 10
         * p_current_page : 1
         */

        public int group_page;
        public int             g_current_page;
        public int             posts_page;
        public int             p_current_page;
        public List<GroupBean> group;
        public List<PostsBean> posts;

        public static class GroupBean {
            /**
             * g_name : 装备2014中队
             * group_id : 1
             * g_image : http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100
             * g_introduction : 1$g_image=1
             * num : 11
             */

            public String g_name;
            public String group_id;
            public String g_image;
            public String g_introduction;
            public String num;
        }

        public static class PostsBean {
            /**
             * post_id : 16
             * p_title : 1
             * p_text : 1
             * lock : 0
             * create_time : 2017-03-03 13:34:57
             * user_name : xjkui
             * group_id : 166
             * g_name : 叶氏春秋
             */

            public String post_id;
            public String p_title;
            public String p_text;
            public String lock;
            public String create_time;
            public String user_name;
            public String group_id;
            public String g_name;
        }
    }
}
