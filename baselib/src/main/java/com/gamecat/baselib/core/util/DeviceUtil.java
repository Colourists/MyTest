package com.gamecat.baselib.core.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.bldby.baselibrary.core.log.LogUtil;

import java.security.SecureRandom;
import java.util.UUID;


public class DeviceUtil {
    private static final String DEVICE_ID = "aa";

    private static String deviceID = null;

    public static final String getDeviceID(Context context) {
        synchronized (DeviceUtil.class) {
            if (deviceID != null)
                return deviceID;
            SharedPreferences sharedPreferences = context.getSharedPreferences(
                    SharedPreferenceHelper.RESERVED_PREFERENCE_DEVICE,
                    Context.MODE_PRIVATE);
            deviceID = sharedPreferences.getString(DEVICE_ID, null);
            if (deviceID != null) {
                deviceID = new String(Base64.decode(deviceID, Base64.DEFAULT));

                return deviceID;
            }
            LogUtil.log(context, DateUtil.formatDate(System.currentTimeMillis())
                    + " operation 0x0b00");
            //添加时间戳,随机数和进程号,
            deviceID = getDeviceIDInner(context);
            deviceID = StringUtil.md5(deviceID);

            Editor editor = sharedPreferences.edit();
            editor.putString(
                    DEVICE_ID,
                    new String(Base64.encode(deviceID.getBytes(),
                            Base64.DEFAULT)));

            editor.commit();
            return deviceID;
        }
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static final String getTelephonyDeviceId(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }

    public static final int getDeviceHeight(Context context) {

        /** 另一种实现方式
         *
         DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
         displayMetrics.widthPixels;
         * */

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int result = 0;
        int resourceId = activity.getResources()
                .getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = activity.getResources().getDimensionPixelSize(resourceId);
        }
        int screenHeight = dm.heightPixels - result;
        return screenHeight;
    }
    public static final int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static final int getScreenScale(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        if (dm.densityDpi < 160)
            return 1;
        return (dm.densityDpi / 160);
    }

    private static final String getDeviceIDInner(Context context) {
        String deviceID = "";
        if (Build.VERSION.SDK_INT < 23) {
            TelephonyManager telephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            deviceID = telephonyManager.getDeviceId();
            if (!StringUtil.isEmptyString(deviceID)) {
                return deviceID;
            }
            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            try {
                String mac = wifiManager.getConnectionInfo().getMacAddress();
                if (!StringUtil.isEmptyString(mac))
                    return mac;
            } catch (Exception e) {
                e.printStackTrace();
            }

            String androidID = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
            if (!StringUtil.isEmptyString(androidID))
                return androidID;
        }

        String uuid = UUID.randomUUID().toString() + System.currentTimeMillis() + "" + new SecureRandom().nextLong() + "" + android.os.Process.myPid();
        return uuid;
    }

    public static final String getDeviceInfo() {
        LogUtil.KeyValueBuilder b = new LogUtil.KeyValueBuilder();

        b.append("Build.BOARD", Build.BOARD);
        b.append("Build.BRAND", Build.BRAND);
        b.append("Build.CPU_ABI", Build.CPU_ABI);
        b.append("Build.DEVICE", Build.DEVICE);
        b.append("Build.DISPLAY", Build.DISPLAY);
        b.append("Build.FINGERPRINT", Build.FINGERPRINT);
        b.append("Build.HOST", Build.HOST);
        b.append("Build.ID", Build.ID);
        b.append("Build.MANUFACTURER", Build.MANUFACTURER);
        b.append("Build.MODEL", Build.MODEL);
        b.append("Build.PRODUCT", Build.PRODUCT);
        b.append("Build.TAGS", Build.TAGS);
        b.append("Build.USER", Build.USER);
        b.append("Build.VERSION.CODENAME", Build.VERSION.CODENAME);
        b.append("Build.VERSION.INCREMENTAL", Build.VERSION.INCREMENTAL);
        b.append("Build.VERSION.RELEASE", Build.VERSION.RELEASE);
        b.append("Build.VERSION.SDK_INT", Build.VERSION.SDK_INT);

        return b.toString();
    }

    public static final String getDeviceName() {
        StringBuilder stringBuilder = new StringBuilder();
        String deviceBrand = Build.BRAND;
        if (deviceBrand == null) {
            deviceBrand = Build.MANUFACTURER;
        }
        if (deviceBrand == null) {
            deviceBrand = "Android";
        }

        String deviceModel = Build.MODEL;
        if (deviceModel == null) {
            deviceModel = "";
        }

        String release = Build.VERSION.RELEASE;
        if (release == null) {
            release = "sdk " + Build.VERSION.SDK_INT;
        }

        stringBuilder.append(deviceBrand);
        stringBuilder.append(" ");
        stringBuilder.append(deviceModel);
        return stringBuilder.toString();
    }

    public static final String getOSV() {
        StringBuilder stringBuilder = new StringBuilder();

        String release = Build.VERSION.RELEASE;
        if (release == null) {
            release = "sdk " + Build.VERSION.SDK_INT;
        }

        String osvByBrand = Build.HOST;

        stringBuilder.append(release);
        stringBuilder.append(" ");
        stringBuilder.append(osvByBrand);
        //用户系统版本返回localhost 微信会屏蔽
        return stringBuilder.toString().replace("localhost","unknown");
    }

    /** 获取状态条高度 */
    public static int getStatusBarHeight(Context context){
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return context.getResources().getDimensionPixelSize(resourceId);
        }
        return dp2px(context,25);
    }

    /** 修改状态条颜色 */
    public static void setStatusBarBackgroundColor(Activity context, int color) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            context.getWindow().setStatusBarColor(color);
            context.getWindow().setNavigationBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            setARGBStatusViewToAct(mainActivity, r, g, b, alpha);
        }
    }

    public static boolean isEmulator() {
        final String brand = Build.BRAND;
        if (brand != null && brand.startsWith("generic")) {
            return true;
        }

        final String fingerprint = Build.FINGERPRINT;
        if (fingerprint != null && fingerprint.startsWith("generic")) {
            return true;
        }

        final String model = Build.MODEL;
        if (model != null) {
            if (model.startsWith("genymotion")
                    || model.startsWith("Genymotion")) {
                return true;
            }
            if (model.contains("sdk") || model.contains("SDK")) {
                return true;
            }
        }

        return false;
    }

    public static String getEmulatorName() {
        if (isEmulator()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Build.BRAND);
            stringBuilder.append(" ");
            stringBuilder.append(Build.DISPLAY);
            return stringBuilder.toString();
        }
        return null;
    }

    public static void putUuid(Context context, String uuid) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                SharedPreferenceHelper.RESERVED_PREFERENCE_DEVICE,
                Context.MODE_PRIVATE);
        //加密uuid
        uuid = StringUtil.md5(uuid);
        //static 的deviceID 会保存上一次的值并直接返回，需要重新赋值
        deviceID = uuid;
        Editor editor = sharedPreferences.edit();
        editor.putString(
                DEVICE_ID,
                new String(Base64.encode(uuid.getBytes(),
                        Base64.DEFAULT)));
        editor.commit();
    }
}
