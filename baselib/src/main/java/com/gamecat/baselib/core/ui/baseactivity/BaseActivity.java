package com.gamecat.baselib.core.ui.baseactivity;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gamecat.baselib.R;
import com.gamecat.baselib.core.ui.basefragment.Basefragment;
import com.gamecat.baselib.core.util.ToastUtil;

import me.yokeyword.fragmentation.SupportActivity;


public abstract class BaseActivity extends SupportActivity implements NavigationCallback {

    //获取数据三种方式
    //1.通过Autowired注解表明key   &  需要在onCreate中调用ARouter.getInstance().inject(this);配合使用
//    @Autowired(name = "key1")
//    public long data;
    //2.通过Autowired注解 & 将key1作为属性的名称   &  需要在onCreate中调用ARouter.getInstance().inject(this);配合使用
//    @Autowired()
//    public long key1;
    //3.通过Bundle获取
//    getIntent().getExtras().getLong("key1")

    // 获取Fragment
//    Fragment fragment = (Fragment) ARouter.getInstance().build("/test/fragment").navigation();
//    @Route(path = RouteConstants.APPMAIN, extras = RouteLoginConstants.SHOWCHECKLOGIN)
    //当extras = RouteLoginConstants.SHOWCHECKLOGIN时
    LoadingPopupView loadingPopup;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        GlobalUtil.setCurrentActivity(this);
        if (getOpenImmersionBar() != null) {
            getOpenImmersionBar();
        }
        bindIngView();
        initView();
        initListener();
        loadData();

//        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GlobalUtil.setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loadingPopup = null;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        if (getOpenImmersionBar() != null) {
            getOpenImmersionBar().init();
        }
        return super.onCreateView(parent, name, context, attrs);
    }

    public abstract void bindIngView();

    public abstract void initView();

    public abstract void loadData();

    public abstract void initListener();

    public ImmersionBar getOpenImmersionBar() {
        return ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(R.color.white)
                .statusBarColor(R.color.white)
                .navigationBarDarkIcon(true);
    }


    protected void showloadDialog(String title) {
        if (loadingPopup == null) {
            loadingPopup = (LoadingPopupView) new XPopup.Builder(this)
                    .hasShadowBg(false)
                    .popupAnimation(PopupAnimation.NoAnimation)
                    .dismissOnBackPressed(true)
                    .dismissOnTouchOutside(true)
                    .asLoading()
                    .bindLayout(R.layout.layout_loading_view);

        }
        loadingPopup.show();

    }

    protected void goneloadDialog() {
        if (null != loadingPopup) {
            loadingPopup.delayDismissWith(600, new Runnable() {
                @Override
                public void run() {
                }
            });
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration newConfig = new Configuration();
        //控制字体缩放 1.0为默认
        newConfig.fontScale = 1.0f;
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //7.0以上系统手机 显示大小 对APP的影响
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                if (displayMetrics.density < DisplayMetrics.DENSITY_DEVICE_STABLE / (float) DisplayMetrics.DENSITY_DEFAULT) {
                    displayMetrics.densityDpi = (int) (DisplayMetrics.DENSITY_DEVICE_STABLE * 0.92);
                } else {
                    displayMetrics.densityDpi = DisplayMetrics.DENSITY_DEVICE_STABLE;
                }
                newConfig.densityDpi = displayMetrics.densityDpi;
            }
            createConfigurationContext(newConfig);
        }
        res.updateConfiguration(newConfig, displayMetrics);
        return res;
    }

    public void start(String url) {
        // 使用两个参数的navigation方法，可以获取单次跳转的结果
        ARouter.getInstance()
                .build(url)
                .navigation(this, this);

    }

    /**
     * 设置传递参数
     *
     * @param url
     * @param bundle
     */
    public void start(String url, Bundle bundle) {
        Postcard build = ARouter.getInstance().build(url);
        build.with(bundle);
        build.navigation(this, this);
    }

    /**
     * 退出所有activity到mainActivity
     */
    public void popToRoot() {
        ARouter.getInstance()
                .build(RouteConstants.APPMAIN)
                .navigation(this, this);
    }


    /**
     * 自定义参数 需要手动调用 .navigation(this, this)
     * ARouter.getInstance().build("/test/1")
     * .withLong("key1", 666L)
     * .withString("key3", "888")
     * .withSerializable("key4", new Test("Jack", "Rose"))
     * .withObject("map", map)
     * .navigation();
     * 也可直接获取fragment
     * 最后可以跳转时一定要使用navigation(this, this)才可以获取回调
     *
     * @param url
     * @return
     */
    public Postcard startWith(String url) {
        Postcard build = ARouter.getInstance().build(url);
        return build;
    }

    public Basefragment getFragment(String url) {
        // 获取Fragment
        Basefragment fragment = (Basefragment) ARouter
                .getInstance()
                .build(url)
                .navigation(this, this);
        return fragment;
    }

    /**
     * 设置传递参数
     *
     * @param url
     * @param bundle
     */
    public Basefragment getFragmentWithBundle(String url, Bundle bundle) {
        Basefragment fragment = (Basefragment) ARouter
                .getInstance()
                .build(url)
                .with(bundle)
                .navigation(this, this);
        return fragment;
    }


    //跳转成功返回
    @Override
    public void onFound(Postcard postcard) {

    }

    //找不到的时候回调
    @Override
    public void onLost(Postcard postcard) {
        ToastUtil.show(getString(R.string.nofoundView));
    }

    //跳转结束
    @Override
    public void onArrival(Postcard postcard) {

    }

    //被拦截
    @Override
    public void onInterrupt(Postcard postcard) {
        ToastUtil.show("被拦截");
    }
}
