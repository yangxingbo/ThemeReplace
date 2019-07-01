package com.chinaiat.bob.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.chinaiat.bob.R;
import com.chinaiat.bob.ThemeReplaceApplication;
import com.chinaiat.bob.db.SPHelper;
import com.chinaiat.themelib.base.BaseActivity;
import com.chinaiat.themelib.domain.EventMsg;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: Bob
 * @date: 2019/5/31
 * @description :设置页面
 */
public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolBar)
    Toolbar toolbar;

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
                finishActivity();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.frame_content, new SettingPreferenceFragment()).commit();
    }

    @Override
    protected void bindButterKnife(BaseActivity baseActivity) {
        ButterKnife.bind(baseActivity);
    }

    public static class SettingPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {
        private int theme;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            findPreference("typeface_change").setOnPreferenceClickListener(this);
            findPreference("change_text_size").setOnPreferenceClickListener(this);
            theme = SPHelper.getTheme();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("theme_change")) {
                int newTheme = sharedPreferences.getInt("theme_change", 19);
                if (newTheme != theme && getActivity() != null) {
                    ThemeReplaceApplication.IS_CHANGE_THEME = true;
                    SettingActivity settingActivity = (SettingActivity) getActivity();
                    settingActivity.restartActivity(settingActivity.getIntent());
                }
            }
        }


        @Override
        public void onResume() {
            super.onResume();
//            getView().setBackgroundColor(getResources().getColor(R.color.menuGray));
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onDestroy() {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onDestroy();
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference.getKey().equals("typeface_change")) {
                startActivity(new Intent(getActivity(), TypefaceChangeActivity.class));
            } else if (preference.getKey().equals("change_text_size")) {
                startActivity(new Intent(getActivity(), TextSizeChangeActivity.class));
            }
            return true;
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
