package com.chinaiat.themelib.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chinaiat.themelib.ThemeLibApplication;
import com.chinaiat.themelib.domain.EventMsg;
import com.chinaiat.themelib.helper.SPHelper;
import com.chinaiat.themelib.utils.FontUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author: Bob
 * @date: 2019/5/29
 * @description: 所有子类继承自BaseActivity, 这样才能更换主题,如果AppCompatActivity不是需要的父类Activity,可以替换掉
 */
public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 改变主题Flag
     */
    protected String CHANGE_THEME = "changeTheme";
    /**
     * 改变字体Flag
     */
    protected String CHANGE_TYPEFACE = "changeTypeface";
    /**
     * 改变字体大小Flag
     */
    protected String CHANGE_TYPEFACE_SIZE = "changeTypefaceSize";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(ThemeLibApplication.mThemes[SPHelper.getTheme()]);
        setContentView(getLayoutId());
        bindButterKnife();
        EventBus.getDefault().register(this);
        initData();
    }

    /**
     * 绑定ButterKnife
     */
    protected void bindButterKnife() {

    }

    /**
     * 得到布局文件
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化数据
     */
    protected void initData() {

    }

    /**
     * 如果有切换主题,就重启Activity
     */
    public void restartActivity(Intent intent) {
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        //覆盖activity动画效果
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 重写字体缩放比例 api<25
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N) {
            Configuration config = res.getConfiguration();
            config.fontScale = SPHelper.getTypefaceSize();//1 设置正常字体大小的倍数
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return res;
    }

    /**
     * 重写字体缩放比例  api>25
     */
    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            final Resources res = newBase.getResources();
            final Configuration config = res.getConfiguration();
            config.fontScale = SPHelper.getTypefaceSize();//1 设置正常字体大小的倍数
            final Context newContext = newBase.createConfigurationContext(config);
            super.attachBaseContext(newContext);
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        String typefacePath = SPHelper.getTypefacePath();
        if (!SPHelper.MONOSPACE.equals(typefacePath)) {
            View decorView = getWindow().getDecorView();
            FontUtil.getInstance().replaceFontFromAsset(decorView, typefacePath);
        }
    }

    /**
     * 使用EventBus通信改变页面的主题,字体,字体大小
     *
     * @param eventMsg
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStyleChange(EventMsg eventMsg) {

    }
}
