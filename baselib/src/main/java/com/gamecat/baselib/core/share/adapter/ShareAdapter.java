package com.gamecat.baselib.core.share.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bldby.baselibrary.R;
import com.bldby.baselibrary.core.share.ShareMenu;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class ShareAdapter extends BaseQuickAdapter<ShareMenu, BaseViewHolder> {
    public ShareAdapter(@Nullable List<ShareMenu> data) {
        super(R.layout.item_share, data);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, ShareMenu item) {
        switch (item) {
            case Url:
                Glide.with(mContext)
                        .load(R.mipmap.share_url)
                        .into((ImageView) helper.getView(R.id.img));
                helper.setText(R.id.title, R.string.share_copy_url);
                break;
            case poster:
                Glide.with(mContext)
                        .load(R.mipmap.share_poster)
                        .into((ImageView) helper.getView(R.id.img));
                helper.setText(R.id.title, R.string.share_poster);
                break;
            case WEIXIN:
                Glide.with(mContext)
                        .load(R.mipmap.share_wx)
                        .into((ImageView) helper.getView(R.id.img));
                helper.setText(R.id.title, R.string.share_wx);
                break;
            case WEIXIN_CIRCLE:
                Glide.with(mContext)
                        .load(R.mipmap.share_wx_ci)
                        .into((ImageView) helper.getView(R.id.img));
                helper.setText(R.id.title, R.string.share_wx_ci);
                break;
            default:
                break;
        }

    }
}
