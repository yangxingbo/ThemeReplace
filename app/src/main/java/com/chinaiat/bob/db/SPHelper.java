package com.chinaiat.bob.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.chinaiat.bob.ThemeReplaceApplication;

public final class SPHelper {

    public static final String CHANGE_THEME = "Theme";//日志TAG
    public static final String MONOSPACE = "monospace";//字体标记

    /**
     * 设置主题
     *
     * @return true 表示明亮主题 false 表示安主题
     */
    public static void setTheme(int index) {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt("theme", index).commit();
    }

    /**
     * 得到主题
     *
     * @return
     */
    public static int getTheme() {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("theme", 7);
    }

    /**
     * 设置字体
     *
     * @return true 表示明亮主题 false 表示安主题
     */
    public static void setTypefacePath(String typeface) {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString("typeface", typeface).commit();
    }

    /**
     * 得到字体类型
     *
     * @return
     */
    public static String getTypefacePath() {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getString("typeface", MONOSPACE);
    }

    /**
     * 设置字体大小
     *
     * @return true 表示明亮主题 false 表示安主题
     */
    public static void setTypefaceSize(float typefaceSize) {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putFloat("typefaceSize", typefaceSize).commit();
    }

    /**
     * 得到字体大小尺寸
     *
     * @return
     */
    public static float getTypefaceSize() {
        SharedPreferences sharedPreferences = ThemeReplaceApplication.context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat("typefaceSize", 1);
    }


}
