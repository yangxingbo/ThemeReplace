package com.chinaiat.themelib.domain;

import java.io.Serializable;

/**
 * @author: Bob
 * @date :2019/6/26 17:44
 * @description 事件传递信息类
 */
public class EventMsg implements Serializable {

    private String action;
    private String msg;
    private Object obj;

    public EventMsg(String action) {
        this.action = action;
    }

    public EventMsg(String action, String msg) {
        this.action = action;
        this.msg = msg;
    }

    public EventMsg(String action, String msg, Object obj) {
        this.action = action;
        this.msg = msg;
        this.obj = obj;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
