package com.gamecat.baselib.core.network;

/**
 * Created by bch on 2020/5/11
 */
public class BaseApiResponse extends JsonAwareObject {
    /**
     * 返回的数据(剔除公共的一些key后的数据)
     */
    public Object getData() {
        return "";
    }

    /**
     * 后台返回的状态码
     */
    public int getCode() {
        return 0;
    }

    /**
     * 请求结果信息
     */
    public String getMessage() {
        return "";
    }

    /**
     * 请求成功,通过判断后台返回的code判断是不是异常流
     */
    public boolean isRequestSuccess() {
        return true;
    }

}