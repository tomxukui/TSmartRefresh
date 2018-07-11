package com.ablingbling.library.tsmartrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.ablingbling.library.tsmartrefresh.decoration.DividerDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by xukui on 2018/3/22.
 */

public class KRefreshRecyclerView extends KRefreshLayout {

    private RecyclerView refreshRecycler_recyclerView;
    private TattooView tattooView;

    private BaseQuickAdapter mAdapter;
    private int mOrientation;
    private boolean mClipToPadding;
    private int mClipPaddingLeft;
    private int mClipPaddingTop;
    private int mClipPaddingRight;
    private int mClipPaddingBottom;
    private int mDivider;
    private String mNoMoreText;
    private String mNetErrorText;
    private int mNetErrorIcon;
    private String mEmptyDataText;
    private int mEmptyDataIcon;
    private boolean mNoMoreEnable;

    public KRefreshRecyclerView(Context context) {
        super(context);
        initData(context, null, 0);
        initView(context);
    }

    public KRefreshRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
        initView(context);
    }

    public KRefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mOrientation = OrientationHelper.VERTICAL;
        mClipToPadding = true;
        mClipPaddingLeft = 0;
        mClipPaddingTop = 0;
        mClipPaddingRight = 0;
        mClipPaddingBottom = 0;
        mDivider = 0;
        mNetErrorIcon = R.mipmap.ic_no_wifi;
        mEmptyDataIcon = R.mipmap.ic_record_no;
        mNoMoreEnable = true;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.KRefreshRecyclerView, defStyleAttr, 0);

            mOrientation = ta.getInt(R.styleable.KRefreshRecyclerView_android_orientation, mOrientation);
            mClipToPadding = ta.getBoolean(R.styleable.KRefreshRecyclerView_android_clipToPadding, mClipToPadding);
            mClipPaddingLeft = ta.getDimensionPixelSize(R.styleable.KRefreshRecyclerView_krrv_clipPaddingLeft, mClipPaddingLeft);
            mClipPaddingTop = ta.getDimensionPixelSize(R.styleable.KRefreshRecyclerView_krrv_clipPaddingTop, mClipPaddingTop);
            mClipPaddingRight = ta.getDimensionPixelSize(R.styleable.KRefreshRecyclerView_krrv_clipPaddingRight, mClipPaddingRight);
            mClipPaddingBottom = ta.getDimensionPixelSize(R.styleable.KRefreshRecyclerView_krrv_clipPaddingBottom, mClipPaddingBottom);
            mDivider = ta.getResourceId(R.styleable.KRefreshRecyclerView_krrv_divider, mDivider);
            mNoMoreText = ta.getString(R.styleable.KRefreshRecyclerView_krrv_noMoreText);
            mNetErrorText = ta.getString(R.styleable.KRefreshRecyclerView_krrv_netErrorText);
            mNetErrorIcon = ta.getResourceId(R.styleable.KRefreshRecyclerView_krrv_netErrorIcon, mNetErrorIcon);
            mEmptyDataText = ta.getString(R.styleable.KRefreshRecyclerView_krrv_emptyDataText);
            mEmptyDataIcon = ta.getResourceId(R.styleable.KRefreshRecyclerView_krrv_emptyDataIcon, mEmptyDataIcon);
            mNoMoreEnable = ta.getBoolean(R.styleable.KRefreshRecyclerView_krrv_noMoreEnable, mNoMoreEnable);

            ta.recycle();
        }

        if (TextUtils.isEmpty(mNoMoreText)) {
            mNoMoreText = "──  没有记录了  ──";
        }
        if (TextUtils.isEmpty(mNetErrorText)) {
            mNetErrorText = "网络不给力, 点击屏幕刷新";
        }
        if (TextUtils.isEmpty(mEmptyDataText)) {
            mEmptyDataText = "暂无数据记录";
        }
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_refresh_recycler, this);

        refreshRecycler_recyclerView = findViewById(R.id.refreshRecycler_recyclerView);
        refreshRecycler_recyclerView.setClipToPadding(mClipToPadding);
        refreshRecycler_recyclerView.setPadding(mClipPaddingLeft, mClipPaddingTop, mClipPaddingRight, mClipPaddingBottom);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(mOrientation);
        refreshRecycler_recyclerView.setLayoutManager(layoutManager);

        if (mDivider != 0) {
            if (mOrientation == OrientationHelper.VERTICAL) {
                addVerticalDivider(mDivider);
            } else {
                addHorizontalDivider(mDivider);
            }
        }

        tattooView = new TattooView(context);
        tattooView.setNoMoreText(mNoMoreText);
        tattooView.setEmptyDataIcon(mEmptyDataIcon);
        tattooView.setEmptyDataText(mEmptyDataText);
        tattooView.setNetErrorIcon(mNetErrorIcon);
        tattooView.setNetErrorText(mNetErrorText);
        tattooView.setNoMoreEnable(mNoMoreEnable);
        tattooView.setRefreshLayout(this);
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mAdapter = adapter;
        refreshRecycler_recyclerView.setAdapter(mAdapter);
        tattooView.setAdapter(mAdapter);
        mAdapter.addFooterView(tattooView);
    }

    private void addDivider(int orientation, @DrawableRes int id) {
        DividerDecoration divider = new DividerDecoration(getContext(), orientation);
        divider.setDrawable(ContextCompat.getDrawable(getContext().getApplicationContext(), id));
        refreshRecycler_recyclerView.addItemDecoration(divider);
    }

    private void addVerticalDivider(@DrawableRes int id) {
        addDivider(DividerDecoration.VERTICAL, id);
    }

    private void addHorizontalDivider(@DrawableRes int id) {
        addDivider(DividerDecoration.HORIZONTAL, id);
    }

    private void setNewData(List list) {
        mAdapter.setNewData(list);
    }

    private void addData(List list) {
        mAdapter.addData(list);
    }

    public void addHeaderView(View view) {
        mAdapter.addHeaderView(view);
    }

    public void addFooterView(View view) {
        mAdapter.addFooterView(view);
    }

    public void setOnItemClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListener(BaseQuickAdapter.OnItemLongClickListener listener) {
        mAdapter.setOnItemLongClickListener(listener);
    }

    public void setOnItemChildClickListener(BaseQuickAdapter.OnItemChildClickListener listener) {
        mAdapter.setOnItemChildClickListener(listener);
    }

    public void finishSuccess(boolean isFirst, boolean isNext, List list) {
        finish();
        setEnableLoadMore(isNext);

        if (isFirst) {
            setNewData(list);
        } else {
            addData(list);
        }

        tattooView.setOK();
    }

    public void finishSuccess(List list) {
        finishSuccess(true, false, list);
    }

    public void finishFailure() {
        finish();
        tattooView.setError();
    }

    public void setOnNoMoreListener(TattooView.OnNoMoreListener listener) {
        if (tattooView != null) {
            tattooView.setOnNoMoreListener(listener);
        }
    }

    public void setOnNetErrorListener(TattooView.OnNetErrorListener listener) {
        if (tattooView != null) {
            tattooView.setOnNetErrorListener(listener);
        }
    }

    public void setOnEmptyDataListener(TattooView.OnEmptyDataListener listener) {
        if (tattooView != null) {
            tattooView.setOnEmptyDataListener(listener);
        }
    }

    public void setNoMoreText(String noMoreText) {
        mNoMoreText = noMoreText;

        if (tattooView != null) {
            tattooView.setNoMoreText(mNoMoreText);
        }
    }

    public void setEmptyDataIcon(int emptyDataIcon) {
        mEmptyDataIcon = emptyDataIcon;

        if (tattooView != null) {
            tattooView.setEmptyDataIcon(mEmptyDataIcon);
        }
    }

    public void setEmptyDataText(String emptyDataText) {
        mEmptyDataText = emptyDataText;

        if (tattooView != null) {
            tattooView.setEmptyDataText(mEmptyDataText);
        }
    }

    public void setNetErrorIcon(int netErrorIcon) {
        mNetErrorIcon = netErrorIcon;

        if (tattooView != null) {
            tattooView.setNetErrorIcon(mNetErrorIcon);
        }
    }

    public void setNetErrorText(String netErrorText) {
        mNetErrorText = netErrorText;

        if (tattooView != null) {
            tattooView.setNetErrorText(mNetErrorText);
        }
    }

    public void setNoMoreEnable(boolean noMoreEnable) {
        mNoMoreEnable = noMoreEnable;

        if (tattooView != null) {
            tattooView.setNoMoreEnable(mNoMoreEnable);
        }
    }

    public RecyclerView getRecyclerView() {
        return refreshRecycler_recyclerView;
    }

}
