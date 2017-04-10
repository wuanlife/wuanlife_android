package com.wuanla.www.wuanlife_120.ui.main.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuanla.www.wuanlife_120.R;
import com.wuanla.www.wuanlife_120.base.BaseMainFragment;
import com.wuanla.www.wuanlife_120.ui.main.fragment.WuAnFirstFragment;
import com.wuanla.www.wuanlife_120.ui.main.fragment.WuAnFiveFragment;
import com.wuanla.www.wuanlife_120.ui.main.fragment.WuAnFourthFragment;
import com.wuanla.www.wuanlife_120.ui.main.fragment.WuAnSecondFragment;
import com.wuanla.www.wuanlife_120.ui.main.fragment.WuAnThirdFragment;
import com.wuanla.www.wuanlife_120.widget.TopBar;
import com.wuanla.www.wuanlife_120.widget.TopBarTab;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener {

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static final int FIVE = 4;
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.iv_search)
    ImageView mIvSearch;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.top_bar)
    TopBar mTopBar;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;


    private SupportFragment[] mFragments = new SupportFragment[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (savedInstanceState == null) {
            mFragments[FIRST] = WuAnFirstFragment.newInstance();
            mFragments[SECOND] = WuAnSecondFragment.newInstance();
            mFragments[THIRD] = WuAnThirdFragment.newInstance();
            mFragments[FOURTH] = WuAnFourthFragment.newInstance();
            mFragments[FIVE] = WuAnFiveFragment.newInstance();


            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH],
                    mFragments[FIVE]
            );
        } else {
            mFragments[FIRST] = findFragment(WuAnFirstFragment.class);
            mFragments[SECOND] = findFragment(WuAnSecondFragment.class);
            mFragments[THIRD] = findFragment(WuAnThirdFragment.class);
            mFragments[FOURTH] = findFragment(WuAnFourthFragment.class);
            mFragments[FIVE] = findFragment(WuAnFiveFragment.class);
        }


        initView();


    }

    private void initView() {
        mTopBar.addItem(new TopBarTab(this, R.drawable.ic_home_violet))
                .addItem(new TopBarTab(this, R.drawable.ic_start_violet))
                .addItem(new TopBarTab(this, R.drawable.ic_start_violet))
                .addItem(new TopBarTab(this, R.drawable.ic_message_violet))
                .addItem(new TopBarTab(this, R.drawable.ic_my_violet));

        mTopBar.setOnTabSelectedListener(new TopBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof WuAnFirstFragment) {
                        currentFragment.popToChild(WuAnFirstFragment.class, false);
                    } else if (currentFragment instanceof WuAnSecondFragment) {
                        currentFragment.popToChild(WuAnSecondFragment.class, false);
                    } else if (currentFragment instanceof WuAnThirdFragment) {
                        currentFragment.popToChild(WuAnThirdFragment.class, false);
                    } else if (currentFragment instanceof WuAnFourthFragment) {
                        currentFragment.popToChild(WuAnFourthFragment.class, false);
                    }else if(currentFragment instanceof  WuAnFiveFragment){
                        currentFragment.popToChild(WuAnFiveFragment.class, false);
                    }
                    return;
                }

            }
        });
    }


    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }

    @Override
    public void onBackToFirstFragment() {
        mTopBar.setCurrentItem(0);
    }


}
