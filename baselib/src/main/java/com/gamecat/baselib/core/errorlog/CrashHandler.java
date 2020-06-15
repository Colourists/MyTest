package com.gamecat.baselib.core.errorlog;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bch on 2020/5/29
 * 全局错误处理
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    //系统默认的UncaughtException处理类

    public static final String LOG_PATH = "/sdcard/bldby/crash/";

    volatile private static CrashHandler sCrashHandler;
    private CrashHandlerCallBack crashHandlerCallBack;

    //系统默认的UncaughtException处理类
    private static Thread.UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler instance = null;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();


    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        if (sCrashHandler == null) {
            synchronized (CrashHandler.class) {
                if (sCrashHandler == null) {
                    //使用Application Context
                    sCrashHandler = new CrashHandler();
                    //获取系统默认的UncaughtException处理器
                    mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
                    //设置该CrashHandler为程序的默认处理器
                    Thread.setDefaultUncaughtExceptionHandler(sCrashHandler);
                }
            }
        }
        return sCrashHandler;
    }

    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    public boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (crashHandlerCallBack != null) {
                    crashHandlerCallBack.onCatchException(ex);
                }
            }
        });
        //获取userid
        collectUserInfo();
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        //打印报错日志
        Log.e(TAG, "EX : ", ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    public void collectUserInfo() {
//        infos.put("UserID", YooliAppStatisticsCollector.getUserId());
//        try {
//            if(YooliAccountController.getInstance().getAccountInfo()==null){
//                infos.put("UserID", "未登录");
//            }else{
//                infos.put("UserID", YooliAccountController.getInstance().getAccountInfo().getId() + "");
//            }
//        } catch (Exception e) {
//            Log.e(TAG, "UserID is null", e);
//            infos.put("UserID", "null");
//        }
    }

    /**
     * 保存错误信息到文件中
     * <p>
     * //     * @param ex
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCrashInfo2File(Throwable ex) {

//        Crashlytics.logException(ex);

        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        FileOutputStream fos = null;
        try {
            long timestamp = System.currentTimeMillis();
            String time = formatter.format(new Date());
            String fileName = "crash-" + time + "-" + timestamp + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(LOG_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                fos = new FileOutputStream(LOG_PATH + fileName);
                fos.write(sb.toString().getBytes("UTF-8"));
                fos.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void setCallBack(CrashHandlerCallBack callBack) {
        crashHandlerCallBack = callBack;
    }

    public interface CrashHandlerCallBack {
        public void onCatchException(Throwable ex);
    }

}