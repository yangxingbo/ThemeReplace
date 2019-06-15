package com.chinaiat.bob.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.chinaiat.bob.R;
import com.chinaiat.bob.db.SPHelper;

import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date: 2019/5/29
 * @description: 必须子类继承自BaseActivity, 这样才能更换主题
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!SPHelper.getTheme(this)) {
            setTheme(R.style.AppTheme);
        } else {
            setTheme(R.style.AppThemeNight);
        }
        setContentView(getLayoutId());
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            if (!SPHelper.getTheme(this)) {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            } else {
                window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDarkNight));
            }
        }
        mContext = this;
        ButterKnife.bind(this);
        initData();
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

    protected void showToastShort(String message){
        Toast.makeText(BaseActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 如果有切换主题,就重启Activity
     */
    protected void restartActivity() {
        finish();
        startActivity(getIntent());
    }
}
