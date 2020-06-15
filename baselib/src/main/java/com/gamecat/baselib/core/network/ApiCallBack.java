package com.gamecat.baselib.core.network;

/**
 * Created by bch on 2020/5/11
 */
public interface ApiCallBack<T> {
    public abstract void onAPIResponse(T response);

    public abstract void onAPIError(int errorCode, String errorMsg);
}