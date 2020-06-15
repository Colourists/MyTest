package com.gamecat.baselib.core.log;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.bldby.baselibrary.core.util.DateUtil;

/**
 * Created by bch on 2020/5/15
 */
public class HttpLogUtil {
    public static boolean isShowView = false;
    private static String tag = "HttpLog";

    /**
     * 在弹窗里展示的时候加上头
     *
     * @return
     */
    private String getLogHeader() {
        long l = System.currentTimeMillis();
        String s = DateUtil.formatDateTopic1(l);
        return s;
    }

    public static void v(String ApiName, String msg) {
        LogUtil.v(tag, msg);
    }

    public static void d(String ApiName, String msg) {
        LogUtil.d(tag, msg);
    }

    public static void i(String ApiName, String msg) {
        LogUtil.i(tag, msg);

    }

    public static void w(String ApiName, String msg) {
        LogUtil.w(tag, msg);

    }

    public static void e(String ApiName, String msg) {
        LogUtil.e(tag, ApiName + "    " + msg);
    }

    public static void d(String tag, Object obj) {
        String msg = JSON.toJSONString(obj);
        if (TextUtils.isEmpty(msg)) {
            Log.d(tag, "msg is null");
        } else {
            Log.d(tag, msg);
        }
    }
}
