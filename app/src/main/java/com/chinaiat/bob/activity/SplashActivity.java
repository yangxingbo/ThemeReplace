package com.chinaiat.bob.activity;

import android.content.Intent;
import android.os.Handler;

import com.chinaiat.bob.R;
import com.chinaiat.themelib.base.BaseActivity;

/**
 * @author: Bob
 * @date :2019/6/18 10:59
 */
public class SplashActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        }, 2000);
    }
}
