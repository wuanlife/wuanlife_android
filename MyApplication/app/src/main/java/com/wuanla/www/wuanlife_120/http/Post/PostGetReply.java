package com.wuanla.www.wuanlife_120.http.Post;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostGetReply {

    /**
     * ret : 200
     * data : {"post_id":"1","reply_count":4,"page_count":1,"current_page":1,"reply":[{"reply_floor":"0","p_text":"2333","user_id":"58","user_name":"xiaochao_php","reply_id":null,"reply_nick_name":null,"create_time":"2017-02-14 10:12:01","p_floor":"2","approved":"0","approvednum":"1","delete_right":0},{"reply_floor":"0","p_text":"哈哈哈","user_id":"3","user_name":"用户一号","reply_id":null,"reply_nick_name":null,"create_time":"2017-03-01 20:19:10","p_floor":"3","approved":"0","approvednum":"0","delete_right":0}]}
     * msg : 帖子回复显示成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * post_id : 1
         * reply_count : 4
         * page_count : 1
         * current_page : 1
         * reply : [{"reply_floor":"0","p_text":"2333","user_id":"58","user_name":"xiaochao_php","reply_id":null,"reply_nick_name":null,"create_time":"2017-02-14 10:12:01","p_floor":"2","approved":"0","approvednum":"1","delete_right":0},{"reply_floor":"0","p_text":"哈哈哈","user_id":"3","user_name":"用户一号","reply_id":null,"reply_nick_name":null,"create_time":"2017-03-01 20:19:10","p_floor":"3","approved":"0","approvednum":"0","delete_right":0}]
         */

        public String post_id;
        public int             reply_count;
        public int             page_count;
        public int             current_page;
        public List<ReplyBean> reply;

        public static class ReplyBean {
            /**
             * reply_floor : 0
             * p_text : 2333
             * user_id : 58
             * user_name : xiaochao_php
             * reply_id : null
             * reply_nick_name : null
             * create_time : 2017-02-14 10:12:01
             * p_floor : 2
             * approved : 0
             * approvednum : 1
             * delete_right : 0
             */

            public String reply_floor;
            public String p_text;
            public String user_id;
            public String user_name;
            public Object reply_id;
            public Object reply_nick_name;
            public String create_time;
            public String p_floor;
            public String approved;
            public String approvednum;
            public int    delete_right;
        }
    }
}
