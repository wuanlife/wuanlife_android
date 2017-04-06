package com.wuanla.www.wuanlife_120.http.Group;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupQuit {


    /**
     * ret : 200
     * data : {"code":1}
     * msg : 退出成功！并通知星球创建者
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         */

        public int code;
    }
}
