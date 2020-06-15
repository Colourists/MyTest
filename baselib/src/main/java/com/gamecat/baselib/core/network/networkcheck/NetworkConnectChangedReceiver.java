package com.gamecat.baselib.core.network.networkcheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by bch on 2020/5/11
 */

public class NetworkConnectChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "NetworkConnect";
    //网络状态
    private NetWorkState networkStatus = NetWorkState.Null;

    public NetworkChangedListener listener;

    public static NetworkConnectChangedReceiver receiver;
    //================== NetworkChangedListener ====================

    public interface NetworkChangedListener {
        public void onNetworkChanged(NetWorkState networkStatus);
    }

    //================== static method ====================

    /**
     * 添加网络监听
     *
     * @param context
     * @param listener
     */
    public static void addNetworkConnectChangedListener(Context context, NetworkChangedListener listener) {
        if (receiver == null) {
            receiver = new NetworkConnectChangedReceiver();
        }
        receiver.listener = listener;
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        context.registerReceiver(receiver, filter);

        receiver.callBack(getNetworkStatus(context));
    }

    /**
     * 移除网络监听
     *
     * @param context
     */
    public static void removeNetworkConnectChangedListener(Context context) {
        context.unregisterReceiver(receiver);
    }

    /**
     * 获取网络连接状态
     */
    public static NetWorkState getNetworkStatus(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.isConnected()) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    return NetWorkState.WIFI;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    return NetWorkState.GPRS;
                }
                return NetWorkState.VPN;
            } else {
                return NetWorkState.NONE;
            }
        } else {   // not connected to the internet
            return NetWorkState.NONE;
        }
    }

    /**
     * wifi开关是否打开
     */
    public static boolean isWifiConnected(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        if (mWifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            return true;
        }
        return false;
    }

    //================== override ====================

    @Override
    public void onReceive(Context context, Intent intent) {

        /*
        这个监听网络连接的设置,包括wifi和移动数据的打开和关闭,最好用的还是这个监听。
        wifi如果打开，关闭，以及连接上可用的连接都会接到监听。
        这个广播的最大弊端是比上边两个广播的反应要慢
        */
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            callBack(getNetworkStatus(context));
        }
    }

    //================== private method ====================

    private void callBack(NetWorkState networkStatus) {
        if (this.networkStatus != networkStatus) {
            this.networkStatus = networkStatus;
            if (listener != null) {
                listener.onNetworkChanged(networkStatus);
            }
        }
    }
}











