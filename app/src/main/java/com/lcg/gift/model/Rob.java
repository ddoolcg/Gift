package com.lcg.gift.model;

import android.databinding.Bindable;
import android.util.Base64;
import android.view.View;

import com.lcg.gift.BR;
import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
import com.lcg.gift.bean.SimpleData;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import java.util.HashMap;

/**
 * TODO
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/11/25 19:23
 */

public class Rob extends BaseObservableMe {
    private long id;
    private String username;
    private float points;
    private String nickname;
    private String clickString;
    private String time;
    private int countTime;//倒计时计数器
    private int clickCount;//点击次数
    /**
     * 倒计时
     */
    private Runnable mTimeRunnable = new Runnable() {
        @Override
        public void run() {
            UIUtils.removeCallbacks(this);
            UIUtils.postDelayed(this, 1000);
            countTime++;
            if (countTime < 0) {
                setTime("预备倒计时" + countTime * -1 + "s");
            } else if (countTime >= 10) {//结束联网执行打劫动作
                setTime("打劫中...");
                UIUtils.removeCallbacks(this);
                HashMap<String, String> map = new HashMap<>();
                map.put("num", Base64.encodeToString(String.valueOf(clickCount).getBytes(), Base64.NO_WRAP));
                HttpManager.getInstance().post(HttpUrl.ROB, map, mHandler);
            } else {
                setTime("打劫倒计时" + (10 - countTime) + "s");
            }
        }
    };
    private BaseDataHandler<SimpleData, String> mHandler = new BaseDataHandler<SimpleData, String>() {
        @Override
        public void onNetFinish() {
        }

        @Override
        public void onSuccess(int code, SimpleData data) {
            setTime("打劫获得" + Integer.valueOf(data.getDetail()) / 100f + "元");
        }
    };

    public Rob(BaseActivity activity) {
        super(activity);
        nickname = "";
        points = 0;
        clickString = "预备";
        countTime = -5;
        time = "预备倒计时5s";
        clickCount = 0;
        UIUtils.removeCallbacks(mTimeRunnable);
        UIUtils.postDelayed(mTimeRunnable, 1000);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Bindable
    public String getClickString() {
        return clickString;
    }

    public void setClickString(String clickString) {
        this.clickString = clickString;
        notifyPropertyChanged(BR.clickString);
    }

    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);
    }

    /**
     * 打劫
     */
    public void rob(View v) {
        if (countTime >= 0 && countTime < 10) {
            clickCount++;
            setClickString("打劫" + clickCount + "次");
        }
    }

}
