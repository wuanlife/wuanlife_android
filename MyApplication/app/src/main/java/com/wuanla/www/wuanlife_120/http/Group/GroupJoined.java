package com.wuanla.www.wuanlife_120.http.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupJoined {

    /**
     * ret : 200
     * data : {"groups":[{"g_name":"星球2号","group_id":"2","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"","num":"23"},{"g_name":"626","group_id":"387","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1b3ce8n241cbn16sktvfbah1h117.jpg?imageView2/1/w/100/h/100","g_introduction":"","num":"3"}],"page_count":1,"current_page":1,"num":2,"user_name":"xiaochao_php"}
     * msg : 获取星球列表成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * groups : [{"g_name":"星球2号","group_id":"2","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"","num":"23"},{"g_name":"626","group_id":"387","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1b3ce8n241cbn16sktvfbah1h117.jpg?imageView2/1/w/100/h/100","g_introduction":"","num":"3"}]
         * page_count : 1
         * current_page : 1
         * num : 2
         * user_name : xiaochao_php
         */

        public int page_count;
        public int              current_page;
        public int              num;
        public String           user_name;
        public List<GroupsBean> groups;

        public static class GroupsBean {
            /**
             * g_name : 星球2号
             * group_id : 2
             * g_image : http://image.suxiazai.com/img/pic/960/359/11680961633.jpg
             * g_introduction :
             * num : 23
             */

            public String g_name;
            public String group_id;
            public String g_image;
            public String g_introduction;
            public String num;
        }
    }
}
