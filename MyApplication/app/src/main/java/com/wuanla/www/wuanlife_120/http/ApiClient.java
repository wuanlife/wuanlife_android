package com.wuanla.www.wuanlife_120.http;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lezi on 2017/1/13/013.
 */

public class ApiClient {


    private static volatile Retrofit sRetrofit;
    private static  GroupApi sGroupApi;
    private static  PostApi sPostApi;
    private static  UserApi sUserApi;


    public static Retrofit getInstance() {
        if (sRetrofit == null) {
            synchronized (ApiClient.class) {
                if (sRetrofit == null) {
                    sRetrofit = new Retrofit.Builder()
                            .baseUrl(ApiDefine.BASE_URL)
                            .client(new OkHttpClient.Builder()
                                    .addInterceptor(createHttpLoggingInterceptor())
                                    .build())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }

        }
        return sRetrofit;
    }


    public static GroupApi getGroupApiService() {
        if (sGroupApi == null) {
                    sGroupApi = getInstance().create(GroupApi.class);
        }
        return sGroupApi;
    }


    public static PostApi getPostApiService() {
        if (sPostApi == null) {
                    sPostApi = getInstance().create(PostApi.class);
        }
        return sPostApi;
    }


    public static UserApi getUserApiService() {
        if (sUserApi == null) {
                    sUserApi = getInstance().create(UserApi.class);
        }
        return sUserApi;
    }


    public static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
