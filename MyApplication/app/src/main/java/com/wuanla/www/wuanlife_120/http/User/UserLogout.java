package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserLogout {

    /**
     * ret : 200
     * data : {"code":"1"}
     * msg : 注销成功！
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         */

        public String code;
    }
}
