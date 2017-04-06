package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/31.
 */

public class UserShowCode {

    /**
     * ret : 200
     * data : {"i_code":"uesvmw","num":"99"}
     * msg : 查询成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * i_code : uesvmw
         * num : 99
         */

        public String i_code;
        public String num;
    }
}
