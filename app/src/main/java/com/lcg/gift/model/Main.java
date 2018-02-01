package com.lcg.gift.model;

import android.content.Intent;
import android.databinding.Bindable;
import android.view.View;

import com.lcg.gift.BR;
import com.lcg.gift.BaseActivity;
import com.lcg.gift.BaseObservableMe;
import com.lcg.gift.MyApplication;
import com.lcg.gift.R;
import com.lcg.gift.activity.CostActivity;
import com.lcg.gift.activity.HistroyActivity;
import com.lcg.gift.activity.RobActivity;
import com.lcg.gift.activity.YMActivity;
import com.lcg.gift.bean.SimpleData;
import com.lcg.gift.bean.UserInfor;
import com.lcg.gift.net.BaseDataHandler;
import com.lcg.gift.net.HttpManager;
import com.lcg.gift.utils.UIUtils;
import com.lcg.gift.value.HttpUrl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import xa.qwe.xz.os.OffersManager;

/**
 * 主页
 *
 * @author lei.chuguang Email:475825657@qq.com
 * @version 1.0
 * @since 2016/11/18 21:14
 */

public class Main extends BaseObservableMe {
    private float points;
    private String otherTip;
    private String titleTip, robTip;
    private UserInfor mUserInfor;
    /**
     * 倒计时
     */
    private Runnable mTimeRunnable = new Runnable() {
        NumberFormat nf;

        @Override
        public void run() {
            if (nf == null) {
                nf = DecimalFormat.getInstance();
                nf.setMinimumIntegerDigits(2);
            }
            UIUtils.removeCallbacks(this);
            UIUtils.postDelayed(this, 1000);
            long robTime = mUserInfor.getRobTime();
            if (robTime > 0) {
                robTime -= 1000;
                mUserInfor.setRobTime(robTime);
                setRobTip("\n" + nf.format(robTime / 1000 / 60) + ":" + nf.format(robTime / 1000 % 60));
            } else {
                setRobTip("");
            }
            long robberyTime = mUserInfor.getRobberyTime();
            if (robberyTime > 0) {
                robberyTime -= 1000;
                mUserInfor.setRobberyTime(robberyTime);
                int h, m, s;
                int time = (int) (robberyTime / 1000);
                s = time % 60;
                time = time / 60;
                m = time % 60;
                h = time / 60;
                setTitleTip(" 保护倒计时：" + nf.format(h) + ":" + nf.format(m) + ":" + nf.format(s));
            } else {
                setTitleTip("");
            }
        }
    };
    /**
     * 更新信息
     */
    private BaseDataHandler<UserInfor, String> mUpdataInforHandler = new BaseDataHandler<UserInfor, String>() {
        @Override
        public void onNetFinish() {
        }

        @Override
        public void onSuccess(int code, UserInfor data) {
            mUserInfor = data;
            UIUtils.removeCallbacks(mTimeRunnable);
            UIUtils.postDelayed(mTimeRunnable, 1000);
            //
//            UIUtils.showToastSafe(JSON.toJSONString(data));
            MyApplication.getInstance().setUserInfor(data);
            setPoints(data.getPoints() / 100f);
        }
    };
    /**
     * 获取打劫人
     */
    private BaseDataHandler<UserInfor, String> mRobHandler = new BaseDataHandler<UserInfor, String>() {

        @Override
        public void onNetFinish() {
            notifyProgressDialogdismiss();
        }

        @Override
        public void onSuccess(int code, UserInfor data) {
            BaseActivity activity = getActivity();
            Intent intent = new Intent(activity, RobActivity.class);
            intent.putExtra("UserInfor", data);
            activity.startActivity(intent);
        }
    };
    /**
     * 历史
     */
    private BaseDataHandler<ArrayList<com.lcg.gift.bean.History>, String> mHistorysHandler = new BaseDataHandler<ArrayList<com.lcg.gift.bean.History>, String>() {

        @Override
        public void onNetFinish() {
            notifyProgressDialogdismiss();
        }

        @Override
        public void onSuccess(int code, ArrayList<com.lcg.gift.bean.History> data) {
            BaseActivity activity = getActivity();
            Intent intent = new Intent(activity, HistroyActivity.class);
            intent.putExtra("Historys", data);
            activity.startActivity(intent);
        }
    };
    /**
     * 分享
     */
    private BaseDataHandler<SimpleData, String> mShareHandler = new BaseDataHandler<SimpleData, String>() {

        @Override
        public void onNetFinish() {
            notifyProgressDialogdismiss();
        }

        @Override
        public void onSuccess(int code, SimpleData data) {
            BaseActivity activity = getActivity();
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            intent.setType("text/plain"); // 分享发送的数据类型
            //文本
            String msg = data.getMsg();
            //短链接
            String detail = data.getDetail();
            intent.putExtra(Intent.EXTRA_SUBJECT, msg + " " + detail); // 分享的内容
            intent.putExtra(Intent.EXTRA_TITLE, msg + " " + detail);
            // 分享的内容
            intent.putExtra(Intent.EXTRA_TEXT, detail); // 分享的内容
            activity.startActivity(Intent.createChooser(intent, activity.getString(R.string.app_name)));// 目标应用选择对话框的标题
        }
    };
    /**
     * 广告类型
     */
    private BaseDataHandler<SimpleData, String> mAdTypeHandler = new BaseDataHandler<SimpleData, String>() {

        @Override
        public void onNetFinish() {
            notifyProgressDialogdismiss();
        }

        @Override
        public void onSuccess(int code, SimpleData data) {
            BaseActivity activity = getActivity();
            if (data.getCode() == 1) {
                startActivity(YMActivity.class);
            } else {
                OffersManager.getInstance(activity).showOffersWall();
            }
        }
    };

