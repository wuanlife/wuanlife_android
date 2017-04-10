package com.wuanla.www.wuanlife_120.contract.main;

import android.util.Log;

import com.wuanla.www.wuanlife_120.http.ApiClient;
import com.wuanla.www.wuanlife_120.http.ApiDefine;
import com.wuanla.www.wuanlife_120.http.Post.PostGetIndex;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by ZhangLe on 2017/4/10/010.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {

    private static final String TAG = HomePresenterImpl.class.getSimpleName();
    private final HomeContract.HomeView mHomeView;

    public HomePresenterImpl(HomeContract.HomeView pHomeView) {
        mHomeView = pHomeView;
    }


    @Override
    public void getHomeDataAsyncTask() {
        ApiClient.getPostApiService().get_index_post(ApiDefine.POST_URL,null,"1")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<PostGetIndex>() {
                    @Override
                    public void call(PostGetIndex pPostGetIndex) {
                        Log.e(TAG,"成功了啊"+pPostGetIndex.getData().getPosts().get(0).getUsers().getUser_name());
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable pThrowable) {
                        Log.e(TAG,"失败了"+pThrowable.getMessage());
                    }
                });
    }
}
