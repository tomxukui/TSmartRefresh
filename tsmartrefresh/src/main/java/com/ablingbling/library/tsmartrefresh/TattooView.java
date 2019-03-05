package com.ablingbling.library.tsmartrefresh;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ablingbling.library.tsmartrefresh.util.DensityUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * Created by xukui on 2018/3/21.
 * <p>
 * 主要显示网络错误或空数据, 一般用于RecyclerView
 */
public class TattooView extends FrameLayout {

    private TextView tattoo_tv_noMore;
    private LinearLayout tattoo_linear_center;
    private AppCompatImageView tattoo_iv_icon;
    private TextView tattoo_tv_message;
    private KRefreshLayout mRefreshLayout;

    private String mNoMoreText;
    private int mTextSize;
    private int mTextColor;
    private String mNetErrorText;
    private int mNetErrorIcon;
    private String mEmptyDataText;
    private int mEmptyDataIcon;
    private boolean mNoMoreEnable;
    private boolean mIsNext;
    private boolean mIsNetError;
    private boolean mIsEmptyData;
    private BaseQuickAdapter mAdapter;
    private OnNoMoreListener mOnNoMoreListener;
    private OnNetErrorListener mOnNetErrorListener;
    private OnEmptyDataListener mOnEmptyDataListener;

    public TattooView(Context context) {
        super(context);
        initData(context, null, 0);
        initView(context);
        setView();
    }

