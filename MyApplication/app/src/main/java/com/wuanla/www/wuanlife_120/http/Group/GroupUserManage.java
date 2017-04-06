package com.wuanla.www.wuanlife_120.http.Group;

import java.util.List;

/**
 * Created by Administrator on 2017/3/29.
 */

public class GroupUserManage {

    /**
     * ret : 200
     * data : {"group_id":"1","users":[{"user_id":"3","user_name":"用户一号","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1b2q9g8uv1o1a125n7qs1btq1glk7.jpg?imageView2/1/w/100/h/100"},{"user_id":"37","user_name":"人人献出一片爱","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"46","user_name":"叶寻","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"51","user_name":"777","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"56","user_name":"qwer","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"71","user_name":"xinbaobao214","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"78","user_name":"asdfasdf","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"86","user_name":"汪汪3","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"118","user_name":"qq.com","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"131","user_name":"??","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"}],"code":1}
     * msg : 显示成功！
     */

    public int ret;
    public DataBean data;
    public String   msg;

    public static class DataBean {
        /**
         * group_id : 1
         * users : [{"user_id":"3","user_name":"用户一号","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1b2q9g8uv1o1a125n7qs1btq1glk7.jpg?imageView2/1/w/100/h/100"},{"user_id":"37","user_name":"人人献出一片爱","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"46","user_name":"叶寻","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"51","user_name":"777","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"56","user_name":"qwer","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"71","user_name":"xinbaobao214","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"78","user_name":"asdfasdf","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"86","user_name":"汪汪3","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"118","user_name":"qq.com","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"},{"user_id":"131","user_name":"??","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100"}]
         * code : 1
         */

        public String group_id;
        public int             code;
        public List<UsersBean> users;

        public static class UsersBean {
            /**
             * user_id : 3
             * user_name : 用户一号
             * profile_picture : http://7xlx4u.com1.z0.glb.clouddn.com/o_1b2q9g8uv1o1a125n7qs1btq1glk7.jpg?imageView2/1/w/100/h/100
             */

            public String user_id;
            public String user_name;
            public String profile_picture;
        }
    }
}
