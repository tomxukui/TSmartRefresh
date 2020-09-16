package com.ablingbling.app.tsmartrefresh;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ablingbling.library.tsmartrefresh.KRefreshRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by xukui on 2018/5/8.
 */
public class RefreshActivity extends AppCompatActivity implements OnRefreshLoadMoreListener, OnItemClickListener {

    private KRefreshRecyclerView refreshView;
    private Button btn_clear;

    private ListAdapter mRecyclerAdapter;

    private LayoutInflater mInflater;
    private int mPageNumber = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        initData();
        initView();
        setView();

        refreshView.autoRefresh();
    }

    private void initData() {
        mInflater = LayoutInflater.from(this);
        mRecyclerAdapter = new ListAdapter();
    }

    private void initView() {
        refreshView = findViewById(R.id.refreshView);
        btn_clear = findViewById(R.id.btn_clear);
    }

    private void setView() {
        refreshView.setAdapter(mRecyclerAdapter);
        refreshView.setOnRefreshLoadMoreListener(this);
        refreshView.setOnItemClickListener(this);

        View headerView = mInflater.inflate(R.layout.header_list_refresh, refreshView, false);
        refreshView.addHeaderView(headerView);

        btn_clear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPageNumber = 1;
                refreshView.finishSuccess(null);
            }

        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Toast.makeText(this, "点击了" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMore(RefreshLayout refreshlayout) {
        mPageNumber++;
        refreshView.finishSuccess(mPageNumber == 1, true, DataHelper.getData(5));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        mPageNumber = 1;
        refreshView.finishSuccess(mPageNumber == 1, true, DataHelper.getData(5));
    }

}
