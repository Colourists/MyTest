package com.gamecat.baselib.core.ui.baseactivity;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.ColorRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bldby.baselibrary.R;
import com.bldby.baselibrary.core.util.StringUtils;
import com.bldby.baselibrary.databinding.ActivityBaseUiBinding;

/**
 * Created by bch on 2020/5/30.
 */
public abstract class BaseUiActivity extends BaseActivity {
    /**
     * 代替Context
     */
    protected Context mContext;
    private ActivityBaseUiBinding viewDataBinding;

    @Override
    public void bindIngView() {
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_base_ui);
        viewDataBinding.setViewmodel(this);
        viewDataBinding.llContent.addView(initContentView(getLayoutInflater(), viewDataBinding.llContent));
        mContext = BaseUiActivity.this;
    }

    /**
     * 取消顶部颜色交由子页面控制
     * @param color
     */
    public void setTitleBackground(@ColorRes int color) {
        viewDataBinding.titleBackground.setBackgroundResource(color);
    }

    /**
     * 取消顶部的45dp,交由子页面控制
     *
     * @param isShow
     */
    public void setGoneTitleBar(boolean isShow) {
        if (isShow) {
            viewDataBinding.titleBar.setVisibility(View.VISIBLE);
        } else {
            viewDataBinding.titleBar.setVisibility(View.GONE);
        }
    }

    public void onClickBack(View v) {
        finish();
    }

    //容器
    protected abstract View initContentView(LayoutInflater layoutInflater, ViewGroup viewGroup);


    /**
     * 创建一个titlebar
     */
    protected void initTitle(String title) {
        if (!StringUtils.isEmpty(title)) {
            viewDataBinding.titleName.setText(title);
        } else {
            viewDataBinding.titleName.setText("");
        }
    }


}
