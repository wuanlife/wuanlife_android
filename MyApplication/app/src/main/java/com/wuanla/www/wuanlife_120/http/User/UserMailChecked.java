package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserMailChecked {

    /**
     * ret : 200
     * data : {"user_id":"1","mail_check":"1"}
     * msg :
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * user_id : 1
         * mail_check : 1
         */

        public String user_id;
        public String mail_check;
    }
}
