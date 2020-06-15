package com.gamecat.baselib.core.network;

import com.alibaba.fastjson.JSON;

/**
 * Created by bch on 2020/5/11
 */
public class JsonAwareObject {
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public byte[] toBytes() {
        return JSON.toJSONBytes(this);
    }
}
