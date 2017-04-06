package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserCheckStatus {

    /**
     * ret : 200
     * data : {"code":1,"info":{"user_id":"85","user_name":"lwy_test"}}
     * msg : 用户已登陆
     */

    public int      ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * info : {"user_id":"85","user_name":"lwy_test"}
         */

        public int      code;
        public InfoBean info;

        public static class InfoBean {
            /**
             * user_id : 85
             * user_name : lwy_test
             */

            public String user_id;
            public String user_name;
        }
    }
}
