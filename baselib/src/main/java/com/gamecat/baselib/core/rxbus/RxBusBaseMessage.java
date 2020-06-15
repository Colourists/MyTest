package com.gamecat.baselib.core.rxbus;

/**
 * Created by gundam on 17/2/23.
 */
public class RxBusBaseMessage {
    private int code;
    private Object object;
    public RxBusBaseMessage(int code, Object object){
        this.code=code;
        this.object=object;
    }
    public RxBusBaseMessage(){}

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
