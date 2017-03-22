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



    private ApiClient(){}


            public static  ApiService service;

        public static ApiService getInstance(){
            if(service==null){
                service =new Retrofit.Builder()
                        .baseUrl(ApiDefine.BASE_URL)
                        .client(new OkHttpClient.Builder()
                                .addInterceptor(createHttpLoggingInterceptor())
                                .build())
                        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return  service;
    }


    public static Interceptor createHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }


}
