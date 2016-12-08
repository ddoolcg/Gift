package com.lcg.gift.bean;

import java.io.Serializable;

/**
 * 兑换与赚取历史
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/11/29 20:26
 */

public class History implements Serializable{
    public static final int TYPE_INCREASE_AD = 0;
    public static final int TYPE_INCREASE_OTHER = 1;
    public static final int TYPE_REDUCE_COST = 2;
    public static final int TYPE_REDUCE_DEPOSITORS = 3;
    public static final int TYPE_REDUCE_OTHER = 4;
    private long id;
    private long uid;
    private int num;
    private int type;
    private String fromName;
    private String fromId;
    private long time;
    public long getId() {
        return id;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
