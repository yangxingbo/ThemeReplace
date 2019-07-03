package com.chinaiat.themelib.utils;

import android.widget.Toast;

import com.chinaiat.themelib.ThemeLibApplication;

/**
 * @author: Bob
 * @date :2019/6/26 9:40
 */
public class ToastUtil {
    public static void showShortToast(String msg) {
        Toast.makeText(ThemeLibApplication.context, msg, Toast.LENGTH_SHORT).show();
    }
}
