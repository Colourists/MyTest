package com.gamecat.baselib.core.network;

/**
 * Created by bch on 2020/5/11
 */
public interface AbsApiRequestLife {

    public boolean showFailToast = false;



    /**
     * 是否展示成功提示
     *
     * @return
     */
    boolean getshowSuccessToast();

    /**
     * 是否展示错误提示
     *
     * @return
     */
    boolean getshowFailToast();

    /**
     * 获取成功提示内容
     */
    String getSuccessToast();

    /**
     * 获取失败提示内容
     */
    String getFailToast();

    /**
     * 调用展示提示
     */
    void showToast(boolean success, int code, String message);
}
