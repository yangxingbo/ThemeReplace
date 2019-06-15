package com.chinaiat.bob.db;

import android.content.Context;
import android.content.SharedPreferences;

public final class SPHelper {

    public static final String CHANGE_THEME = "Theme";//日志TAG

    /**
     * 设置主题
     *
     * @param context
     * @return true 表示明亮主题 false 表示安主题
     */
    public static void setTheme(Context context, boolean is) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("theme", is).commit();
    }

    /**
     * 得到主题
     *
     * @param context
     * @return
     */
    public static boolean getTheme(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CHANGE_THEME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("theme", false);
    }

}
