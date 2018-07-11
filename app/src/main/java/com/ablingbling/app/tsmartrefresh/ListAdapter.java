package com.ablingbling.app.tsmartrefresh;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by xukui on 2017/11/20.
 */

public class ListAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ListAdapter() {
        super(R.layout.item_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int position = helper.getLayoutPosition() - getHeaderLayoutCount();

        TextView tv_name = helper.getView(R.id.tv_name);
        tv_name.setText(position + "." + item);
    }

}