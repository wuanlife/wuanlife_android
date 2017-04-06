package com.wuanla.www.wuanlife_120.http.Post;

/**
 * Created by Administrator on 2017/3/31.
 */

public class PostStatus {

    /**
     * ret : 200
     * data : {"code":1,"post_id":45}
     * msg : 发表成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * post_id : 45
         */

        public int code;
        public int post_id;
    }
}
