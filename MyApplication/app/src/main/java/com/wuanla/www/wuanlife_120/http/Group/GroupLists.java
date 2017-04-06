package com.wuanla.www.wuanlife_120.http.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 * 星球列表-按成员数降序显示星球列表
 */

public class GroupLists {

    /**
     * ret : 200
     * data : {"groups":[{"g_name":"星球2号","group_id":"2","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"","num":"23"},{"g_name":"测试29","group_id":"29","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":"测试29","num":"15"},{"g_name":"装备2014中队","group_id":"1","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"1$g_image=1","num":"12"}],"page_count":19,"current_page":1}
     * msg : 获取星球列表成功
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * groups : [{"g_name":"星球2号","group_id":"2","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"","num":"23"},{"g_name":"测试29","group_id":"29","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":"测试29","num":"15"},{"g_name":"装备2014中队","group_id":"1","g_image":"http://image.suxiazai.com/img/pic/960/359/11680961633.jpg","g_introduction":"1$g_image=1","num":"12"}]
         * page_count : 19
         * current_page : 1
         */

        public int page_count;
        public int              current_page;
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
