package com.chinaiat.bob;

import android.content.Context;

import com.chinaiat.bob.greendao.gen.DaoMaster;
import com.chinaiat.bob.greendao.gen.DaoSession;
import com.chinaiat.themelib.ThemeLibApplication;

import org.greenrobot.greendao.database.Database;

/**
 * @author: Bob
 * @date: 2019/6/11
 */
public class ThemeReplaceApplication extends ThemeLibApplication {

    private static DaoSession daoSession;
    public static boolean IS_CHANGE_THEME = false;

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    /**
     * 初始化创建数据库
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "chinaiat.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    /**
     * 获取可操作的数据库
     *
     * @return
     */
    public static DaoSession getDaoSession() {
        return daoSession;
    }
}
