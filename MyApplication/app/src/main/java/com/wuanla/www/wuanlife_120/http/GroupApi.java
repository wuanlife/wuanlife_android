package com.wuanla.www.wuanlife_120.http;


import com.wuanla.www.wuanlife_120.http.Group.GroupCreate;
import com.wuanla.www.wuanlife_120.http.Group.GroupInfo;
import com.wuanla.www.wuanlife_120.http.Group.GroupJoin;
import com.wuanla.www.wuanlife_120.http.Group.GroupJoined;
import com.wuanla.www.wuanlife_120.http.Group.GroupLists;
import com.wuanla.www.wuanlife_120.http.Group.GroupQuit;
import com.wuanla.www.wuanlife_120.http.Group.GroupSearch;
import com.wuanla.www.wuanlife_120.http.Group.GroupStatus;
import com.wuanla.www.wuanlife_120.http.Group.GroupUserManage;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/29.
 */

public interface GroupApi {
    /**
     * 星球创建接口-用于创建星球
     *
     * @param user_id 整型 	            必须 	- 	            用户id
     * @param g_name  字符串 	        必须 	最小：1 最大：80 	星球名称
     * @param map     g_image 	字符串 	可选 		            星球图片base64编码
     *                g_introduction 	字符串 	可选 		    星球简介
     *                private 	整型 	可选 		            私密，1为私密0为不私密
     */
    @GET("create")
    Observable<GroupCreate> create(@Query("user_id") int user_id, @Query("g_name") String g_name,
                                   @QueryMap Map<String, String> map);

    /**
     * 加入星球接口-用户加入星球
     *
     * @param user_id  整形 	必须 	- 	用户ID
     * @param group_id 整形 	必须 	最小：1 	星球ID
     */
    @GET("join")
    Observable<GroupJoin> join(@Query("user_id") int user_id, @Query("group_id") int group_id);

    /**
     * 退出星球接口-用户退出星球
     *
     * @param user_id  整形 	必须 	- 	用户ID
     * @param group_id 整形 	必须 	最小：1 	星球ID
     */
    @GET("quit")
    Observable<GroupQuit> quit(@Query("user_id") int user_id, @Query("group_id") int group_id);

    /**
     * 星球列表-按成员数降序显示星球列表
     *
     * @param pn 整型 	可选 	- 	当前页数
     */
    @GET("lists")
    Observable<GroupLists> lists(@Query("pn") int pn);

    /**
     * 判断用户是否加入该星球
     *
     * @param user_id  整形 	必须 	- 	用户ID
     * @param group_id 整型 	必须 	- 	星球ID
     */
    @GET("g_status")
    Observable<GroupStatus> g_status(@Query("user_id") int user_id, @Query("group_id") int group_id);

    /**
     * 获取用户加入星球的接口
     *
     * @param user_id 整型 	必须 			用户id
     * @param pn      整型 	可选 	1 		当前页面
     */
    @FormUrlEncoded
    @POST("get_joined")
    Observable<GroupJoined> get_joined(@Field("user_id") int user_id, @Field("pn") int pn);


    /**
     * 获取用户创建星球的接口
     *
     * @param user_id 整型 	必须 			用户id
     * @param pn      整型 	可选 	1 		当前页面
     */
    @FormUrlEncoded
    @POST("get_create")
    Observable<GroupJoined> get_create(@Field("user_id") int user_id, @Field("pn") int pn);

    /**
     * 获取星球详情
     *
     * @param group_id int 	必须 	星球id
     * @param user_id  int 	必须 	用户ID
     */
    @GET("get_group_info")
    Observable<GroupInfo> get_group_info(@Query("group_id") int group_id, @Query("user_id") int user_id);

    /**
     * 修改星球详情
     *
     * @param group_id int 	必须 	星球id
     * @param user_id  int 	必须 	用户ID
     * @param map      g_introduction 	string 	可选 	星球简介
     *                 g_image 	string 	可选 	星球图片
     */
    @GET("alter_group_info")
    Observable<GroupStatus> alter_group_info(@Query("group_id") int group_id, @Query("user_id") int user_id,
                                             @QueryMap Map<String, String> map);

    /**
     * 私密星球申请加入接口-用于申请者加入私密星球
     *
     * @param user_id  整型 	必须 	- 	最小：1 	用户ID
     * @param group_id 整型 	必须 	- 	最小：1 	星球ID
     * @param map      text 	字符串 	可选 	- 	最小：1 最大：50 	申请信息
     */
    @GET("private_group")
    Observable<GroupStatus> private_group(@Query("user_id") int user_id, @Query("group_id") int group_id,
                                          @QueryMap Map<String, String> map);

    /**
     * 搜索接口
     *
     * @param text string 	必须 	搜索内容
     * @param map  pnum 	int 	可选 	帖子每页数量，为0时不查询帖子
     *             pn 	    int 	可选 	帖子当前页数
     *             gnum 	int 	可选 	星球每页数量，为0时不查询星球
     *             gn 	    int 	可选 	星球当前页数
     */
    @GET("search")
    Observable<GroupSearch> search(@Query("text") String text, @QueryMap Map<String, String> map);

    /**
     * 星球用户管理接口-用于显示加入星球的用户，方便管理
     *
     * @param user_id  整型 	必须 	- 	- 	用户ID
     * @param group_id 整型 	必须 	- 	- 	星球ID
     */
    @GET("user_manage")
    Observable<GroupUserManage> user_manage(@Query("user_id") int user_id, @Query("group_id") int group_id);

    /**
     * 删除星球成员
     *
     * @param user_id   整型 	必须 	- 	- 	用户ID
     * @param group_id  整型 	必须 	- 	- 	星球ID
     * @param member_id 整型 	必须 	- 	- 	成员ID
     */
    @GET("delete_group_member")
    Observable<GroupStatus> delete_group_member(@Query("user_id") int user_id, @Query("group_id") int group_id,
                                                @Query("member_id") int member_id);
}
