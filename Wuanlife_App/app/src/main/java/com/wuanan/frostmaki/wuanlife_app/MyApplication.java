package com.wuanan.frostmaki.wuanlife_app;

import android.app.Application;

/**
 * Created by Frostmaki on 2016/7/18.
 */
public class MyApplication {
    private static String ApiHost="dev.wuanlife.com:800";


    public static String getUrl(){
        return ApiHost;
    }


}
