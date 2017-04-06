package com.wuanla.www.wuanlife_120.http.Post;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostReply {

    /**
     * ret : 200
     * data : {"code":1,"reply_page":1,"post_id":"45","user_id":"1","reply_id":null,"p_floor":3,"p_text":"6656xsxs","create_time":"2017-03-10 21:27:46","user_name":"我是一只鸟","reply_user_name":null,"page":1}
     * msg : 回复成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * reply_page : 1
         * post_id : 45
         * user_id : 1
         * reply_id : null
         * p_floor : 3
         * p_text : 6656xsxs
         * create_time : 2017-03-10 21:27:46
         * user_name : 我是一只鸟
         * reply_user_name : null
         * page : 1
         */

        public int code;
        public int    reply_page;
        public String post_id;
        public String user_id;
        public Object reply_id;
        public int    p_floor;
        public String p_text;
        public String create_time;
        public String user_name;
        public Object reply_user_name;
        public int    page;
    }
}
