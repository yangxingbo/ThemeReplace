package com.chinaiat.bob.utils;

import android.widget.Toast;

import com.chinaiat.bob.ThemeReplaceApplication;

/**
 * @author: Bob
 * @date :2019/6/26 9:40
 */
public class ToastUtil {
    private ToastUtil() {
    }

    public static void showShortToast(String msg) {
        Toast.makeText(ThemeReplaceApplication.context, msg, Toast.LENGTH_SHORT).show();
    }
}
