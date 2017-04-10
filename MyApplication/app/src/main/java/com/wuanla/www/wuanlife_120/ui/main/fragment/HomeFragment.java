package com.wuanla.www.wuanlife_120.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuanla.www.wuanlife_120.R;
import com.wuanla.www.wuanlife_120.base.BaseFragment;
import com.wuanla.www.wuanlife_120.contract.main.HomeContract;
import com.wuanla.www.wuanlife_120.contract.main.HomePresenterImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZhangLe on 2017/4/10/010.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {


    @BindView(R.id.rv_content)
    RecyclerView mRvContent;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private HomePresenterImpl mHomePresenter;


    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);

        initView();

        mHomePresenter = new HomePresenterImpl(this);
        mHomePresenter.getHomeDataAsyncTask();

        return view;
    }

    private void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRvContent.setLayoutManager(new LinearLayoutManager(this.getContext()));

    }


    @Override
    public void start() {

    }

    @Override
    public void detachView() {

    }
}
