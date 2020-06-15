package com.gamecat.baselib.core.network;

import com.bldby.baselibrary.core.log.HttpLogUtil;
import com.lzy.okgo.model.HttpParams;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by bch on 2020/5/11
 * 网络请求参数对象
 */
public class ParamsBuilder {
    private Map<String, Object> params = new LinkedHashMap<String, Object>();

    public ParamsBuilder append(String key, Object value) {
        params.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return params;
    }

    public static HttpParams getHttpParams(Map<String, Object> params) {
        HttpParams httpParams = new HttpParams();
//        Map<String, Object> mparams = appendSignature(params);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (entry.getValue() instanceof File) {
                if (entry.getValue() != null) {
                    httpParams.put(entry.getKey(), (File) entry.getValue());
                }
            } else if (entry.getValue() instanceof List) {
                List value = (List) entry.getValue();
                if (!value.isEmpty() && value.get(0) instanceof File) {
                    // MyType object
                    httpParams.putFileParams(entry.getKey(), value);
                } else {
                    httpParams.put(entry.getKey(), getStringValue(entry.getValue()));
                    HttpLogUtil.e("HttpParams", "如果请求出错,且出现本日志,请查看请求参数和设置的是否一样,或者联系我修改代码");
                }

            } else {
                httpParams.put(entry.getKey(), getStringValue(entry.getValue()));//
            }
        }
        return httpParams;
    }

    private static String getStringValue(Object value) {
        if (value != null) {
            return value.toString();
        }
        return "";
    }
}
