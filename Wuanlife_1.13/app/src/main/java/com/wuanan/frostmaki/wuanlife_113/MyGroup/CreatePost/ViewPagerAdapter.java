package com.wuanan.frostmaki.wuanlife_113.MyGroup.CreatePost;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.wuanan.frostmaki.wuanlife_113.MyGroup.JoinCreateGroup;

import java.util.List;

/**
 * Created by Frostmaki on 2016/10/8.
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> lisViews;


    public ViewPagerAdapter(List<View>  lisViews) {
        this.lisViews=lisViews;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lisViews.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0==arg1;//官方提示这样写
    }


    @Override
    public void destroyItem(View container, int position, Object object) {
        // TODO Auto-generated method stub
        ((ViewPager) container).removeView(lisViews.get(position));//删除页卡
    }
    //这个方法用来实例化页卡
    @Override
    public Object instantiateItem(View container, int position) {
        ((ViewGroup) container).addView(lisViews.get(position), 0);//添加页卡
        return lisViews.get(position);
    }

}

