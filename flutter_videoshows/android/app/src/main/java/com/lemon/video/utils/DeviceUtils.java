package com.lemon.video.utils;

import com.lemon.video.application.BaseApp;

/**
 * 获取设备的一些信息
 */
public class DeviceUtils {

    /**
     * dp 转化为 px
     *
     * @param dpValue dpValue
     * @return int
     */
    public static int dp2px(float dpValue) {
        final float scale = BaseApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * px 转化为 dp
     *
     * @param pxValue pxValue
     */
    public static int px2dp(float pxValue) {
        final float scale = BaseApp.getInstance().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * 获取设备宽度（px）
     *
     * @return int
     */
    public static int width() {
        return BaseApp.getInstance().getResources().getDisplayMetrics().widthPixels;
    }
}
