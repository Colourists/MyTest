package com.gamecat.baselib.core.load;

import com.bldby.baselibrary.R;
import com.bldby.baselibrary.app.GlobalUtil;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.impl.LoadingPopupView;

/**
 * Created by bch on 2020/5/15
 * 加载进度圈旋转
 */
public class LoadingUtil {
    /**
     * 用到的地方的数量
     */
    private static int usefulCount = 0;
    private static LoadingPopupView loadingPopup;


    synchronized public static void setLoadingViewShowWithContent(Boolean show, String content) {
        if (show) {
            usefulCount++;
            if (loadingPopup == null) {
                loadingPopup = (LoadingPopupView) new XPopup.Builder(GlobalUtil.getCurrentActivity())
                        .hasShadowBg(false)
                        .dismissOnTouchOutside(false)
                        .popupAnimation(PopupAnimation.NoAnimation)
                        .asLoading()
                        .bindLayout(R.layout.layout_loading_view)
                        .show();
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
            }
        }
    }

//    public static void setLoadingViewShow(Boolean show, String c) {
//        setLoadingViewShowWithContent(show, c);
//    }

    public static void setLoadingViewShow(Boolean show) {
        setLoadingViewShowWithContent(show, "加载中");
    }

}
