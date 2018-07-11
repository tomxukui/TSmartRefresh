package com.ablingbling.library.tsmartrefresh;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

/**
 * Created by xukui on 2018/1/17.
 */
public class KRefreshLayout extends SmartRefreshLayout {

    public KRefreshLayout(Context context) {
        super(context);
    }

    public KRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void finish() {
        finishRefresh(0);
        finishLoadMore(0);
    }

    public boolean isEnableRefresh() {
        return isEnableRefreshOrLoadMore(mEnableRefresh);
    }

    public boolean isEnableLoadMore() {
        return isEnableRefreshOrLoadMore(mEnableLoadMore);
    }

}
