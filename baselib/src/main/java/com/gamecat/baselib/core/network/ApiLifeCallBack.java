package com.gamecat.baselib.core.network;

/**
 * Created by bch on 2020/5/11
 */
public interface ApiLifeCallBack<T> extends ApiCallBack<T> {
    public abstract void onStart();

    public abstract void onFinsh();

}