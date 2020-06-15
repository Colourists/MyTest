package com.gamecat.baselib.core.network.test;

import com.alibaba.fastjson.TypeReference;
import com.bldby.baselibrary.app.network.BaseRequest;
import com.bldby.baselibrary.core.network.ParamsBuilder;
import com.bldby.baselibrary.core.network.RequestLevel;


/**
 * Created by bch on 2020/5/11
 */
public class UpdataRequest extends BaseRequest {
    public String accessToken = "";
    public String userId = "";


    @Override
    public String getAPIName() {
        return "UAPDATE";
    }

    @Override
    public ParamsBuilder appendParams(ParamsBuilder builder) {
        return super.appendParams(builder.append("accessToken", accessToken)
                .append("userId", userId)
                .append("type", "1"));
    }

    @Override
    public RequestLevel getRequestLevel() {
        return RequestLevel.GET;
    }

    @Override
    public TypeReference getDatatype() {
        return null;
//        return new TypeReference<>() {
//        };
    }


    //    http://8.129.218.83:8088/fhwl/soft/newv?accessToken=5fde8fdbb42c406b96d06b9a7c3e86e1&userId=321276&type=1


}
