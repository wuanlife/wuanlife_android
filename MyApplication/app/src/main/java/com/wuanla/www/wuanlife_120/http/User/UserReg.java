package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserReg {

    /**
     * ret : 200
     * data : {"code":1,"info":{"user_id":"192","user_name":"dfgsdsd","user_email":"1112"}}
     * msg : 注册成功，并自动登录！
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * info : {"user_id":"192","user_name":"dfgsdsd","user_email":"1112"}
         */

        public int code;
        public InfoBean info;

        public static class InfoBean {
            /**
             * user_id : 192
             * user_name : dfgsdsd
             * user_email : 1112
             */

            public String user_id;
            public String user_name;
            public String user_email;
        }
    }
}
