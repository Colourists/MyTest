package com.gamecat.baselib.core.network;

import android.util.Log;


import com.bldby.baselibrary.R;
import com.bldby.baselibrary.app.GlobalUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.XPopupCallback;

import java.util.HashMap;

/**
 * Created by bch on 2020/5/15
 */
public class HttpLoadingUtil {
    /**
     * 用到的地方的数量
     */
    private static int usefulCount = 0;
    private static LoadingPopupView loadingPopup;
    private static HashMap<String, BaseApiRequest> map = new HashMap<>();

    //TODO 加载条 添加退出按钮 ,点击取消所有请求 2020-5-15

    synchronized private static void setLoadingViewShowWithContent(BaseApiRequest baseApiRequest, Boolean show, String content) {
        if (show) {
            usefulCount++;
            if (loadingPopup == null) {
                loadingPopup = (LoadingPopupView) new XPopup.Builder(GlobalUtil.getCurrentActivity())
                        .hasShadowBg(false)
                        .dismissOnTouchOutside(false)
                        .dismissOnBackPressed(false)
                        .setPopupCallback(new XPopupCallback() {
                            @Override
                            public void onCreated() {

                            }

                            @Override
                            public void beforeShow() {

                            }

                            @Override
                            public void onShow() {

                            }

                            @Override
                            public void onDismiss() {
//TODO 感觉取消的逻辑有问题，先不用，等什么时候有这需求在试试，测试测试
                                if (map != null && !map.isEmpty()) {
                                    for (String key : map.keySet()) {
                                        map.get(key).cancelRequest();
                                    }
                                    map.clear();
//                                    if (map.containsKey(baseApiRequest.requestTag)) {
//                                        map.remove(baseApiRequest.requestTag);
//                                        baseApiRequest.cancelRequest();
//                                    }
                                    //TODO 加载条 添加退出按钮 ,点击取消所有请求 2020-5-15
                                }
                            }

                            @Override
                            public boolean onBackPressed() {
                                return false;
                            }
                        })
                        .popupAnimation(PopupAnimation.NoAnimation)
                        .asLoading()
                        .bindLayout(R.layout.layout_loading_view)
                        .show();
            }

            if (baseApiRequest != null) {
                if (!map.containsKey(baseApiRequest.requestTag)) {
                    map.put(baseApiRequest.requestTag, baseApiRequest);
                }
            }
        } else {
            usefulCount--;
            if (usefulCount <= 0 && loadingPopup != null) {
                loadingPopup.delayDismissWith(600, new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                loadingPopup = null;
                //取消进度条取消展示,清空
                if (baseApiRequest != null) {
                    // TODO 这里需要取消请求，如果要的话
                    if (!map.isEmpty()) {
                        map.clear();
                    }
                }
            } else {
                if (baseApiRequest != null) {
                    if (map.containsKey(baseApiRequest.requestTag)) {
                        map.remove(baseApiRequest.requestTag);
                        baseApiRequest.cancelRequest();
                    }
                }
            }

        }


    }

    public static void setLoadingViewShow(BaseApiRequest baseApiRequest, Boolean show) {
        Log.e("TAG", "setLoadingViewShow: " + baseApiRequest.requestTag + "..." + show);
        setLoadingViewShowWithContent(baseApiRequest, show, "加载中");
    }

}
