package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserCheckNewInfo {
    /**
     * ret : 200
     * data : {"num":1}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * num : 1
         */

        public int num;
    }
}
