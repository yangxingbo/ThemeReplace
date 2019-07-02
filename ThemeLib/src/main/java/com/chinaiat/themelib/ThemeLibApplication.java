package com.chinaiat.themelib;

import android.app.Application;
import android.content.Context;

import com.chinaiat.themelib.helper.SPHelper;
import com.chinaiat.themelib.utils.FontUtil;


/**
 * @author: Bob
 * @date: 2019/6/11
 */
public class ThemeLibApplication extends Application {

    public static Context context;
    /**
     * style文件中的所有Theme引用
     */
    public static int[] mThemes = new int[]{
            R.style.Theme1, R.style.Theme2, R.style.Theme3
            , R.style.Theme4, R.style.Theme5, R.style.Theme6
            , R.style.Theme7, R.style.Theme8, R.style.Theme9
            , R.style.Theme10, R.style.Theme11, R.style.Theme12
            , R.style.Theme13, R.style.Theme14, R.style.Theme15
            , R.style.Theme16, R.style.Theme17, R.style.Theme18
            , R.style.Theme19, R.style.Theme21};

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        String typeface = SPHelper.getTypefacePath();
        if (!SPHelper.MONOSPACE.equals(typeface)) {
            //改变系统方式
            FontUtil.getInstance().replaceSystemDefaultFontFromAsset(this, typeface);
        }
    }

}
