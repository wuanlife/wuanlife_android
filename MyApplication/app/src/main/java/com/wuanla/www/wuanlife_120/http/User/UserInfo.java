package com.wuanla.www.wuanlife_120.http.User;

/**
 * Created by Administrator on 2017/3/30.
 */

public class UserInfo {

    /**
     * ret : 200
     * data : {"userID":"1","sex":"0","year":"","month":"","day":"","mail_checked":"1","profile_picture":"http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100","email":"taotao@taotao.com","nickname":"我是一只鸟"}
     * msg : null
     */

    public int ret;
    public DataBean data;
    public Object   msg;

    public static class DataBean {
        /**
         * userID : 1
         * sex : 0
         * year :
         * month :
         * day :
         * mail_checked : 1
         * profile_picture : http://7xlx4u.com1.z0.glb.clouddn.com/o_1aqt96pink2kvkhj13111r15tr7.jpg?imageView2/1/w/100/h/100
         * email : taotao@taotao.com
         * nickname : 我是一只鸟
         */

        public String userID;
        public String sex;
        public String year;
        public String month;
        public String day;
        public String mail_checked;
        public String profile_picture;
        public String email;
        public String nickname;
    }
}
