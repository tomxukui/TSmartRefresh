package com.ablingbling.app.tsmartrefresh;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDelegate;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;

/**
 * Created by xukui on 2018/5/8.
 */
public class Mapp extends Application {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {

            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                return new MaterialHeader(context);
            }

        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {

            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                BallPulseFooter footer = new BallPulseFooter(context);
                footer.setSpinnerStyle(SpinnerStyle.Scale);
                footer.setNormalColor(Color.parseColor("#528bd2"));
                footer.setAnimatingColor(Color.parseColor("#528bd2"));
                return footer;
            }

        });
    }

}
