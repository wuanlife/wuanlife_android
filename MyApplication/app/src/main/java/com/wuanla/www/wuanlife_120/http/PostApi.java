package com.wuanla.www.wuanlife_120.http;


import com.wuanla.www.wuanlife_120.http.Post.PostGetBase;
import com.wuanla.www.wuanlife_120.http.Post.PostGetCollect;
import com.wuanla.www.wuanlife_120.http.Post.PostGetGroup;
import com.wuanla.www.wuanlife_120.http.Post.PostGetIndex;
import com.wuanla.www.wuanlife_120.http.Post.PostGetMyGroup;
import com.wuanla.www.wuanlife_120.http.Post.PostGetReply;
import com.wuanla.www.wuanlife_120.http.Post.PostReply;
import com.wuanla.www.wuanlife_120.http.Post.PostStatus;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/3/31.
 */

public interface PostApi {
    /**
     * 首页帖子接口-用于展示首页帖子
     *
     * @param user_id 整型	可选	-	最小：1
     * @param pn      整型	可选	1	第几页
     */
    @GET("get_index_post")
    Observable<PostGetIndex> get_index_post(@Query("user_id") int user_id, @Query("pn") int pn);

    /**
     * 我的星球页面帖子显示
     *
     * @param user_id int	可选	-	用户ID
     * @param pn      int 不必须	1	第几页
     */
    @GET("get_mygroup_post")
    Observable<PostGetMyGroup> get_mygroup_post(@Query("user_id") int user_id, @Query("pn") int pn);

    /**
     * 星球页面帖子显示
     *
     * @param group_id int	必须  	-	星球ID
     * @param user_id  int	不必须	-	用户ID
     * @param pn       int	不必须	1	第几页
     */
    @GET("get_group_post")
    Observable<PostGetGroup> get_group_post(@Query("group_id") int group_id, @Query("user_id") int user_id,
                                            @Query("pn") int pn);

    /**
     * 帖子详情-发帖内容
     *
     * @param post_id int	必须	帖子ID
     * @param user_id int	可选	用户ID
     */
    @GET("get_post_base")
    Observable<PostGetBase> get_post_base(@Query("post_id") int post_id, @Query("user_id") int user_id);

    /**
     * 帖子详情-回帖内容
     *
     * @param post_id int	必须	-	帖子ID
     * @param user_id int	可选	-	用户ID
     * @param pn      int	可选	1	第几页
     */
    @GET("get_post_reply")
    Observable<PostGetReply> get_post_reply(@Query("post_id") int post_id, @Query("user_id") int user_id,
                                            @Query("pn") int pn);

    /**
     * 帖子发布-星球帖子发布
     *
     * @param user_id  整形	    必须	    -	                用户ID
     * @param group_id 整型	    必须	    最小：1	            发帖星球
     * @param p_title  字符串	必须  	最小：1 最大：60	    帖子标题
     * @param p_text   字符串	必须	    最小：1 最大：5000	帖子正文
     */
    @FormUrlEncoded
    @POST("posts")
    Observable<PostStatus> posts(@Field("user_id") int user_id, @Field("group_id") int group_id,
                                 @Field("p_title") String p_title, @Field("p_text") String p_text);

    /**
     * 帖子的回复-单个帖子的回复操作
     *
     * @param post_id     整型	必须	    -	-	        帖子ID
     * @param p_text      字符串	必须		大于1小于5000   	回复内容
     * @param user_id     整型	必须			            回帖人ID
     * @param reply_floor 整型	可选			            帖子内被回复的人的楼层
     */
    @FormUrlEncoded
    @POST("post_reply")
    Observable<PostReply> post_reply(@Field("post_id") int post_id, @Field("p_text") String p_text,
                                     @Field("user_id") int user_id, @Field("reply_floor") int reply_floor);

    /**
     * 帖子的编辑-单个帖子的编辑操作
     *
     * @param user_id 整型	必须			                用户ID
     * @param post_id 整型  	必须			                帖子ID
     * @param p_title 字符串	必须		最小：1 最大 ：60	    帖子标题
     * @param p_text  字符串	必须		最小：1 最大 ：5000	帖子内容
     */
    @FormUrlEncoded
    @POST("edit_post")
    Observable<PostStatus> edit_post(@Field("user_id") int user_id, @Field("post_id") int post_id,
                                     @Field("p_title") String p_title, @Field("p_text") String p_text);

    /**
     * 删除帖子接口
     *
     * @param user_id 字符串	必须		最小：1	用户id
     * @param post_id 字符串	必须		最小：1	帖子id
     */
    @GET("delete_post")
    Observable<PostStatus> delete_post(@Query("user_id") String user_id, @Query("post_id") String post_id);

    /**
     * 置顶帖子接口
     *
     * @param post_id 字符串	必须		最小：1	帖子id
     */
    @GET("post_sticky")
    Observable<PostStatus> post_sticky(@Query("post_id") String post_id);

    /**
     * 取消置顶帖子接口
     *
     * @param post_id 字符串	必须		最小：1	帖子id
     */
    @GET("post_unsticky")
    Observable<PostStatus> post_unsticky(@Query("post_id") String post_id);

    /**
     * 锁定帖子接口
     *
     * @param post_id 整型	必须			帖子id
     */
    @GET("lock_post")
    Observable<PostStatus> lock_post(@Query("post_id") int post_id);

    /**
     * 解锁帖子接口 可能有问题  看文档
     *
     * @param post_id 整型	必须			帖子id
     */
    @GET("unlock_post")
    Observable<PostStatus> unlock_post(@Query("post_id") int post_id);

    /**
     * 收藏帖子接口
     *
     * @param user_id 字符串	必须		最小：1	用户id
     * @param post_id 字符串	必须		最小：1	帖子id
     */
    @GET("collect_post")
    Observable<PostStatus> collect_post(@Query("user_id") int user_id, @Query("post_id") int post_id);

    /**
     * 获取用户收藏帖子的接口
     *
     * @param user_id 整型	必须      	-	-	用户id
     * @param pn      整型  	可选        	1  	-	当前页面
     */
    @GET("get_collect_post")
    Observable<PostGetCollect> get_collect_post(@Query("user_id") int user_id, @Query("pn") int pn);

    /**
     * 删除帖子回复接口
     *
     * @param user_id 整型	必须		最小：1	用户id
     * @param post_id 整型	必须		最小：1	帖子id
     * @param floor   整型	必须		最小：1	帖子楼层
     */
    @GET("delete_post_reply")
    Observable<PostStatus> delete_post_reply(@Query("user_id") int user_id, @Query("post_id") int post_id,
                                             @Query("floor") int floor);

    /**
     * 取消收藏帖子接口
     *
     * @param user_id 字符串	必须		最小：1	用户id
     * @param post_id 字符串	必须		最小：1	帖子id
     */
    @GET("delete_collect_post")
    Observable<PostStatus> delete_collect_post(@Query("user_id") String user_id, @Query("post_id") String post_id);

    /**
     * 点赞帖子及其回复
     *
     * @param user_id 整型	必须	-	    最小：1	用户ID
     * @param post_id 整型	必须	-	    最小：1	帖子ID
     * @param floor   整型	可选	默认：1	最小：1	帖子楼层
     */
    @GET("approve_post")
    Observable approve_post(@Query("user_id") int user_id, @Query("post_id") int post_id,
                            @Query("floor") int floor);
}
