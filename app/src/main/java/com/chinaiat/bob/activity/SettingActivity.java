package com.chinaiat.bob.activity;

import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chinaiat.bob.R;
import com.chinaiat.bob.base.BaseActivity;
import com.chinaiat.bob.db.SPHelper;

import butterknife.BindView;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :设置页面
 */
public class SettingActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.toolBar)
    Toolbar toolbar;

    @BindView(R.id.sc_theme)
    SwitchCompat sc_theme;

    @BindView(R.id.tv_theme_name)
    TextView tv_themeName;

    @BindView(R.id.tv_theme_desc)
    TextView tv_themeDesc;

    private boolean themeIsChange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        themeIsChange = SPHelper.getTheme(mContext);
        sc_theme.setChecked(themeIsChange);
        sc_theme.setOnCheckedChangeListener(this);
        themeMode();
    }

    private void themeMode() {
        if (themeIsChange) {
            tv_themeName.setText("白天模式");
            tv_themeDesc.setText("适合强光下玩手机！");
        } else {
            tv_themeName.setText("夜间模式");
            tv_themeDesc.setText("适合躲在被窝里看！");
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        boolean cc = SPHelper.getTheme(mContext);
//        if (cc == isChecked) {
//            setResult(RESULT_CANCELED);
//        } else {
        setResult(101);
        SPHelper.setTheme(mContext, !cc);
//        }
//        restartActivity();

        finish();
    }
}
