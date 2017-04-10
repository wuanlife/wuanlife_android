package com.wuanla.www.wuanlife_120.contract.main;

import com.wuanla.www.wuanlife_120.base.BasePresenter;
import com.wuanla.www.wuanlife_120.base.BaseView;

/**
 * Created by ZhangLe on 2017/4/10/010.
 */

public interface HomeContract {


    interface HomeView extends BaseView{

    }



    interface HomePresenter extends BasePresenter {


        void getHomeDataAsyncTask();
    }


}
