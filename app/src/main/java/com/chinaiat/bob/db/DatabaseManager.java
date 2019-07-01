package com.chinaiat.bob.db;

import com.chinaiat.bob.ThemeReplaceApplication;
import com.chinaiat.bob.bean.FruitInfo;
import com.chinaiat.bob.greendao.gen.DaoSession;
import com.chinaiat.bob.greendao.gen.FruitInfoDao;

import java.util.List;

/**
 * @author: Bob
 * @date: 2019/6/11
 * @description :统一数据库管理
 */
public class DatabaseManager {

    private static DatabaseManager instance;
    private FruitInfoDao fruitInfoDao;

    private DatabaseManager() {
        DaoSession daoSession = ThemeReplaceApplication.getDaoSession();
        fruitInfoDao = daoSession.getFruitInfoDao();
    }

    public static DatabaseManager getInstance() {
        if (null == instance) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * 添加水果信息集合
     *
     * @param fruitInfoList
     */
    public void saveFruitList(List<FruitInfo> fruitInfoList) {
        fruitInfoDao.insertInTx(fruitInfoList);
    }

    /**
     * 添加水果信息
     *
     * @param fruitInfo
     */
    public void saveFruit(FruitInfo fruitInfo) {
        fruitInfoDao.insertOrReplace(fruitInfo);
    }

    /**
     * 获取所以的水果信息
     */
    public List<FruitInfo> getAllFruitInfo() {
        return fruitInfoDao.loadAll();
    }

    /**
     * 过滤获取水果信息
     */
    public List<FruitInfo> getAllCollectFruitInfo() {
        return fruitInfoDao.queryBuilder().where(FruitInfoDao.Properties.IsCollect.eq(true)).list();
    }

    /**
     * 删除水果信息
     *
     * @param info
     */
    public void delFruitInfo(FruitInfo info) {
        fruitInfoDao.delete(info);
    }

    /**
     * 更新水果信息
     *
     * @param info
     */
    public void update(FruitInfo info) {
        fruitInfoDao.update(info);
    }

}
