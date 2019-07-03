package com.chinaiat.bob.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.chinaiat.bob.R;
import com.chinaiat.bob.ThemeReplaceApplication;
import com.chinaiat.themelib.activity.TextSizeChangeActivity;
import com.chinaiat.themelib.activity.TypefaceChangeActivity;
import com.chinaiat.themelib.base.BaseActivity;
import com.chinaiat.themelib.domain.EventMsg;
import com.chinaiat.themelib.view.ThemeAlertDialog;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :设置页面
 */
public class SettingActivity extends BaseActivity {

    private ThemeAlertDialog alertDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void bindButterKnife() {
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
        alertDialog = new ThemeAlertDialog(this);
        alertDialog.setListener(new ThemeAlertDialog.ThemeChangeListener() {
            @Override
            public void onThemeChange() {
                ThemeReplaceApplication.IS_CHANGE_THEME = true;
                restartActivity(getIntent());
            }
        });
    }

    @OnClick({R.id.rl_change_theme, R.id.rl_chang_typeface, R.id.rl_chang_typeface_size})
    public void onItemClick(View view) {
        int id = view.getId();
        if (id == R.id.rl_change_theme) {
            alertDialog.show();
        } else if (id == R.id.rl_chang_typeface) {
            startActivity(new Intent(SettingActivity.this, TypefaceChangeActivity.class));
        } else if (id == R.id.rl_chang_typeface_size) {
            startActivity(new Intent(SettingActivity.this, TextSizeChangeActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        if (ThemeReplaceApplication.IS_CHANGE_THEME) {
            ThemeReplaceApplication.IS_CHANGE_THEME = false;
            //刷新所有界面字体
            EventBus.getDefault().postSticky(new EventMsg(CHANGE_THEME));
        }
        finish();
    }


    @Override
    public void onStyleChange(EventMsg eventMsg) {
        if (null != eventMsg) {
            String action = eventMsg.getAction();
            //主题发生改变
            if (!TextUtils.isEmpty(action) && CHANGE_THEME.equals(action)) {
//                restartActivity(getIntent());
                //字体发生改变
            } else if (!TextUtils.isEmpty(action) && CHANGE_TYPEFACE.equals(action)) {
                restartActivity(getIntent());
                //字体大小发生改变
            } else if (!TextUtils.isEmpty(action) && CHANGE_TYPEFACE_SIZE.equals(action)) {
                restartActivity(getIntent());
            }
        }
    }

}
