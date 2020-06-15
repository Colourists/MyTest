package com.gamecat.baselib.core.share;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;

import com.bldby.baselibrary.core.util.ToastUtil;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.util.List;

public class ShareUtils {
    private UMShareListener listener;
    private Activity activity;

    public ShareUtils(Activity activity) {
        this.activity = activity;
    }

    public UMShareListener getListener() {
        return listener;
    }

    public void setListener(@Nullable UMShareListener listener) {
        this.listener = listener;
    }

    public void shareImg(Bitmap bitmap, String text, SHARE_MEDIA share_media) {
        requestPermission(bitmap, text, share_media);
    }

    public void shareText(String text, SHARE_MEDIA share_media) {
        requestPermission(null, text, share_media);
    }

    private void requestPermission(Bitmap bitmap, String text, SHARE_MEDIA share_media) {
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE};
        XXPermissions.with(activity)
                // 可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                //.constantRequest()
                // 支持请求6.0悬浮窗权限8.0请求安装权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 不指定权限则自动获取清单中的危险权限
                .permission(mPermissionList)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean all) {
                        if (all) {
                            doShare(bitmap, text, share_media);
                        } else {
                            ToastUtil.show("获取权限成功，部分权限未正常授予");
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        if (quick) {
                            ToastUtil.show("被永久拒绝授权，请手动授予权限");
                            //如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.gotoPermissionSettings(activity);
                        } else {
                            ToastUtil.show("获取权限失败");
                        }
                    }
                });
    }

    public void doShare(Bitmap bitmap, String text, SHARE_MEDIA share_media) {
        ShareAction shareAction = new ShareAction(activity);
        if (bitmap != null) {
            UMImage image = new UMImage(activity, bitmap);//网络图片
            shareAction.withMedia(image);
            shareAction.withText(text);
        } else {
            shareAction.withText(text);
        }
        shareAction.setDisplayList(share_media)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        ToastUtil.show("分享成功");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.show("分享失败");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.show("取消分享");
                    }
                }).share();
    }
}
