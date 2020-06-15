package com.gamecat.baselib.core.log;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;

import com.alibaba.fastjson.JSON;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bch on 2020/5/15
 */
public class LogUtil {
    public static boolean isShowView = false;

    public static void v(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.v(tag, "msg is null");
        } else {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.d(tag, "msg is null");
        } else {
            Log.d(tag, msg);
        }
    }


    public static void i(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.i(tag, "msg is null");
        } else {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {
            Log.w(tag, "msg is null");
        } else {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        // if(BuildConfig.DEBUG)
        if (TextUtils.isEmpty(msg)) {
            Log.d(tag, "msg is null");
        } else {
            Log.e(tag, msg);
        }
    }


    public static void checkLog(String s) {
        if (isShowView) {

        }
    }

    public static void d(String tag, Object obj) {
        String msg = JSON.toJSONString(obj);
        if (TextUtils.isEmpty(msg)) {
            Log.d(tag, "msg is null");
        } else {
            Log.d(tag, msg);
        }
    }

    public static final void log(Context context, String line) {
        if (context == null || line == null)
            return;
        if (!line.endsWith("\n")) {
            line += '\n';
        }
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput("log",
                    Context.MODE_APPEND);
            fos.write(line.getBytes());
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public static String desc(MotionEvent event) {
        if (event == null)
            return "null";
        String log = null;
        int action = event.getAction();
        int pointers = event.getPointerCount();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                log = "down";
                break;
            case MotionEvent.ACTION_CANCEL:
                log = "cancel";
                break;
            case MotionEvent.ACTION_MOVE:
                log = "move";
                break;
            case MotionEvent.ACTION_OUTSIDE:
                log = "outside";
                break;
            case MotionEvent.ACTION_UP:
                log = "up";
                break;

            default:
                log = "other";
                break;
        }
        log += " pointers " + pointers;
        return log;
    }

    public static class KeyValueBuilder {
        private StringBuilder builder = null;

        public KeyValueBuilder() {
            builder = new StringBuilder();
        }

        public KeyValueBuilder append(String key, String value) {
            builder.append(key + "-" + value + " | ");
            return this;
        }

        public KeyValueBuilder append(String key, int value) {
            builder.append(key + "-" + value + " | ");
            return this;
        }

        public KeyValueBuilder append(String key, boolean value) {
            builder.append(key + "-" + value + " | ");
            return this;
        }

        public String build() {
            return builder.toString();
        }

        public String toString() {
            return build();
        }
    }

}
