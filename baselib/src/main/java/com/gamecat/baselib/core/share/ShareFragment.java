package com.gamecat.baselib.core.share;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bldby.baselibrary.R;
import com.bldby.baselibrary.constants.RouteConstants;
import com.bldby.baselibrary.core.share.adapter.ShareAdapter;
import com.bldby.baselibrary.core.ui.basefragment.Basefragment;
import com.bldby.baselibrary.core.util.ShareImageUtils;
import com.bldby.baselibrary.core.util.ToastUtil;
import com.bldby.baselibrary.databinding.FragmentShareBinding;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Route(path = RouteConstants.APPShare)
public class ShareFragment extends Basefragment {
    private FragmentShareBinding shareBinding;
    private List<ShareMenu> shareMenus;
    private ShareAdapter shareAdapter;
    private Bitmap bitmap;
    public int type = 0;
    private ShareUtils shareUtils;
    private String httpUrl;
    /**
     * 获取图片
     */
//    Bitmap bitmap = ShareImageUtils.viewToBitmap(dataBinding.titleBackground);

    /**
     * 设置分享目标默认全部
     *
     * @param type
     */
    public void setShareClassify(ShareMenu... type) {
        shareMenus = Arrays.asList(type);
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shareBinding = FragmentShareBinding.inflate(inflater, container, false);
        shareBinding.setViewModel(this);
        return shareBinding.getRoot();
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        shareBinding.root.setBackgroundColor(getResources().getColor(R.color.transparent_50));
    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        shareBinding.root.setBackgroundColor(getResources().getColor(R.color.transparent));

    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }

    @Override
    public void initView() {
        shareUtils = new ShareUtils(getActivity());
        shareUtils.setListener(new UMShareListener() {
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
        });
        shareAdapter = new ShareAdapter(null);
        shareBinding.item.setAdapter(shareAdapter);
    }

    @Override
    public void loadData() {
        if (shareMenus != null && shareMenus.size() > 0) {
            shareAdapter.setNewData(shareMenus);
        } else {
            shareMenus = new ArrayList<>();
            shareMenus.add(ShareMenu.WEIXIN_CIRCLE);
            shareMenus.add(ShareMenu.WEIXIN);
            shareMenus.add(ShareMenu.poster);
            shareMenus.add(ShareMenu.Url);
            shareAdapter.setNewData(shareMenus);
        }
        shareAdapter.notifyDataSetChanged();
    }

    @Override
    public void initListener() {
        shareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                ShareMenu shareMenu = shareMenus.get(position);
                switch (shareMenu) {
                    case Url:
                        //获取剪贴板管理器：
                        ClipboardManager cm = (ClipboardManager) getActivity().getSystemService(getContext().CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
                        ClipData mClipData = ClipData.newPlainText("Label", httpUrl);
// 将ClipData内容放到系统剪贴板里。
                        cm.setPrimaryClip(mClipData);
                        ToastUtil.show("复制链接成功");
                        break;
                    case poster:
                        ToastUtil.show("制作海报");
//TODO 逻辑待定
                        break;
                    case WEIXIN:
                        if (type == 0) {
                            if (bitmap == null) {
                                ToastUtil.show("图片为空");
                                return;
                            }
                            shareUtils.shareImg(bitmap, getString(R.string.app_name), SHARE_MEDIA.WEIXIN);
                        } else if (type == 1) {
                            shareUtils.shareText(getString(R.string.app_name), SHARE_MEDIA.WEIXIN);
                        }
                        break;
                    case WEIXIN_CIRCLE:
                        if (type == 0) {
                            if (bitmap == null) {
                                ToastUtil.show("图片为空");
                                return;
                            }
                            shareUtils.shareImg(bitmap, getString(R.string.app_name), SHARE_MEDIA.WEIXIN_CIRCLE);
                        } else if (type == 1) {
                            shareUtils.shareText(getString(R.string.app_name), SHARE_MEDIA.WEIXIN);
                        }
                        break;
                    default:
                        break;
                }


            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
}
