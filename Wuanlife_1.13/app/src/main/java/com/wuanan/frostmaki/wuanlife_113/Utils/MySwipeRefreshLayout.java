package com.wuanan.frostmaki.wuanlife_113.Utils;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wuanan.frostmaki.wuanlife_113.MainActivity;
import com.wuanan.frostmaki.wuanlife_113.R;

/**
 * Created by Frostmaki on 2016/10/7.
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {

    private int mTouchSlop;
    private ListView mListView;
    private OnLoadListener mOnLoadListener;
    private View mListViewFooter;
    private int mYDown;
    private int mLastY;
    private boolean isLoading = false;

    public MySwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // 初始化ListView对象
        if (mListView == null) {
            getListView();
        }
    }

    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }

    public void setListView(ListView list) {
        this.mListView = list;
        setLoading(true);
        this.mListView.setOnScrollListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    /**
     * @方法说明:是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * @方法说明:判断是否到了最底部
     */
    private boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * @方法说明:是否是上拉操作
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * @方法说明: 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            mOnLoadListener.onLoad();
        }
        // 设置状态
        setLoading(true);
    }

    /**
     * @方法说明:设置刷新
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (mListView != null && mListView.getFooterViewsCount() > 0)
            mListView.removeFooterView(mListViewFooter);
        if (isLoading) {
            if (mListView != null && mListView.getFooterViewsCount() <= 0)
                mListView.addFooterView(mListViewFooter);
        } else {
            mYDown = 0;
            mLastY = 0;
        }
    }

    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
       // MainActivity.setTabGone();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (isFastDoubleClick(100))
            return;

        // 滚动时到了最底部也可以加载更多
        isLoading = false;
        if (canLoad()) {
            loadData();
        }
    }

    public View getmListViewFooter() {
        return mListViewFooter;
    }

    /**
     * @类描述:加载更多的监听器
     */
    public static interface OnLoadListener {
        public void onLoad();
    }

    private static long lastClickTime;

    public static boolean isFastDoubleClick(long times) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < times) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
