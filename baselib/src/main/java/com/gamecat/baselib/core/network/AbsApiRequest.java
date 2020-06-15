package com.gamecat.baselib.core.network;

import com.alibaba.fastjson.TypeReference;
import com.lzy.okgo.model.HttpHeaders;

/**
 * Created by bch on 2020/5/11
 */
public abstract class AbsApiRequest {

    /**
     * 获取域名
     *
     * @return
     */
    public abstract String getAPIBaseURL();

    /**
     * 获取域名后网址
     *
     * @return
     */
    public abstract String getAPIName();

    /**
     * 拼接域名和网址
     *
     * @return
     */
    public String getApiUrl() {
        return getAPIBaseURL() + getAPIName();
    }


    /**
     * 添加参数
     *
     * @param builder
     * @return
     */
    public  ParamsBuilder appendParams(ParamsBuilder builder){
        return builder;
    }


    /**
     * 添加头参数
     *
     * @return
     */
    public HttpHeaders addHeads(HttpHeaders headers) {
        return headers;
    }


    /**
     * 是否使用post请求
     * //TODO 现在先只支持get和post请求 ,其他请求以后优化 2020-5-11
     *
     * @return
     */
    public abstract RequestLevel getRequestLevel();

    /**
     * 开始请求
     */
    public abstract void onStart();

    /**
     * 服务器返回的json统一处理类
     *
     * @return
     */
    public abstract TypeReference getResponseType();

    /**
     * 在将返回值转换成对应的model之前,对返回值进行一些处理
     * 第一次处理之后
     * 第二次处理之前
     */
    public abstract Object handleRsponseBeforeTransform(Object rsp);


    /**
     * json统一处理完之后第二次处理为详细model
     *
     * @return
     */
    public abstract TypeReference getDatatype();

    /**
     * 在将返回值转换成对应的model后,对返回值进行一些处理
     * 完成了第二次处理
     */
    public abstract Object handleRsponseAfterTransform(Object rsp);

    /**
     * 发起请求
     *
     * @param listener
     * @return
     */
    public abstract void call(ApiCallBack listener);

    /**
     * 错误处理
     *
     * @param errorCode
     * @param errorMsg
     */
    public abstract void handleError(int errorCode, String errorMsg);

    public abstract void onFinsh();

    /**
     * 取消请求
     */
    public abstract void cancelRequest();

}