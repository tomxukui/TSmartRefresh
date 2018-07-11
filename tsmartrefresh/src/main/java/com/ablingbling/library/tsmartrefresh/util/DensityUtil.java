package com.ablingbling.library.tsmartrefresh.util;

import android.content.res.Resources;

/**
 * Created by xukui on 2017/11/17.
 */

public class DensityUtil {

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     */
    public static final int dp2px(float dpValue) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     */
    public static final int px2dp(float pxValue) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为sp
     */
    public static final int px2sp(float pxValue) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / density + 0.5f);
    }

    /**
     * 根据手机的分辨率从sp(像素) 的单位 转成为px
     */
    public static final int sp2px(float spValue) {
        final float density = Resources.getSystem().getDisplayMetrics().density;
        return (int) (spValue * density + 0.5f);
    }

}