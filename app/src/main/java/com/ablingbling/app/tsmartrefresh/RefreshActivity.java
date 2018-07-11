package com.ablingbling.app.tsmartrefresh;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.ablingbling.library.tsmartrefresh.KRefreshRecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/**
 * Created by xukui on 2018/5/8.
 */
public class RefreshActivity extends AppCompatActivity implements OnRefreshLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private KRefreshRecyclerView refreshView;

    private ListAdapter mRecyclerAdapter;

    private int mPageNumber = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        mRecyclerAdapter = new ListAdapter();

        refreshView = findViewById(R.id.refreshView);
        refreshView.setAdapter(mRecyclerAdapter);
        refreshView.setOnRefreshLoadMoreListener(this);
        refreshView.setOnItemClickListener(this);

        View headerView = LayoutInflater.from(this).inflate(R.layout.header_list_refresh, null);
        refreshView.addHeaderView(headerView);

        View footerView = LayoutInflater.from(this).inflate(R.layout.footer_list_refresh, null);
        refreshView.addFooterView(footerView);

        refreshView.autoRefresh();
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
