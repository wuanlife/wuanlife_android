package com.wuanla.www.wuanlife_120.http.Group;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupStatus {

    /**
     * ret : 200
     * data : {"code":1}
     * msg : 已加入该星球
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
