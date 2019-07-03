package com.chinaiat.themelib.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.chinaiat.themelib.R;
import com.chinaiat.themelib.ThemeLibApplication;
import com.chinaiat.themelib.helper.SPHelper;

/**
 * @author: Bob
 * @date :2019/7/3 14:36
 */
public class ThemeAlertDialog extends AlertDialog {

    /**
     * 当前主题索引
     */
    private int mTheme;

    /**
     * 所有的radioButton
     */
    private int[] radioButtons;

    private ThemeChangeListener mListener;

    public ThemeAlertDialog(@NonNull Context context) {
        this(context, R.style.ThemeDialogStyle);
        initView();
    }

    public ThemeAlertDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    public interface ThemeChangeListener {
        void onThemeChange();
    }

    public void setListener(ThemeChangeListener listener) {
        this.mListener = listener;
    }

    private void initView() {
        mTheme = SPHelper.getTheme();
        View view = LayoutInflater.from(ThemeLibApplication.context).inflate(R.layout.dialog_theme_choice, null);
        ((RadioGroup) view.findViewById(R.id.group1)).setOnCheckedChangeListener(mCheckedChangeListener);
        ((RadioGroup) view.findViewById(R.id.group2)).setOnCheckedChangeListener(mCheckedChangeListener);
        ((RadioGroup) view.findViewById(R.id.group3)).setOnCheckedChangeListener(mCheckedChangeListener);
        ((RadioGroup) view.findViewById(R.id.group4)).setOnCheckedChangeListener(mCheckedChangeListener);

        radioButtons = new int[]{R.id.rdobtn_1, R.id.rdobtn_2, R.id.rdobtn_3, R.id.rdobtn_4, R.id.rdobtn_5
                , R.id.rdobtn_6, R.id.rdobtn_7, R.id.rdobtn_8, R.id.rdobtn_9, R.id.rdobtn_10
                , R.id.rdobtn_11, R.id.rdobtn_12, R.id.rdobtn_13, R.id.rdobtn_14, R.id.rdobtn_15
                , R.id.rdobtn_16, R.id.rdobtn_17, R.id.rdobtn_18, R.id.rdobtn_19, R.id.rdobtn_20};
        for (int i = 0; i < ThemeLibApplication.mThemes.length; i++) {
            if (mTheme == i) {
                RadioButton radioButton = view.findViewById(radioButtons[i]);
                radioButton.setChecked(true);
                break;
            }
        }
        this.setTitle(R.string.change_theme);
        this.setView(view);
    }


    RadioGroup.OnCheckedChangeListener mCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (group != null && checkedId != -1) {
                for (int i = 0; i < radioButtons.length; i++) {
                    if (checkedId == radioButtons[i]) {
                        group.check(radioButtons[i]);
                        SPHelper.setTheme(i);
                        cancel();
                        if (null != mListener) {
                            mListener.onThemeChange();
                        }
                    }
                }
            }
        }
    };
}
