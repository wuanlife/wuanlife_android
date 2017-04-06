package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserLogin {

    /**
     * ret : 200
     * data : {"code":"1","info":{"user_id":"189","user_name":"c1ha11c11","user_email":"ch1111ac"}}
     * msg : 登录成功！
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * info : {"user_id":"189","user_name":"c1ha11c11","user_email":"ch1111ac"}
         */

        public String code;
        public InfoBean info;

        public static class InfoBean {
            /**
             * user_id : 189
             * user_name : c1ha11c11
             * user_email : ch1111ac
             */

            public String user_id;
            public String user_name;
            public String user_email;
        }
    }
}