    public Main(BaseActivity activity) {
        super(activity);
        otherTip = "";
        titleTip = "";
        robTip = "";
        mUserInfor = MyApplication.getInstance().getUserInfor();
        UIUtils.removeCallbacks(mTimeRunnable);
        UIUtils.postDelayed(mTimeRunnable, 1000);
    }

    /**
     * 更新信息
     */
    public void updataInfor() {
        HttpManager.getInstance().get(HttpUrl.AUTH, mUpdataInforHandler);
    }

    /**
     * 提现
     */
    public void depositors(View v) {
        UIUtils.showToastSafe("开发中");
        //TODO
    }

    /**
     * 历史
     */
    public void histroy(View v) {
        Call call = HttpManager.getInstance().get(HttpUrl.PINTS_HISTORY, mHistorysHandler);
        notifyProgressDialogShow("加载中...", call);
    }

    /**
     * 花费
     */
    public void cost(View v) {
        startActivity(CostActivity.class);
    }

    /**
     * 广告
     */
    public void ad(View v) {
        Call call = HttpManager.getInstance().get(HttpUrl.AD, mAdTypeHandler);
        notifyProgressDialogShow("加载中...", call);
    }

    /**
     * 分享
     */
    public void share(View v) {
        Call call = HttpManager.getInstance().post(HttpUrl.SHARE, new HashMap<String, String>(), mShareHandler);
        notifyProgressDialogShow("加载中...", call);
    }

    /**
     * 打劫
     */
    public void get4Other(View v) {
        if (mUserInfor.getRobTime() <= 0) {
            mUserInfor.setRobTime(600000);
            Call call = HttpManager.getInstance().get(HttpUrl.ROB, mRobHandler);
            notifyProgressDialogShow("加载中...", call);
        } else {
            UIUtils.showToastSafe("请等待倒计时结束...");
        }
    }

    @Bindable
    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
        notifyPropertyChanged(BR.points);
    }

    @Bindable
    public String getOtherTip() {
        return otherTip;
    }

    public void setOtherTip(String otherTip) {
        this.otherTip = otherTip;
        notifyPropertyChanged(BR.otherTip);
    }

    @Bindable
    public String getTitleTip() {
        return titleTip;
    }

    public void setTitleTip(String titleTip) {
        this.titleTip = titleTip;
        notifyPropertyChanged(BR.titleTip);
    }

    @Bindable
    public String getRobTip() {
        return robTip;
    }

    public void setRobTip(String robTip) {
        this.robTip = robTip;
        notifyPropertyChanged(BR.robTip);
    }
}
