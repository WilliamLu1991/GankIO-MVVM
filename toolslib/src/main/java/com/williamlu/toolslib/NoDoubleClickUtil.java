package com.williamlu.toolslib;

/**
 * 防止重复点击，工具
 * Created by Menghui on 2017/8/16.
 */
public class NoDoubleClickUtil {
    private static long lastClickTime = 0;
    private final static int SPACE_TIME = 500;

    /**
     * 判断是否重复点击，默认未重复点击
     *
     * @return
     */
    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick2 = false;
        } else {
            isClick2 = true;
        }
        lastClickTime = currentTime;
        return isClick2;
    }

    /**
     * 判断是否重复点击，默认未重复点击
     *
     * @return
     */
    public synchronized static boolean isNoDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClick2;
        if (currentTime - lastClickTime > SPACE_TIME) {
            isClick2 = true;
        } else {
            isClick2 = false;
        }
        lastClickTime = currentTime;
        return isClick2;
    }

}
