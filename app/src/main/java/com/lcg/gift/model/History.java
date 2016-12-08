package com.lcg.gift.model;

import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 兑换与赚取历史
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016-11-22 ����3:10:45
 */
public class History extends BaseObservableMe implements Serializable {
    private String date, name;
    private float num;
    private static DateFormat df = new SimpleDateFormat("yy/MM/dd HH:mm");

    public History(BaseActivity activity, com.lcg.gift.bean.History history) {
        super(activity);
        date = df.format(new Date(history.getTime()));
        num = history.getNum() / 100f;
        switch (history.getType()) {
            case com.lcg.gift.bean.History.TYPE_INCREASE_AD:
                name = "广告收入《" + history.getFromName() + "》";
                break;
            case com.lcg.gift.bean.History.TYPE_INCREASE_OTHER:
                name = "打劫《" + history.getFromName() + "》所得";
                break;
            case com.lcg.gift.bean.History.TYPE_REDUCE_COST:
                name = "兑换《" + history.getFromName() + "》";
                break;
            case com.lcg.gift.bean.History.TYPE_REDUCE_DEPOSITORS:
                name = "提现到《" + history.getFromName() + "》";
                break;
            case com.lcg.gift.bean.History.TYPE_REDUCE_OTHER:
                name = "被《" + history.getFromName() + "》打劫";
                break;
            default:
                name = history.getFromName();
                break;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }
}
