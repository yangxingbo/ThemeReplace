package com.chinaiat.bob;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.chinaiat.bob.greendao.gen.DaoMaster;
import com.chinaiat.bob.greendao.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * @author: Bob
 * @date: 2019/6/11
 */
public class ThemeReplaceApplication extends Application {

    private static DaoSession daoSession;

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
