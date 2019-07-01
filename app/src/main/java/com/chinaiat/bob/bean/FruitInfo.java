package com.chinaiat.bob.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: Bob
 * @date: 2019/6/3
 * @description :水果信息
 */
@Entity
public class FruitInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    private Long id;
    private String fruitName;
    private String fruitDescription;
    private int imgResId;
    private boolean isCollect;

    public FruitInfo() {
    }

    public FruitInfo(String fruitName, String fruitDescription, int imgResId) {
        this.fruitName = fruitName;
        this.fruitDescription = fruitDescription;
        this.imgResId = imgResId;
    }

    @Generated(hash = 1651672931)
    public FruitInfo(Long id, String fruitName, String fruitDescription,
            int imgResId, boolean isCollect) {
        this.id = id;
        this.fruitName = fruitName;
        this.fruitDescription = fruitDescription;
        this.imgResId = imgResId;
        this.isCollect = isCollect;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFruitName() {
        return fruitName;
    }

    public void setFruitName(String fruitName) {
        this.fruitName = fruitName;
    }

    public String getFruitDescription() {
        return fruitDescription;
    }

    public void setFruitDescription(String fruitDescription) {
        this.fruitDescription = fruitDescription;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }

    public boolean getIsCollect() {
        return this.isCollect;
    }

    public void setIsCollect(boolean isCollect) {
        this.isCollect = isCollect;
    }
}
