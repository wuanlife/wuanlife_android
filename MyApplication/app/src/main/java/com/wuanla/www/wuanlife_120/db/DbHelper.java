package com.wuanla.www.wuanlife_120.db;

import android.content.Context;

import org.greenrobot.greendao.database.Database;

/**
 * Created by lezi on 2017/3/22.
 */

public class DbHelper {


    public static DbHelper sDbHelper;
//   private static DaoSession sDaoSession;
//   private static DaoManager sDaoManager;
//
//
//
//
    public static DbHelper initDB(Context mContext) {
        if (sDbHelper == null) {
//            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(mContext, "WuAnLife_db");
//            Database db = helper.getWritableDb();
//            sDaoSession = new DaoMaster(db).newSession();
//            sDaoManager = new DaoManager(sDaoSession);
//            helper.onUpgrade(db, 0, 0);
        }
        return sDbHelper;
    }
////
//    public static DaoManager getDaoManager(){
//        return sDaoManager;
//    }
//

}