    public TattooView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData(context, attrs, 0);
        initView(context);
        setView();
    }

    public TattooView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context, attrs, defStyleAttr);
        initView(context);
        setView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TattooView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initData(context, attrs, defStyleAttr);
        initView(context);
        setView();
    }

    private void initData(Context context, AttributeSet attrs, int defStyleAttr) {
        mIsNext = true;
        mIsNetError = false;
        mIsEmptyData = false;
        mTextSize = DensityUtil.sp2px(15);
        mTextColor = Color.parseColor("#888888");
        mNetErrorIcon = R.drawable.tsr_ic_failure;
        mEmptyDataIcon = R.drawable.tsr_ic_empty;
        mNoMoreEnable = true;

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TattooView, defStyleAttr, 0);

            mNoMoreText = ta.getString(R.styleable.TattooView_tttv_noMoreText);
            mTextSize = ta.getDimensionPixelSize(R.styleable.TattooView_android_textSize, mTextSize);
            mTextColor = ta.getColor(R.styleable.TattooView_android_textColor, mTextColor);
            mNetErrorText = ta.getString(R.styleable.TattooView_tttv_netErrorText);
            mNetErrorIcon = ta.getResourceId(R.styleable.TattooView_tttv_netErrorIcon, mNetErrorIcon);
            mEmptyDataText = ta.getString(R.styleable.TattooView_tttv_emptyDataText);
            mEmptyDataIcon = ta.getResourceId(R.styleable.TattooView_tttv_emptyDataIcon, mEmptyDataIcon);
            mNoMoreEnable = ta.getBoolean(R.styleable.TattooView_tttv_noMoreEnable, mNoMoreEnable);

            ta.recycle();
        }

        if (TextUtils.isEmpty(mNoMoreText)) {
            mNoMoreText = "──  没有记录了  ──";
        }
        if (TextUtils.isEmpty(mNetErrorText)) {
            mNetErrorText = "数据请求失败, 点击屏幕刷新";
        }
        if (TextUtils.isEmpty(mEmptyDataText)) {
            mEmptyDataText = "暂无数据记录";
        }
    }

    private void initView(Context context) {
        inflate(context, R.layout.view_tattoo, this);

        tattoo_tv_noMore = findViewById(R.id.tattoo_tv_noMore);
        tattoo_linear_center = findViewById(R.id.tattoo_linear_center);
        tattoo_iv_icon = findViewById(R.id.tattoo_iv_icon);

        tattoo_tv_message = findViewById(R.id.tattoo_tv_message);
        tattoo_tv_message.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        tattoo_tv_message.setTextColor(mTextColor);

        tattoo_tv_noMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnNoMoreListener != null) {
                    mOnNoMoreListener.onNoMoreListener(TattooView.this);
                }
            }
        });

        tattoo_linear_center.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (mRefreshLayout != null && mIsEmptyData) {
                    if (mIsNetError) {
                        if (mOnNetErrorListener != null) {
                            mOnNetErrorListener.onNetErrorListener(TattooView.this);

                        } else {
                            if (mRefreshLayout.isEnableRefresh()) {
                                mRefreshLayout.autoRefresh(0);
                            }
                        }

                    } else {
                        if (mOnEmptyDataListener != null) {
                            mOnEmptyDataListener.onEmptyDataListener(TattooView.this);
                        }
                    }
                }
            }

        });
    }

    private void setView() {
        if (mIsEmptyData && mIsNetError) {//空数据, 网络错误
            setVisibility(View.VISIBLE);
            tattoo_tv_noMore.setVisibility(View.GONE);
            tattoo_linear_center.setVisibility(View.VISIBLE);

            tattoo_iv_icon.setImageResource(mNetErrorIcon);
            tattoo_tv_message.setText(mNetErrorText);

            adjustHeight(measureHeight());

        } else if (mIsEmptyData && !mIsNetError) {//空数据, 网络正常
            setVisibility(View.VISIBLE);
            tattoo_tv_noMore.setVisibility(View.GONE);
            tattoo_linear_center.setVisibility(View.VISIBLE);

            tattoo_iv_icon.setImageResource(mEmptyDataIcon);
            tattoo_tv_message.setText(mEmptyDataText);

            adjustHeight(measureHeight());

        } else if ((!mIsEmptyData) && (!mIsNext) && mNoMoreEnable) {//有数据, 没有更多数据
            setVisibility(View.VISIBLE);
            tattoo_tv_noMore.setVisibility(View.VISIBLE);
            tattoo_tv_noMore.setText(mNoMoreText);

            tattoo_linear_center.setVisibility(View.GONE);

            adjustHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        } else {//有数据, 有更多数据
            setVisibility(View.GONE);
            tattoo_tv_noMore.setVisibility(View.GONE);
            tattoo_linear_center.setVisibility(View.GONE);

            adjustHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private int measureHeight() {
        int headerHeight = 0;
        if (mAdapter.getHeaderLayout() != null) {
            headerHeight = mAdapter.getHeaderLayout().getHeight();
        }

        int contentHeight = mRefreshLayout.getHeight() - mRefreshLayout.getPaddingTop() - mRefreshLayout.getPaddingBottom() - headerHeight;
        int viewHeight = tattoo_linear_center.getHeight();
        if (viewHeight == 0) {
            viewHeight = DensityUtil.dp2px(140) + DensityUtil.sp2px(mTextSize);
        }

        if (contentHeight <= viewHeight) {
            return ViewGroup.LayoutParams.WRAP_CONTENT;
        } else {
            return contentHeight;
        }
    }

    private void adjustHeight(int height) {
        ViewGroup.LayoutParams params = getLayoutParams();

        if (params == null) {
            setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

        } else if (params.height != height) {
            params.height = height;
            setLayoutParams(params);
        }
    }

    public void setRefreshLayout(KRefreshLayout refreshLayout) {
        mRefreshLayout = refreshLayout;
    }

    public void setAdapter(BaseQuickAdapter adapter) {
        mAdapter = adapter;
    }

    private void setOK(int count, boolean isNext) {
        mIsNext = isNext;
        mIsNetError = false;
        mIsEmptyData = (count == 0);

        setView();
    }

    private void setError(int count) {
        mIsNetError = true;
        mIsEmptyData = (count == 0);

        setView();
    }

    public void setOK() {
        setOK(mAdapter.getData().size(), mRefreshLayout.isEnableLoadMore());
    }

    public void setError() {
        setError(mAdapter.getData().size());
    }

    public void setNetErrorText(String netErrorText) {
        mNetErrorText = netErrorText;
    }

    public void setNetErrorIcon(int netErrorIcon) {
        mNetErrorIcon = netErrorIcon;
    }

    public void setEmptyDataText(String emptyDataText) {
        mEmptyDataText = emptyDataText;
    }

    public void setEmptyDataIcon(int emptyDataIcon) {
        mEmptyDataIcon = emptyDataIcon;
    }

    public void setNoMoreText(String noMoreText) {
        mNoMoreText = noMoreText;
    }

    public void setNoMoreEnable(boolean noMoreEnable) {
        mNoMoreEnable = noMoreEnable;
    }

    public void setOnNoMoreListener(OnNoMoreListener listener) {
        mOnNoMoreListener = listener;
    }

    public void setOnNetErrorListener(OnNetErrorListener listener) {
        mOnNetErrorListener = listener;
    }

    public void setOnEmptyDataListener(OnEmptyDataListener listener) {
        mOnEmptyDataListener = listener;
    }

    public interface OnNoMoreListener {

        void onNoMoreListener(TattooView view);

    }

    public interface OnNetErrorListener {

        void onNetErrorListener(TattooView view);

    }

    public interface OnEmptyDataListener {

        void onEmptyDataListener(TattooView view);

    }

}
