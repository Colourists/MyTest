package com.gamecat.baselib.core.analyze;

import android.content.Context;
import android.util.Log;

import com.bldby.baselibrary.AppidManifests;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.statistics.common.DeviceConfig;

//import com.umeng.message.IUmengRegisterCallback;
//import com.umeng.message.PushAgent;

/**
 * Created by bch on 2020/5/16
 */
public class UMengAnalyze {
    private static UMengAnalyze UMengAnalyze = new UMengAnalyze();
    private String TAG = "UMengAnalyze";
    //    eviceToken是推送消息的唯一标志
    private String deviceToken;

    public static UMengAnalyze getInstance() {
        return UMengAnalyze;
    }

    public void init(Context context) {


        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(context, AppidManifests.UMENGAPPKEY, AppidManifests.Channel, UMConfigure.DEVICE_TYPE_PHONE, AppidManifests.UMENGMessageSecret);

        // 选用AUTO页面采集模式

        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
//        UMConfigure.init(context, APPKEY, Channel, UMConfigure.DEVICE_TYPE_PHONE, MessageSecret);
//        getTestDeviceInfo(context);
        //获取消息推送代理示例
//        PushAgent mPushAgent = PushAgent.getInstance(context);
//
//
////注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//            @Override
//            public void onSuccess(String deviceToken) {
//                UMengAnalyze.this.deviceToken = deviceToken;
//                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
//                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
//            }
//        });


    }

    /**
     * 获取手机信息,用于配测试手机
     *
     * @param context
     * @return
     */
    public String getTestDeviceInfo(Context context) {
        Log.e(TAG, "getTestDeviceInfo: ");

        DeviceInfoUmeng deviceInfoUmeng = new DeviceInfoUmeng();
        try {
            if (context != null) {
                deviceInfoUmeng.device_id = DeviceConfig.getDeviceIdForGeneral(context);
                deviceInfoUmeng.mac = DeviceConfig.getMac(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        String s = gson.toJson(deviceInfoUmeng);
        Log.e(TAG, "getTestDeviceInfo: " + s);
        return s;

    }

    /**
     * 设置组件化的Log开关
     * 参数: boolean 默认为false，如需查看LOG设置为true
     */
    public void openLog(boolean isOPen) {
        UMConfigure.setLogEnabled(isOPen);
    }
}
