package com.wuanla.www.wuanlife_120.ui.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wuanla.www.wuanlife_120.R;
import com.wuanla.www.wuanlife_120.base.BaseMainFragment;

/**
 * Created by ZhangLe on 2017/4/10/010.
 */

public class WuAnThirdFragment extends BaseMainFragment {



    public static WuAnThirdFragment newInstance() {

        Bundle args = new Bundle();

        WuAnThirdFragment fragment = new WuAnThirdFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wuan_third, container, false);
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_third_container, HomeFragment.newInstance());
        }
    }




}
