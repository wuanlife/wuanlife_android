package com.wuanla.www.wuanlife_120.http.Group;

/**
 * Created by Administrator on 2017/3/29.
 * 创建星球
 */

public class GroupCreate {

    /**
     * ret : 200
     * data : {"code":1,"info":{"g_name":"12chhac","group_id":"410","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":null,"user_id":"1","authorization":"01"}}
     * msg : 创建成功！
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * code : 1
         * info : {"g_name":"12chhac","group_id":"410","g_image":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","g_introduction":null,"user_id":"1","authorization":"01"}
         */

        public int code;
        public InfoBean info;

        public static class InfoBean {
            /**
             * g_name : 12chhac
             * group_id : 410
             * g_image : http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100
             * g_introduction : null
             * user_id : 1
             * authorization : 01
             */

            public String g_name;
            public String group_id;
            public String g_image;
            public Object g_introduction;
            public String user_id;
            public String authorization;
        }
    }
}
