package com.wuanla.www.wuanlife_120.http;


import com.wuanla.www.wuanlife_120.http.User.UserAlterInfo;
import com.wuanla.www.wuanlife_120.http.User.UserCheckNewInfo;
import com.wuanla.www.wuanlife_120.http.User.UserCheckStatus;
import com.wuanla.www.wuanlife_120.http.User.UserInfo;
import com.wuanla.www.wuanlife_120.http.User.UserLogin;
import com.wuanla.www.wuanlife_120.http.User.UserLogout;
import com.wuanla.www.wuanlife_120.http.User.UserMailChecked;
import com.wuanla.www.wuanlife_120.http.User.UserMessage;
import com.wuanla.www.wuanlife_120.http.User.UserReg;
import com.wuanla.www.wuanlife_120.http.User.UserShowCode;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/30.
 */

public interface UserApi {

    /**
     * 注册接口-用于验证并注册用户
     *
     * @param user_name  字符串 	必须 	- 	用户昵称
     * @param user_email 字符串 	必须 	- 	用户邮箱
     * @param password   字符串 	必须 	最小：6 	用户密码
     * @param i_code     字符串 	必须 	- 	邀请码
     */
    @FormUrlEncoded
    @POST("reg")
    Observable<UserReg> reg(@Field("user_name") String user_name, @Field("user_email") String user_email,
                            @Field("password") String password, @Field("i_code") String i_code);

    /**
     * 登录接口-用于验证并登录用户
     *
     * @param user_email 字符串 	必须 	最小：1 	用户邮箱
     * @param password   字符串 	必须 	最小：1 	用户密码
     */
    @FormUrlEncoded
    @POST("login")
    Observable<UserLogin> login(@Field("user_email") String user_email, @Field("password") String password);

    /**
     * 注销接口-用于清除用户登录信息
     */
    @GET("logout")
    Observable<UserLogout> logout();

    /**
     * 邮箱验证接口-用于发送包含验证邮箱验证码的邮件
     *
     * @param user_email 字符串	必须		最小：1	用户邮箱
     */
    @GET("check_mail_1")
    Observable<UserLogout> check_mail_1(@Query("user_email") String user_email);

    /**
     * 邮箱验证接口-用于检验验证码的正确性并验证邮箱
     *
     * @param user_id 字符串	必须    	     	  最小：1	  用户id，由token解密
     */
    @GET("check_mail_2")
    Observable<UserLogout> check_mail_2(@Query("user_id") String user_id);

    /**
     * 确认邮箱验证接口-用于验证用户邮箱是否已被验证
     *
     * @param user_id 整型	必须	用户id
     */
    @GET("get_mail_checked")
    Observable<UserMailChecked> get_mail_checked(@Query("user_id") String user_id);

    /**
     * 邮件发送接口-用于发送包含修改密码验证码的邮件
     *
     * @param user_email 字符串	必须		最小：1	用户邮箱
     */
    @GET("send_mail")
    Observable<UserLogout> send_mail(@Query("user_email") String user_email);

    /**
     * 重置密码接口-用于检验验证码的正确性并找回密码
     *
     * @param user_id  字符串	必须	   	最小：1	用户id,由token解析
     * @param password 字符串	必须		最小：6	用户密码
     * @param psw      字符串	必须		最小：6	用户二次确认密码
     */
    @FormUrlEncoded
    @POST("re_psw")
    Observable<UserLogout> re_psw(@Field("user_id") String user_id, @Field("password") String password,
                                  @Field("psw") String psw);

    /**
     * 获取用户详情
     *
     * @param user_id int	必须	用户ID
     */
    @GET("get_user_info")
    Observable<UserInfo> get_user_info(@Query("user_id") int user_id);

    /**
     * 修改用户的信息
     *
     * @param user_id         int	    必须	用户ID
     * @param user_name       string	可选	用户昵称
     * @param profile_picture string	可选	用户头像
     * @param sex             int	    可选	性别 String
     * @param year            string	可选	年
     * @param month           string	可选	月
     * @param day             string	可选	日
     */
    @GET("alter_user_info")
    Observable<UserAlterInfo> alter_user_info(@Query("user_id") int user_id, @Query("user_name") String user_name,
                                              @Query("profile_picture") String profile_picture, @Query("sex") String sex,
                                              @Query("year") String year, @Query("month") String month,
                                              @Query("day") String day);

    /**
     * 判断用户登陆状态-判断是否登录
     *
     * @param user_id 整形	必须	-	用户ID
     */
    @GET("check_status")
    Observable<UserCheckStatus> check_status(@Query("user_id") int user_id);

    /**
     * 用户消息未读检查接口-检查用户是否有新信息
     *
     * @param user_id 整形	必须	-	用户ID
     */
    @GET("check_new_info")
    Observable<UserCheckNewInfo> check_new_info(@Query("user_id") int user_id);

    /**
     * 有问题未处理
     * 用户消息中心接口-用于接收其他用户发送给用户消息
     *
     * @param user_id 整型	必须	-	最小：1	用户ID
     * @param pn      整型	可选	默认1	-	消息页码
     * @param m_type  整型	可选	默认4	-	消息分类，见下行
     */
    @FormUrlEncoded
    @POST("show_message")
    Observable<UserMessage> show_message(@Field("user_id") int user_id, @Field("pn") int pn,
                                         @Field("m_type") int m_type);

    /**
     * 处理申请者加入私密星球的申请接口-用于同意或者拒绝申请人加入私密星球
     *
     * @param user_id 整型	必须	-	最小：1	用户ID
     * @param m_id    整型	必须	-	最小：1	消息ID
     * @param mark    整型	必须	-	-	标识符，1为同意，0为拒绝
     */
    @GET("process_apply")
    Observable<UserLogout> process_apply(@Query("user_id") int user_id, @Query("m_id") int m_id,
                                         @Query("mark") int mark);

    /**
     * 删除信息接口-用于删除回复我的消息类型中帖子回复已被删除的消息
     *
     * @param m_id 整型	必须	-	-	消息ID
     */
    @GET("delete_message")
    Observable<UserLogout> delete_message(@Query("m_id") int m_id);

    /**
     * 修改密码接口-用于验证旧密码，修改新密码
     *
     * @param user_id   整型	必须		最小：1	用户ID
     * @param password  字符串	必须		最小：6	登录密码
     * @param psw       字符串	必须		最小：6	新密码
     * @param check_psw 字符串	必须		最小：6	二次确认新密码
     */
    @FormUrlEncoded
    @POST("change_pwd")
    Observable<UserLogout> change_pwd(@Field("user_id") int user_id, @Field("password") String password,
                                      @Field("psw") String psw, @Field("check_psw") String check_psw);

    /**
     * 邀请码接口-用于查看用户邀请码
     *
     * @param user_id 整型	必须	-	用户id
     */
    @FormUrlEncoded
    @POST("show_code")
    Observable<UserShowCode> show_code(@Field("user_id") int user_id);
}
